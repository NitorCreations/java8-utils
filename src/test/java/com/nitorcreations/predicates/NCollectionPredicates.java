package com.nitorcreations.predicates;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.nitorcreations.predicates.NPredicates.*;
import static com.nitorcreations.streams.NStreams.asStream;

public class NCollectionPredicates {
    private NCollectionPredicates() { /** prevent instantiation */}


    /**
     * Checks that the iterable is non-null and not empty
     * @param <T> type of an element
     * @param <S> type of the iterable
     * @return predicate
     */
    public static <T, S extends Iterable<T>> Predicate<S> notEmpty() {
        return NPredicates.<S>notNull().and(s -> s.iterator().hasNext());
    }

    /**
     * Checks that the iterable is non-null and empty
     * @param <T> type of an element
     * @param <S> type of the iterable
     * @return predicate
     */
    public static <T, S extends Iterable<T>> Predicate<S> empty() {
        return NPredicates.<S>notNull().and(s -> !s.iterator().hasNext());
    }

    /**
     * Checks that the iterable is non-null and contains target element (comparison by {@code #equals})
     * @param <T> type of an element
     * @param <S> type of the iterable
     * @return predicate
     */
    public static <T, S extends Iterable<T>> Predicate<S> contains(T element) {
        return NPredicates.<S>notNull().and(it -> asStream(it).anyMatch(equalTo(element)));
    }

    /**
     * Checks that the iterable is non-null and contains all of the target elements (comparison by {@code #equals})
     * @param <T> type of an element
     * @param <S> type of the iterable
     * @return predicate
     */
    public static <T, S extends Iterable<T>> Predicate<S> containsAll(T... elements) {
        final Predicate<S> allmatch = Stream.of(elements)
                .map(NCollectionPredicates::<T, S>contains)
                .reduce(notEmpty(), (p1, p2) -> p1.and(p2));
        return  NPredicates.<S>notNull().and(allmatch);
    }

    /**
     * Checks that the iterable is non-null and contains any of the target elements (comparison by {@code #equals})
     * @param <T> type of an element
     * @param <S> type of the iterable
     * @return predicate
     */
    public static <T, S extends Iterable<T>> Predicate<S> containsAny(T... elements) {
        final Predicate<S> anyMatches = Arrays.stream(elements)
                .map(NCollectionPredicates::<T, S>contains)
                .reduce(never(), (p1, p2) -> p1.or(p2));
        return NPredicates.<S>notNull().and(anyMatches);
    }


    /**
     * Checks that the iterable is non-null and does not contain the target element (comparison by {@code #equals})
     * @param <T> type of an element
     * @param <S> type of the iterable
     * @return predicate
     */
    public static <T, S extends Iterable<T>> Predicate<S> doesNotContain(T element) {
        return doesNotContainAnyOf(element);
    }

    /**
     * Checks that the iterable is non-null and contains none of target elements
     * @param <T> type of an element
     * @param <S> type of the iterable
     * @return predicate
     */
    public static <T, S extends Iterable<T>> Predicate<S> doesNotContainAnyOf(T... elements) {
        return NPredicates.<S>notNull().and(not(containsAny(elements)));
    }
}
