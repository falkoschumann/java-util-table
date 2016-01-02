/*
 * Copyright (c) 2015 Falko Schumann
 * Released under the terms of the MIT License.
 */

package de.muspellheim.java.util;

import javafx.util.Pair;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Falko Schumann
 * @since 1.0
 */
public class HashTable<R, C, V> extends AbstractTable<R, C, V> {

    private Map<Pair<R, C>, V> elementData = new LinkedHashMap<>();

    public HashTable() {
        // default constructor
    }

    public HashTable(Table<R, C, V> t) {
        putAll(t);
    }

    @Override
    public Set<Cell<R, C, V>> cellSet() {
        Set<Cell<R, C, V>> result = new LinkedHashSet<>();
        elementData.forEach((rc, v) -> result.add(new SimpleCell<>(rc.getKey(), rc.getValue(), v)));
        return result;
    }

    @Override
    public V get(R rowKey, C columnKey) {
        return elementData.get(new Pair<>(rowKey, columnKey));
    }

    @Override
    public V put(R rowKey, C columnKey, V value) {
        return elementData.put(new Pair<>(rowKey, columnKey), value);
    }

    @Override
    public V remove(R rowKey, C columnKey) {
        return elementData.remove(new Pair<>(rowKey, columnKey));
    }

    @Override
    public void clear() {
        elementData.clear();
    }

}
