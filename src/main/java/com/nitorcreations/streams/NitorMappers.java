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

    public static <K, V, W> Function<Map.Entry<K,V>, Map.Entry<K, W>> mappingValue(BiFunction<K, V, W> mapper) {
        return entry -> new AbstractMap.SimpleImmutableEntry<>(entry.getKey(), mapper.apply(entry.getKey(), entry.getValue()));
    }

    public static <K, V, KK> Function<Map.Entry<K,V>, Map.Entry<KK, V>> mappingKey(BiFunction<K, V, KK> mapper) {
        return entry -> new AbstractMap.SimpleImmutableEntry<>(mapper.apply(entry.getKey(), entry.getValue()), entry.getValue());
    }

    public static <K, V, OUT> Function<Map.Entry<K,V>, OUT> mappingEntry(BiFunction<K, V, OUT> mapper) {
        return entry -> mapper.apply(entry.getKey(), entry.getValue());
    }

    public static <K, V> Consumer<Map.Entry<K,V>> consumingEntry(BiConsumer<K, V> consumer) {
        return entry -> consumer.accept(entry.getKey(), entry.getValue());
    }
}
