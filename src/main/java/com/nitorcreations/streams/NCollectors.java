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

import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collector;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public final class NCollectors {
    private NCollectors() { /** prevent instantiation */}

    public static <T> Collector<T, ?, Map<T, Long>> countingOccurrences() {
        return groupingBy(identity(), counting());
    }

    /**
     * NOTE: will throw exception on duplicate keys. See {@link #entriesToMap(BinaryOperator)} to
     * cope with this.
     */
    public static <K,V> Collector<Map.Entry<K,V>, ?, Map<K, V>> entriesToMap() {
        return toMap(
                entry -> entry.getKey(),
                entry -> entry.getValue()
        );
    }

    public static <K,V> Collector<Map.Entry<K,V>, ?, Map<K, V>> entriesToMap(BinaryOperator<V> mergeFn) {
        return toMap(
                entry -> entry.getKey(),
                entry -> entry.getValue(),
                mergeFn
        );
    }
}
