/*
 * Copyright (c) 2015 Falko Schumann
 * Released under the terms of the MIT License.
 */

package de.muspellheim.util.table;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link EnumTable} implementation.
 *
 * @author Falko Schumann
 * @since 1.0
 */
public class EnumTableTest {

    private EnumTable<VerticalAlignment, HorizontalAlignment, String> table;

    @Before
    public void setUp() {
        table = new EnumTable<>(VerticalAlignment.class, HorizontalAlignment.class);
    }

    @Test
    public void testEmptyTable() {
        assertTableIsEmpty(table);
    }

    protected static void assertTableIsEmpty(Table table) {
        assertTrue(table.isEmpty());
        assertEquals(0, table.size());
        assertTrue(table.values().isEmpty());
        assertTrue(table.cellSet().isEmpty());
    }

    @Test
    public void testFilledTable() {
        fillTable(table);

        assertTableIsFilled(table);
    }

    protected static void fillTable(Table<VerticalAlignment, HorizontalAlignment, String> table) {
        table.put(VerticalAlignment.TOP, HorizontalAlignment.LEFT, "Foo");
        table.put(VerticalAlignment.MIDDLE, HorizontalAlignment.CENTER, "Foo");
        table.put(VerticalAlignment.BOTTOM, HorizontalAlignment.RIGHT, "Foobar");
    }

    protected static void assertTableIsFilled(Table<VerticalAlignment, HorizontalAlignment, String> table) {
        assertFalse(table.isEmpty());
        assertEquals(3, table.size());
        assertTrue(table.containsValue("Foobar"));
        assertFalse(table.containsValue("Bar"));
    }

    @Test
    public void testCopyTable() {
        fillTable(table);

        Table<VerticalAlignment, HorizontalAlignment, String> copy = new EnumTable<>(table);

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
        assertTrue(table.containsCell(VerticalAlignment.MIDDLE, HorizontalAlignment.CENTER));

        table.remove(VerticalAlignment.MIDDLE, HorizontalAlignment.CENTER);

        assertEquals(2, table.size());
        assertFalse(table.containsCell(VerticalAlignment.MIDDLE, HorizontalAlignment.CENTER));
    }

    @Test
    public void testEquals() {
        Table<VerticalAlignment, HorizontalAlignment, String> a = new EnumTable<>(VerticalAlignment.class, HorizontalAlignment.class);
        fillTable(a);
        Table<VerticalAlignment, HorizontalAlignment, String> b = new EnumTable<>(VerticalAlignment.class, HorizontalAlignment.class);
        fillTable(b);
        Table<VerticalAlignment, HorizontalAlignment, String> c = new EnumTable<>(VerticalAlignment.class, HorizontalAlignment.class);
        fillTable(c);
        Table emptyTable = new EnumTable<>(VerticalAlignment.class, HorizontalAlignment.class);

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

        int expected = new AbstractTable.SimpleCell<>(VerticalAlignment.TOP, HorizontalAlignment.LEFT, "Foo").hashCode();
        expected += new AbstractTable.SimpleCell<>(VerticalAlignment.MIDDLE, HorizontalAlignment.CENTER, "Foo").hashCode();
        expected += new AbstractTable.SimpleCell<>(VerticalAlignment.BOTTOM, HorizontalAlignment.RIGHT, "Foobar").hashCode();
        assertEquals(expected, hashCode);
    }

    @Test
    public void testToString() {
        String emptyTable = table.toString();
        assertEquals("{}", emptyTable);

        fillTable(table);
        String filledTable = table.toString();
        assertEquals("{(TOP, LEFT)=Foo, (MIDDLE, CENTER)=Foo, (BOTTOM, RIGHT)=Foobar}", filledTable);
    }

}
