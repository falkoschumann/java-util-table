/*
 * Copyright (c) 2015 Falko Schumann
 * Released under the terms of the MIT License.
 */

package de.muspellheim.util.table;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Falko Schumann
 * @since 1.0
 */
public class EnumTable<R extends Enum<R>, C extends Enum<C>, V> extends AbstractTable<R, C, V> {

    private final Class<R> rowKeyType;
    private transient R[] rowKeyUniverse;

    private final Class<C> columnKeyType;
    private transient C[] columnKeyUniverse;

    private Object[][] vals;

    public EnumTable(Class<R> rowKeyType, Class<C> columnKeyType) {
        this.rowKeyType = rowKeyType;
        this.columnKeyType = columnKeyType;
        rowKeyUniverse = rowKeyType.getEnumConstants();
        columnKeyUniverse = columnKeyType.getEnumConstants();
        vals = new Object[rowKeyUniverse.length][columnKeyUniverse.length];
    }

    public EnumTable(EnumTable<R, C, V> t) {
        this(t.rowKeyType, t.columnKeyType);
        putAll(t);
    }

    @Override
    public Set<Cell<R, C, V>> cellSet() {
        Set<Cell<R, C, V>> result = new LinkedHashSet<>();
        for (R r : rowKeyUniverse)
            for (C c : columnKeyUniverse)
                if (containsCell(r, c))
                    result.add(new SimpleCell<>(r, c, get(r, c)));
        return result;
    }

    @Override
    public V get(R rowKey, C columnKey) {
        return isValidRowKey(rowKey) && isValidColumnKey(columnKey) ? unmaskNull(vals[rowKey.ordinal()][columnKey.ordinal()]) : null;
    }

    private Object maskNull(Object value) {
        return (value == null ? NULL : value);
    }

    @SuppressWarnings("unchecked")
    private V unmaskNull(Object value) {
        return (V) (value == NULL ? null : value);
    }

    private boolean isValidRowKey(Object rowKey) {
        if (rowKey == null)
            return false;

        // Cheaper than instanceof Enum followed by getDeclaringClass
        Class<?> rowKeyClass = rowKey.getClass();
        return rowKeyClass == rowKeyType || rowKeyClass.getSuperclass() == rowKeyType;
    }

    private boolean isValidColumnKey(Object columnKey) {
        if (columnKey == null)
            return false;

        // Cheaper than instanceof Enum followed by getDeclaringClass
        Class<?> columnKeyClass = columnKey.getClass();
        return columnKeyClass == columnKeyType || columnKeyClass.getSuperclass() == columnKeyType;
    }

    @Override
    public V put(R rowKey, C columnKey, V value) {
        V result = get(rowKey, columnKey);
        vals[rowKey.ordinal()][columnKey.ordinal()] = maskNull(value);
        return result;
    }

    @Override
    public V remove(R rowKey, C columnKey) {
        if (!isValidRowKey(rowKey) || !isValidColumnKey(columnKey))
            return null;
        int rowIndex = rowKey.ordinal();
        int columnIndex = columnKey.ordinal();
        Object oldValue = vals[rowIndex][columnIndex];
        vals[rowIndex][columnIndex] = null;
        return unmaskNull(oldValue);
    }

    @Override
    public void clear() {
        for (int rowIndex = 0; rowIndex < rowKeyUniverse.length; rowIndex++)
            for (int columnIndex = 0; columnIndex < columnKeyUniverse.length; columnIndex++)
                fastRemove(rowIndex, columnIndex);
    }

    private void fastRemove(int rowIndex, int columnIndex) {
        vals[rowIndex][columnIndex] = null;
    }


    private static final Object NULL = new Object() {

        public int hashCode() {
            return 0;
        }

        public String toString() {
            return "EnumTable.NULL";
        }

    };

}
