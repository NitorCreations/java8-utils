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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.nitorcreations.streams.Fixture.linkedMap;
import static com.nitorcreations.streams.NCollectors.entriesToMap;
import static com.nitorcreations.streams.NMappers.*;
import static com.nitorcreations.streams.TestUtils.invokePrivateConstructor;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class NMappersTest {

    @Test
    public void forCoverage() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        invokePrivateConstructor(NMappers.class);
    }

    @Test
    public void test_mappingValue() {
        final Map<String, String> mapped = linkedMap.entrySet().stream()
                .map(mappingValue((key, val) -> key + val))
                .collect(entriesToMap());

        assertThat(mapped)
                .containsKeys("Eka", "Toka", "Kolmas", "Neljäs", "Viides", "Kuudes")
                .containsValues("Eka1", "Toka2", "Kolmas3", "Neljäs4", "Viides5", "Kuudes6");
    }

    @Test
    public void test_mappingKey() {
        final Map<String, Integer> mapped = linkedMap.entrySet().stream()
                .map(mappingKey((key, val) -> key + val))
                .collect(entriesToMap());

        assertThat(mapped)
                .containsKeys("Eka1", "Toka2", "Kolmas3", "Neljäs4", "Viides5", "Kuudes6")
                .containsValues(1,2,3,4,5,6);
    }

    @Test
    public void test_mappingEntry() {
        final List<String> combined = linkedMap.entrySet().stream()
                .map(mappingEntry((k, v) -> k + v))
                .collect(toList());

        assertThat(combined).containsExactly("Eka1", "Toka2", "Kolmas3", "Neljäs4", "Viides5", "Kuudes6");
    }

    @Test
    public void test_consumingEntry() {
        final List<String> combined = new ArrayList<>();
        linkedMap.entrySet().forEach(consumingEntry((k,v) -> {
            combined.add(k+v);
        }));
        assertThat(combined).containsExactly("Eka1", "Toka2", "Kolmas3", "Neljäs4", "Viides5", "Kuudes6");
    }
}