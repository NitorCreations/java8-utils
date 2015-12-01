package com.nitorcreations.collections;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static com.nitorcreations.streams.NMappers.entryOf;
import static com.nitorcreations.collections.NMaps.*;
import static com.nitorcreations.collections.NSets.asSet;
import static com.nitorcreations.test.TestUtils.invokePrivateConstructor;
import static java.util.Arrays.asList;
import static java.util.function.Function.identity;
import static com.nitorcreations.test.Assertions.assertThat;

public class NMapsTest {

    @Test
    public void forCoverage() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        invokePrivateConstructor(NMaps.class);
    }

    //
    // MAP OF ENTRIES
    //

    @Test
    public void testMapOfEntries() {
        final Map<String, String> map = mapOfEntries(entryOf("a", "b"), entryOf("c", "d"));
        assertThat(map).hasSize(2)
                .containsEntry("a", "b")
                .containsEntry("c", "d");
    }

    @Test
    public void testMapOfEntries_empty() {
        assertThat(mapOfEntries()).isEmpty();
    }

    @Test(expected = NullPointerException.class)
    public void testMapOfEntries_nullEntry() {
        mapOfEntries(entryOf(1, 2), null);
    }

    //
    // MAPPING KEYS TO VALUES
    //
    @Test
    public void testMapping() {
        final Map<String, Integer> map = mapping(asSet("a", "bee", "cheetah"), String::length);
        assertThat(map).hasSize(3)
                .containsEntry("a", 1)
                .containsEntry("bee", 3)
                .containsEntry("cheetah", 7);
    }

    @Test(expected = ArithmeticException.class)
    public void testMapping_withException() {
        mapping(asSet(1,2,0), i -> 10 / i);
    }

    @Test(expected = NullPointerException.class)
    public void testMapping_nullKeys() {
        mapping(asSet(1, null), identity());
    }

    //
    // TEST COMBINE
    //
    @Test
    public void combineMaps() {
        final Map<Integer, Integer> combined = combine(mapOf(1, 2), mapOf(3, 4));
        assertThat(combined).hasSize(2)
                .containsEntry(1, 2)
                .containsEntry(3, 4);
    }

    @Test(expected = IllegalStateException.class)
    public void combineMapsDuplicateKeys() {
        combine(mapOf(1, 2), mapOf(1, 3));
    }

    @Test
    public void combineMaps_summingOnDuplicate() {
        final Map<Integer, Integer> combined = combine((a, b) -> a + b, mapOf(1, 2), mapOf(1, 3));
        assertThat(combined).hasSize(1)
                .containsEntry(1, 5); // Summed
    }

    @Test
    public void combineMapsAndSkip_duplicateKeys_selectsFirst() {
        final Map<Integer, Integer> map = combineAndSkip(mapOf(1, 2), mapOf(1, 3));
        assertThat(map).hasSize(1).containsEntry(1, 2);
    }

    //
    // MAP OF LISTS
    //

    @Test
    public void testMapOfLists() {
        final Map<Integer, String> map = mapOfLists(asList(1, 2, 3), asList("a", "b", "c"));
        assertThat(map).hasSize(3)
                .containsEntry(1, "a")
                .containsEntry(2, "b")
                .containsEntry(3, "c");
    }

    @Test(expected = IllegalStateException.class)
    public void testMapOfLists_duplicateKeys() {
        mapOfLists(asList(1, 1, 1), asList(1, 2, 3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMapOfLists_differentSize() {
        mapOfLists(asList(1, 2, 3), asList(1, 2));
    }

    @Test(expected = NullPointerException.class)
    public void testMapOfLists_nullKeys() {
        mapOfLists(null, asList(1, 2, 3));
    }

    @Test(expected = NullPointerException.class)
    public void testMapOfLists_nullValues() {
        mapOfLists(asList(1, 2, 3), null);
    }

    //
    // HELPER MAP CREATION
    //

    @Test
    public void testMapOf_1() {
        assertThat(mapOf(1, 10)).hasSize(1).containsEntry(1, 10);
    }

    @Test
    public void testMapOf_2() {
        assertThat(mapOf(1, 10, 2, 20))
                .hasSize(2)
                .containsEntry(1, 10)
                .containsEntry(2, 20);
    }

    @Test
    public void testMapOf_3() {
        assertThat(mapOf(1, 10, 2, 20, 3, 30))
                .hasSize(3)
                .containsEntry(1, 10)
                .containsEntry(2, 20)
                .containsEntry(3, 30);
    }

    @Test
    public void testMapOf_4() {
        assertThat(mapOf(1, 10, 2, 20, 3, 30, 4, 40))
                .hasSize(4)
                .containsEntry(1, 10)
                .containsEntry(2, 20)
                .containsEntry(3, 30)
                .containsEntry(4, 40);
    }

    @Test
    public void testMapOf_5() {
        assertThat(mapOf(1, 10, 2, 20, 3, 30, 4, 40, 5, 50))
                .hasSize(5)
                .containsEntry(1, 10)
                .containsEntry(2, 20)
                .containsEntry(3, 30)
                .containsEntry(4, 40)
                .containsEntry(5, 50);
    }

    @Test
    public void testMapOf_6() {
        assertThat(mapOf(1, 10, 2, 20, 3, 30, 4, 40, 5, 50, 6, 60))
                .hasSize(6)
                .containsEntry(1, 10)
                .containsEntry(2, 20)
                .containsEntry(3, 30)
                .containsEntry(4, 40)
                .containsEntry(5, 50)
                .containsEntry(6, 60);
    }

    @Test
    public void testMapOf_7() {
        assertThat(mapOf(1, 10, 2, 20, 3, 30, 4, 40, 5, 50, 6, 60, 7, 70))
                .hasSize(7)
                .containsEntry(1, 10)
                .containsEntry(2, 20)
                .containsEntry(3, 30)
                .containsEntry(4, 40)
                .containsEntry(5, 50)
                .containsEntry(6, 60)
                .containsEntry(7, 70);
    }

    @Test
    public void testMapOf_8() {
        assertThat(mapOf(1, 10, 2, 20, 3, 30, 4, 40, 5, 50, 6, 60, 7, 70, 8, 80))
                .hasSize(8)
                .containsEntry(1, 10)
                .containsEntry(2, 20)
                .containsEntry(3, 30)
                .containsEntry(4, 40)
                .containsEntry(5, 50)
                .containsEntry(6, 60)
                .containsEntry(7, 70)
                .containsEntry(8, 80);
    }

    @Test
    public void testMapOf_9() {
        assertThat(mapOf(1, 10, 2, 20, 3, 30, 4, 40, 5, 50, 6, 60, 7, 70, 8, 80, 9, 90))
                .hasSize(9)
                .containsEntry(1, 10)
                .containsEntry(2, 20)
                .containsEntry(3, 30)
                .containsEntry(4, 40)
                .containsEntry(5, 50)
                .containsEntry(6, 60)
                .containsEntry(7, 70)
                .containsEntry(8, 80)
                .containsEntry(9, 90);
    }

    @Test
    public void testMapOf_10() {
        assertThat(mapOf(1, 10, 2, 20, 3, 30, 4, 40, 5, 50, 6, 60, 7, 70, 8, 80, 9, 90, 10, 100))
                .hasSize(10)
                .containsEntry(1, 10)
                .containsEntry(2, 20)
                .containsEntry(3, 30)
                .containsEntry(4, 40)
                .containsEntry(5, 50)
                .containsEntry(6, 60)
                .containsEntry(7, 70)
                .containsEntry(8, 80)
                .containsEntry(9, 90)
                .containsEntry(10, 100);
    }

    @Test(expected = IllegalStateException.class)
    public void testMapOf_2_withDuplicates() {
        mapOf(1, 2, 1, 3);
    }
}