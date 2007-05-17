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

import jdave.mock.UnsafeHackConcreteClassImposteriser;

import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.jmock.api.Imposteriser;
import org.jmock.api.Invocation;
import org.jmock.api.Invokable;

/**
 * @author Joni Freeman
 */
public class Where <T> {
    /*
     * Proxy which records the method calls and replays at match.
     */ 
    protected T item; 
    private Invocation invocation;
    private Matcher<?> matcher;
    
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

    public Where() {
        ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
        @SuppressWarnings("unchecked")
        Class<T> type = (Class<T>) superclass.getActualTypeArguments()[0];
        Imposteriser hack = UnsafeHackConcreteClassImposteriser.INSTANCE;
        item = hack.imposterise(new Invokable() {
            public Object invoke(Invocation invocation) throws Throwable {
                Where.this.invocation = invocation;
                return nullOrBoxed(invocation);
            }
        }, type);
    }
    
    private Object nullOrBoxed(Invocation invocation) {
        return BOXED_VALUES.get(invocation.getInvokedMethod().getReturnType());        
    }

    protected void each(Object itemToMatch, Matcher<?> matcher) {
        this.matcher = matcher;
    }

    void match(T realItem) {
        Method invokedMethod = invocation.getInvokedMethod();
        try {
            Method realMethod = realItem.getClass().getMethod(invokedMethod.getName(), invokedMethod.getParameterTypes());
            Object itemToMatch = realMethod.invoke(realItem, invocation.getParametersAsArray()); 
            if (!matcher.matches(itemToMatch)) {
                throw new ExpectationFailedException(itemToMatch + " does not satisfy '" + 
                        StringDescription.toString(matcher) + "'");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
