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
package jdave;

/**
 * @author Joni Freeman
 * @author Pekka Enberg
 */
public class Specification<T> {
    protected Specification<T> should = this;
    public T be;
    
    public Specification<T> not() {
        return this;
    }

    public void specify(boolean expected) {
    }

    public void specify(Object actual, Object expected) {
    }

    public Object equal(Object obj) {
        return obj;
    }
    
    public Object raise(Class<? extends Throwable> expected) {
        return null;
    }
}