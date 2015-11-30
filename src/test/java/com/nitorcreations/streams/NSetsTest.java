package com.nitorcreations.streams;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import static com.nitorcreations.streams.NSets.asSet;
import static com.nitorcreations.streams.TestUtils.invokePrivateConstructor;
import static org.assertj.core.api.Assertions.assertThat;

public class NSetsTest {

    @Test
    public void forCoverage() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        invokePrivateConstructor(NSets.class);
    }

    @Test
    public void testAsSet() {
        final Set<String> strings = asSet("foo", "bar", "baz");
        assertThat(strings).contains("foo", "bar", "baz");
    }

    @Test
    public void testAsSet_nullValues() {
        final Set<String> foo = asSet(null, null, null, "Foo");
        assertThat(foo).hasSize(2).contains(null, "Foo");
    }
}