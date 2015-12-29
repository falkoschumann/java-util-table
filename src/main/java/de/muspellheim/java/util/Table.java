/*
 * Copyright (c) 2015 Falko Schumann
 * Released under the terms of the MIT License.
 */

package de.muspellheim.java.util;

import java.util.Collection;
import java.util.Set;

/**
 * An object that maps rows and columns to value.
 * <p>A table cannot contain duplicate tuples of row and column; each row/column tupel can map to at most one value.</p>
 *
 * @param <R> the type of row keys maintained by this table
 * @param <C> the type of column keys maintained by this table
 * @param <V> the type of mapped values
 */
public interface Table<R, C, V> {

    // TODO add default implementation for Java 8 Streaming API, see java.util.Map
    // TODO implement with array
    // TODO implement with map
    // TODO implement for enum

    interface Cell<R, C, V> {

        R getRowKey();

        C getColumnKey();

        V getValue();

        void setValue(V value);

        @Override
        boolean equals(Object o);

        @Override
        int hashCode();

    }

    Set<Cell<R, C, V>> cellSet();

    // TODO from Guava: Map<R, V> column(C columnKey);

    Set<C> columnKeySet();

    void clear();

    boolean containsCell(R rowKey, C columnKey);

    // TODO from Guava: boolean containsColumn(C columnKey);

    // TODO from Guava: boolean containsRow(R rowKey);

    boolean containsValue(V value);

    V get(R rowKey, C columnKey);

    boolean isEmpty();

    V put(R rowKey, C columnKey, V value);

    void putAll(Table<? extends R, ? extends C, ? extends V> table);

    V remove(R rowKey, C columnKey);

    // TODO from Guava: Map<C, V> row(R rowKey);

    Set<R> rowKeySet();

    int size();

    Collection<V> values();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();

}
