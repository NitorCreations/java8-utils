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

package com.nitorcreations.predicates;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public final class NPredicates {
    private NPredicates() { /** prevent instantiation */}

    /**
     * Predicate that checks if {@code value == x}
     */
    public static <T> Predicate<T> is(T value) {
        return x -> x == value;
    }

    /**
     * Predicate that checks {@code value.equals(x)}
     */
    public static <T> Predicate<T> equalTo(T value) {
        return x -> Objects.equals(value, x);
    }

    /**
     * Predicate that inverts the result of target predicate.
     */
    public static <T> Predicate<T> not(Predicate<T> predicate) {
        return x -> !predicate.test(x);
    }

    /**
     * Predicate that checks if {@code !(value == x)}
     */
    public static <T> Predicate<T> not(T value) {
        return not(is(value));
    }

    /**
     * Predicate that checks if {@code !value.equalTo(x)}
     */
    public static <T> Predicate<T> notEqualTo(T value) {
        return not(equalTo(value));
    }

    public static <T> Predicate<T> isNull() {
        return x -> x == null;
    }

    public static <T> Predicate<T> notNull() {
        return not(isNull());
    }

    /**
     * Predicate that checks if {@code test} matches the result of {@code fn}
     * @param fn the fn that is applied to the element under test
     * @param test the predicate applied to the result of {@code fn}
     * @param <T>
     * @param <S>
     * @return
     */
    public static <T, S> Predicate<T> having(Function<T, S> fn, Predicate<S> test) {
        Objects.requireNonNull(fn, "fn must be supplied");
        Objects.requireNonNull(test, "fn must be supplied");
        return x -> test.test(fn.apply(x));
    }

    public static <T, S> Predicate<T> havingEqual(Function<T, S> fn, S value) {
        Objects.requireNonNull(fn, "fn must be supplied");
        return x -> equalTo(value).test(fn.apply(x));
    }

    /**
     * Shorthand for {@code having(fn, equalTo(true))}
     * @see #having(Function, Predicate)
     * @see #equalTo(Object)
     */
    public static <T> Predicate<T> having(Function<T, Boolean> fn) {
        return having(fn, equalTo(true));
    }

    /**
     * Shorthand for {@code having(fn, equalTo(false))}
     * @see #having(Function, Predicate)
     * @see #equalTo(Object)
     */
    public static <T> Predicate<T> notHaving(Function<T, Boolean> fn) {
        return having(fn, equalTo(false));
    }
}
