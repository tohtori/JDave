/*
 * Copyright 2008 the original author or authors.
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
 */
package jdave.runner;

import java.util.Comparator;

/**
 * Sorts (inner) classes according to the order in which they have been declared in the source code.
 *
 * @author Esko Luontola
 * @since 4.1.2008
 */
public class ClassLineNumberComparator implements Comparator<Class<?>> {
    private final LineNumberStrategy strategy = LineNumberStrategy.CURRENT_STRATEGY;

    public int compare(Class<?> c1, Class<?> c2) {
        int line1 = strategy.firstLineNumber(c1, 0);
        int line2 = strategy.firstLineNumber(c2, 0);
        return line1 - line2;
    }
}
