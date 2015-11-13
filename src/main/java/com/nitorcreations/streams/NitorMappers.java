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
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public final class NitorMappers {
    private NitorMappers() { /** prevent instantiation */}

    /**
     * A function to map the values of an {@link java.util.Map.Entry} and return the modified entry. Provides the key to the mapper function as a convenience.
     * <p/>
     * Example:
     * <pre>{@code
     *   Map<String, Integer> map = singletonMap("foo", 1); // Or whatever
     *   Map<String, String> mapped = map.entrySet().stream()
     *                                   .map(mappingValue((key, value) -> "Value was: " + value))
     *                                   .collect(entriesToMap());
     * }</pre>
     *
     * @param mapper the mapping function for the values
     * @param <K> key type, will not change
     * @param <V> value source type
     * @param <W> value target type
     * @return a mapper from entry -> entry
     */
    public static <K, V, W> Function<Map.Entry<K,V>, Map.Entry<K, W>> mappingValue(BiFunction<K, V, W> mapper) {
        return entry -> entryOf(entry.getKey(), mapper.apply(entry.getKey(), entry.getValue()));
    }

    /**
     * A function to map the keys of an {@link java.util.Map.Entry} and return the modified entry. Provides the value to the mapper function as a convenience
     * <p/>
     * Example:
     * <pre>{@code
     *   Map<String, Integer> map = singletonMap("foo", 1); // Or whatever
     *   Map<Integer, Integer> mapped = map.entrySet().stream()
     *                                     .map(mappingKey((key, value) -> key.length()))
     *                                     .collect(NitorCollectors.entriesToMap());
     * }</pre>
     *
     * @param mapper the mapping function for the values
     * @param <K> key source type
     * @param <V> value type, will not change
     * @param <KK> key target type
     * @return a mapper from entry -> entry
     */
    public static <K, V, KK> Function<Map.Entry<K,V>, Map.Entry<KK, V>> mappingKey(BiFunction<K, V, KK> mapper) {
        return entry -> entryOf(mapper.apply(entry.getKey(), entry.getValue()), entry.getValue());
    }

    /**
     * A function to map the entries to any type.
     * <p/>
     * Example:
     * <pre>{@code
     *   Map<String, Integer> map = singletonMap("foo", 1); // Or whatever
     *   List<String> mapped = map.entrySet().stream()
     *                            .map(mappingEntry((key, value) -> key + value))
     *                            .collect(toList());
     * }</pre>
     *
     * @param mapper the mapping function for the values
     * @param <K> key type, will not change
     * @param <V> value type, will not change
     * @param <OUT> target type
     * @return a mapper from entry -> entry
     */
    public static <K, V, OUT> Function<Map.Entry<K,V>, OUT> mappingEntry(BiFunction<K, V, OUT> mapper) {
        return entry -> mapper.apply(entry.getKey(), entry.getValue());
    }

    /**
     * A convenience function to consume map entries, e.g., when you need transformations during stream processing
     * <p/>
     * Example:
     * <pre>{@code
     *   Map<String, Integer> map = singletonMap("foo", 1); // Or whatever
     *   map.entrySet().stream()
     *      .map(mappingValue((k, v) -> ...)
     *      .forEach(consumingEntry((key, value) -> ...));
     * }</pre>
     *
     * @param consumer the consumer function
     * @param <K> key type
     * @param <V> value type
     * @return a consumer for map entries
     */
    public static <K, V> Consumer<Map.Entry<K,V>> consumingEntry(BiConsumer<K, V> consumer) {
        return entry -> consumer.accept(entry.getKey(), entry.getValue());
    }

    /**
     * Convenience method to create a {@link java.util.AbstractMap.SimpleImmutableEntry}
     */
    public static <K,V> Map.Entry<K, V> entryOf(K key, V value) {
        return new AbstractMap.SimpleImmutableEntry<>(key, value);
    }
}
