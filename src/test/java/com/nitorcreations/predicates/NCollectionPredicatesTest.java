package com.nitorcreations.predicates;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;

import static com.nitorcreations.test.TestUtils.invokePrivateConstructor;
import static com.nitorcreations.collections.NSets.asSet;
import static com.nitorcreations.predicates.NCollectionPredicates.*;
import static com.nitorcreations.test.Assertions.assertThat;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class NCollectionPredicatesTest {

    @Test
    public void forCoverage() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        invokePrivateConstructor(NCollectionPredicates.class);
    }

    @Test
    public void testEmpty() {
        assertThat(empty())
                .matchesAll(new HashSet<>(), new ArrayList<>())
                .matchesNone(null, singletonList(1), asSet(1));
    }

    @Test
    public void testNotEmpty() {
        assertThat(notEmpty())
                .matchesAll(singletonList(1), asSet(1))
                .matchesNone(null, new HashSet<>(), new ArrayList<>());
    }

    @Test
    public void testContains() {
        final Long num = 666_666L;
        assertThat(contains(num))
                .matchesAll(asList(1L, num), asSet(num), singletonList(666_666L))
                .matchesNone(null, emptyList(), singletonList(113L));
    }

    @Test
    public void testContainsAll() {
        final Long n1 = 123_123L;
        final Long n2 = 321_321L;
        assertThat(containsAll(n1, n2))
                .matchesAll(asList(n1, n2), asList(n2, n1), asSet(n1, n2))
                .matchesNone(null, emptyList(), singletonList(113L));
    }

    @Test
    public void testContainsAny() {
        final Long n1 = 123_123L;
        final Long n2 = 321_321L;
        assertThat(containsAny(n1, n2))
                .matchesAll(singletonList(n1), singletonList(n2), asList(n1, n2), asList(n2, n1), asSet(n1, n2))
                .matchesNone(null, emptyList(), singletonList(113L));
    }

    @Test
    public void testDoesNotContain() {
        final Long num = 666_666L;
        assertThat(doesNotContain(num))
                .matchesAll(emptyList(), singletonList(113L))
                .matchesNone(null, asList(1L, num), asSet(num), singletonList(666_666L));
    }

    @Test
    public void testDoesNotContainAnyOf() {
        assertThat(doesNotContainAnyOf(123L, 321L))
                .matchesAll(emptyList(), singletonList(113L))
                .matchesNone(null, asList(1L, 123L), asSet(321L), asList(2L, 3L, 123L, 321L));
    }

    @Test
    public void testDoesNotContainAllOf() {
        assertThat(doesNotContainAllOf(123L, 321L))
                .matchesAll(emptyList(), singletonList(123L), singletonList(321L))
                .matchesNone(null, asList(2L, 3L, 123L, 321L));
    }
}