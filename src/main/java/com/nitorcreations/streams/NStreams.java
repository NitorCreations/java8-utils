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

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class NStreams {
    private NStreams() { /** prevent instantiation */}

    public static <T> Stream<T> asStream(Optional<T> optional) {
        return optional.map(Stream::of).orElse(Stream.empty());
    }

    public static <T> Stream<T> asStream(Iterator<T> iterator) {
        return asStream(iterator, false);
    }

    public static <T> Stream<T> asStream(Iterator<T> iterator, boolean parallel) {
        Iterable<T> iterable = () -> iterator;
        return StreamSupport.stream(iterable.spliterator(), parallel);
    }

    /**
     * Concatenate the given streams to a single stream. Follows the semantics
     * of {@link Stream#concat(Stream, Stream)}.
     * @param streams the streams to concatenate
     * @param <T> type of the stream element
     * @return a stream containing all of the elements in all of the streams
     */
    @SafeVarargs
    public static <T> Stream<T> concat(Stream<T> ...streams) {
      return Stream.of(streams)
              .filter(Objects::nonNull)
              .reduce(Stream::concat).get();
    }
}
