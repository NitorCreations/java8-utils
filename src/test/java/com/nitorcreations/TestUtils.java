package com.nitorcreations;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class TestUtils {
    public static <T> void invokePrivateConstructor(Class<T> clazz) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        final Constructor<T> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
