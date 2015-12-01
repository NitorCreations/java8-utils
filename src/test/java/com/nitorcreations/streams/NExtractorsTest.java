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

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static com.nitorcreations.streams.Fixture.linkedMap;
import static com.nitorcreations.streams.NExtractors.*;
import static com.nitorcreations.test.TestUtils.invokePrivateConstructor;
import static java.util.Collections.emptyMap;
import static java.util.function.Function.identity;
import static com.nitorcreations.test.Assertions.assertThat;

public class NExtractorsTest {

    @Test
    public void forCoverage() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        invokePrivateConstructor(NExtractors.class);
    }
    //
    // DO WITH KEYS
    //
    
    @Test
    public void extractKeysToSetWorks() {
        assertThat(extractKeysToSet(linkedMap, String::length)).contains(3,4,6);
        assertThat(extractKeysToSet(emptyMap(), String::length)).isEmpty();
    }

    @Test(expected = NullPointerException.class)
    public void extractKeysToSet_null() {
        extractKeysToSet(null, String::length);
    }

    @Test
    public void extractKeysToListWorks_keepsOrder() {
        assertThat(extractKeysToList(linkedMap, String::length))
                .containsExactly(3,4,6,6,6,6);
        assertThat(extractKeysToList(emptyMap(), String::length))
                .isEmpty();
    }

    @Test(expected = NullPointerException.class)
    public void extractKeysToList_null() {
        extractKeysToList(null, String::length);
    }
    
    //
    // DO WITH VALUES
    //

    @Test
    public void extractValuesToSetWorks() {
        assertThat(extractValuesToSet(linkedMap, identity())).contains(1,2,3,4,5,6);
        assertThat(extractValuesToSet(emptyMap(), String::length)).isEmpty();
    }

    @Test(expected = NullPointerException.class)
    public void extractValuesToSet_null() {
        extractValuesToSet(null, String::length);
    }

    @Test
    public void extractValuesToListWorks_keepsOrder() {
        assertThat(extractValuesToList(linkedMap, identity()))
                .containsExactly(1,2,3,4,5,6);
        assertThat(extractValuesToList(emptyMap(), String::length))
                .isEmpty();
    }

    @Test(expected = NullPointerException.class)
    public void extractValuesToList_null() {
        extractValuesToList(null, String::length);
    }

}