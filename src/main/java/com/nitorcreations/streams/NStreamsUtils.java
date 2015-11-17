package com.nitorcreations.streams;

import java.util.stream.Stream;

public class NStreamsUtils {

    @SafeVarargs
    public static <T> Stream<T> concat(Stream<T> ...streams) {
      return Stream.of(streams).reduce(Stream::concat).get();
    }

}
