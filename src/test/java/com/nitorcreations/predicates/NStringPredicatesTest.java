package com.nitorcreations.predicates;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static com.nitorcreations.TestUtils.invokePrivateConstructor;
import static com.nitorcreations.predicates.NStringPredicates.*;
import static com.nitorcreations.predicates.PredicateAssert.assertThat;

public class NStringPredicatesTest {

    @Test
    public void forCoverage() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        invokePrivateConstructor(NStringPredicates.class);
    }

    @Test
    public void testStartsWith() {
        assertThat(startsWith("foo"))
                .matchesAll("foo", "foobar", "foooo")
                .matchesNone("bar", "fo", "", null);
    }

    @Test
    public void testEndsWith() {
        assertThat(endsWith("bar"))
                .matchesAll("bar", "foobar", "foooobarbar")
                .matchesNone("barr", "byar", "", null);
    }

    @Test
    public void testIsBlank() {
        assertThat(isBlank())
                .matchesAll("", "     ", "\n \n", null)
                .matchesNone("a", "\n\na");
    }

    @Test
    public void testNotBlank() {
        assertThat(notBlank())
                .matchesAll("a", "\n\na")
                .matchesNone(null, "", "     ", "\n \n");
    }

    @Test
    public void testContains() {
        assertThat(contains("foo"))
                .matchesAll("foo", "foobar", "barquxfoo", "osdfooasd")
                .matchesNone("bar", "", null, "fo o");
    }

    @Test
    public void testContainsAll() {
        assertThat(containsAll("foo", "oof"))
                .matchesAll("foof", "foobaroof", "foooooooof", "foo roof")
                .matchesNone("foo", "oof", "", null, "fo of");
    }

    @Test
    public void testContainsAll_emptyContains() {
        assertThat(containsAll())
                .matchesAll("", "sadfdsf", "osvfibjisf osbjoiab oj")
                .matchesNone((String) null);
    }

    @Test
    public void testContainsAny() {
        assertThat(containsAny("foo", "oof"))
                .matchesAll("foo", "oof", "foof", "foobaroof", "foooooooof", "foo roof")
                .matchesNone("", null, "fo of");
    }

    @Test
    public void testDoesNotContain() {
        assertThat(doesNotContain("foo"))
                .matchesAll("", "bar", "baz", "of", "fo", "fo of")
                .matchesNone(null, "foo", "foobar", "ooffoo", "barfoo", "barfooazx");
    }

    @Test
    public void testDoesNotContainAnyOf() {
        assertThat(doesNotContainAnyOf("foo", "bar"))
                .matchesAll("", "baz", "of", "fo", "fo of")
                .matchesNone(null, "foo", "bar", "foobar", "ooffoo", "barfoo", "barfooazx");
    }

    @Test
    public void testDoesNotContainAllOf() {
        assertThat(doesNotContainAllOf("foo", "bar"))
                .matchesAll("", "baz", "of", "fo", "fo of", "foo ba", "fo bar", "foo", "bar")
                .matchesNone(null, "foobar", "barfoo", "barfooazx");
    }

}