/******************************************************************************
 *  Compilation:  javac CPM.java
 *  Execution:    java CPM < input.txt
 *  Dependencies: Graph.java AcyclicDigraphLP.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/44sp/jobsPC.txt
 *
 *  Critical path method.
 *
 *  % java CPM < jobsPC.txt
 *   job   start  finish
 *  --------------------
 *     0     0.0    41.0
 *     1    41.0    92.0
 *     2   123.0   173.0
 *     3    91.0   127.0
 *     4    70.0   108.0
 *     5     0.0    45.0
 *     6    70.0    91.0
 *     7    41.0    73.0
 *     8    91.0   123.0
 *     9    41.0    70.0
 *  Finish time:   173.0
 *
 ******************************************************************************/

package potatoxf.infrastructure.algs4.graph;

import potatoxf.infrastructure.algs.graph.Graph;
import potatoxf.infrastructure.global.Std;

/**
 * The {@code CPM} class provides a client that solves the
 * parallel precedence-constrained job scheduling problem
 * via the <em>critical path method</em>. It reduces the problem
 * to the longest-paths problem in edge-weighted DAGs.
 * It builds an edge-weighted digraph (which must be a DAG)
 * from the job-scheduling problem specification,
 * finds the longest-paths tree, and computes the longest-paths
 * lengths (which are precisely the start times for each job).
 * <p>
 * This implementation uses {@link AcyclicLP} to find a longest
 * path in a DAG.
 * The running time is proportional to <em>V</em> + <em>E</em>,
 * where <em>V</em> is the number of jobs and <em>E</em> is the
 * number of precedence constraints.
 * <p>
 * For additional documentation,
 * see <a href="https://algs4.cs.princeton.edu/44sp">Section 4.4</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class CPM {

    // this class cannot be instantiated
    private CPM() {
    }

    /**
     * Reads the precedence constraints from standard input
     * and prints a feasible schedule to standard output.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        // number of jobs
        int n = Std.readInt();

        // source and sink
        int source = 2 * n;
        int sink = 2 * n + 1;

        // build network
        Graph G = new Graph(2 * n + 2, true);
        for (int i = 0; i < n; i++) {
            double duration = Std.readDouble();
            G.addEdge(new Graph.Edge(source, i, 0.0));
            G.addEdge(new Graph.Edge(i + n, sink, 0.0));
            G.addEdge(new Graph.Edge(i, i + n, duration));

            // precedence constraints
            int m = Std.readInt();
            for (int j = 0; j < m; j++) {
                int precedent = Std.readInt();
                G.addEdge(new Graph.Edge(n + i, precedent, 0.0));
            }
        }

        // compute longest path
        AcyclicLP lp = new AcyclicLP(G, source);

        // print results
        Std.println(" job   start  finish");
        Std.println("--------------------");
        for (int i = 0; i < n; i++) {
            Std.printf("%4d %7.1f %7.1f\n", i, lp.distTo(i), lp.distTo(i + n));
        }
        Std.printf("Finish time: %7.1f\n", lp.distTo(sink));
    }

}

