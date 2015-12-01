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

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.nitorcreations.predicates.NOptionalPredicates.*;
import static com.nitorcreations.predicates.NPredicates.having;
import static com.nitorcreations.test.TestUtils.invokePrivateConstructor;
import static java.util.stream.Collectors.toList;
import static com.nitorcreations.test.Assertions.assertThat;

public class NOptionalPredicatesTest {

    private final Optional<String> emptyOpt = Optional.empty();
    private final Optional<String> opt = Optional.of("Foo");

    @Test
    public void forCoverage() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        invokePrivateConstructor(NOptionalPredicates.class);
    }

    @Test
    public void testEmpty() {
        assertThat(empty()).matches(emptyOpt).doesNotMatch(opt);
    }

    @Test
    public void testPresent() {
        assertThat(present()).matches(opt).doesNotMatch(emptyOpt);
    }

    @Test
    public void testHavingValue() {
        assertThat(havingValue("Foo")).matches(opt);
        assertThat(havingValue("Bar")).doesNotMatch(opt);

        assertThat(havingValue((String) null)).doesNotMatch(opt);
    }

    @Test
    public void testNotHavingValue() {
        assertThat(notHavingValue("Foo")).doesNotMatch(opt);
        assertThat(notHavingValue("Bar")).matches(opt);

        assertThat(notHavingValue((String) null)).matches(opt);
    }

    @Test
    public void testThatWorksAsFilter() {
        final List<String> strings = Stream.of(emptyOpt, emptyOpt, emptyOpt, opt, opt, emptyOpt, opt)
                .filter(present())
                .map(Optional::get)
                .collect(toList());
        assertThat(strings).containsExactly("Foo", "Foo", "Foo");
    }

    @Test
    public void testChainingPredicates() {
        final List<String> strings = Stream.of(
                emptyOpt,
                Optional.of("1"),
                Optional.of("12"),
                Optional.of("123"),
                emptyOpt,
                Optional.of("1234"),
                Optional.of("12345"),
                Optional.of("123456"),
                emptyOpt
        ).filter(havingValue(having(Integer::parseInt, i -> i > 100)))
                .map(Optional::get)
                .collect(toList());

        assertThat(strings)
                .contains("123", "1234", "12345", "123456");
    }
}