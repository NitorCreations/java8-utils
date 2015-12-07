package com.nitorcreations.collections;

import com.nitorcreations.streams.NStreams;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;

public final class NSets {

    private NSets() { /** prevent instantiation */}

    /**
     * Create a new {@link HashSet} with the given values
     *
     * @param values the values to add to the set.
     * @param <V> the type of the element
     * @return the set containing the values
     */
    @SafeVarargs
    public static <V> Set<V> asSet(V... values) {
        return new HashSet<>(asList(values));
    }

    /**
     * Create a new set with the values of the given iterable
     *
     * @param values the values to add to the set.
     * @param <V> the type of the element
     * @return the set containing the values
     */
    public static <V> Set<V> asSet(Iterable<V> values) {
        return NStreams.asStream(values).collect(toSet());
    }

    /**
     * Create a new set with the values of the given iterator
     *
     * @param iterator the values to add to the set.
     * @param <V> the type of the element
     * @return the set containing the values
     */
    public static <V> Set<V> asSet(Iterator<V> iterator) {
        return asSet(() -> iterator);
    }
}
