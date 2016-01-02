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
    public Set<R> rowKeySet() {
        Set<R> result = new LinkedHashSet<>();
        cellSet().forEach(c -> result.add(c.getRowKey()));
        return result;
    }

    @Override
    public Set<C> columnKeySet() {
        Set<C> result = new LinkedHashSet<>();
        cellSet().forEach(c -> result.add(c.getColumnKey()));
        return result;
    }

    @Override
    public Collection<V> values() {
        Collection<V> result = new ArrayList<>();
        cellSet().forEach(c -> result.add(c.getValue()));
        return result;
    }

    @Override
    public boolean containsCell(R rowKey, C columnKey) {
        return get(rowKey, columnKey) != null;
    }

    @Override
    public boolean containsValue(V value) {
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

        if (!(o instanceof Table))
            return false;
        Table t = (Table) o;
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

    public String toString() {
        Iterator<Table.Cell<R, C, V>> i = cellSet().iterator();
        if (!i.hasNext())
            return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        do {
            Table.Cell<R, C, V> e = i.next();
            R rowKey = e.getRowKey();
            C columnKey = e.getColumnKey();
            V value = e.getValue();
            sb.append('(');
            sb.append(rowKey == this ? "(this Table)" : rowKey);
            sb.append(',').append(' ');
            sb.append(columnKey == this ? "(this Table)" : columnKey);
            sb.append(')');
            sb.append('=');
            sb.append(value == this ? "(this Table)" : value);
            if (i.hasNext())
                sb.append(',').append(' ');
        } while (i.hasNext());
        return sb.append('}').toString();
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
            this(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
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
