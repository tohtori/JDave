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

import java.util.Arrays;

import junit.framework.TestCase;

/**
 * @author Joni Freeman
 */
public class SpecificationTest extends TestCase {
    private Specification<EmptyStack> specification;

    @Override
    protected void setUp() throws Exception {
        specification = new Specification<EmptyStack>() { };
        specification.be = new EmptyStack();
    }
    
    public void testPassesWhenActualAndExpectedAreBothNull() {
        Object actual = null;
        Object expected = null;
        specification.specify(actual, expected);        
    }
    
    public void testShouldPassWhenExpectationMet() {
        specification.specify(1, 1);
    }
    
    public void testShouldPassWhenExpectationWithinDelta() {
        specification.specify(1.0, 1.001, 0.01);
    }
    
    public void testShouldFailWhenExpectationNotWithinDelta() {
        try {
            specification.specify(1.0, 1.01, 0.001);
            fail();
        } catch (ExpectationFailedException e) {
            assertEquals("Expected: 1.01, but was: 1.0", e.getMessage());
        }
    }
    
    public void testShouldFailWhenBooleanExpectationNotMet() {
        try {
            specification.specify(null, false);
            fail();
        } catch (ExpectationFailedException e) {
            assertEquals("Expected: true, but was: false", e.getMessage());
        }
    }
    
    public void testShouldFailWhenBooleanExpectationWithWrapperNotMet() {
        try {
            specification.specify(null, new Boolean(false));
            fail();
        } catch (ExpectationFailedException e) {
            assertEquals("Expected: true, but was: false", e.getMessage());
        }
    }

    public void testShouldFailWhenInvertedBooleanExpectationNotMet() {
        try {
            specification.specify(null, specification.not().be.empty());
            fail();
        } catch (ExpectationFailedException e) {
            assertEquals("Expected: true, but was: false", e.getMessage());
        }
    }

    public void testShouldFailWhenExpectationNotMet() {
        try {
            specification.specify(1, 2);
            fail();
        } catch (ExpectationFailedException e) {
            assertEquals("Expected: 2, but was: 1", e.getMessage());
        }
    }
    
    public void testShouldPassWhenExpectedExceptionRaised() {
        specification.specify(new Block() {
            public void run() throws Throwable {
                throw new IllegalArgumentException();
            }
        }, specification.raise(IllegalArgumentException.class));
    }

    public void testShouldPassWhenExpectedExceptionRaisedWithSpecifiedMessage() {
        specification.specify(new Block() {
            public void run() throws Throwable {
                throw new IllegalArgumentException("argument is null");
            }
        }, specification.raise(IllegalArgumentException.class, "argument is null"));
    }

    public void testShouldPassWhenExpectedThrowableRaised() {
        specification.specify(new Block() {
            public void run() throws Throwable {
                throw new Throwable();
            }
        }, specification.raise(Throwable.class));
    }
    
    public void testShouldPassWhenSubclassOfExpectedThrowableRaised() {
        specification.specify(new Block() {
            public void run() throws Throwable {
                throw new NoClassDefFoundError();
            }
        }, specification.raise(Throwable.class));
    }
    
    public void testShouldPassWhenExactExpectedThrowableRaised() {
        specification.specify(new Block() {
            public void run() throws Throwable {
                throw new Throwable();
            }
        }, specification.raiseExactly(Throwable.class));
    }
    
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

