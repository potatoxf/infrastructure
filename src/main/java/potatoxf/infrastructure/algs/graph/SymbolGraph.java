package potatoxf.infrastructure.algs.graph;

import java.util.HashMap;
import java.util.Map;

/**
 * <p/>
 * Create Time:2024-03-22
 *
 * @author potatoxf
 */
public class SymbolGraph extends Graph {
    private final Map<String, Integer> indexes;
    private final String[] symbols;

    public SymbolGraph(String[] data, boolean isDirection) {
        super(data.length, isDirection);
        this.symbols = new String[data.length];
        this.indexes = new HashMap<>(data.length, 1);
        for (int i = 0; i < data.length; i++) {
            symbols[i] = data[i];
            indexes.put(data[i], i);
        }
    }

    public boolean hasVertexSymbol(String symbol) {
        return indexes.containsKey(symbol);
    }

    public void validateVertexSymbol(String symbol) {
        if (!hasVertexSymbol(symbol)) {
            throw new IllegalArgumentException("vertex " + symbol + " is not exist");
        }
    }

    public int indexOf(String symbol) {
        validateVertexSymbol(symbol);
        return indexes.get(symbol);
    }

    public String nameOf(int v) {
        validateVertexIndex(v);
        return symbols[v];
    }

    public void addEdge(String v, String w) {
        this.addEdge(v, w, Double.NaN);
    }

    public void addEdge(String v, String w, double weight) {
        this.addEdge(indexOf(v), indexOf(w), weight);
    }

    @Override
    protected String forVertexString(int v) {
        return symbols[v];
    }
}

