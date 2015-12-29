/*
 * Copyright (c) 2015 Falko Schumann
 * Released under the terms of the MIT License.
 */

package de.muspellheim.java.util;

import java.util.*;

/**
 * @author Falko Schumann
 * @since 1.0
 */
public abstract class AbstractTable<R, C, V> implements Table<R, C, V> {

    @Override
    public Collection<V> values() {
        Collection<V> result = new ArrayList<>();
        cellSet().forEach(c -> result.add(c.getValue()));
        return result;
    }

    @Override
    public Set<Cell<R, C, V>> cellSet() {
        Set<Cell<R, C, V>> result = new LinkedHashSet<>();
        for (R r : rowKeySet())
            for (C c : columnKeySet())
                if (containsCell(r, c))
                    result.add(new SimpleCell<>(r, c, get(r, c)));
        return result;
    }

    @Override
    public boolean containsCell(Object rowKey, Object columnKey) {
        return get(rowKey, columnKey) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return values().contains(value);
    }

    @Override
    public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
        table.cellSet().forEach(c -> this.put(c.getRowKey(), c.getColumnKey(), c.getValue()));
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return cellSet().size();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Map))
            return false;
        Table<?, ?, ?> t = (Table<?, ?, ?>) o;
        if (t.size() != size())
            return false;

        try {
            for (Cell<R, C, V> e : cellSet()) {
                R rowKey = e.getRowKey();
                C columnKey = e.getColumnKey();
                V value = e.getValue();
                if (value == null) {
                    if (!(t.get(rowKey, columnKey) == null && t.containsCell(rowKey, columnKey)))
                        return false;
                } else {
                    if (!value.equals(t.get(rowKey, columnKey)))
                        return false;
                }
            }
        } catch (ClassCastException unused) {
            return false;
        } catch (NullPointerException unused) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int h = 0;
        for (Cell<R, C, V> rcvCell : cellSet())
            h += rcvCell.hashCode();
        return h;
    }

    public static class SimpleCell<R, C, V> implements Table.Cell<R, C, V> {

        private R rowKey;
        private C columnKey;
        private V value;

        public SimpleCell(R rowKey, C columnKey, V value) {
            this.rowKey = rowKey;
            this.columnKey = columnKey;
            this.value = value;
        }

        public SimpleCell(Table.Cell<R, C, V> cell) {
            this.rowKey = cell.getRowKey();
            this.columnKey = cell.getColumnKey();
            this.value = cell.getValue();
        }

        @Override
        public R getRowKey() {
            return rowKey;
        }

        @Override
        public C getColumnKey() {
            return columnKey;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SimpleCell)) return false;
            SimpleCell<?, ?, ?> that = (SimpleCell<?, ?, ?>) o;
            return Objects.equals(getRowKey(), that.getRowKey()) &&
                    Objects.equals(getColumnKey(), that.getColumnKey()) &&
                    Objects.equals(getValue(), that.getValue());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getRowKey(), getColumnKey(), getValue());
        }

    }

}
