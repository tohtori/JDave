/*
 * Copyright 2007 the original author or authors.
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
package jdave;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.jmock.api.Imposteriser;
import org.jmock.api.Invocation;
import org.jmock.api.Invokable;
import org.jmock.lib.legacy.ClassImposteriser;

/**
 * @author Joni Freeman
 */
public class Each<T> {
    /*
     * Proxy which records the method calls and replays at match.
     */ 
    protected T item; 
    private Invocation invocation;
    private Matcher<?> matcher;
    private Matcher<?>[] matchers;
    
    private static final Map<Class<?>, Object> BOXED_VALUES = new HashMap<Class<?>, Object>() {{
        put(boolean.class, Boolean.TRUE);
        put(byte.class, new Byte((byte) 0));
        put(char.class, 'a');
        put(short.class, new Short((short) 0));
        put(int.class, new Integer(0));
        put(long.class, new Long(0));
        put(float.class, new Float(0));
        put(double.class, new Double(0));
    }};

    public Each() {
        ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
        @SuppressWarnings("unchecked")
        Class<T> type = (Class<T>) superclass.getActualTypeArguments()[0];
        Imposteriser imposteriser = ClassImposteriser.INSTANCE;
        item = imposteriser.imposterise(new Invokable() {
            public Object invoke(Invocation invocation) throws Throwable {
                Each.this.invocation = invocation;
                return nullOrBoxed(invocation);
            }
        }, type);
    }
    
    private Object nullOrBoxed(Invocation invocation) {
        return BOXED_VALUES.get(invocation.getInvokedMethod().getReturnType());        
    }

    protected void matches(Object itemToMatch, Matcher<?> matcher) {
        this.matcher = matcher;
    }
    
    protected void matches(Object itemToMatch, Matcher<?>... matchers) {
        this.matchers = matchers;
    }

    void match(T realItem, int index) {
        Object itemToMatch = itemToMatch(realItem);
        Matcher<?> nextMatcher = nextMatcher(index);
        if (!nextMatcher.matches(itemToMatch)) {
            throw new ExpectationFailedException(itemToMatch + " does not satisfy '" + 
                    StringDescription.toString(nextMatcher) + "'");
        }
    }

    private Object itemToMatch(T realItem) {
        if (invocation == null) {
            return realItem;
        }
        return nextItemToMatch(realItem, invocation.getInvokedMethod());
    }

    private Object nextItemToMatch(T realItem, Method invokedMethod) {
        try {
            Method realMethod = realItem.getClass().getMethod(invokedMethod.getName(), invokedMethod.getParameterTypes());
            return realMethod.invoke(realItem, invocation.getParametersAsArray()); 
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Matcher<?> nextMatcher(int index) {
        if (matcher != null) {
            return matcher;
        }
        if (index == matchers.length) {
            throw new ExpectationFailedException("Not enough matchers, current index = " + index);
        }
        return matchers[index];
    }

    public void areAllMatchersUsed(int index) {
        if (matchers != null && index < matchers.length) {
            throw new ExpectationFailedException("Not enough elements, expected " + matchers.length + " elements.");
        }
    }
}
