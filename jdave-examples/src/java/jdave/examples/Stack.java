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
package jdave.examples;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author Joni Freeman
 * @author Pekka Enberg
 */
public class Stack<T> implements Collection<T> {
    private static final int UNLIMITED = -1;
    private final int maxSize;
    private java.util.Stack<T> stack = new java.util.Stack<T>();
    
    public Stack() {
        this(UNLIMITED);
    }
    
    public Stack(int maxSize) {
        this.maxSize = maxSize;
    }

    public boolean empty() {
        return stack.isEmpty();
    }

    public boolean full() {
        return maxSize != UNLIMITED && stack.size() == maxSize;
    }

    public T peek() {
        return stack.peek();
    }

    public void push(T element) {
        if (full()) {
            throw new StackOverflowException();
        }
        stack.push(element);
    }

    public boolean add(T o) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public boolean contains(Object o) {
        return stack.contains(o);
    }

    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public int size() {
        throw new UnsupportedOperationException();
    }

    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    public <V> V[] toArray(V[] a) {
        throw new UnsupportedOperationException();
    }
}
