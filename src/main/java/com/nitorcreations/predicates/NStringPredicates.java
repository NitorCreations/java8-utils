package com.nitorcreations.predicates;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

import static com.nitorcreations.predicates.NPredicates.not;
import static com.nitorcreations.predicates.NPredicates.notNull;

public final class NStringPredicates {
    private NStringPredicates() { /** prevent instantiation */}

    /**
     * Null-safely check that the string starts with the prefix. The predicate returns {@code false} if
     * target is {@code null}.
     *
     * @param prefix the prefix to match
     * @return predicate
     */
    public static Predicate<String> startsWith(String prefix) {
        return NPredicates.<String>notNull().and(s -> s.startsWith(prefix));
    }

    /**
     * Null-safely check that the string ends with the suffix. The predicate returns {@code false} if
     * target is {@code null}.
     *
     * @param suffix the suffix to match
     * @return predicate
     */
    public static Predicate<String> endsWith(String suffix) {
        return NPredicates.<String>notNull().and(s -> s.endsWith(suffix));
    }

    /**
     * Check that the non-null string is either empty or contains only whitespace. The predicate returns {@code true} if
     * target is {@code null}.
     *
     * @return predicate
     */
    public static Predicate<String> isBlank() {
        return NPredicates.<String>isNull().or(s -> s.trim().isEmpty());
    }

    /**
     * Check that the non-null string is either empty or contains only whitespace. The predicate returns {@code false} if
     * target is {@code null}.
     *
     * @return predicate
     */
    public static Predicate<String> notBlank() {
        return not(isBlank());
    }

    /**
     * Check that the non-null string contains the substring
     *
     * @param substring the string to find
     * @return predicate
     */
    public static Predicate<String> contains(String substring) {
        return containsAll(substring);
    }

    /**
     * Check that the non-null string contains all of the substrings in any order
     *
     * @param substring the strings to find
     * @return predicate
     */
    public static Predicate<String> containsAll(String... substring) {
        return NPredicates.<String> notNull()
                .and(s -> Arrays.stream(substring)
                        .filter(Objects::nonNull)
                        .map(s::contains)
                        .reduce(true, Boolean::logicalAnd));
    }

    /**
     * Check that the non-null string contains any of the substrings
     *
     * @param substring the strings to find
     * @return predicate
     */
    public static Predicate<String> containsAny(String... substring) {
        return NPredicates.<String> notNull()
                .and(s -> Arrays.stream(substring)
                        .filter(Objects::nonNull)
                        .anyMatch(s::contains));
    }

    /**
     * Check that the non-null string does not contain the substring. The predicate will return {@code false}
     * is target is {@code null}
     *
     * @param substring the string to find
     * @return predicate
     */
    public static Predicate<String> doesNotContain(String substring) {
        return doesNotContainAnyOf(substring);
    }

    /**
     * Check that the non-null string does not contain any of the the substrings. The predicate will return {@code false}
     * is target is {@code null}
     *
     * @param substring the string to find
     * @return predicate
     */
    public static Predicate<String> doesNotContainAnyOf(String... substring) {
        return not(containsAny(substring)).and(notNull());
    }
}
