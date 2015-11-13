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

import java.util.List;
import java.util.Map;

import static com.nitorcreations.streams.Fixture.entriesWithDuplicates;
import static com.nitorcreations.streams.Fixture.entries;
import static com.nitorcreations.streams.Fixture.listWithDuplicates;
import static com.nitorcreations.streams.NCollectors.countingOccurrences;
import static com.nitorcreations.streams.NCollectors.entriesToMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.emptyList;

public class NCollectorsTest {

    //
    // COUNT OCCURRENCES
    //

    @Test
    public void countingOccurrencesWorks() {
        final Map<String, Long> occurrences = listWithDuplicates.stream().collect(countingOccurrences());
        assertThat(occurrences)
                .hasSize(4)
                .containsEntry("Foo", 4L)
                .containsEntry("Bar", 1L)
                .containsEntry("Baz", 2L)
                .containsEntry("Qux", 1L);
    }

    @Test
    public void countingOccurencesEmptyList() {
        final List<String> strings = emptyList();
        final Map<String, Long> occurrences = strings.stream().collect(countingOccurrences());
        assertThat(occurrences).isEmpty();
    }

    //
    // COLLECT ENTRIES TO MAP
    //

    @Test(expected = IllegalStateException.class)
    public void collectingEntriesWithDuplicatesToMap() {
        entriesWithDuplicates.stream().collect(entriesToMap());
    }

    @Test
    public void collectingEntriesWithDuplicatesWithMapping() {
        final Map<String, Integer> map = entriesWithDuplicates.stream().collect(entriesToMap((a, b) -> a + b));
        assertThat(map)
                .hasSize(3)
                .containsEntry("Eka", 6) // Summed
                .containsEntry("Toka", 4)
                .containsEntry("Kolmas", 5);
    }

    @Test
    public void collectingEntriesWithoutDuplicates() {
        final Map<String, Integer> map = entries.stream().distinct().collect(entriesToMap());
        assertThat(map)
                .hasSize(3)
                .containsEntry("Eka", 3)
                .containsEntry("Toka", 4)
                .containsEntry("Kolmas", 5);
    }
}