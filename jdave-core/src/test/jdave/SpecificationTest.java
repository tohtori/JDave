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

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

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
    
    public void testShouldPassWhenInvertedExpectationMet() {
        specification.specify(1, specification.should.not().equal(2));
    }
    
    public void testShouldFailWhenInvertedExpectationNotMet() {
        try {
            specification.specify(1, specification.should.not().equal(1));
            fail();
        } catch (ExpectationFailedException e) {
            assertEquals("Did not expect: 1, but was: 1", e.getMessage());            
        }
    }
    
    public void testShouldPassWhenExpectationWithinDelta() {
        specification.specify(1.0, specification.should.equal(1.001, 0.01));
    }
    
    public void testShouldFailWhenExpectationNotWithinDelta() {
        try {
            specification.specify(1.0, specification.should.equal(1.01, 0.001));
            fail();
        } catch (ExpectationFailedException e) {
            assertEquals("Expected: 1.01, but was: 1.0", e.getMessage());
        }
    }
    
    public void testShouldPassWhenExpectationWithinDeltaForWrapper() {
        specification.specify(1.0, specification.should.equal(new Double(1.001), 0.01));
    }
    
    public void testShouldPassWhenInvertedExpectationWithinDeltaMet() {
        specification.specify(1.01, specification.should.not().equal(1, 0.001));
    }
    
    public void testShouldFailWhenInvertedExpectationWithinDeltaNotMet() {
        try {
            specification.specify(1.001, specification.should.not().equal(1, 0.01));
            fail();
        } catch (ExpectationFailedException e) {
            assertEquals("Did not expect: 1, but was: 1.001", e.getMessage());            
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
    
    public void testPassesWhenHamcrestMatcherPasses() {
        specification.specify(null, new BaseMatcher<Object>() {
            public boolean matches(Object item) {
                return true;
            }
            public void describeTo(Description description) {
            }            
        });
    }
    
    public void testFailsWhenHamcrestMatcherFails() {
        try {
            specification.specify(null, new BaseMatcher<Object>() {
                public boolean matches(Object item) {
                    return false;
                }
                public void describeTo(Description description) {
                }            
            });
            fail();
        } catch (ExpectationFailedException e) {            
        }
    }
    
    public void testPassesWhenHamcrestMatcherPassesAllElements() {
        List<Money> m = Arrays.asList(new Money(4), new Money(5), new Money(6));
        specification.specify(m, 
                new Where<Money>() {{ each(item.value(), is(greaterThan(3))); }});
    }
    
    public void testFailsWhenHamcrestMatcherFailsAnyElements() {
        List<Money> m = Arrays.asList(new Money(4), new Money(5), new Money(6));
        try {
            specification.specify((Iterable<Money>) m, 
                    new Where<Money>() {{ each(item.value(), is(greaterThan(5))); }});
            fail();
        } catch (ExpectationFailedException e) {            
        }
    }
    
    public static class Money {
        private final int value;

        public Money(int value) {
            this.value = value;            
        }
        
        public Integer value() {
            return value;
        }
    }
    
    class EmptyStack {
        public boolean empty() {
            return true;
        }
    }
}
