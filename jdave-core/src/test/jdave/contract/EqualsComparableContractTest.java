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
package jdave.contract;

import java.util.Comparator;

import jdave.ExpectationFailedException;
import jdave.Specification;
import junit.framework.TestCase;

/**
 * @author Joni Freeman
 */
public class EqualsComparableContractTest extends TestCase {
    private Specification<Object> spec;

    @Override
    protected void setUp() throws Exception {
        spec = new Specification<Object>() {};
    }

    public void testContractIsSatisfiedForInteger() {
        Comparable<Integer> comparable = 2;
        spec.specify(comparable, spec.satisfies(new EqualsComparableContract<Integer>() {
            @Override
            public Integer preceding() {
                return 1;
            }
            @Override
            public Integer subsequent() {
                return 3;
            }
            @Override
            public Integer equivalentByComparisonButNotByEqual() {
                return null;
            }
        }));
    }
    
    public void testContractIsSatisfiedForIntegerUsingRevereseComparator() {
        Comparable<Integer> comparable = 2;
        spec.specify(comparable, spec.satisfies(new EqualsComparableContract<Integer>(revereseComparator()) {
            @Override
            public Integer preceding() {
                return 3;
            }
            @Override
            public Integer subsequent() {
                return 1;
            }
            @Override
            public Integer equivalentByComparisonButNotByEqual() {
                return null;
            }
        }));
    }
    
    private Comparator<Integer> revereseComparator() {
        return new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        };
    }

    public void testContractIsSatisfiedForClassWhoseCompareToIsConsistentWithEquals() {
        Comparable<?> comparable = new ComparableWhoseCompareToIsConsistentWithEquals(1, "b");
        spec.specify(comparable, spec.satisfies(new EqualsComparableContract<ComparableWhoseCompareToIsConsistentWithEquals>() {
            @Override
            public ComparableWhoseCompareToIsConsistentWithEquals preceding() {
                return new ComparableWhoseCompareToIsConsistentWithEquals(2, "a");
            }
            @Override
            public ComparableWhoseCompareToIsConsistentWithEquals subsequent() {
                return new ComparableWhoseCompareToIsConsistentWithEquals(3, "c");
            }
            @Override
            public ComparableWhoseCompareToIsConsistentWithEquals equivalentByComparisonButNotByEqual() {
                return new ComparableWhoseCompareToIsConsistentWithEquals(4, "b");
            }
        }));
    }
    
    public void testContractIsNotSatisfiedForClassWhoseCompareToIsInconsistentWithEquals() {        
        Comparable<?> comparable = new ComparableWhoseCompareToIsNotConsistentWithEquals(1, "b");
        try {
            spec.specify(comparable, spec.satisfies(new EqualsComparableContract<ComparableWhoseCompareToIsNotConsistentWithEquals>() {
                @Override
                public ComparableWhoseCompareToIsNotConsistentWithEquals preceding() {
                    return new ComparableWhoseCompareToIsNotConsistentWithEquals(2, "a");
                }
                @Override
                public ComparableWhoseCompareToIsNotConsistentWithEquals subsequent() {
                    return new ComparableWhoseCompareToIsNotConsistentWithEquals(3, "c");
                }
                @Override
                public ComparableWhoseCompareToIsNotConsistentWithEquals equivalentByComparisonButNotByEqual() {
                    return new ComparableWhoseCompareToIsNotConsistentWithEquals(4, "b");
                }
            }));
            fail();
        } catch (ExpectationFailedException e) {            
        }
    }
    
    public void testContractIsNotSatisfiedForClassWhichPrecedesItsPrecidingObject() {        
        Comparable<Integer> comparable = 2;
        try {
            spec.specify(comparable, spec.satisfies(new EqualsComparableContract<Integer>() {
                @Override
                public Integer preceding() {
                    return 3;
                }
                @Override
                public Integer subsequent() {
                    return 3;
                }
                @Override
                public Integer equivalentByComparisonButNotByEqual() {
                    return null;
                }
            }));
            fail();
        } catch (ExpectationFailedException e) {            
        }
    }
    
    public void testContractIsNotSatisfiedForClassWhichIsAfterItsSubsequentObject() {        
        Comparable<Integer> comparable = 2;
        try {
            spec.specify(comparable, spec.satisfies(new EqualsComparableContract<Integer>() {
                @Override
                public Integer preceding() {
                    return 1;
                }
                @Override
                public Integer subsequent() {
                    return 2;
                }
                @Override
                public Integer equivalentByComparisonButNotByEqual() {
                    return null;
                }
            }));
            fail();
        } catch (ExpectationFailedException e) {            
        }
    }
    
    public void testContractIsNotSatisfiedForClassWhichDoesNotThrowNullpointerException() {        
        try {
            spec.specify(new ComparableWhoseCompareToDoesNotThrowNullpointerException(), new AnyContract());
            fail();
        } catch (ExpectationFailedException e) {
            assertTrue(e.getMessage().endsWith("compareTo(null) should throw NullpointerException"));
        }
    }
    
    private static class ComparableWhoseCompareToIsConsistentWithEquals implements Comparable<ComparableWhoseCompareToIsConsistentWithEquals> {
        private final int id;
        protected final String s;

        ComparableWhoseCompareToIsConsistentWithEquals(int id, String s) {
            this.id = id;
            this.s = s;            
        }
        
        @Override
        public boolean equals(Object obj) {
            ComparableWhoseCompareToIsConsistentWithEquals other = (ComparableWhoseCompareToIsConsistentWithEquals) obj;
            return id == other.id;
        }
        
        public int compareTo(ComparableWhoseCompareToIsConsistentWithEquals o) {
            if (s.equals(o.s)) {
                return id - o.id;
            }
            return s.compareTo(o.s);
        }        
    }
    
    private static class ComparableWhoseCompareToIsNotConsistentWithEquals extends ComparableWhoseCompareToIsConsistentWithEquals {
        ComparableWhoseCompareToIsNotConsistentWithEquals(int id, String s) {
            super(id, s);
        }

        @Override
        public int compareTo(ComparableWhoseCompareToIsConsistentWithEquals o) {
            return s.compareTo(o.s);
        }
    }
    
    private static class ComparableWhoseCompareToDoesNotThrowNullpointerException implements Comparable<Object> {
        public int compareTo(Object o) {
            if (o == null) {
                return -1;
            }
            return 1;
        }
    }
    
    private static class AnyContract extends EqualsComparableContract<Object> {        
        @Override
        protected Object preceding() {
            return null;
        }
        
        @Override
        protected Object subsequent() {
            return null;
        }
        
        @Override
        protected Object equivalentByComparisonButNotByEqual() {
            return null;
        }
    }
}
