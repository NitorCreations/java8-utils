/*
 * Copyright 2015- Nitor Creations Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.nitorcreations.predicates;

import org.assertj.core.util.Strings;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static com.nitorcreations.predicates.NPredicates.*;
import static com.nitorcreations.test.Assertions.assertThat;
import static com.nitorcreations.test.TestUtils.invokePrivateConstructor;

public class NPredicatesTest {
    final Object o2 = new Object();
    final Object o1 = new Object();
    final Object nullObject = null;

    final String s1 = "Foobar";
    final String s2 = "Baz";
    final String nullString = null;

    @Test
    public void forCoverage() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        invokePrivateConstructor(NPredicates.class);
    }

    @Test
    public void testIs() {
        assertThat(is(o1).test(o1)).isTrue();
        assertThat(is(o2).test(o2)).isTrue();
        assertThat(is(o1).test(o2)).isFalse();
        assertThat(is(o2).test(o1)).isFalse();
        assertThat(is(null).test(null)).isTrue();
    }

    @Test
    public void testNot() {
        assertThat(not(o1).test(o1)).isFalse();
        assertThat(not(o2).test(o2)).isFalse();
        assertThat(not(o1).test(o2)).isTrue();
        assertThat(not(o2).test(o1)).isTrue();
        assertThat(not((Object)null).test(null)).isFalse();
    }

    @Test
    public void testEqualTo() {
        assertThat(equalTo(s1).test(s1)).isTrue();
        assertThat(equalTo(s2).test(s2)).isTrue();
        assertThat(equalTo(s1).test("Foobar")).isTrue();
        assertThat(equalTo("Baz").test(s2)).isTrue();
        assertThat(equalTo(s1).test(s2)).isFalse();
        assertThat(equalTo(s2).test(s1)).isFalse();
        assertThat(equalTo(null).test(null)).isTrue();
    }

    @Test
    public void testNotEqualTo() {
        assertThat(notEqualTo(s1).test(s1)).isFalse();
        assertThat(notEqualTo(s2).test(s2)).isFalse();
        assertThat(notEqualTo(s1).test("Foobar")).isFalse();
        assertThat(notEqualTo("Baz").test(s2)).isFalse();
        assertThat(notEqualTo(s1).test(s2)).isTrue();
        assertThat(notEqualTo(s2).test(s1)).isTrue();
        assertThat(notEqualTo(null).test(null)).isFalse();
    }

    @Test
    public void testIsNull() {
        assertThat(isNull().test(nullString)).isTrue();
        assertThat(isNull().test(s1)).isFalse();
    }

    @Test
    public void testNotNull() {
        assertThat(notNull().test(s1)).isTrue();
        assertThat(notNull().test(nullString)).isFalse();
    }

    @Test
    public void testHaving() {
        assertThat(having(String::length, is(3)).test(s1)).isFalse();
        assertThat(having(String::length, is(3)).test(s2)).isTrue();
    }

    @Test
    public void testHavingEqual() {
        assertThat(havingEqual(String::length, 3).test(s1)).isFalse();
        assertThat(havingEqual(String::length, 3).test(s2)).isTrue();
    }

    @Test
    public void testHavingShorthand() {
        assertThat(having(Strings::isNullOrEmpty).test(s1)).isFalse();
        assertThat(having(Strings::isNullOrEmpty).test(nullString)).isTrue();
    }

    @Test
    public void testNotHavingShorthand() {
        assertThat(notHaving(Strings::isNullOrEmpty).test(s1)).isTrue();
        assertThat(notHaving(Strings::isNullOrEmpty).test(nullString)).isFalse();
    }

    @Test
    public void testAllOf() {
        assertThat(allOf(notNull(), is(123l)))
                .matches(123l)
                .matchesNone(null, 122l);
    }

    @Test
    public void testAnyOf() {
        assertThat(anyOf(is(123l), isNull()))
                .matchesAll(null, 123l)
                .matchesNone(122l);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAllOf_emptyArgs() {
        allOf();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAnyOf_emptyArgs() {
        anyOf();
    }
}