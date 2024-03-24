package potatoxf.infrastructure.algs.graph;

import java.util.*;

/**
 * <p/>
 * Create Time:2024-03-22
 *
 * @author potatoxf
 */
public class Graph {
    private final NodeList[] adjacency;
    private final int[] degree;
    private final boolean isDirection;
    private final int countVertex;
    private int countEdge;

    public Graph(int countVertex, boolean isDirection) {
        if (countVertex < 0) throw new IllegalArgumentException("Number of vertices must be non negative");
        this.degree = isDirection ? new int[countVertex] : null;
        this.adjacency = new NodeList[countVertex];
        for (int v = 0; v < countVertex; v++) {
            this.adjacency[v] = new NodeList();
        }
        this.countVertex = countVertex;
        this.isDirection = isDirection;
        this.countEdge = 0;
    }

    public Graph(Graph graph) {
        this(graph.countVertex, graph.isDirection);
        this.countEdge = graph.countEdge;
        if (this.degree != null) {
            System.arraycopy(graph.degree, 0, this.degree, 0, this.countVertex);
        }
        for (int v = 0; v < this.countVertex; v++) {
            Edge[] edges = graph.adjacencyEdge(v);
            for (int i = edges.length - 1; i >= 0; i--) {
                this.addEdgeNode(edges[i], v);
            }
        }
    }

    public final boolean isDirection() {
        return isDirection;
    }

    public final int vertexCount() {
        return countVertex;
    }

    public final int edgeCount() {
        return countEdge;
    }

    public final boolean hasVertexIndex(int vertex) {
        return vertex >= 0 && vertex < countVertex;
    }

    public final void validateVertexIndex(int vertex) {
        if (!hasVertexIndex(vertex)) {
            throw new IllegalArgumentException("vertex " + vertex + " is not between 0 and " + (countVertex - 1));
        }
    }

    public Iterator<Edge> adjacencyEdgeIterator(int vertex) {
        this.validateVertexIndex(vertex);
        return new Iterator<Edge>() {
            Node current = adjacency[vertex].first;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Edge next() {
                if (!hasNext()) throw new NoSuchElementException();
                Edge item = current.value;
                current = current.next;
                return item;
            }
        };
    }

    public Edge[] adjacencyEdge(int vertex) {
        Edge[] adj = new Edge[adjacency[vertex].size];
        Iterator<Edge> iterator = adjacencyEdgeIterator(vertex);
        for (int i = 0; iterator.hasNext(); i++) {
            adj[i] = iterator.next();
        }
        return adj;
    }

    public Iterator<Integer> adjacencyVertexIterator(int vertex) {
        this.validateVertexIndex(vertex);
        return new Iterator<Integer>() {
            Node current = adjacency[vertex].first;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Integer next() {
                if (!hasNext()) throw new NoSuchElementException();
                int item = current.value.other(vertex);
                current = current.next;
                return item;
            }
        };
    }

    public int[] adjacencyVertex(int vertex) {
        int[] adj = new int[adjacency[vertex].size];
        Iterator<Integer> iterator = adjacencyVertexIterator(vertex);
        for (int i = 0; iterator.hasNext(); i++) {
            adj[i] = iterator.next();
        }
        return adj;
    }

    public int degree(int vertex) {
        return outDegree(vertex);
    }

    public int inDegree(int vertex) {
        validateVertexIndex(vertex);
        return degree == null ? this.adjacency[vertex].size : degree[vertex];
    }

    public int outDegree(int vertex) {
        validateVertexIndex(vertex);
        return this.adjacency[vertex].size;
    }

    public Iterable<Edge> edges() {
        List<Edge> list = new ArrayList<>(this.countEdge);
        for (int v = 0; v < this.countVertex; v++) {
            Collections.addAll(list, adjacencyEdge(v));
        }
        return list;
    }

    public Graph reverse() {
        if (!isDirection) return this;
        Graph reverse = new Graph(countVertex, true);
        for (int v = 0; v < countVertex; v++) {
            for (Edge edge : adjacencyEdge(v)) {
                int w = edge.other(v);
                reverse.addEdgeNode(edge, w);
                reverse.degree[w]++;
                reverse.countEdge++;
            }
        }
        return reverse;
    }

    public final void addEdge(int v, int w) {
        this.addEdge(v, w, Double.NaN);
    }

    public final void addEdge(int v, int w, double weight) {
        this.addEdge(new Edge(v, w, weight));
    }

    public final void addEdge(Edge edge) {
        int v = edge.v(), w = edge.w();
        this.validateVertexIndex(v);
        this.validateVertexIndex(w);
        this.addEdgeNode(edge, v);
        if (this.isDirection) {
            this.degree[w]++;
        }
        this.countEdge++;
        if (!this.isDirection) {
            this.addEdgeNode(edge, w);
        }
    }

    private void addEdgeNode(Edge edge, int v) {
        NodeList nodeList = this.adjacency[v];
        Node node = new Node(edge);
        if (nodeList.size == 0) {
            nodeList.first = node;
        } else {
            nodeList.last.next = node;
        }
        nodeList.last = node;
        nodeList.size++;
    }

    public final String toString() {
        StringBuilder s = new StringBuilder();
        s.append(isDirection ? "Directed" : "Undirected").append("Graph(Vertices[")
                .append(countVertex).append("],").append("Edges[").append(countEdge).append("])\n");
        for (int v = 0; v < countVertex; v++) {
            String vs = forVertexString(v);
            s.append(vs).append(":\n");
            for (Edge w : adjacencyEdge(v)) {
                String ws = forVertexString(w.other(v));
                s.append("  ").append(vs).append(isDirection ? "->" : "--").append(ws);
                if (w.hasWeighted()) {
                    s.append(" ").append(w.weight());
                }
                s.append("\n");
            }
        }
        return s.toString();
    }

    protected String forVertexString(int v) {
        return String.valueOf(v);
    }

    private static class NodeList {
        private Node first = null;
        private Node last = null;
        private int size = 0;
    }

    private static class Node {
        private final Edge value;
        private Node next;

        private Node(Edge value) {
            this.value = value;
        }
    }

    public static class Edge implements Comparable<Edge> {
        private final int v;
        private final int w;
        private volatile double weight;

        public Edge(int v, int w) {
            this(v, w, Double.NaN);
        }
        public Edge(int v, int w, double weight) {
            if (v < 0) throw new IllegalArgumentException("vertex index must be a non negative integer");
            if (w < 0) throw new IllegalArgumentException("vertex index must be a non negative integer");
            this.v = v;
            this.w = w;
            this.weight = weight;
        }

        public int v() {
            return v;
        }

        public int w() {
            return w;
        }

        public boolean hasWeighted() {
            return !Double.isNaN(weight);
        }

        public double weight() {
            return weight;
        }

        public double weight(double weight) {
            double old = this.weight;
            this.weight = weight;
            return old;
        }

        public int other(int vertex) {
            if (vertex == v) return w;
            else if (vertex == w) return v;
            else throw new IllegalArgumentException("Illegal endpoint");
        }

        @Override
        public int compareTo(Edge that) {
            return Double.compare(this.weight, that.weight);
        }

        public String toString() {
            return String.format("%d %d %.5f", v, w, weight);
        }
    }
}
