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

import junit.framework.TestCase;

/**
 * @author Pekka Enberg
 */
public class SpecificationWithNotExpectingToThrowTest extends TestCase {
    private Specification<Void> specification;

    @Override
    protected void setUp() throws Exception {
        specification = new Specification<Void>() { };
    }

    public void testShouldPassWhenNoExceptionRaised() {
        specification.specify(new Block() {
            public void run() throws Throwable {
                // Intentionally left blank.
            }
        }, specification.not().raise(IllegalArgumentException.class));
    }

    public void testShouldFailIfThrowsExpectedException() {
        try {
            specification.specify(new Block() {
                public void run() throws Throwable {
                    throw new IllegalArgumentException();
                }
            }, specification.not().raise(IllegalArgumentException.class));
            fail();
        } catch (ExpectationFailedException e) {
            assertEquals("The specified block threw java.lang.IllegalArgumentException", e.getMessage());
        }
    }

    public void testShouldFailIfThrowsSubclassOfExpectedException() {
        try {
            specification.specify(new Block() {
                public void run() throws Throwable {
                    throw new IllegalArgumentException();
                }
            }, specification.not().raise(Throwable.class));
            fail();
        } catch (ExpectationFailedException e) {
            assertEquals("The specified block threw java.lang.IllegalArgumentException", e.getMessage());
        }
    }

    public void testShouldFailIfThrowsExactlyExpectedException() {
        try {
            specification.specify(new Block() {
                public void run() throws Throwable {
                    throw new IllegalArgumentException();
                }
            }, specification.not().raiseExactly(IllegalArgumentException.class));
            fail();
        } catch (ExpectationFailedException e) {
            assertEquals("The specified block threw java.lang.IllegalArgumentException", e.getMessage());
        }
    }

    public void testShouldRethrowIfThrowsSubclassOfExactlyExpectedException() {
        try {
            specification.specify(new Block() {
                public void run() throws Throwable {
                    throw new IllegalArgumentException("rethrown");
                }
            }, specification.not().raiseExactly(Throwable.class));
            fail();
        } catch (RuntimeException e) {
            assertEquals(IllegalArgumentException.class, e.getCause().getClass());
            assertEquals("rethrown", e.getCause().getMessage());
        }
    }
}
