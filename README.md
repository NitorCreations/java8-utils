# java8-utils [![Build Status](https://travis-ci.org/NitorCreations/java8-utils.svg?branch=master)](https://travis-ci.org/NitorCreations/java8-utils) [![Coverage Status](https://coveralls.io/repos/NitorCreations/java8-utils/badge.svg?branch=master&service=github)](https://coveralls.io/github/NitorCreations/java8-utils?branch=master) [![Maven Central](https://img.shields.io/maven-central/v/com.nitorcreations/java8utils.svg)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.nitorcreations%22%20AND%20a%3A%22java8utils%22)

The package provides helper methods for interacting with, e.g., the stream features of Java 8 and above.

## Stream helpers

Transforming to streams:

    NStreams.asStream(Optional<T>)
    NStreams.asStream(Iterable<T>)
    NStreams.asStream(Iterator<T>)
    
Concatenating multiple streams:

    NStreams.concat(Stream...)

### Collectors

Collecting elements of streams:

    Map<Integer,Long> map = Stream.of(1, 1, 1, 3, 3, 6)
        .collect(NCollectors.countingOccurrences());
    // {1=3, 3=2, 6=1}
    
## Predicates

Common predicates include, e.g.

    NPredicates.is(T)               // compare using ==
    NPredicates.equalTo(T)          // compare using #equals
    NPredicates.not(Predicate<T>)   // invert result of predicate
    ...
    NPredicates.having(Function<T, S>, Predicate<S>) // for matching, e.g., properties in a stream
    
Example of `having` predicate usage:

    Stream.of("foo", "foobar", "barbar", "qux")
        .filter(s -> s.startsWith("f"))
        .filter(havingEqual(String::length, 3))
        .collect(toList()); // -> List("foo") 

Includes predicates for collections, strings, and comparables:

    NCollectionPredicates:
      .empty() / .notEmpty()
      .contains(T) / .containsAll(T...) / .containsAny(T...)
      .doesNotContain()T / .doesNotContainsAllOf(T...) / .doesNotContainAnyOf(T...)

Not forgetting the optionals: 

    NOptionalPredicates
      .empty() / .present()
      .havingValue(T) / .havingValue(Predicate<T>)
      .notHavingValue(T) / .notHavingValue(Predicate<T>)
 

## Set helpers

Helpers for creating sets:

    Set<V> set = NSets.asSet(Iterable<V>)
    Set<V> set = NSets.asSet(Iterator<V>)
    Set<V> set = NSets.asSet(V...)

## Map helpers

Helpers for creating map entries:

    Map.Entry<String,Integer> entry = NMappers.entryOf("a", 1);

Helpers for creating simple maps:

    NMaps.mapOfEntries(entryOf("a", 1), entryOf("c", 2), ...);
    NMaps.mapOf("a", 1, "c", 2, ...); // Up to ten entries
    NMaps.mapping(asList("a", "bee", "cheetah"), s -> s.length()); // Map from keys to values by function
    NMaps.mapOfLists(asList("a", "b", "c"), asList(1, 2, 3));
    NMaps.combine(mapOf("a", 1), mapOf("b", 2, "c", 3));

### Map entries as streams

A stream of `Map.Entry<K, V>` can be collected to a map by `NCollectors.entriesToMap()`:

    Stream<Entry<String, Integer>> stream = ...;
    Map<String, Integer> map = stream.collect(NCollectors.entriesToMap());
    
Combining entries on duplicate keys:

    Stream<Entry<String, Integer>> stream = ...;
    Map<String, Integer> stream.collect(NCollectors.entriesToMap((a, b) -> a+b);

#### Consuming and mapping streams of entries

Entries can be consumed and manipulated in a multitude of ways:

    // Just to show consumingEntry
    NMaps.mapOf("a", 1, "b", 2)
        .entrySet()
        .forEach(consumingEntry((key, val) -> System.out.printf("%s -> %d%n", key, val)));

Further ways to manipulate entries in a stream:

    Map<String, Integer> map = NMaps.mapOf("a", 1, "b", 2, "c", 3);
    final Map<String, Integer> mapped = map.entrySet().stream()
            .map(NMappers.mappingKey((k, v) -> k + v))
            .map(NMappers.mappingValue((k, v) -> v * v))
            .collect(NCollectors.entriesToMap());
    // Mapped now contains { "a1"=1; "b2"=4; "c3"=9 }

### Map extractors

Helper methods for consuming maps:
 
    Stream<T> keyStream = NExtractors.doWithKeys(Map<K, ?> map, Function<K, T> fn);
    Stream<T> valueStream = NExtractors.doWithValues(Map<?, V> map, Function<V, T> fn);
    
Keys and values to sets or lists:

    NExtractors.extractKeysToSet(Map<F, ?> map, Function<F, T> fn)
    NExtractors.extractKeysToList(Map<F, ?> map, Function<F, T> fn)
    
    NExtractors.extractValuesToSet(Map<?, F> map, Function<F, T> fn)
    NExtractors.extractValuesToList(Map<?, F> map, Function<F, T> fn)
