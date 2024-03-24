/******************************************************************************
 *  Compilation:  javac GlobalMincut.java
 *  Execution:    java  GlobalMincut filename.txt
 *  Dependencies: EdgeWeightedGraph.java Edge.java UF.java 
 *                IndexMaxPQ.java FlowNetwork.java FlowEdge.java 
 *                FordFulkerson.java In.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/43mst/tinyEWG.txt
 *                https://algs4.cs.princeton.edu/43mst/mediumEWG.txt
 *
 *  Computes a minimum cut using Stoer-Wagner's algorithm.
 *
 *  % java GlobalMincut tinyEWG.txt 
 *    Min cut: 5 
 *    Min cut weight = 0.9500000000000001
 *
 *  % java GlobalMincut mediumEWG.txt 
 *    Min cut: 25 60 63 96 199 237 
 *    Min cut weight = 0.14021
 *
 ******************************************************************************/

package potatoxf.infrastructure.algs4.graph;


import potatoxf.infrastructure.algs.graph.Graph;
import potatoxf.infrastructure.algs4.*;
import potatoxf.infrastructure.global.In;
import potatoxf.infrastructure.global.Std;

/**
 * The {@code GlobalMincut} class represents a data type for computing a
 * <em>global minimum cut</em> in an edge-weighted graph where the edge
 * weights are nonnegative. A <em>cut</em> is a partition of the set
 * of vertices of a graph into two nonempty subsets. An edge that has one
 * endpoint in each subset of a cut is a <em>crossing edge</em>. The weight
 * of a cut is the sum of the weights of its crossing edges.
 * A <em>global minimum cut</em> is a cut for which the weight is not
 * larger than the weight of any other cut.
 * <p>
 * The {@code weight()} method returns the weight of the minimum cut and the
 * {@code cut(int v)} method determines if a vertex {@code v} is on the first or
 * on the second subset of vertices of the minimum cut.
 * <p>
 * This is an implementation of <em>Stoer–Wagner's algorithm</em> using an index
 * priority queue and the union-find data type in order to simplify dealing with
 * contracting edges. Precisely, the index priority queue is an instance of
 * {@link IndexMaxPQ} which is based on a binary heap. As a consequence, the
 * constructor takes <em>O</em>(<em>V</em> (<em>V</em> + <em> E</em> ) log <em>
 * V </em>) time and <em>O</em>(<em>V</em>) extra space (not including the
 * graph), where <em>V</em> is the number of vertices and <em>E</em> is the
 * number of edges. However, this time can be reduced to <em>O</em>(<em>V E</em>
 * + <em> V<sup>2</sup></em> log <em>V</em>) by using an index priority queue
 * implemented using Fibonacci heaps.
 * <p>
 * Afterwards, the {@code weight()} and {@code cut(int v)} methods take constant
 * time.
 * <p>
 * For additional documentation, see
 * <ul>
 * <li>M. Stoer and F. Wagner (1997). A simple min-cut algorithm. <em>Journal of
 * the ACM </em>, 44(4):585-591.
 * </ul>
 *
 * @author Marcelo Silva
 */
public class GlobalMincut {
    private static final double FLOATING_POINT_EPSILON = 1E-11;
    // number of vertices
    private final int V;
    // the weight of the minimum cut
    private double weight = Double.POSITIVE_INFINITY;
    // cut[v] = true if v is on the first subset of vertices of the minimum cut;
    // or false if v is on the second subset
    private boolean[] cut;

    /**
     * Computes a minimum cut of an edge-weighted graph.
     *
     * @param G the edge-weighted graph
     * @throws IllegalArgumentException if the number of vertices of {@code G}
     *                                  is less than {@code 2} or if anny edge weight is negative
     */
    public GlobalMincut(Graph G) {
        V = G.vertexCount();
        validate(G);
        minCut(G, 0);
        assert check(G);
    }

    /**
     * Unit tests the {@code GlobalMincut} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = GraphParser.parse(in, false);
        GlobalMincut mc = new GlobalMincut(G);
        Std.print("Min cut: ");
        for (int v = 0; v < G.vertexCount(); v++) {
            if (mc.cut(v)) Std.print(v + " ");
        }
        Std.println();
        Std.println("Min cut weight = " + mc.weight());
    }

    /**
     * Validates the edge-weighted graph.
     *
     * @param G the edge-weighted graph
     * @throws IllegalArgumentException if the number of vertices of {@code G}
     *                                  is less than {@code 2} or if any edge weight is negative
     */
    private void validate(Graph G) {
        if (G.vertexCount() < 2) throw new IllegalArgumentException("number of vertices of G is less than 2");
        for (Graph.Edge e : G.edges()) {
            if (e.weight() < 0) throw new IllegalArgumentException("edge " + e + " has negative weight");
        }
    }

    /**
     * Returns the weight of the minimum cut.
     *
     * @return the weight of the minimum cut
     */
    public double weight() {
        return weight;
    }

    /**
     * Returns {@code true} if the vertex {@code v} is on the first subset of
     * vertices of the minimum cut; or {@code false} if the vertex {@code v} is
     * on the second subset.
     *
     * @param v the vertex to check
     * @return {@code true} if the vertex {@code v} is on the first subset of
     * vertices of the minimum cut; or {@code false} if the vertex
     * {@code v} is on the second subset.
     * @throws IllegalArgumentException unless vertex {@code v} is between
     *                                  {@code 0} and {@code (G.V() - 1)}
     */
    public boolean cut(int v) {
        validateVertex(v);
        return cut[v];
    }

