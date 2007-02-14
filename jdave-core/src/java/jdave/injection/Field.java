/*
 * Copyright 2006 the original author or authors.
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
package jdave.injection;


/**
 * @author Joni Freeman
 */
public class Field {
    private final Object object;
    private final java.lang.reflect.Field field;

    Field(Object object, java.lang.reflect.Field field) {
        this.object = object;
        this.field = field;
        field.setAccessible(true);
    }

    public void set(Object value) {
        try {
            field.set(object, value);
        } catch (IllegalArgumentException e) {
            throw new ReflectionException(e);
        } catch (IllegalAccessException e) {
            throw new ReflectionException(e);
        }
    }

    public Object get() {
        try {
            return field.get(object);
        } catch (IllegalArgumentException e) {
            throw new ReflectionException(e);
        } catch (IllegalAccessException e) {
            throw new ReflectionException(e);
        }
    }
    
    public java.lang.reflect.Field field() {
        return field;
    }
}
