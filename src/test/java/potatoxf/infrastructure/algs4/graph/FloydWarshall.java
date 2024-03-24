/******************************************************************************
 *  Compilation:  javac FloydWarshall.java
 *  Execution:    java FloydWarshall V E
 *  Dependencies: AdjMatrixEdgeWeightedDigraph.java
 *
 *  Floyd-Warshall all-pairs shortest path algorithm.
 *
 *  % java FloydWarshall 100 500
 *
 *  Should check for negative cycles during triple loop; otherwise
 *  intermediate numbers can get exponentially large.
 *  Reference: "The Floyd-Warshall algorithm on graphs with negative cycles"
 *  by Stefan Hougardy
 *
 ******************************************************************************/

package potatoxf.infrastructure.algs4.graph;


import potatoxf.infrastructure.algs.graph.Graph;
import potatoxf.infrastructure.algs4.Stack;
import potatoxf.infrastructure.global.Std;
import potatoxf.infrastructure.global.StdRandom;

/**
 * The {@code FloydWarshall} class represents a data type for solving the
 * all-pairs shortest paths problem in edge-weighted digraphs with
 * no negative cycles.
 * The edge weights can be positive, negative, or zero.
 * This class finds either a shortest path between every pair of vertices
 * or a negative cycle.
 * <p>
 * This implementation uses the Floyd-Warshall algorithm.
 * The constructor takes time proportional to <em>V</em><sup>3</sup> in the
 * worst case, where <em>V</em> is the number of vertices.
 * Afterwards, the {@code dist()}, {@code hasPath()}, and {@code hasNegativeCycle()}
 * methods take constant time; the {@code path()} and {@code negativeCycle()}
 * method takes time proportional to the number of edges returned.
 * <p>
 * For additional documentation,
 * see <a href="https://algs4.cs.princeton.edu/44sp">Section 4.4</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class FloydWarshall {
    private final double[][] distTo;         // distTo[v][w] = length of shortest v->w path
    private final Graph.Edge[][] edgeTo;   // edgeTo[v][w] = last edge on shortest v->w path
    private boolean hasNegativeCycle;  // is there a negative cycle?

    /**
     * Computes a shortest paths tree from each vertex to to every other vertex in
     * the edge-weighted digraph {@code G}. If no such shortest path exists for
     * some pair of vertices, it computes a negative cycle.
     *
     * @param G the edge-weighted digraph
     */
    public FloydWarshall(Graph G) {
        int V = G.vertexCount();
        distTo = new double[V][V];
        edgeTo = new Graph.Edge[V][V];

        // initialize distances to infinity
        for (int v = 0; v < V; v++) {
            for (int w = 0; w < V; w++) {
                distTo[v][w] = Double.POSITIVE_INFINITY;
            }
        }

        // initialize distances using edge-weighted digraph's
        for (int v = 0; v < G.vertexCount(); v++) {
            for (Graph.Edge e : G.adjacencyEdge(v)) {
                distTo[e.v()][e.w()] = e.weight();
                edgeTo[e.v()][e.w()] = e;
            }
            // in case of self-loops
            if (distTo[v][v] >= 0.0) {
                distTo[v][v] = 0.0;
                edgeTo[v][v] = null;
            }
        }

        // Floyd-Warshall updates
        for (int i = 0; i < V; i++) {
            // compute shortest paths using only 0, 1, ..., i as intermediate vertices
            for (int v = 0; v < V; v++) {
                if (edgeTo[v][i] == null) continue;  // optimization
                for (int w = 0; w < V; w++) {
                    if (distTo[v][w] > distTo[v][i] + distTo[i][w]) {
                        distTo[v][w] = distTo[v][i] + distTo[i][w];
                        edgeTo[v][w] = edgeTo[i][w];
                    }
                }
                // check for negative cycle
                if (distTo[v][v] < 0.0) {
                    hasNegativeCycle = true;
                    return;
                }
            }
        }
        assert check(G);
    }

    /**
     * Unit tests the {@code FloydWarshall} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        // random graph with V vertices and E edges, parallel edges allowed
        int V = Integer.parseInt(args[0]);
        int E = Integer.parseInt(args[1]);
        Graph G = new Graph(V, true);
        for (int i = 0; i < E; i++) {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            double weight = Math.round(100 * (StdRandom.uniform() - 0.15)) / 100.0;
            if (v == w) G.addEdge(new Graph.Edge(v, w, Math.abs(weight)));
            else G.addEdge(new Graph.Edge(v, w, weight));
        }

        Std.println(G);

        // run Floyd-Warshall algorithm
        FloydWarshall spt = new FloydWarshall(G);

        // print all-pairs shortest path distances
        Std.printf("  ");
        for (int v = 0; v < G.vertexCount(); v++) {
            Std.printf("%6d ", v);
        }
        Std.println();
        for (int v = 0; v < G.vertexCount(); v++) {
            Std.printf("%3d: ", v);
            for (int w = 0; w < G.vertexCount(); w++) {
                if (spt.hasPath(v, w)) Std.printf("%6.2f ", spt.dist(v, w));
                else Std.printf("  Inf ");
            }
            Std.println();
        }

        // print negative cycle
        if (spt.hasNegativeCycle()) {
            Std.println("Negative cost cycle:");
            for (Graph.Edge e : spt.negativeCycle())
                Std.println(e);
            Std.println();
        }

        // print all-pairs shortest paths
        else {
            for (int v = 0; v < G.vertexCount(); v++) {
                for (int w = 0; w < G.vertexCount(); w++) {
                    if (spt.hasPath(v, w)) {
                        Std.printf("%d to %d (%5.2f)  ", v, w, spt.dist(v, w));
                        for (Graph.Edge e : spt.path(v, w))
                            Std.print(e + "  ");
                        Std.println();
                    } else {
                        Std.printf("%d to %d no path\n", v, w);
                    }
                }
            }
        }

    }

    /**
     * Is there a negative cycle?
     *
     * @return {@code true} if there is a negative cycle, and {@code false} otherwise
     */
    public boolean hasNegativeCycle() {
        return hasNegativeCycle;
    }

    /**
     * Returns a negative cycle, or {@code null} if there is no such cycle.
     *
     * @return a negative cycle as an iterable of edges,
     * or {@code null} if there is no such cycle
     */
    public Iterable<Graph.Edge> negativeCycle() {
        for (int v = 0; v < distTo.length; v++) {
            // negative cycle in v's predecessor graph
            if (distTo[v][v] < 0.0) {
                int V = edgeTo.length;
                Graph spt = new Graph(V, true);
                for (int w = 0; w < V; w++)
                    if (edgeTo[v][w] != null)
                        spt.addEdge(edgeTo[v][w]);
                EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(spt);
                assert finder.hasCycle();
                return finder.cycle();
            }
        }
        return null;
    }

    /**
     * Is there a path from the vertex {@code s} to vertex {@code t}?
     *
     * @param s the source vertex
     * @param t the destination vertex
     * @return {@code true} if there is a path from vertex {@code s}
     * to vertex {@code t}, and {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     * @throws IllegalArgumentException unless {@code 0 <= t < V}
     */
    public boolean hasPath(int s, int t) {
        validateVertex(s);
        validateVertex(t);
        return distTo[s][t] < Double.POSITIVE_INFINITY;
    }

    /**
     * Returns the length of a shortest path from vertex {@code s} to vertex {@code t}.
     *
     * @param s the source vertex
     * @param t the destination vertex
     * @return the length of a shortest path from vertex {@code s} to vertex {@code t};
     * {@code Double.POSITIVE_INFINITY} if no such path
     * @throws UnsupportedOperationException if there is a negative cost cycle
     * @throws IllegalArgumentException      unless {@code 0 <= v < V}
     */
    public double dist(int s, int t) {
        validateVertex(s);
        validateVertex(t);
        if (hasNegativeCycle())
            throw new UnsupportedOperationException("Negative cost cycle exists");
        return distTo[s][t];
    }

    /**
     * Returns a shortest path from vertex {@code s} to vertex {@code t}.
     *
     * @param s the source vertex
     * @param t the destination vertex
     * @return a shortest path from vertex {@code s} to vertex {@code t}
     * as an iterable of edges, and {@code null} if no such path
     * @throws UnsupportedOperationException if there is a negative cost cycle
     * @throws IllegalArgumentException      unless {@code 0 <= v < V}
     */
    public Iterable<Graph.Edge> path(int s, int t) {
        validateVertex(s);
        validateVertex(t);
        if (hasNegativeCycle())
            throw new UnsupportedOperationException("Negative cost cycle exists");
        if (!hasPath(s, t)) return null;
        Stack<Graph.Edge> path = new Stack<Graph.Edge>();
        for (Graph.Edge e = edgeTo[s][t]; e != null; e = edgeTo[s][e.v()]) {
            path.push(e);
        }
        return path;
    }

    // check optimality conditions
    private boolean check(Graph G) {

        // no negative cycle
        if (!hasNegativeCycle()) {
            for (int v = 0; v < G.vertexCount(); v++) {
                for (Graph.Edge e : G.adjacencyEdge(v)) {
                    int w = e.w();
                    for (int i = 0; i < G.vertexCount(); i++) {
                        if (distTo[i][w] > distTo[i][v] + e.weight()) {
                            System.err.println("edge " + e + " is eligible");
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

}

