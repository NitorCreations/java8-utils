package com.nitorcreations.predicates;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static com.nitorcreations.TestUtils.invokePrivateConstructor;
import static com.nitorcreations.predicates.NComparablePredicates.*;
import static com.nitorcreations.predicates.PredicateAssert.assertThat;

public class NComparablePredicatesTest {

    @Test
    public void forCoverage() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        invokePrivateConstructor(NComparablePredicates.class);
    }

    @Test
    public void testLessThan() {
        assertThat(lt(10))
                .matchesAll(Integer.MIN_VALUE, -1, 0, 1, 9)
                .matchesNone(10, 11, Integer.MAX_VALUE);
    }

    @Test
    public void testLessThanOrEqual() {
        assertThat(lte(10))
                .matchesAll(Integer.MIN_VALUE, -1, 0, 1, 9, 10)
                .matchesNone(11, Integer.MAX_VALUE);
    }

    @Test
    public void testGreaterThan() {
        assertThat(gt(10))
                .matchesNone(Integer.MIN_VALUE, -1, 0, 1, 9, 10)
                .matchesAll(11, Integer.MAX_VALUE);
    }

    @Test
    public void testGreaterThanOrEqual() {
        assertThat(gte(10))
                .matchesNone(Integer.MIN_VALUE, -1, 0, 1, 9)
                .matchesAll(10, 11, Integer.MAX_VALUE);
    }
}