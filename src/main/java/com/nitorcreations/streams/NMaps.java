package com.nitorcreations.streams;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.IntStream;

import static com.nitorcreations.streams.NCollectors.entriesToMap;
import static com.nitorcreations.streams.NMappers.entryOf;

public final class NMaps {
    private NMaps() { /** prevent instantiation */}

    /*
    Map<String, Integer> aMap = NMaps.mapOf(entryOf("a", 1), entryOf("c", 2), ...);
Map<String, Integer> bMap = NMaps.mapOf("a", 1, "c", 2, ...); // Up to five entries
Map<String, Integer> bMap = NMaps.mapping(asList("a", "bee", "cheetah"), s -> s.length()); // Map from keys
     */

    /**
     * Create a map of an arbitrary amount of map entries. Will throw exception on duplicate keys.
     * @param entries entries to collect to a map
     * @param <K> the key type
     * @param <V> the value type
     * @return the new map
     * @see NCollectors#entriesToMap()
     */
    @SafeVarargs
    public static <K,V> Map<K, V> mapOfEntries(Map.Entry<K, V>... entries) {
        return Arrays.stream(entries).collect(entriesToMap());
    }

    /**
     * Create a map with keys mapped to values by the {@code valueFn}. The values are evaluated
     * eagerly and stored immediately.
     *
     * @param keys the keys to map
     * @param valueFn the function mapping keys to values
     * @param <K> type of key
     * @param <V> type of values
     * @return the map
     */
    public static <K, V> Map<K, V> mapping(Set<K> keys, Function<K, V> valueFn) {
        return keys.stream()
                .map(k -> entryOf(k, valueFn.apply(k)))
                .collect(entriesToMap());
    }

    /**
     * Create a map of the list of keys and list of values. Values are associated by the index
     * in the list.
     * <p>
     * Will throw exception if sizes differ or either one is {@code null}.
     *
     * @param keys the keys of the map
     * @param values the values of the map
     * @param <K> type of keys
     * @param <V> type of values
     * @return the map containing the entries 
     */
    public static <K,V> Map<K, V> mapOfLists(List<K> keys, List<V> values) {
        if (keys.size() != values.size()) {
            throw new IllegalArgumentException(String.format("Keys and values sizes differ: %d != %d", keys.size(), values.size()));
        }
        return IntStream.range(0, keys.size())
                .mapToObj(i -> entryOf(keys.get(i), values.get(i)))
                .collect(entriesToMap());
    }

    /**
     * Combine multiple maps to a single one. Will throw exception on duplicate keys.
     * If duplicate values are expected, use {@link #combine(BinaryOperator, Map[])}
     *
     * @param maps the maps to combine
     * @param <K> the key type
     * @param <V> the value type
     * @return the resulting combined map
     */
    public static <K,V> Map<K, V> combine(Map<K, V>... maps) {
        return Arrays.stream(maps)
                .flatMap(m -> m.entrySet().stream())
                .collect(entriesToMap());
    }

    /**
     * Combine multiple maps to a single one. Uses {@code mergeFn} to cope with duplicate keys.
     *
     * @param maps the maps to combine
     * @param mergeFn the function to combine values on duplicate keys
     * @param <K> the key type
     * @param <V> the value type
     * @return the resulting combined map
     */
    public static <K,V> Map<K, V> combine(BinaryOperator<V> mergeFn, Map<K, V>... maps) {
        return Arrays.stream(maps)
                .flatMap(m -> m.entrySet().stream())
                .collect(entriesToMap(mergeFn));
    }

    /**
     * Combine multiple maps to a single one. Uses the first encountered value as the value
     * on duplicate keys.
     *
     * @param maps the maps to combine
     * @param <K> the key type
     * @param <V> the value type
     * @return the resulting combined map
     */
    public static <K,V> Map<K, V> combineAndSkip(Map<K, V>... maps) {
        return combine((V v1, V v2) -> v1, maps);
    }

    /**
     * Create a map with the given values
     * @param k1 key
     * @param v1 value
     * @param <K> type of key
     * @param <V> type of value
     * @return the map
     */
    public static <K, V> Map<K, V> mapOf(K k1, V v1) {
        return mapOfEntries(entryOf(k1, v1));
    }

