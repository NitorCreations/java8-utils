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

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class Fixture {
    private Fixture() { /** prevent instantiation */}

    public static Map<String, Integer> linkedMap;

    public static List<String> listWithDuplicates = asList("Foo", "Bar", "Baz", "Qux", "Foo", "Foo", "Foo", "Baz");

    static {
        linkedMap = new LinkedHashMap<>();
        linkedMap.put("Eka", 1);
        linkedMap.put("Toka", 2);
        linkedMap.put("Kolmas", 3);
        linkedMap.put("Neljäs", 4);
        linkedMap.put("Viides", 5);
        linkedMap.put("Kuudes", 6);
    }

    public static List<Map.Entry<String, Integer>> entries = asList(
            entry("Eka", 1),
            entry("Eka", 2),
            entry("Eka", 3),
            entry("Toka", 4),
            entry("Kolmas", 5)
    );


    public static <K,V> Map.Entry<K, V> entry(K key, V value) {
        return new AbstractMap.SimpleImmutableEntry<>(key, value);
    }
}
