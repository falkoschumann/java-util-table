/*
 * Copyright (c) 2015 Falko Schumann
 * Released under the terms of the MIT License.
 */

package de.muspellheim.java.util;

import java.util.LinkedHashMap;
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
    public V get(Object rowKey, Object columnKey) {
        return elementData(rowKey, columnKey);
    }

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
        elementData[rowKeys.get(rowKey)][columnKeys.get(columnKey)] = null;
        return result;
    }

    @Override
    public void clear() {
        for (int r = 0; r < rowKeySet().size(); r++)
            for (int c = 0; c < columnKeySet().size(); c++)
                elementData[r][c] = null;
    }

}
