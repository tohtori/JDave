/*
 * Copyright 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jdave.support;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * @author Lasse Koskela
 */
public class Reflection {
    public static Method getMethod(Class<?> specType, String name, int... modifiers)
            throws NoSuchMethodException {
        return getMethod(specType, name, null, modifiers);
    }

    public static Method getMethod(Class<?> specType, String name, Class<?>[] parameterTypes,
            int... modifiers) throws NoSuchMethodException {
        for (Method method : specType.getDeclaredMethods()) {
            if (name.equals(method.getName()) && hasModifiers(method, modifiers)
                    && takesParameters(method, parameterTypes)) {
                return method;
            }
        }
        throw new NoSuchMethodException(Modifier.toString(combine(modifiers)) + " method '" + name
                + "' not found");
    }

    private static int combine(int... modifiers) {
        if (modifiers == null) {
            return 0;
        }
        int modifier = 0;
        for (int m : modifiers) {
            modifier = (modifier | m);
        }
        return modifier;
    }

    public static boolean isVoid(Method method) {
        Class<?> returnType = method.getReturnType();
        return "void".equals(returnType.toString());
    }

    public static boolean isPublic(Method method) {
        return hasModifier(method, Modifier.PUBLIC);
    }

    public static boolean isStatic(Method method) {
        return hasModifier(method, Modifier.STATIC);
    }

    private static boolean hasModifiers(Method method, int... modifiers) {
        if (modifiers == null) {
            return true;
        }
        for (int modifier : modifiers) {
            if (!hasModifier(method, modifier)) {
                return false;
            }
        }
        return true;
    }

    private static boolean hasModifier(Method method, int modifier) {
        return (method.getModifiers() & modifier) != 0;
    }

    private static boolean takesParameters(Method method, Class<?>... parameterTypes) {
        if (parameterTypes == null) {
            parameterTypes = new Class[0];
        }
        return Arrays.asList(method.getParameterTypes()).equals(Arrays.asList(parameterTypes));
    }
}
