/*
 * Copyright (c) 2015 Falko Schumann
 * Released under the terms of the MIT License.
 */

package de.muspellheim.java.util;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Falko Schumann
 * @since 1.0
 */
public class ArrayTable<R, C, V> extends AbstractTable<R, C, V> {

    private Map<R, Integer> rowKeys = new LinkedHashMap<>();
    private Map<C, Integer> columnKeys = new LinkedHashMap<>();
    private Object[][] elementData;

    public ArrayTable(Iterable<? extends R> rowKeys, Iterable<? extends C> columnKeys) {
        this.rowKeys = mapKeys(rowKeys);
        this.columnKeys = mapKeys(columnKeys);
        elementData = new Object[rowKeySet().size()][columnKeySet().size()];
    }

    public ArrayTable(Table<? extends R, ? extends C, ? extends V> t) {
        this(t.rowKeySet(), t.columnKeySet());
        putAll(t);
    }

    private <T> Map<T, Integer> mapKeys(Iterable<? extends T> keys) {
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
    public Set<Cell<R, C, V>> cellSet() {
        Set<Cell<R, C, V>> result = new LinkedHashSet<>();
        for (R r : rowKeySet())
            for (C c : columnKeySet())
                if (containsCell(r, c))
                    result.add(new SimpleCell<>(r, c, get(r, c)));
        return result;
    }

    @Override
    public V get(Object rowKey, Object columnKey) {
        return elementData(rowKey, columnKey);
    }

    @SuppressWarnings("unchecked")
    private V elementData(Object rowKey, Object columnKey) {
        return (V) elementData[rowKeys.get(rowKey)][columnKeys.get(columnKey)];
    }

    @Override
    public V put(R rowKey, C columnKey, V value) {
        V result = elementData(rowKey, columnKey);
        elementData[rowKeys.get(rowKey)][columnKeys.get(columnKey)] = value;
        return result;
    }

    @Override
    public V remove(R rowKey, C columnKey) {
        V result = elementData(rowKey, columnKey);
        fastRemove(rowKeys.get(rowKey), columnKeys.get(columnKey));
        return result;
    }

    @Override
    public void clear() {
        for (int rowIndex = 0; rowIndex < rowKeySet().size(); rowIndex++)
            for (int columnIndex = 0; columnIndex < columnKeySet().size(); columnIndex++)
                fastRemove(rowIndex, columnIndex);
    }

    private void fastRemove(int rowIndex, int columnIndex) {
        elementData[rowIndex][columnIndex] = null;
    }

}
