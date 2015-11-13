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

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public final class NitorExtractors {
    private NitorExtractors() { /** prevent instantiation */}

    public static <F, T> Stream<T> doWithKeys(Map<F, ?> map, Function<F, T> fn) {
        return map.keySet().stream().map(fn);
    }

    public static <F, T> Set<T> extractKeysToSet(Map<F, ?> map, Function<F, T> fn) {
        return doWithKeys(map, fn).collect(toSet());
    }

    public static <F, T> List<T> extractKeysToList(Map<F, ?> map, Function<F, T> fn) {
        return doWithKeys(map, fn).collect(toList());
    }

    public static <F, T> Stream<T> doWithValues(Map<?, F> map, Function<F, T> fn) {
        return map.values().stream().map(fn);
    }

    public static <F, T> Set<T> extractValuesToSet(Map<?, F> map, Function<F, T> fn) {
        return doWithValues(map, fn).collect(toSet());
    }

    public static <F, T> List<T> extractValuesToList(Map<?, F> map, Function<F, T> fn) {
        return doWithValues(map, fn).collect(toList());
    }
}
