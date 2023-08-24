package org.attias.open.interactive.simulation.core.backend.utils;

import java.lang.reflect.InvocationTargetException;

public class ReflectionUtils {

    public static <T extends Object> T newInstance(String classToLoad) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class<T> loadedClass = (Class<T>) Class.forName(classToLoad);
        return loadedClass.getDeclaredConstructor().newInstance();
    }
}
