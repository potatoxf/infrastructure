package potatoxf.infrastructure.lot;

import potatoxf.api.support.Arg;

/**
 * 简单集合
 * <p/>
 * Create Time:2024-05-05
 *
 * @author potatoxf
 */
public final class SimpleSet {
    private Object[] table;
    private int size;

    public SimpleSet() {
        table = new Object[16];
    }

    public boolean record(Object element) {
        while (true) {
            final Object[] tab = table;
            final int len = tab.length;
            int i = hash(element, len);

            for (Object item; (item = tab[i]) != null; i = next(i, len)) {
                if (item == element) return false;
            }

            final int s = size + 1;
            if (s * 3 > len && resize(len)) continue;

            tab[i] = element;
            size = s;
            return true;
        }
    }

    private boolean resize(int newCapacity) {
        Object[] oldTable = table;
        Object[] newTable = Arg.arrayResize(oldTable, newCapacity, size);
        if (oldTable == newTable) return false;
        for (Object o : oldTable) {
            if (o != null) {
                int i = hash(o, newTable.length);
                while (newTable[i] != null) {
                    i = next(i, newTable.length);
                }
                newTable[i] = o;
            }
        }
        table = newTable;
        return true;
    }

    private static int hash(Object x, int length) {
        return System.identityHashCode(x) & (length - 1);
    }

    private static int next(int i, int len) {
        return i + 1 < len ? i + 1 : 0;
    }
}
