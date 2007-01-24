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

import junit.framework.TestCase;

/**
 * @author Joni Freeman
 */
public class EqualsHashCodeContractTest extends TestCase {
    private Specification<Object> spec;

    @Override
    protected void setUp() throws Exception {
        spec = new Specification<Object>() {};
    }
    
    public void testCorrectlyImplementedClassIsAccepted() {
        ClassWithCorrectEqualsAndHashCode context = new ClassWithCorrectEqualsAndHashCode(1);
        spec.specify(context, spec.satisfies(new EqualsHashCodeContract<ClassWithCorrectEqualsAndHashCode>() {
            @Override
            public ClassWithCorrectEqualsAndHashCode equal() {
                return new ClassWithCorrectEqualsAndHashCode(1);
            }
            @Override
            public ClassWithCorrectEqualsAndHashCode nonEqual() {
                return new ClassWithCorrectEqualsAndHashCode(2);
            }
            @Override
            public ClassWithCorrectEqualsAndHashCode subType() {
                return new ClassWithCorrectEqualsAndHashCode(1) {};
            }
        }));
    }
    
    public void testCorrectlyImplementedClassIsAcceptedWhenSubTypeIsNotChecked() {
        ClassWithCorrectEqualsAndHashCode context = new ClassWithCorrectEqualsAndHashCode(1);
        spec.specify(context, spec.satisfies(new EqualsHashCodeContract<ClassWithCorrectEqualsAndHashCode>() {
            @Override
            public ClassWithCorrectEqualsAndHashCode equal() {
                return new ClassWithCorrectEqualsAndHashCode(1);
            }
            @Override
            public ClassWithCorrectEqualsAndHashCode nonEqual() {
                return new ClassWithCorrectEqualsAndHashCode(2);
            }
            @Override
            public ClassWithCorrectEqualsAndHashCode subType() {
                return null;
            }
        }));
    }
    
    public void testIncorrectlyImplementedClassIsNotAccepted() {
        ClassWithInconsistentEqualsAndHashCode context = new ClassWithInconsistentEqualsAndHashCode(1);
        try {
            spec.specify(context, spec.satisfies(new EqualsHashCodeContract<ClassWithInconsistentEqualsAndHashCode>() {
                @Override
                public ClassWithInconsistentEqualsAndHashCode equal() {
                    return new ClassWithInconsistentEqualsAndHashCode(1);
                }
                @Override
                public ClassWithInconsistentEqualsAndHashCode nonEqual() {
                    return new ClassWithInconsistentEqualsAndHashCode(2);
                }
                @Override
                public ClassWithInconsistentEqualsAndHashCode subType() {
                    return new ClassWithInconsistentEqualsAndHashCode(1) {};
                }
            }));
            fail();
        } catch (ExpectationFailedException e) {            
        }
    }
    
    public void testContractIsNotAcceptedIfNonEqualObjectEquals() {
        ClassWithCorrectEqualsAndHashCode context = new ClassWithCorrectEqualsAndHashCode(1);
        try {
            spec.specify(context, spec.satisfies(new EqualsHashCodeContract<ClassWithCorrectEqualsAndHashCode>() {
                @Override
                public ClassWithCorrectEqualsAndHashCode equal() {
                    return new ClassWithCorrectEqualsAndHashCode(1);
                }
                @Override
                public ClassWithCorrectEqualsAndHashCode nonEqual() {
                    return new ClassWithCorrectEqualsAndHashCode(1);
                }
                @Override
                public ClassWithCorrectEqualsAndHashCode subType() {
                    return new ClassWithCorrectEqualsAndHashCode(1) {};
                }
            }));
            fail();
        } catch (ExpectationFailedException e) {            
        }        
    }
    
    public void testContractIsNotAcceptedIfSubtypeObjectEquals() {
        ClassWithCorrectEqualsAndHashCode context = new ClassWithCorrectEqualsAndHashCode(1);
        try {
            spec.specify(context, spec.satisfies(new EqualsHashCodeContract<ClassWithCorrectEqualsAndHashCode>() {
                @Override
                public ClassWithCorrectEqualsAndHashCode equal() {
                    return new ClassWithCorrectEqualsAndHashCode(1);
                }
                @Override
                public ClassWithCorrectEqualsAndHashCode nonEqual() {
                    return new ClassWithCorrectEqualsAndHashCode(2);
                }
                @Override
                public ClassWithCorrectEqualsAndHashCode subType() {
                    return new ClassWithCorrectEqualsAndHashCode(1);
                }
            }));
            fail();
        } catch (ExpectationFailedException e) {            
        }        
    }
    
    public void testContractIsNotAcceptedIfObjectEqualsWithNull() {
        ClassWhichEqualsWithNull context = new ClassWhichEqualsWithNull();
        try {
            spec.specify(context, spec.satisfies(new EqualsHashCodeContract<ClassWhichEqualsWithNull>() {
                @Override
                public ClassWhichEqualsWithNull equal() {
                    return new ClassWhichEqualsWithNull();
                }
                @Override
                public ClassWhichEqualsWithNull nonEqual() {
                    return new ClassWhichEqualsWithNull();
                }
                @Override
                public ClassWhichEqualsWithNull subType() {
                    return new ClassWhichEqualsWithNull();
                }
            }));
            fail();
        } catch (ExpectationFailedException e) {
            assertTrue(e.getMessage().endsWith("equals null"));
        }        
    }
    
    private static class ClassWithCorrectEqualsAndHashCode {
        private final int id;

        public ClassWithCorrectEqualsAndHashCode(int id) {
            this.id = id;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (obj == null || !(obj.getClass().equals(ClassWithCorrectEqualsAndHashCode.class))) {
                return false;
            }
            ClassWithCorrectEqualsAndHashCode other = (ClassWithCorrectEqualsAndHashCode) obj;
            return id == other.id;
        }
        
        @Override
        public int hashCode() {
            return id;
        }
    }
    
    private static class ClassWithInconsistentEqualsAndHashCode extends ClassWithCorrectEqualsAndHashCode {
        private int count = 0;
        
        public ClassWithInconsistentEqualsAndHashCode(int id) {
            super(id);
        }
        
        @Override
        public int hashCode() {
            return count++;
        }
    }
    
    private static class ClassWhichEqualsWithNull {
        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return true;
            }
            return false;
        }
    }
}