    /**
     * Create a map with the given values
     * @param k1 key
     * @param v1 value
     * @param k2 key
     * @param v2 value
     * @param <K> type of key
     * @param <V> type of value
     * @return the map
     */
    public static <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2) {
        return combine(
                mapOf(k1, v1),
                mapOf(k2, v2)
        );
    }

    /**
     *
     * @param k1 key
     * @param v1 value
     * @param k2 key
     * @param v2 value
     * @param k3 key
     * @param v3 value
     * @param <K> key type
     * @param <V> value type
     * @return a map with the values
     */
    public static <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3) {
        return combine(
                mapOf(k1, v1),
                mapOf(k2, v2),
                mapOf(k3, v3)
        );
    }

    /**
     *
     * @param k1 key
     * @param v1 value
     * @param k2 key
     * @param v2 value
     * @param k3 key
     * @param v3 value
     * @param k4 key
     * @param v4 value
     * @param <K> key type
     * @param <V> value type
     * @return a map with the values
     */
    public static <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        return combine(
                mapOf(k1, v1),
                mapOf(k2, v2),
                mapOf(k3, v3),
                mapOf(k4, v4)
        );
    }

    /**
     *
     * @param k1 key
     * @param v1 value
     * @param k2 key
     * @param v2 value
     * @param k3 key
     * @param v3 value
     * @param k4 key
     * @param v4 value
     * @param k5 key
     * @param v5 value
     * @param <K> key type
     * @param <V> value type
     * @return a map with the values
     */
    public static <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return combine(
                mapOf(k1, v1),
                mapOf(k2, v2),
                mapOf(k3, v3),
                mapOf(k4, v4),
                mapOf(k5, v5)
        );
    }

    /**
     *
     * @param k1 key
     * @param v1 value
     * @param k2 key
     * @param v2 value
     * @param k3 key
     * @param v3 value
     * @param k4 key
     * @param v4 value
     * @param k5 key
     * @param v5 value
     * @param k6 key
     * @param v6 value
     * @param <K> key type
     * @param <V> value type
     * @return a map with the values
     */
    public static <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6) {
        return combine(
                mapOf(k1, v1),
                mapOf(k2, v2),
                mapOf(k3, v3),
                mapOf(k4, v4),
                mapOf(k5, v5),
                mapOf(k6, v6)
        );
    }

    /**
     *
     * @param k1 key
     * @param v1 value
     * @param k2 key
     * @param v2 value
     * @param k3 key
     * @param v3 value
     * @param k4 key
     * @param v4 value
     * @param k5 key
     * @param v5 value
     * @param k6 key
     * @param v6 value
     * @param k7 key
     * @param v7 value
     * @param <K> key type
     * @param <V> value type
     * @return a map with the values
     */
    public static <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7) {
        return combine(
                mapOf(k1, v1),
                mapOf(k2, v2),
                mapOf(k3, v3),
                mapOf(k4, v4),
                mapOf(k5, v5),
                mapOf(k6, v6),
                mapOf(k7, v7)
        );
    }

    /**
     *
     * @param k1 key
     * @param v1 value
     * @param k2 key
     * @param v2 value
     * @param k3 key
     * @param v3 value
     * @param k4 key
     * @param v4 value
     * @param k5 key
     * @param v5 value
     * @param k6 key
     * @param v6 value
     * @param k7 key
     * @param v7 value
     * @param k8 key
     * @param v8 value
     * @param <K> key type
     * @param <V> value type
     * @return a map with the values
     */
    public static <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8) {
        return combine(
                mapOf(k1, v1),
                mapOf(k2, v2),
                mapOf(k3, v3),
                mapOf(k4, v4),
                mapOf(k5, v5),
                mapOf(k6, v6),
                mapOf(k7, v7),
                mapOf(k8, v8)
        );
    }

    /**
     *
     * @param k1 key
     * @param v1 value
     * @param k2 key
     * @param v2 value
     * @param k3 key
     * @param v3 value
     * @param k4 key
     * @param v4 value
     * @param k5 key
     * @param v5 value
     * @param k6 key
     * @param v6 value
     * @param k7 key
     * @param v7 value
     * @param k8 key
     * @param v8 value
     * @param k9 key
     * @param v9 value
     * @param <K> key type
     * @param <V> value type
     * @return a map with the values
     */
    public static <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9) {
        return combine(
                mapOf(k1, v1),
                mapOf(k2, v2),
                mapOf(k3, v3),
                mapOf(k4, v4),
                mapOf(k5, v5),
                mapOf(k6, v6),
                mapOf(k7, v7),
                mapOf(k8, v8),
                mapOf(k9, v9)
        );
    }

    /**
     *
     * @param k1 key
     * @param v1 value
     * @param k2 key
     * @param v2 value
     * @param k3 key
     * @param v3 value
     * @param k4 key
     * @param v4 value
     * @param k5 key
     * @param v5 value
     * @param k6 key
     * @param v6 value
     * @param k7 key
     * @param v7 value
     * @param k8 key
     * @param v8 value
     * @param k9 key
     * @param v9 value
     * @param k10 key
     * @param v10 value
     * @param <K> key type
     * @param <V> value type
     * @return a map with the values
     */
    public static <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K k10, V v10) {
        return combine(
                mapOf(k1, v1),
                mapOf(k2, v2),
                mapOf(k3, v3),
                mapOf(k4, v4),
                mapOf(k5, v5),
                mapOf(k6, v6),
                mapOf(k7, v7),
                mapOf(k8, v8),
                mapOf(k9, v9),
                mapOf(k10, v10)
        );
    }
}