    /**
     * Makes a cut for the current edge-weighted graph by partitioning its set
     * of vertices into two nonempty subsets. The vertices connected to the
     * vertex {@code t} belong to the first subset. Other vertices not connected
     * to {@code t} belong to the second subset.
     *
     * @param t  the vertex {@code t}
     * @param uf the union-find data type
     */
    private void makeCut(int t, UF uf) {
        for (int v = 0; v < cut.length; v++) {
            cut[v] = uf.connected(v, t);
        }
    }

    /**
     * Computes a minimum cut of the edge-weighted graph. Precisely, it computes
     * the lightest of the cuts-of-the-phase which yields the desired minimum
     * cut.
     *
     * @param G the edge-weighted graph
     * @param a the starting vertex
     */
    private void minCut(Graph G, int a) {
        UF uf = new UF(G.vertexCount());
        boolean[] marked = new boolean[G.vertexCount()];
        cut = new boolean[G.vertexCount()];
        CutPhase cp = new CutPhase(0.0, a, a);
        for (int v = G.vertexCount(); v > 1; v--) {
            cp = minCutPhase(G, marked, cp);
            if (cp.weight < weight) {
                weight = cp.weight;
                makeCut(cp.t, uf);
            }
            G = contractEdge(G, cp.s, cp.t);
            marked[cp.t] = true;
            uf.union(cp.s, cp.t);
        }
    }

    /**
     * Returns the cut-of-the-phase. The cut-of-the-phase is a minimum s-t-cut
     * in the current graph, where {@code s} and {@code t} are the two vertices
     * added last in the phase. This algorithm is known in the literature as
     * <em>maximum adjacency search</em> or <em>maximum cardinality search</em>.
     *
     * @param G      the edge-weighted graph
     * @param marked the array of contracted vertices, where {@code marked[v]}
     *               is {@code true} if the vertex {@code v} was already
     *               contracted; or {@code false} otherwise
     * @param cp     the previous cut-of-the-phase
     * @return the cut-of-the-phase
     */
    private CutPhase minCutPhase(Graph G, boolean[] marked, CutPhase cp) {
        IndexMaxPQ<Double> pq = new IndexMaxPQ<Double>(G.vertexCount());
        for (int v = 0; v < G.vertexCount(); v++) {
            if (v != cp.s && !marked[v]) pq.insert(v, 0.0);
        }
        pq.insert(cp.s, Double.POSITIVE_INFINITY);
        while (!pq.isEmpty()) {
            int v = pq.delMax();
            cp.s = cp.t;
            cp.t = v;
            for (Graph.Edge e : G.adjacencyEdge(v)) {
                int w = e.other(v);
                if (pq.contains(w)) pq.increaseKey(w, pq.keyOf(w) + e.weight());
            }
        }
        cp.weight = 0.0;
        for (Graph.Edge e : G.adjacencyEdge(cp.t)) {
            cp.weight += e.weight();
        }
        return cp;
    }

    /**
     * Contracts the edges incidents on the vertices {@code s} and {@code t} of
     * the given edge-weighted graph.
     *
     * @param G the edge-weighted graph
     * @param s the vertex {@code s}
     * @param t the vertex {@code t}
     * @return a new edge-weighted graph for which the edges incidents on the
     * vertices {@code s} and {@code t} were contracted
     */
    private Graph contractEdge(Graph G, int s, int t) {
        Graph H = new Graph(G.vertexCount(), false);
        for (int v = 0; v < G.vertexCount(); v++) {
            for (Graph.Edge e : G.adjacencyEdge(v)) {
                int w = e.other(v);
                if (v == s && w == t || v == t && w == s) continue;
                if (v < w) {
                    if (w == t) H.addEdge(new Graph.Edge(v, s, e.weight()));
                    else if (v == t) H.addEdge(new Graph.Edge(w, s, e.weight()));
                    else H.addEdge(new Graph.Edge(v, w, e.weight()));
                }
            }
        }
        return H;
    }

    /**
     * Checks optimality conditions.
     *
     * @param G the edge-weighted graph
     * @return {@code true} if optimality conditions are fine
     */
    private boolean check(Graph G) {

        // compute min st-cut for all pairs s and t
        // shortcut: s must appear on one side of global mincut,
        // so it suffices to try all pairs s-v for some fixed s
        double value = Double.POSITIVE_INFINITY;
        for (int s = 0, t = 1; t < G.vertexCount(); t++) {
            FlowNetwork F = new FlowNetwork(G.vertexCount());
            for (Graph.Edge e : G.edges()) {
                int v = e.v(), w = e.other(v);
                F.addEdge(new FlowEdge(v, w, e.weight()));
                F.addEdge(new FlowEdge(w, v, e.weight()));
            }
            FordFulkerson maxflow = new FordFulkerson(F, s, t);
            value = Math.min(value, maxflow.value());
        }
        if (Math.abs(weight - value) > FLOATING_POINT_EPSILON) {
            System.err.println("Min cut weight = " + weight + " , max flow value = " + value);
            return false;
        }
        return true;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    /**
     * This helper class represents the <em>cut-of-the-phase</em>. The
     * cut-of-the-phase is a <em>minimum s-t-cut</em> in the current graph,
     * where {@code s} and {@code t} are the two vertices added last in the
     * phase.
     */
    private class CutPhase {
        private double weight; // the weight of the minimum s-t cut
        private int s;         // the vertex s
        private int t;         // the vertex t

        public CutPhase(double weight, int s, int t) {
            this.weight = weight;
            this.s = s;
            this.t = t;
        }
    }
}