    public void testShouldFailWhenExpectedExceptionRaisedWithWrongNullMessage() {
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
    
    public void testShouldPassWhenContainmentExpected() {
        specification.specify(Arrays.asList(1, 2, 3), specification.contains(2));
    }
    
    public void testShouldPassWhenContainmentExpectedForObjectArray() {
        Integer[] actual = new Integer[] { 1, 2, 3 };
        specification.specify(actual, specification.contains(2));
    }
    
    public void testShouldPassWhenContainmentExpectedForBooleanArray() {
        boolean[] actual = new boolean[] { true, false };
        specification.specify(actual, specification.contains(true));
    }

    public void testShouldPassWhenContainmentExpectedForByteArray() {
        byte[] actual = new byte[] { 1, 2, 3 };
        specification.specify(actual, specification.contains((byte) 2));
    }
    
    public void testShouldPassWhenContainmentExpectedForCharArray() {
        char[] actual = new char[] { '1', '2', '3' };
        specification.specify(actual, specification.contains('2'));
    }
    
    public void testShouldPassWhenContainmentExpectedForDoubleArray() {
        double[] actual = new double[] { 1, 2, 3 };
        specification.specify(actual, specification.contains((double) 2));
    }
    
    public void testShouldPassWhenContainmentExpectedForFloatArray() {
        float[] actual = new float[] { 1, 2, 3 };
        specification.specify(actual, specification.contains((float) 2));
    }
    
    public void testShouldPassWhenContainmentExpectedForIntArray() {
        int[] actual = new int[] { 1, 2, 3 };
        specification.specify(actual, specification.contains(2));
    }
    
    public void testShouldPassWhenContainmentExpectedForLongArray() {
        long[] actual = new long[] { 1, 2, 3 };
        specification.specify(actual, specification.contains((long) 2));
    }

    public void testShouldPassWhenContainmentExpectedForShortArray() {
        short[] actual = new short[] { 1, 2, 3 };
        specification.specify(actual, specification.contains((short) 2));
    }
    
    public void testShouldPassWhenContainmentExpectedForPrimitiveByteArrayInput() {
        byte[] actual = new byte[]{ 0, 1, 2 };
        specification.specify(actual, specification.containsInOrder(new byte[]{ 0, 1, 2 }));
        specification.specify(actual, specification.containsExactly(new byte[]{ 0, 1, 2 }));
        specification.specify(actual, specification.containsAll(new byte[]{ 2, 1, 0 }));
        specification.specify(actual, specification.containsAny(new byte[]{ -1, 1, 3 }));
        specification.specify(actual, specification.containInOrder(new byte[]{ 0, 1, 2 }));
        specification.specify(actual, specification.containExactly(new byte[]{ 0, 1, 2 }));
        specification.specify(actual, specification.containAll(new byte[]{ 2, 1, 0 }));
        specification.specify(actual, specification.containAny(new byte[]{ -1, 1, 3 }));
    }
    
    public void testShouldPassWhenContainmentExpectedForPrimitiveShortArrayInput() {
        short[] actual = new short[]{ 0, 1, 2 };
        specification.specify(actual, specification.containsInOrder(new short[]{ 0, 1, 2 }));
        specification.specify(actual, specification.containsExactly(new short[]{ 0, 1, 2 }));
        specification.specify(actual, specification.containsAll(new short[]{ 2, 1, 0 }));
        specification.specify(actual, specification.containsAny(new short[]{ -1, 1, 3 }));
        specification.specify(actual, specification.containInOrder(new short[]{ 0, 1, 2 }));
        specification.specify(actual, specification.containExactly(new short[]{ 0, 1, 2 }));
        specification.specify(actual, specification.containAll(new short[]{ 2, 1, 0 }));
        specification.specify(actual, specification.containAny(new short[]{ -1, 1, 3 }));
    }
    
    public void testShouldPassWhenContainmentExpectedForPrimitiveIntArrayInput() {
        int[] actual = new int[]{ 0, 1, 2 };
        specification.specify(actual, specification.containsInOrder(new int[]{ 0, 1, 2 }));
        specification.specify(actual, specification.containsExactly(new int[]{ 0, 1, 2 }));
        specification.specify(actual, specification.containsAll(new int[]{ 2, 1, 0 }));
        specification.specify(actual, specification.containsAny(new int[]{ -1, 1, 3 }));
        specification.specify(actual, specification.containInOrder(new int[]{ 0, 1, 2 }));
        specification.specify(actual, specification.containExactly(new int[]{ 0, 1, 2 }));
        specification.specify(actual, specification.containAll(new int[]{ 2, 1, 0 }));
        specification.specify(actual, specification.containAny(new int[]{ -1, 1, 3 }));
    }
    
    public void testShouldPassWhenContainmentExpectedForPrimitiveLongArrayInput() {
        long[] actual = new long[]{ 0, 1, 2 };
        specification.specify(actual, specification.containsInOrder(new long[]{ 0, 1, 2 }));
        specification.specify(actual, specification.containsExactly(new long[]{ 0, 1, 2 }));
        specification.specify(actual, specification.containsAll(new long[]{ 2, 1, 0 }));
        specification.specify(actual, specification.containsAny(new long[]{ -1, 1, 3 }));
        specification.specify(actual, specification.containInOrder(new long[]{ 0, 1, 2 }));
        specification.specify(actual, specification.containExactly(new long[]{ 0, 1, 2 }));
        specification.specify(actual, specification.containAll(new long[]{ 2, 1, 0 }));
        specification.specify(actual, specification.containAny(new long[]{ -1, 1, 3 }));
    }
    
    public void testShouldPassWhenContainmentExpectedForPrimitiveFloatArrayInput() {
        float[] actual = new float[]{ .0f, .1f, .2f };
        specification.specify(actual, specification.containsInOrder(new float[]{ .0f, .1f, .2f }));
        specification.specify(actual, specification.containsExactly(new float[]{ .0f, .1f, .2f }));
        specification.specify(actual, specification.containsAll(new float[]{ .2f, .1f, .0f }));
        specification.specify(actual, specification.containsAny(new float[]{ -.1f, .1f, .3f }));
        specification.specify(actual, specification.containInOrder(new float[]{ .0f, .1f, .2f }));
        specification.specify(actual, specification.containExactly(new float[]{ .0f, .1f, .2f }));
        specification.specify(actual, specification.containAll(new float[]{ .2f, .1f, .0f }));
        specification.specify(actual, specification.containAny(new float[]{ -.1f, .1f, .3f }));
    }
    
    public void testShouldPassWhenContainmentExpectedForPrimitiveDoubleArrayInput() {
        double[] actual = new double[]{ .0d, .1d, .2d };
        specification.specify(actual, specification.containsInOrder(new double[]{ .0d, .1d, .2d }));
        specification.specify(actual, specification.containsExactly(new double[]{ .0d, .1d, .2d }));
        specification.specify(actual, specification.containsAll(new double[]{ .2d, .1d, .0d }));
        specification.specify(actual, specification.containsAny(new double[]{ -.1d, .1d, .3d }));
        specification.specify(actual, specification.containInOrder(new double[]{ .0d, .1d, .2d }));
        specification.specify(actual, specification.containExactly(new double[]{ .0d, .1d, .2d }));
        specification.specify(actual, specification.containAll(new double[]{ .2d, .1d, .0d }));
        specification.specify(actual, specification.containAny(new double[]{ -.1d, .1d, .3d }));
    }
    
    public void testShouldFailWhenContainmentNotExpected() {
        try {
            specification.specify(Arrays.asList(1, 2, 3), specification.contains(0));
            fail();
        } catch (ExpectationFailedException e) {
            assertEquals("The specified collection [1, 2, 3] does not contain '0'", e.getMessage());
        }
    }
    
    public void testShouldPassWhenNonContainmentExpected() {
        specification.specify(Arrays.asList(1, 2, 3), specification.does.not().contain(0));
    }
    
    public void testShouldFailWhenNonContainmentNotExpected() {
        try {
            specification.specify(Arrays.asList(1, 2, 3), specification.does.not().contains(1));
            fail();
        } catch (ExpectationFailedException e) {
            assertEquals("The specified collection [1, 2, 3] contains '1'", e.getMessage());
        }
    }
    
    public void testShouldPassWhenContainmentForAllExpected() {
        specification.specify(Arrays.asList(1, 2, 3), specification.containsAll(Arrays.asList(2, 3)));
    }
    
    public void testShouldFailWhenContainmentForAllNotExpected() {
        try {
            specification.specify(Arrays.asList(1, 2, 3), specification.containsAll(0, 1));
            fail();
        } catch (ExpectationFailedException e) {
            assertEquals("The specified collection [1, 2, 3] does not contain '[0, 1]'", e.getMessage());
        }
    }
    
    public void testNotDoesNotAffectNextSpecifyStatement() {
        specification.specify(Arrays.asList(1, 2, 3), specification.does.not().contain(0));
        specification.specify(null, true);
    }
    
    class EmptyStack {
        public boolean empty() {
            return true;
        }
    }
}
