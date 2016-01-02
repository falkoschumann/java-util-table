/*
 * Copyright (c) 2015 Falko Schumann
 * Released under the terms of the MIT License.
 */

package de.muspellheim.java.util;

import java.util.Arrays;

/**
 * Unit tests for {@link ArrayTable} implementation.
 *
 * @author Falko Schumann
 * @since 1.0
 */
public class ArrayTableTest extends AbstractTableTest {

    protected Table<Integer, Character, String> createTable() {
        return new ArrayTable<>(Arrays.asList(1, 2, 3, 4), Arrays.asList('A', 'B', 'C'));
    }

    @Override
    protected Table<Integer, Character, String> createTable(Table<Integer, Character, String> table) {
        return new ArrayTable<>(table);
    }

}
