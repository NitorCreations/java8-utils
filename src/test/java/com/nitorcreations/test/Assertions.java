package com.nitorcreations.test;

import java.util.function.Predicate;

public class Assertions extends org.assertj.core.api.Assertions {

    public static <T> PredicateAssert<T> assertThat(Predicate<T> predicate) {
        return PredicateAssert.assertThat(predicate);
    }
}
