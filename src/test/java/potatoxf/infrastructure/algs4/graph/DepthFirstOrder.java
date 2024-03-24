/******************************************************************************
 *  Compilation:  javac DepthFirstOrder.java
 *  Execution:    java DepthFirstOrder digraph.txt
 *  Dependencies: Graph.java Queue.java Stack.java StdOut.java
 *                Graph.java Edge.java
 *  Data files:   https://algs4.cs.princeton.edu/42digraph/tinyDAG.txt
 *                https://algs4.cs.princeton.edu/42digraph/tinyDG.txt
 *
 *  Compute preorder and postorder for a digraph or edge-weighted digraph.
 *  Runs in O(E + V) time.
 *
 *  % java DepthFirstOrder tinyDAG.txt
 *     v  pre post
 *  --------------
 *     0    0    8
 *     1    3    2
 *     2    9   10
 *     3   10    9
 *     4    2    0
 *     5    1    1
 *     6    4    7
 *     7   11   11
 *     8   12   12
 *     9    5    6
 *    10    8    5
 *    11    6    4
 *    12    7    3
 *  Preorder:  0 5 4 1 6 9 11 12 10 2 3 7 8 
 *  Postorder: 4 5 1 12 11 10 9 6 0 3 2 7 8 
 *  Reverse postorder: 8 7 2 3 0 6 9 10 11 12 1 5 4 
 *
 ******************************************************************************/

package potatoxf.infrastructure.algs4.graph;

import potatoxf.infrastructure.algs.graph.Graph;
import potatoxf.infrastructure.algs4.Queue;
import potatoxf.infrastructure.algs4.Stack;
import potatoxf.infrastructure.global.In;
import potatoxf.infrastructure.global.Std;

/**
 * The {@code DepthFirstOrder} class represents a data type for
 * determining depth-first search ordering of the vertices in a digraph
 * or edge-weighted digraph, including preorder, postorder, and reverse postorder.
 * <p>
 * This implementation uses depth-first search.
 * The constructor takes time proportional to <em>V</em> + <em>E</em>
 * (in the worst case),
 * where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 * Afterwards, the <em>preorder</em>, <em>postorder</em>, and <em>reverse postorder</em>
 * operation takes take time proportional to <em>V</em>.
 * <p>
 * For additional documentation,
 * see <a href="https://algs4.cs.princeton.edu/42digraph">Section 4.2</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class DepthFirstOrder {
    private final boolean[] marked;          // marked[v] = has v been marked in dfs?
    private final int[] pre;                 // pre[v]    = preorder  number of v
    private final int[] post;                // post[v]   = postorder number of v
    private final Queue<Integer> preorder;   // vertices in preorder
    private final Queue<Integer> postorder;  // vertices in postorder
    private int preCounter;            // counter or preorder numbering
    private int postCounter;           // counter for postorder numbering

    /**
     * Determines a depth-first order for the digraph {@code G}.
     *
     * @param G the digraph
     */
    public DepthFirstOrder(Graph G) {
        pre = new int[G.vertexCount()];
        post = new int[G.vertexCount()];
        postorder = new Queue<Integer>();
        preorder = new Queue<Integer>();
        marked = new boolean[G.vertexCount()];
        for (int v = 0; v < G.vertexCount(); v++)
            if (!marked[v]) dfs(G, v);

        assert check();
    }

    /**
     * Unit tests the {@code DepthFirstOrder} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = GraphParser.parse(in, true);

        DepthFirstOrder dfs = new DepthFirstOrder(G);
        Std.println("   v  pre post");
        Std.println("--------------");
        for (int v = 0; v < G.vertexCount(); v++) {
            Std.printf("%4d %4d %4d\n", v, dfs.pre(v), dfs.post(v));
        }

        Std.print("Preorder:  ");
        for (int v : dfs.pre()) {
            Std.print(v + " ");
        }
        Std.println();

        Std.print("Postorder: ");
        for (int v : dfs.post()) {
            Std.print(v + " ");
        }
        Std.println();

        Std.print("Reverse postorder: ");
        for (int v : dfs.reversePost()) {
            Std.print(v + " ");
        }
        Std.println();


    }

    // run DFS in digraph G from vertex v and compute preorder/postorder
    private void dfs(Graph G, int v) {
        marked[v] = true;
        pre[v] = preCounter++;
        preorder.enqueue(v);
        for (int w : G.adjacencyVertex(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
        postorder.enqueue(v);
        post[v] = postCounter++;
    }

    /**
     * Returns the preorder number of vertex {@code v}.
     *
     * @param v the vertex
     * @return the preorder number of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int pre(int v) {
        validateVertex(v);
        return pre[v];
    }

    /**
     * Returns the postorder number of vertex {@code v}.
     *
     * @param v the vertex
     * @return the postorder number of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int post(int v) {
        validateVertex(v);
        return post[v];
    }

    /**
     * Returns the vertices in postorder.
     *
     * @return the vertices in postorder, as an iterable of vertices
     */
    public Iterable<Integer> post() {
        return postorder;
    }

    /**
     * Returns the vertices in preorder.
     *
     * @return the vertices in preorder, as an iterable of vertices
     */
    public Iterable<Integer> pre() {
        return preorder;
    }

    /**
     * Returns the vertices in reverse postorder.
     *
     * @return the vertices in reverse postorder, as an iterable of vertices
     */
    public Iterable<Integer> reversePost() {
        Stack<Integer> reverse = new Stack<Integer>();
        for (int v : postorder)
            reverse.push(v);
        return reverse;
    }

    // check that pre() and post() are consistent with pre(v) and post(v)
    private boolean check() {

        // check that post(v) is consistent with post()
        int r = 0;
        for (int v : post()) {
            if (post(v) != r) {
                Std.println("post(v) and post() inconsistent");
                return false;
            }
            r++;
        }

        // check that pre(v) is consistent with pre()
        r = 0;
        for (int v : pre()) {
            if (pre(v) != r) {
                Std.println("pre(v) and pre() inconsistent");
                return false;
            }
            r++;
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

