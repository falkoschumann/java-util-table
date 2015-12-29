/*
 * Copyright (c) 2015 Falko Schumann
 * Released under the terms of the MIT License.
 */

package de.muspellheim.java.util;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link Table} implementation.
 *
 * @author Falko Schumann
 * @since 1.0
 */
public class TableTest {

    private Table<Integer, Character, String> table;

    @Before
    public void setUp() {
        table = new ArrayTable<>(Arrays.asList(1, 2, 3, 4), Arrays.asList('A', 'B', 'C'));
    }

    @Test
    public void testEmptyTable() {
        assertTrue(table.isEmpty());
        assertEquals(0, table.size());
        assertEquals(4, table.rowKeySet().size());
        assertEquals(3, table.columnKeySet().size());
        assertTrue(table.values().isEmpty());
        assertTrue(table.cellSet().isEmpty());
    }

    @Test
    public void testTable() {
        table.put(1, 'A', "Foo");
        table.put(2, 'B', "Foo");
        table.put(4, 'C', "Foobar");

        assertFalse(table.isEmpty());
        assertEquals(3, table.size());
    }

}
