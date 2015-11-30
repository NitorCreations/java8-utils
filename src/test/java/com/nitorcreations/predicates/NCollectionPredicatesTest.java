package com.nitorcreations.predicates;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;

import static com.nitorcreations.TestUtils.invokePrivateConstructor;
import static com.nitorcreations.collections.NSets.asSet;
import static com.nitorcreations.predicates.NCollectionPredicates.*;
import static com.nitorcreations.predicates.PredicateAssert.assertThat;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public class NCollectionPredicatesTest {

    @Test
    public void forCoverage() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        invokePrivateConstructor(NCollectionPredicates.class);
    }

    @Test
    public void testEmpty() {
        assertThat(empty())
                .matchesAll(new HashSet<>(), new ArrayList<>())
                .matchesNone(null, asList(1), asSet(1));
    }

    @Test
    public void testNotEmpty() {
        assertThat(notEmpty())
                .matchesAll(asList(1), asSet(1))
                .matchesNone(null, new HashSet<>(), new ArrayList<>());
    }

    @Test
    public void testContains() {
        final Long num = 666_666l;
        assertThat(contains(num))
                .matchesAll(asList(1l, num), asSet(num), asList(666_666l))
                .matchesNone(null, emptyList(), asList(113l));
    }

    @Test
    public void testContainsAll() {
        final Long n1 = 123_123l;
        final Long n2 = 321_321l;
        assertThat(containsAll(n1, n2))
                .matchesAll(asList(n1, n2), asList(n2, n1), asSet(n1, n2))
                .matchesNone(null, emptyList(), asList(113l));
    }

    @Test
    public void testContainsAny() {
        final Long n1 = 123_123l;
        final Long n2 = 321_321l;
        assertThat(containsAny(n1, n2))
                .matchesAll(asList(n1), asList(n2), asList(n1, n2), asList(n2, n1), asSet(n1, n2))
                .matchesNone(null, emptyList(), asList(113l));
    }

    @Test
    public void testDoesNotContain() {
        final Long num = 666_666l;
        assertThat(doesNotContain(num))
                .matchesAll(emptyList(), asList(113l))
                .matchesNone(null, asList(1l, num), asSet(num), asList(666_666l));
    }

    @Test
    public void testDoesNotContainAnyOf() {
        assertThat(doesNotContainAnyOf(123l, 321l))
                .matchesAll(emptyList(), asList(113l))
                .matchesNone(null, asList(1l, 123l), asSet(321l), asList(2l, 3l, 123l, 321l));
    }

}