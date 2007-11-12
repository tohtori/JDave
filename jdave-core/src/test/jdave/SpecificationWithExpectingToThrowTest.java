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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * @author Lasse Koskela
 * @author Pekka Enberg
 */
public class SpecificationWithExpectingToThrowTest {
    private Specification<Void> specification = new Specification<Void>() { };

    @Test
    public void testShouldPassWhenExpectedExceptionRaised() {
        specification.specify(new Block() {
            public void run() throws Throwable {
                throw new IllegalArgumentException();
            }
        }, specification.raise(IllegalArgumentException.class));
    }

    @Test
    public void testShouldPassWhenExpectedExceptionRaisedWithSpecifiedMessage() {
        specification.specify(new Block() {
            public void run() throws Throwable {
                throw new IllegalArgumentException("argument is null");
            }
        }, specification.raise(IllegalArgumentException.class, "argument is null"));
    }

    @Test
    public void testShouldPassWhenExpectedExceptionRaisedExpectingNullMessage() {
        specification.specify(new Block() {
            public void run() throws Throwable {
                throw new IllegalArgumentException((String) null);
            }
        }, specification.raise(IllegalArgumentException.class, null));
    }

    @Test
    public void testShouldPassWhenExpectedThrowableRaised() {
        specification.specify(new Block() {
            public void run() throws Throwable {
                throw new Throwable();
            }
        }, specification.raise(Throwable.class));
    }
    
    @Test
    public void testShouldPassWhenSubclassOfExpectedThrowableRaised() {
        specification.specify(new Block() {
            public void run() throws Throwable {
                throw new NoClassDefFoundError();
            }
        }, specification.raise(Throwable.class));
    }
    
    @Test
    public void testShouldPassWhenExactExpectedThrowableRaised() {
        specification.specify(new Block() {
            public void run() throws Throwable {
                throw new Throwable();
            }
        }, specification.raiseExactly(Throwable.class));
    }
    
    @Test
    public void testShouldFailWhenExpectingAnExactExceptionButNoneIsRaised() {
        try {
            specification.specify(new Block() {
                public void run() throws Throwable {
                }
            }, specification.raiseExactly(IllegalArgumentException.class));
            fail();
        } catch (ExpectationFailedException e) {
            assertEquals("The specified block should throw java.lang.IllegalArgumentException but nothing was thrown.", e.getMessage());
        }
    }
    
    @Test
    public void testShouldFailWhenExpectingAnExactExceptionButSubclassIsRaised() {
        try {
            specification.specify(new Block() {
                public void run() throws Throwable {
                    throw new IllegalArgumentException();
                }
            }, specification.raiseExactly(Throwable.class));
            fail();
        } catch (ExpectationFailedException e) {
            assertEquals("The specified block should throw java.lang.Throwable but java.lang.IllegalArgumentException was thrown.", e.getMessage());
        }
    }
    
    @Test
    public void testShouldFailWhenExpectingAnExactExceptionButWrongMessageIsGiven() {
        try {
            specification.specify(new Block() {
                public void run() throws Throwable {
                    throw new IllegalArgumentException("invalid range");
                }
            }, specification.raiseExactly(IllegalArgumentException.class, "argument is null"));
            fail();
        } catch (ExpectationFailedException e) {
            assertEquals("Expected the exception message to be \"argument is null\", but was: \"invalid range\".", e.getMessage());
        }
    }
    
    @Test
    public void testShouldFailWhenExpectingAnExactExceptionWithMessageButSubclassIsGiven() {
        try {
            specification.specify(new Block() {
                public void run() throws Throwable {
                    throw new IllegalArgumentException();
                }
            }, specification.raiseExactly(Throwable.class, "argument is null"));
            fail();
        } catch (ExpectationFailedException e) {
            assertEquals("The specified block should throw java.lang.Throwable but java.lang.IllegalArgumentException was thrown.", e.getMessage());
        }
    }
    
    @Test
    public void testShouldFailWhenExpectedExceptionNotRaised() {
        try {
            specification.specify(new Block() {
                public void run() throws Throwable {
                }
            }, specification.raise(IllegalArgumentException.class));
            fail();
        } catch (ExpectationFailedException e) {
            assertEquals("The specified block should throw java.lang.IllegalArgumentException but nothing was thrown.", e.getMessage());
        }
    }
    
    @Test
    public void testShouldFailWhenDifferentExceptionAsExpectedWasRaised() {
        try {
            specification.specify(new Block() {
                public void run() throws Throwable {
                    throw new NullPointerException();
                }
            }, specification.raise(IllegalArgumentException.class));
            fail();
        } catch (ExpectationFailedException e) {
            assertEquals("The specified block should throw java.lang.IllegalArgumentException but java.lang.NullPointerException was thrown.", e.getMessage());
        }
    }

    @Test
    public void testShouldFailWhenExpectedExceptionRaisedWithWrongMessage() {
        try {
            specification.specify(new Block() {
                public void run() throws Throwable {
                    throw new IllegalArgumentException("invalid range");
                }
            }, specification.raise(IllegalArgumentException.class, "argument is null"));
            fail();
        } catch (ExpectationFailedException e) {
            assertEquals("Expected the exception message to be \"argument is null\", but was: \"invalid range\".", e.getMessage());
        }
    }

    @Test
    public void testShouldFailWhenExpectedExceptionRaisedButMessageIsNonNullWhenExpectingNull() {
        try {
            specification.specify(new Block() {
                public void run() throws Throwable {
                    throw new IllegalArgumentException("invalid range");
                }
            }, specification.raise(IllegalArgumentException.class, null));
            fail();
        } catch (ExpectationFailedException e) {
            assertEquals("Expected the exception message to be \"null\", but was: \"invalid range\".", e.getMessage());
        }
    }

    @Test
    public void testShouldFailWhenExpectedExceptionRaisedWithNullMessageWhenExpectingNonNull() {
        try {
            specification.specify(new Block() {
                public void run() throws Throwable {
                    throw new IllegalArgumentException((String) null);
                }
            }, specification.raise(IllegalArgumentException.class, "argument is null"));
            fail();
        } catch (ExpectationFailedException e) {
            assertEquals("Expected the exception message to be \"argument is null\", but was: \"null\".", e.getMessage());
        }
    }
}
