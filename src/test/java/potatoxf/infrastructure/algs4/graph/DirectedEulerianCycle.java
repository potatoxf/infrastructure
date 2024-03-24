/******************************************************************************
 *  Compilation:  javac DirectedEulerianCycle.java
 *  Execution:    java DirectedEulerianCycle V E
 *  Dependencies: Graph.java Stack.java StdOut.java
 *                BreadthFirstPaths.java
 *                DigraphGenerator.java StdRandom.java
 *
 *  Find an Eulerian cycle in a digraph, if one exists.
 *
 ******************************************************************************/

package potatoxf.infrastructure.algs4.graph;

import potatoxf.infrastructure.algs.graph.Graph;
import potatoxf.infrastructure.algs4.Stack;
import potatoxf.infrastructure.global.In;
import potatoxf.infrastructure.global.Std;
import potatoxf.infrastructure.global.StdRandom;

import java.util.Iterator;

/**
 * The {@code DirectedEulerianCycle} class represents a data type
 * for finding an Eulerian cycle or path in a digraph.
 * An <em>Eulerian cycle</em> is a cycle (not necessarily simple) that
 * uses every edge in the digraph exactly once.
 * <p>
 * This implementation uses a nonrecursive depth-first search.
 * The constructor runs in O(<Em>E</em> + <em>V</em>) time,
 * and uses O(<em>V</em>) extra space, where <em>E</em> is the
 * number of edges and <em>V</em> the number of vertices
 * All other methods take O(1) time.
 * <p>
 * To compute Eulerian paths in digraphs, see {@link DirectedEulerianPath}.
 * To compute Eulerian cycles and paths in undirected graphs, see
 * {@link EulerianCycle} and {@link EulerianPath}.
 * <p>
 * For additional documentation,
 * see <a href="https://algs4.cs.princeton.edu/42digraph">Section 4.2</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 * @author Nate Liu
 */
public class DirectedEulerianCycle {
    private Stack<Integer> cycle = null;  // Eulerian cycle; null if no such cylce

    /**
     * Computes an Eulerian cycle in the specified digraph, if one exists.
     *
     * @param G the digraph
     */
    public DirectedEulerianCycle(Graph G) {

        // must have at least one edge
        if (G.edgeCount() == 0) return;

        // necessary condition: indegree(v) = outdegree(v) for each vertex v
        // (without this check, DFS might return a path instead of a cycle)
        for (int v = 0; v < G.vertexCount(); v++)
            if (G.outDegree(v) != G.inDegree(v))
                return;

        // create local view of adjacency lists, to iterate one vertex at a time
        Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[G.vertexCount()];
        for (int v = 0; v < G.vertexCount(); v++)
            adj[v] = G.adjacencyVertexIterator(v);

        // initialize stack with any non-isolated vertex
        int s = nonIsolatedVertex(G);
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(s);

        // greedily add to putative cycle, depth-first search style
        cycle = new Stack<Integer>();
        while (!stack.isEmpty()) {
            int v = stack.pop();
            while (adj[v].hasNext()) {
                stack.push(v);
                v = adj[v].next();
            }
            // add vertex with no more leaving edges to cycle
            cycle.push(v);
        }

        // check if all edges have been used
        // (in case there are two or more vertex-disjoint Eulerian cycles)
        if (cycle.size() != G.edgeCount() + 1)
            cycle = null;

        assert certifySolution(G);
    }

    // returns any non-isolated vertex; -1 if no such vertex
    private static int nonIsolatedVertex(Graph G) {
        for (int v = 0; v < G.vertexCount(); v++)
            if (G.outDegree(v) > 0)
                return v;
        return -1;
    }

    /**************************************************************************
     *
     *  The code below is solely for testing correctness of the data type.
     *
     **************************************************************************/

