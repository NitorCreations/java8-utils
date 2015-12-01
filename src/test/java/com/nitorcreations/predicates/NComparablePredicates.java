package com.nitorcreations.predicates;

import java.util.function.Predicate;

public final class NComparablePredicates {

    private NComparablePredicates() { /** prevent instantiation */}

    /**
     * Matches when target is less than {@code other}
     * @param other the number to compare to
     * @param <T> type of comparables
     * @return predicate
     */
    public static <T> Predicate<Comparable<T>> lt(T other) {
        return lessThan(other);
    }

    /**
     * Matches when target is less than or equal to {@code other}
     * @param other the number to compare to
     * @param <T> type of comparables
     * @return predicate
     */
    public static <T> Predicate<Comparable<T>> lte(T other) {
        return lessThanOrEqualTo(other);
    }

    /**
     * Matches when target is less than {@code other}
     * @param other the number to compare to
     * @param <T> type of comparables
     * @return predicate
     */
    public static <T> Predicate<Comparable<T>> lessThan(T other) {
        return a -> a.compareTo(other) < 0;
    }

    /**
     * Matches when target is less than or equal to {@code other}
     * @param other the number to compare to
     * @param <T> type of comparables
     * @return predicate
     */
    public static <T> Predicate<Comparable<T>> lessThanOrEqualTo(T other) {
        return a -> a.compareTo(other) <= 0;
    }

    /**
     * Matches when target is greater than {@code other}
     * @param other the number to compare to
     * @param <T> type of comparables
     * @return predicate
     */
    public static <T> Predicate<Comparable<T>> gt(T other) {
        return greaterThan(other);
    }

    /**
     * Matches when target is greater than or equal to {@code other}
     * @param other the number to compare to
     * @param <T> type of comparables
     * @return predicate
     */
    public static <T> Predicate<Comparable<T>> gte(T other) {
        return greaterThanOrEqualTo(other);
    }

    /**
     * Matches when target is greater than {@code other}
     * @param other the number to compare to
     * @param <T> type of comparables
     * @return predicate
     */
    public static <T> Predicate<Comparable<T>> greaterThan(T other) {
        return a -> a.compareTo(other) > 0;
    }

    /**
     * Matches when target is greater than or equal to {@code other}
     * @param other the number to compare to
     * @param <T> type of comparables
     * @return predicate
     */
    public static <T> Predicate<Comparable<T>> greaterThanOrEqualTo(T other) {
        return a -> a.compareTo(other) >= 0;
    }
}
