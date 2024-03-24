/******************************************************************************
 *  Compilation:  javac GabowSCC.java
 *  Execution:    java GabowSCC V E
 *  Dependencies: Graph.java Stack.java TransitiveClosure.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/42digraph/tinyDG.txt
 *                https://algs4.cs.princeton.edu/42digraph/mediumDG.txt
 *                https://algs4.cs.princeton.edu/42digraph/largeDG.txt
 *
 *  Compute the strongly-connected components of a digraph using 
 *  Gabow's algorithm (aka Cheriyan-Mehlhorn algorithm).
 *
 *  Runs in O(E + V) time.
 *
 *  % java GabowSCC tinyDG.txt
 *  5 components
 *  1 
 *  0 2 3 4 5
 *  9 10 11 12
 *  6 8
 *  7 
 *
 ******************************************************************************/

package potatoxf.infrastructure.algs4.graph;

import potatoxf.infrastructure.algs.graph.Graph;
import potatoxf.infrastructure.algs4.Queue;
import potatoxf.infrastructure.algs4.Stack;
import potatoxf.infrastructure.global.In;
import potatoxf.infrastructure.global.Std;

/**
 * The {@code GabowSCC} class represents a data type for
 * determining the strong components in a digraph.
 * The <em>id</em> operation determines in which strong component
 * a given vertex lies; the <em>areStronglyConnected</em> operation
 * determines whether two vertices are in the same strong component;
 * and the <em>count</em> operation determines the number of strong
 * components.
 * <p>
 * The <em>component identifier</em> of a component is one of the
 * vertices in the strong component: two vertices have the same component
 * identifier if and only if they are in the same strong component.
 *
 * <p>
 * This implementation uses the Gabow's algorithm.
 * The constructor takes time proportional to <em>V</em> + <em>E</em>
 * (in the worst case),
 * where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 * Afterwards, the <em>id</em>, <em>count</em>, and <em>areStronglyConnected</em>
 * operations take constant time.
 * For alternate implementations of the same API, see
 * {@link KosarajuSharirSCC} and {@link TarjanSCC}.
 * <p>
 * For additional documentation,
 * see <a href="https://algs4.cs.princeton.edu/42digraph">Section 4.2</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class GabowSCC {

    private final boolean[] marked;        // marked[v] = has v been visited?
    private final int[] id;                // id[v] = id of strong component containing v
    private final int[] preorder;          // preorder[v] = preorder of v
    private final Stack<Integer> stack1;
    private final Stack<Integer> stack2;
    private int pre;                 // preorder number counter
    private int count;               // number of strongly-connected components


    /**
     * Computes the strong components of the digraph {@code G}.
     *
     * @param G the digraph
     */
    public GabowSCC(Graph G) {
        marked = new boolean[G.vertexCount()];
        stack1 = new Stack<Integer>();
        stack2 = new Stack<Integer>();
        id = new int[G.vertexCount()];
        preorder = new int[G.vertexCount()];
        for (int v = 0; v < G.vertexCount(); v++)
            id[v] = -1;

        for (int v = 0; v < G.vertexCount(); v++) {
            if (!marked[v]) dfs(G, v);
        }

        // check that id[] gives strong components
        assert check(G);
    }

    /**
     * Unit tests the {@code GabowSCC} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = GraphParser.parse(in, true);
        GabowSCC scc = new GabowSCC(G);

        // number of connected components
        int m = scc.count();
        Std.println(m + " components");

        // compute list of vertices in each strong component
        Queue<Integer>[] components = (Queue<Integer>[]) new Queue[m];
        for (int i = 0; i < m; i++) {
            components[i] = new Queue<Integer>();
        }
        for (int v = 0; v < G.vertexCount(); v++) {
            components[scc.id(v)].enqueue(v);
        }

        // print results
        for (int i = 0; i < m; i++) {
            for (int v : components[i]) {
                Std.print(v + " ");
            }
            Std.println();
        }

    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        preorder[v] = pre++;
        stack1.push(v);
        stack2.push(v);
        for (int w : G.adjacencyVertex(v)) {
            if (!marked[w]) dfs(G, w);
            else if (id[w] == -1) {
                while (preorder[stack2.peek()] > preorder[w])
                    stack2.pop();
            }
        }

        // found strong component containing v
        if (stack2.peek() == v) {
            stack2.pop();
            int w;
            do {
                w = stack1.pop();
                id[w] = count;
            } while (w != v);
            count++;
        }
    }

    /**
     * Returns the number of strong components.
     *
     * @return the number of strong components
     */
    public int count() {
        return count;
    }

    /**
     * Are vertices {@code v} and {@code w} in the same strong component?
     *
     * @param v one vertex
     * @param w the other vertex
     * @return {@code true} if vertices {@code v} and {@code w} are in the same
     * strong component, and {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     * @throws IllegalArgumentException unless {@code 0 <= w < V}
     */
    public boolean stronglyConnected(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return id[v] == id[w];
    }

    /**
     * Returns the component id of the strong component containing vertex {@code v}.
     *
     * @param v the vertex
     * @return the component id of the strong component containing vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int id(int v) {
        validateVertex(v);
        return id[v];
    }

    // does the id[] array contain the strongly connected components?
    private boolean check(Graph G) {
        TransitiveClosure tc = new TransitiveClosure(G);
        for (int v = 0; v < G.vertexCount(); v++) {
            for (int w = 0; w < G.vertexCount(); w++) {
                if (stronglyConnected(v, w) != (tc.reachable(v, w) && tc.reachable(w, v)))
                    return false;
            }
        }
        return true;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

}