    // Determines whether a digraph has an Eulerian cycle using necessary
    // and sufficient conditions (without computing the cycle itself):
    //    - at least one edge
    //    - indegree(v) = outdegree(v) for every vertex
    //    - the graph is connected, when viewed as an undirected graph
    //      (ignoring isolated vertices)
    private static boolean satisfiesNecessaryAndSufficientConditions(Graph G) {

        // Condition 0: at least 1 edge
        if (G.edgeCount() == 0) return false;

        // Condition 1: indegree(v) == outdegree(v) for every vertex
        for (int v = 0; v < G.vertexCount(); v++)
            if (G.outDegree(v) != G.inDegree(v))
                return false;

        // Condition 2: graph is connected, ignoring isolated vertices
        Graph H = new Graph(G.vertexCount(), false);
        for (int v = 0; v < G.vertexCount(); v++)
            for (int w : G.adjacencyVertex(v))
                H.addEdge(v, w);

        // check that all non-isolated vertices are conneted
        int s = nonIsolatedVertex(G);
        BreadthFirstPaths bfs = new BreadthFirstPaths(H, s);
        for (int v = 0; v < G.vertexCount(); v++)
            if (H.degree(v) > 0 && !bfs.hasPathTo(v))
                return false;

        return true;
    }

    private static void unitTest(Graph G, String description) {
        Std.println(description);
        Std.println("-------------------------------------");
        Std.print(G);

        DirectedEulerianCycle euler = new DirectedEulerianCycle(G);

        Std.print("Eulerian cycle: ");
        if (euler.hasEulerianCycle()) {
            for (int v : euler.cycle()) {
                Std.print(v + " ");
            }
            Std.println();
        } else {
            Std.println("none");
        }
        Std.println();
    }

    /**
     * Unit tests the {@code DirectedEulerianCycle} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        int V = Integer.parseInt(args[0]);
        int E = Integer.parseInt(args[1]);

        // Eulerian cycle
        Graph G1 = DigraphGenerator.eulerianCycle(V, E);
        unitTest(G1, "Eulerian cycle");

        // Eulerian path
        Graph G2 = DigraphGenerator.eulerianPath(V, E);
        unitTest(G2, "Eulerian path");

        // empty digraph
        Graph G3 = new Graph(V, true);
        unitTest(G3, "empty digraph");

        // self loop
        Graph G4 = new Graph(V, true);
        int v4 = StdRandom.uniform(V);
        G4.addEdge(v4, v4);
        unitTest(G4, "single self loop");

        // union of two disjoint cycles
        Graph H1 = DigraphGenerator.eulerianCycle(V / 2, E / 2);
        Graph H2 = DigraphGenerator.eulerianCycle(V - V / 2, E - E / 2);
        int[] perm = new int[V];
        for (int i = 0; i < V; i++)
            perm[i] = i;
        StdRandom.shuffle(perm);
        Graph G5 = new Graph(V, true);
        for (int v = 0; v < H1.vertexCount(); v++)
            for (int w : H1.adjacencyVertex(v))
                G5.addEdge(perm[v], perm[w]);
        for (int v = 0; v < H2.vertexCount(); v++)
            for (int w : H2.adjacencyVertex(v))
                G5.addEdge(perm[V / 2 + v], perm[V / 2 + w]);
        unitTest(G5, "Union of two disjoint cycles");

        // random digraph
        Graph G6 = DigraphGenerator.simple(V, E);
        unitTest(G6, "simple digraph");

        // 4-vertex digraph
        Graph G7 = GraphParser.parse(new In("eulerianD.txt"), true);
        unitTest(G7, "4-vertex Eulerian digraph");
    }

    /**
     * Returns the sequence of vertices on an Eulerian cycle.
     *
     * @return the sequence of vertices on an Eulerian cycle;
     * {@code null} if no such cycle
     */
    public Iterable<Integer> cycle() {
        return cycle;
    }

    /**
     * Returns true if the digraph has an Eulerian cycle.
     *
     * @return {@code true} if the digraph has an Eulerian cycle;
     * {@code false} otherwise
     */
    public boolean hasEulerianCycle() {
        return cycle != null;
    }

    // check that solution is correct
    private boolean certifySolution(Graph G) {

        // internal consistency check
        if (hasEulerianCycle() == (cycle() == null)) return false;

        // hashEulerianCycle() returns correct value
        if (hasEulerianCycle() != satisfiesNecessaryAndSufficientConditions(G)) return false;

        // nothing else to check if no Eulerian cycle
        if (cycle == null) return true;

        // check that cycle() uses correct number of edges
        return cycle.size() == G.edgeCount() + 1;

        // check that cycle() is a directed cycle of G
        // TODO
    }

}

