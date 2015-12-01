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

import java.util.Optional;
import java.util.function.Predicate;

import static com.nitorcreations.predicates.NPredicates.equalTo;
import static com.nitorcreations.predicates.NPredicates.not;

public final class NOptionalPredicates {
    private NOptionalPredicates() { /** prevent instantiation */}

    /**
     * Predicate that checks if optional is empty
     * @param <T> type of containing optional
     * @return the predicate
     */
    public static <T> Predicate<Optional<? extends T>> empty() {
        return not(present());
    }

    /**
     * Predicate that checks if optional is present
     * @param <T> type of containing optional
     * @return the predicate
     */
    public static <T> Predicate<Optional<? extends T>> present() {
        return x -> x.isPresent();
    }


    /**
     * Predicate that checks if value in optional matches target.
     * Returns {@code false} if value not present.
     * @param predicate the predicate to apply to the optional's contents
     * @param <T> type of containing optional
     * @return the predicate
     */
    public static <T> Predicate<Optional<? extends T>> havingValue(Predicate<T> predicate) {
        return NOptionalPredicates.<T>present().and(o -> predicate.test(o.get()));
    }

    /**
     * Shorthand for {@code havingValue(equalTo(value))}
     * @param value the value to compare the contents to
     * @param <T> type of containing optional
     * @return the predicate
     */
    public static <T> Predicate<Optional<? extends T>> havingValue(T value) {
        return havingValue(equalTo(value));
    }

    /**
     * Shorthand for {@code havingValue(not(predicate))}
     * @param predicate the predicate to apply to the optional's contents
     * @param <T> type of containing optional
     * @return the predicate
     */
    public static <T> Predicate<Optional<? extends T>> notHavingValue(Predicate<T> predicate) {
        return havingValue(not(predicate));
    }

    /**
     * Shorthand for {@code havingValue(not(equalTo(value)))}
     * @param value the value to compare the contents to
     * @param <T> type of containing optional
     * @return the predicate
     */
    public static <T> Predicate<Optional<? extends T>> notHavingValue(T value) {
        return notHavingValue(equalTo(value));
    }
}
