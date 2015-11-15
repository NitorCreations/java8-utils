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

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.nitorcreations.predicates.NOptionalPredicates.empty;
import static com.nitorcreations.predicates.NOptionalPredicates.havingValue;
import static com.nitorcreations.predicates.NOptionalPredicates.present;
import static com.nitorcreations.predicates.NPredicates.having;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class NOptionalPredicatesTest {

    private final Optional<String> emptyOpt = Optional.empty();
    private final Optional<String> opt = Optional.of("Foo");

    @Test
    public void testEmpty() {
        assertThat(empty().test(emptyOpt)).isTrue();
        assertThat(empty().test(opt)).isFalse();
    }

    @Test
    public void testPresent() {
        assertThat(present().test(opt)).isTrue();
        assertThat(present().test(emptyOpt)).isFalse();
    }

    @Test
    public void testHavingValue() {
        assertThat(havingValue("Foo").test(opt)).isTrue();
        assertThat(havingValue("Bar").test(opt)).isFalse();

        assertThat(havingValue((String) null).test(opt)).isFalse();
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