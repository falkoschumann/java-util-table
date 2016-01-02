/*
 * Copyright (c) 2015 Falko Schumann
 * Released under the terms of the MIT License.
 */

package de.muspellheim.java.util;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author Falko Schumann
 * @since 1.0
 */
public class PerformanceTest {

    private static final int RUNS = 10_000_000;

    private static void putValues(Table<VerticalAlignment, HorizontalAlignment, String> table, int n) {
        for (int i = 0; i < n; i++) {
            VerticalAlignment v = VerticalAlignment.values()[i % 2];
            HorizontalAlignment h = HorizontalAlignment.values()[i % 2];
            table.put(v, h, "Foobar");
        }
    }

    private static void removeValues(Table<VerticalAlignment, HorizontalAlignment, String> table, int n) {
        for (int i = 0; i < n; i++) {
            VerticalAlignment v = VerticalAlignment.values()[i % 2];
            HorizontalAlignment h = HorizontalAlignment.values()[i % 2];
            table.remove(v, h);
        }
    }

    private static void runPerformanceTest(Table<VerticalAlignment, HorizontalAlignment, String> table) {
        putValues(table, RUNS);
        removeValues(table, RUNS);
        putValues(table, RUNS);
        removeValues(table, RUNS);
    }

    @Test
    public void testArrayTable() {
        Table<VerticalAlignment, HorizontalAlignment, String> table = new ArrayTable<>(Arrays.asList(VerticalAlignment.values()), Arrays.asList(HorizontalAlignment.values()));
        runPerformanceTest(table);
    }

    @Test
    public void testHashTable() {
        Table<VerticalAlignment, HorizontalAlignment, String> table = new HashTable<>();
        runPerformanceTest(table);
    }

    @Test
    public void testEnumTable() {
        Table<VerticalAlignment, HorizontalAlignment, String> table = new EnumTable<>(VerticalAlignment.class, HorizontalAlignment.class);
        runPerformanceTest(table);
    }

}
