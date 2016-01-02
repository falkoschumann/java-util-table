/*
 * Copyright (c) 2015 Falko Schumann
 * Released under the terms of the MIT License.
 */

package de.muspellheim.util.table;

/**
 * Unit tests for {@link HashTable} implementation.
 *
 * @author Falko Schumann
 * @since 1.0
 */
public class HashTableTest extends AbstractTableTest {

    protected Table<Integer, Character, String> createTable() {
        return new HashTable<>();
    }

    @Override
    protected Table<Integer, Character, String> createTable(Table<Integer, Character, String> table) {
        return new HashTable<>(table);
    }

}
