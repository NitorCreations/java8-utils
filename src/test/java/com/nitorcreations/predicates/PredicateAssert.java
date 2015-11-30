package com.nitorcreations.predicates;

import org.assertj.core.api.AbstractAssert;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static com.nitorcreations.predicates.NPredicates.not;
import static java.util.stream.Collectors.toList;

public class PredicateAssert<T> extends AbstractAssert<PredicateAssert<T>, Predicate<T>> {

    public PredicateAssert(Predicate<T> actual) {
        super(actual, PredicateAssert.class);
    }

    public static <T> PredicateAssert<T> assertThat(Predicate<T> predicate) {
        return new PredicateAssert<>(predicate);
    }

    public PredicateAssert<T> matches(T testable) {
        isNotNull();
        if (!actual.test(testable)) {
            failWithMessage("Expected predicate to match <%s>, but failed", testable);
        }
        return this;
    }

    public PredicateAssert<T> doesNotMatch(T testable) {
        isNotNull();
        if (actual.test(testable)) {
            failWithMessage("Expected predicate not to match <%s>, but matched", testable);
        }
        return this;
    }

    @SafeVarargs
    public final PredicateAssert<T> matchesAll(T... testables) {
        isNotNull();
        final List<T> errors = Arrays.stream(testables)
                .filter(not(actual))
                .collect(toList());
        if (errors.size() > 0) {
            failWithMessage("Expected predicate to match all of <%s>, but did not match <%s>", Arrays.asList(testables), errors);
        }
        return this;
    }

    @SafeVarargs
    public final PredicateAssert<T> matchesNone(T... testables) {
        isNotNull();
        final List<T> errors = Arrays.stream(testables)
                .filter(actual)
                .collect(toList());
        if (errors.size() > 0) {
            failWithMessage("Expected predicate not to match any of <%s>, but matched <%s>", Arrays.asList(testables), errors);
        }
        return this;
    }
}
