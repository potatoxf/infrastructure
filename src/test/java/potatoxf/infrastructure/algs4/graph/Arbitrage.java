/******************************************************************************
 *  Compilation:  javac Arbitrage.java
 *  Execution:    java Arbitrage < input.txt
 *  Dependencies: Graph.java Edge.java
 *                BellmanFordSP.java
 *  Data file:    https://algs4.cs.princeton.edu/44sp/rates.txt
 *
 *  Arbitrage detection.
 *
 *  % more rates.txt
 *  5
 *  USD 1      0.741  0.657  1.061  1.005
 *  EUR 1.349  1      0.888  1.433  1.366
 *  GBP 1.521  1.126  1      1.614  1.538
 *  CHF 0.942  0.698  0.619  1      0.953
 *  CAD 0.995  0.732  0.650  1.049  1
 *
 *  % java Arbitrage < rates.txt
 *  1000.00000 USD =  741.00000 EUR
 *   741.00000 EUR = 1012.20600 CAD
 *  1012.20600 CAD = 1007.14497 USD
 *
 ******************************************************************************/

package potatoxf.infrastructure.algs4.graph;

import potatoxf.infrastructure.algs.graph.Graph;
import potatoxf.infrastructure.global.Std;

/**
 * The {@code Arbitrage} class provides a client that finds an arbitrage
 * opportunity in a currency exchange table by constructing a
 * complete-digraph representation of the exchange table and then finding
 * a negative cycle in the digraph.
 * <p>
 * This implementation uses the Bellman-Ford algorithm to find a
 * negative cycle in the complete digraph.
 * The running time is proportional to <em>V</em><sup>3</sup> in the
 * worst case, where <em>V</em> is the number of currencies.
 * <p>
 * For additional documentation,
 * see <a href="https://algs4.cs.princeton.edu/44sp">Section 4.4</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class Arbitrage {

    // this class cannot be instantiated
    private Arbitrage() {
    }

    /**
     * Reads the currency exchange table from standard input and
     * prints an arbitrage opportunity to standard output (if one exists).
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        // V currencies
        int V = Std.readInt();
        String[] name = new String[V];

        // create complete network
        Graph G = new Graph(V, true);
        for (int v = 0; v < V; v++) {
            name[v] = Std.readString();
            for (int w = 0; w < V; w++) {
                double rate = Std.readDouble();
                Graph.Edge e = new Graph.Edge(v, w, -Math.log(rate));
                G.addEdge(e);
            }
        }

        // find negative cycle
        BellmanFordSP spt = new BellmanFordSP(G, 0);
        if (spt.hasNegativeCycle()) {
            double stake = 1000.0;
            for (Graph.Edge e : spt.negativeCycle()) {
                Std.printf("%10.5f %s ", stake, name[e.v()]);
                stake *= Math.exp(-e.weight());
                Std.printf("= %10.5f %s\n", stake, name[e.w()]);
            }
        } else {
            Std.println("No arbitrage opportunity");
        }
    }

}

