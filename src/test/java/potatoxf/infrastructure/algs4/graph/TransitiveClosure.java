/******************************************************************************
 *  Compilation:  javac TransitiveClosure.java
 *  Execution:    java TransitiveClosure filename.txt
 *  Dependencies: Graph.java DepthFirstDirectedPaths.java In.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/42digraph/tinyDG.txt
 *
 *  Compute transitive closure of a digraph and support
 *  reachability queries.
 *
 *  Preprocessing time: O(V(E + V)) time.
 *  Query time: O(1).
 *  Space: O(V^2).
 *
 *  % java TransitiveClosure tinyDG.txt
 *         0  1  2  3  4  5  6  7  8  9 10 11 12
 *  --------------------------------------------
 *    0:   T  T  T  T  T  T                     
 *    1:      T                                 
 *    2:   T  T  T  T  T  T                     
 *    3:   T  T  T  T  T  T                     
 *    4:   T  T  T  T  T  T                     
 *    5:   T  T  T  T  T  T                     
 *    6:   T  T  T  T  T  T  T        T  T  T  T
 *    7:   T  T  T  T  T  T  T  T  T  T  T  T  T
 *    8:   T  T  T  T  T  T  T  T  T  T  T  T  T
 *    9:   T  T  T  T  T  T           T  T  T  T
 *   10:   T  T  T  T  T  T           T  T  T  T
 *   11:   T  T  T  T  T  T           T  T  T  T
 *   12:   T  T  T  T  T  T           T  T  T  T
 *
 ******************************************************************************/

package potatoxf.infrastructure.algs4.graph;

import potatoxf.infrastructure.algs.graph.Graph;
import potatoxf.infrastructure.global.In;
import potatoxf.infrastructure.global.Std;

/**
 * The {@code TransitiveClosure} class represents a data type for
 * computing the transitive closure of a digraph.
 * <p>
 * This implementation runs depth-first search from each vertex.
 * The constructor takes time proportional to <em>V</em>(<em>V</em> + <em>E</em>)
 * (in the worst case) and uses space proportional to <em>V</em><sup>2</sup>,
 * where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 * <p>
 * For large digraphs, you may want to consider a more sophisticated algorithm.
 * <a href = "http://www.cs.hut.fi/~enu/thesis.html">Nuutila</a> proposes two
 * algorithm for the problem (based on strong components and an interval representation)
 * that runs in <em>E</em> + <em>V</em> time on typical digraphs.
 * <p>
 * For additional documentation,
 * see <a href="https://algs4.cs.princeton.edu/42digraph">Section 4.2</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class TransitiveClosure {
    private final DirectedDFS[] tc;  // tc[v] = reachable from v

    /**
     * Computes the transitive closure of the digraph {@code G}.
     *
     * @param G the digraph
     */
    public TransitiveClosure(Graph G) {
        tc = new DirectedDFS[G.vertexCount()];
        for (int v = 0; v < G.vertexCount(); v++)
            tc[v] = new DirectedDFS(G, v);
    }

    /**
     * Unit tests the {@code TransitiveClosure} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = GraphParser.parse(in, true);

        TransitiveClosure tc = new TransitiveClosure(G);

        // print header
        Std.print("     ");
        for (int v = 0; v < G.vertexCount(); v++)
            Std.printf("%3d", v);
        Std.println();
        Std.println("--------------------------------------------");

        // print transitive closure
        for (int v = 0; v < G.vertexCount(); v++) {
            Std.printf("%3d: ", v);
            for (int w = 0; w < G.vertexCount(); w++) {
                if (tc.reachable(v, w)) Std.printf("  T");
                else Std.printf("   ");
            }
            Std.println();
        }
    }

    /**
     * Is there a directed path from vertex {@code v} to vertex {@code w} in the digraph?
     *
     * @param v the source vertex
     * @param w the target vertex
     * @return {@code true} if there is a directed path from {@code v} to {@code w},
     * {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     * @throws IllegalArgumentException unless {@code 0 <= w < V}
     */
    public boolean reachable(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return tc[v].marked(w);
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = tc.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

}

