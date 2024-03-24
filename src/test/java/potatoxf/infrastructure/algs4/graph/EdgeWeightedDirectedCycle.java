/******************************************************************************
 *  Compilation:  javac EdgeWeightedDirectedCycle.java
 *  Execution:    java EdgeWeightedDirectedCycle V E F
 *  Dependencies: Graph.java Edge.java Stack.java
 *
 *  Finds a directed cycle in an edge-weighted digraph.
 *  Runs in O(E + V) time.
 *
 *
 ******************************************************************************/

package potatoxf.infrastructure.algs4.graph;

import potatoxf.infrastructure.algs.graph.Graph;
import potatoxf.infrastructure.algs4.Stack;
import potatoxf.infrastructure.global.Std;
import potatoxf.infrastructure.global.StdRandom;

/**
 * The {@code EdgeWeightedDirectedCycle} class represents a data type for
 * determining whether an edge-weighted digraph has a directed cycle.
 * The <em>hasCycle</em> operation determines whether the edge-weighted
 * digraph has a directed cycle and, if so, the <em>cycle</em> operation
 * returns one.
 * <p>
 * This implementation uses depth-first search.
 * The constructor takes time proportional to <em>V</em> + <em>E</em>
 * (in the worst case),
 * where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 * Afterwards, the <em>hasCycle</em> operation takes constant time;
 * the <em>cycle</em> operation takes time proportional
 * to the length of the cycle.
 * <p>
 * See {@link Topological} to compute a topological order if the edge-weighted
 * digraph is acyclic.
 * <p>
 * For additional documentation,
 * see <a href="https://algs4.cs.princeton.edu/44sp">Section 4.4</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class EdgeWeightedDirectedCycle {
    private final boolean[] marked;             // marked[v] = has vertex v been marked?
    private final Graph.Edge[] edgeTo;        // edgeTo[v] = previous edge on path to v
    private final boolean[] onStack;            // onStack[v] = is vertex on the stack?
    private Stack<Graph.Edge> cycle;    // directed cycle (or null if no such cycle)

    /**
     * Determines whether the edge-weighted digraph {@code G} has a directed cycle and,
     * if so, finds such a cycle.
     *
     * @param G the edge-weighted digraph
     */
    public EdgeWeightedDirectedCycle(Graph G) {
        marked = new boolean[G.vertexCount()];
        onStack = new boolean[G.vertexCount()];
        edgeTo = new Graph.Edge[G.vertexCount()];
        for (int v = 0; v < G.vertexCount(); v++)
            if (!marked[v]) dfs(G, v);

        // check that digraph has a cycle
        assert check();
    }

    /**
     * Unit tests the {@code EdgeWeightedDirectedCycle} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        // create random DAG with V vertices and E edges; then add F random edges
        int V = Integer.parseInt(args[0]);
        int E = Integer.parseInt(args[1]);
        int F = Integer.parseInt(args[2]);
        Graph G = new Graph(V, true);
        int[] vertices = new int[V];
        for (int i = 0; i < V; i++)
            vertices[i] = i;
        StdRandom.shuffle(vertices);
        for (int i = 0; i < E; i++) {
            int v, w;
            do {
                v = StdRandom.uniform(V);
                w = StdRandom.uniform(V);
            } while (v >= w);
            double weight = StdRandom.uniform();
            G.addEdge(new Graph.Edge(v, w, weight));
        }

        // add F extra edges
        for (int i = 0; i < F; i++) {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            double weight = StdRandom.uniform(0.0, 1.0);
            G.addEdge(new Graph.Edge(v, w, weight));
        }

        Std.println(G);

        // find a directed cycle
        EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(G);
        if (finder.hasCycle()) {
            Std.print("Cycle: ");
            for (Graph.Edge e : finder.cycle()) {
                Std.print(e + " ");
            }
            Std.println();
        }

        // or give topologial sort
        else {
            Std.println("No directed cycle");
        }
    }

    // check that algorithm computes either the topological order or finds a directed cycle
    private void dfs(Graph G, int v) {
        onStack[v] = true;
        marked[v] = true;
        for (Graph.Edge e : G.adjacencyEdge(v)) {
            int w = e.w();

            // short circuit if directed cycle found
            if (cycle != null) return;

                // found new vertex, so recur
            else if (!marked[w]) {
                edgeTo[w] = e;
                dfs(G, w);
            }

            // trace back directed cycle
            else if (onStack[w]) {
                cycle = new Stack<Graph.Edge>();

                Graph.Edge f = e;
                while (f.v() != w) {
                    cycle.push(f);
                    f = edgeTo[f.v()];
                }
                cycle.push(f);

                return;
            }
        }

        onStack[v] = false;
    }

    /**
     * Does the edge-weighted digraph have a directed cycle?
     *
     * @return {@code true} if the edge-weighted digraph has a directed cycle,
     * {@code false} otherwise
     */
    public boolean hasCycle() {
        return cycle != null;
    }

    /**
     * Returns a directed cycle if the edge-weighted digraph has a directed cycle,
     * and {@code null} otherwise.
     *
     * @return a directed cycle (as an iterable) if the edge-weighted digraph
     * has a directed cycle, and {@code null} otherwise
     */
    public Iterable<Graph.Edge> cycle() {
        return cycle;
    }

    // certify that digraph is either acyclic or has a directed cycle
    private boolean check() {

        // edge-weighted digraph is cyclic
        if (hasCycle()) {
            // verify cycle
            Graph.Edge first = null, last = null;
            for (Graph.Edge e : cycle()) {
                if (first == null) first = e;
                if (last != null) {
                    if (last.w() != e.v()) {
                        System.err.printf("cycle edges %s and %s not incident\n", last, e);
                        return false;
                    }
                }
                last = e;
            }

            if (last.w() != first.v()) {
                System.err.printf("cycle edges %s and %s not incident\n", last, first);
                return false;
            }
        }


        return true;
    }

}

