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

package com.nitorcreations.streams;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.nitorcreations.streams.NStreams.asStream;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class NStreamsTest {

    private Optional<String> opt = Optional.of("Foo");
    private Optional<String> emptyOpt = Optional.empty();

    final List<Integer> list = asList(1, 2, 3, 4, 5);

    @Test
    public void testOptionalToStream() {
        final List<String> strings = asStream(opt).collect(toList());
        assertThat(strings).containsExactly("Foo");
    }

    @Test
    public void testEmptyOptionalToStream() {
        final List<String> strings = asStream(emptyOpt).collect(toList());
        assertThat(strings).isEmpty();
    }

    @Test
    public void iteratorToStream() {
        final List<Integer> actual = asStream(list.listIterator()).collect(toList());
        assertThat(actual).containsExactlyElementsOf(list);
    }

    @Test
    public void iteratorToStream_parallel() {
        final List<Integer> actual = asStream(list.iterator(), true).collect(toList());
        assertThat(actual)
                .containsOnlyElementsOf(list)
                .containsAll(list);
    }

    @Test
    public void test_concatenation_empty_lists() {
        List<String> emptylist = new ArrayList<>();
        List<String> combinedList = NStreams.concat(emptylist.stream(), emptylist.stream())
                .collect(toList());
        assertThat(combinedList).isEmpty();
    }

    @Test
    public void test_concatenation() {
        List<String> list1 = asList("1", "11");
        List<String> list2 = asList("2", "22");
        List<String> list3 = asList("3");

        List<String> combinedList = NStreams.concat(list1.stream(), list2.stream(), list3.stream()).collect(toList());
        assertThat(combinedList).containsExactly("1","11","2","22","3");
    }

    @Test(expected = NoSuchElementException.class)
    public void test_concatNulls() {
        NStreams.concat();
    }

}