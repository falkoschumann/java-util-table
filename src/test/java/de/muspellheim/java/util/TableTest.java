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
        table = createTable();
    }

    private ArrayTable<Integer, Character, String> createTable() {
        return new ArrayTable<>(Arrays.asList(1, 2, 3, 4), Arrays.asList('A', 'B', 'C'));
    }

    @Test
    public void testEmptyTable() {
        assertTableIsEmpty(table);
    }

    private static void assertTableIsEmpty(Table table) {
        assertTrue(table.isEmpty());
        assertEquals(0, table.size());
        assertEquals(4, table.rowKeySet().size());
        assertEquals(3, table.columnKeySet().size());
        assertTrue(table.values().isEmpty());
        assertTrue(table.cellSet().isEmpty());
    }

    @Test
    public void testFilledTable() {
        fillTable(table);

        assertTableIsFilled(table);
    }

    private static void fillTable(Table<Integer, Character, String> table) {
        table.put(1, 'A', "Foo");
        table.put(2, 'B', "Foo");
        table.put(4, 'C', "Foobar");
    }

    private static void assertTableIsFilled(Table table) {
        assertFalse(table.isEmpty());
        assertEquals(3, table.size());
        assertTrue(table.containsValue("Foobar"));
        assertFalse(table.containsValue("Bar"));
    }

    @Test
    public void testCopyTable() {
        fillTable(table);

        Table copy = new ArrayTable<>(table);

        assertTableIsFilled(copy);
    }

    @Test
    public void testClearTable() {
        fillTable(table);

        table.clear();

        assertTableIsEmpty(table);
    }

    @Test
    public void testRemoveCell() {
        fillTable(table);
        assertEquals(3, table.size());
        assertTrue(table.containsCell(4, 'C'));

        table.remove(4, 'C');

        assertEquals(2, table.size());
        assertFalse(table.containsCell(4, 'C'));
    }

    @Test
    public void testEquals() {
        Table<Integer, Character, String> a = createTable();
        fillTable(a);
        Table<Integer, Character, String> b = createTable();
        fillTable(b);
        Table<Integer, Character, String> c = createTable();
        fillTable(c);
        Table emptyTable = createTable();

        // reflexive
        assertEquals(a, a);

        // symmetric
        assertEquals(a, b);
        assertEquals(b, a);

        // transitive
        assertEquals(a, b);
        assertEquals(b, c);

        assertFalse(a.equals(null));
        assertFalse(a.equals(emptyTable));
    }

    @Test
    public void testHashCode() {
        fillTable(table);

        int hashCode = table.hashCode();

        int expected = new AbstractTable.SimpleCell<>(1, 'A', "Foo").hashCode();
        expected += new AbstractTable.SimpleCell<>(2, 'B', "Foo").hashCode();
        expected += new AbstractTable.SimpleCell<>(4, 'C', "Foobar").hashCode();
        assertEquals(expected, hashCode);
    }

    @Test
    public void testToString() {
        String emptyTable = table.toString();
        assertEquals("{}", emptyTable);

        fillTable(table);
        String filledTable = table.toString();
        assertEquals("{(1, A)=Foo, (2, B)=Foo, (4, C)=Foobar}", filledTable);
    }

}
