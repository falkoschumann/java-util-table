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
public class ArrayTable<R, C, V> implements Table<R, C, V> {

    private Map<R, Integer> rowKeys = new LinkedHashMap<>();
    private Map<C, Integer> columnKeys = new LinkedHashMap<>();
    private Object[][] elementData;

    public ArrayTable(Iterable<R> rowKeys, Iterable<C> columnKeys) {
        this.rowKeys = mapKeys(rowKeys);
        this.columnKeys = mapKeys(columnKeys);
        elementData = new Object[rowKeySet().size()][columnKeySet().size()];
    }

    private <T> Map<T, Integer> mapKeys(Iterable<T> keys) {
        Map<T, Integer> result = new LinkedHashMap<>();
        int index = 0;
        for (T key : keys) {
            result.put(key, index);
            index++;
        }
        return result;
    }

    @Override
    public Set<R> rowKeySet() {
        return rowKeys.keySet();
    }

    @Override
    public Set<C> columnKeySet() {
        return columnKeys.keySet();
    }

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
    public boolean containsCell(R rowKey, C columnKey) {
        return get(rowKey, columnKey) != null;
    }

    @Override
    public boolean containsValue(V value) {
        return values().contains(value);
    }

    @Override
    public V get(R rowKey, C columnKey) {
        return elementData(rowKey, columnKey);
    }

    private V elementData(R rowKey, C columnKey) {
        return (V) elementData[rowKeys.get(rowKey)][columnKeys.get(columnKey)];
    }

    @Override
    public V put(R rowKey, C columnKey, V value) {
        V result = elementData(rowKey, columnKey);
        elementData[rowKeys.get(rowKey)][columnKeys.get(columnKey)] = value;
        return result;
    }

    @Override
    public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
        table.cellSet().forEach(c -> this.put(c.getRowKey(), c.getColumnKey(), c.getValue()));
    }

    @Override
    public V remove(R rowKey, C columnKey) {
        V result = elementData(rowKey, columnKey);
        elementData[rowKeys.get(rowKey)][columnKeys.get(columnKey)] = null;
        return result;
    }

    @Override
    public void clear() {
        for (int r = 0; r < rowKeySet().size(); r++)
            for (int c = 0; c < columnKeySet().size(); c++)
                elementData[r][c] = null;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return values().size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArrayTable)) return false;
        ArrayTable<?, ?, ?> that = (ArrayTable<?, ?, ?>) o;
        return Objects.equals(rowKeySet(), that.rowKeySet()) &&
                Objects.equals(columnKeySet(), that.columnKeySet()) &&
                Objects.equals(cellSet(), that.cellSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowKeySet(), columnKeySet(), cellSet());
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
