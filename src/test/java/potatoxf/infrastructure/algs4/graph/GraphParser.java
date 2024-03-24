package potatoxf.infrastructure.algs4.graph;

import potatoxf.infrastructure.algs.graph.Graph;
import potatoxf.infrastructure.algs.graph.SymbolGraph;
import potatoxf.infrastructure.global.In;
import potatoxf.infrastructure.global.Std;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * <p/>
 * Create Time:2024-03-23
 *
 * @author potatoxf
 */
public class GraphParser {

    public static Graph parse(In in, boolean isDirection) {
        Graph graph = new Graph(in.readInt(), isDirection);
        int E = in.readInt();
        if (E < 0) throw new IllegalArgumentException("Number of edges must be non negative");
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            graph.addEdge(v, w, weight);
        }
        return graph;
    }


    /**
     * Initializes a graph from a file using the specified delimiter.
     * Each line in the file contains
     * the name of a vertex, followed by a list of the names
     * of the vertices adjacent to that vertex, separated by the delimiter.
     *
     * @param filename  the name of the file
     * @param delimiter the delimiter between fields
     */
    public static SymbolGraph parseSymbol(String filename, String delimiter, boolean isDirection) {
        Set<String> st = new HashSet<>();

        // First pass builds the index by reading strings to associate
        // distinct strings with an index
        In in = new In(filename);
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(delimiter);
            Collections.addAll(st, a);
        }

        // second pass builds the graph by connecting first vertex on each
        // line to all others
        SymbolGraph graph = new SymbolGraph(st.toArray(new String[0]), isDirection);
        in = new In(filename);
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(delimiter);
            for (int i = 1; i < a.length; i++) {
                graph.addEdge(a[0], a[i]);
            }
        }
        return graph;
    }


    public static void main(String[] args) {
        String filename = args[0];
        String delimiter = args[1];
        SymbolGraph sg = parseSymbol(filename, delimiter, false);
        while (Std.hasNextLine()) {
            String t = Std.readLine();
            if (sg.hasVertexSymbol(t)) {
                for (int v : sg.adjacencyVertex(sg.indexOf(t))) {
                    Std.println("   " + sg.nameOf(v));
                }
            } else {
                Std.println("input not contain '" + t + "'");
            }
        }
    }
}
