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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import jdave.ExpectationFailedException;
import jdave.Specification;

import org.junit.Test;

/**
 * @author Joni Freeman
 */
public class EqualsHashCodeContractTest {
    private Specification<Object> spec = new Specification<Object>() {};

    @Test
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
    
    @Test
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
    
    @Test
    public void testClassWithIncorrectlyImplementedHashCodeIsNotAccepted() {
        ClassWithIncorrectlyImplementedHashCode context = new ClassWithIncorrectlyImplementedHashCode(1);
        try {
            spec.specify(context, spec.satisfies(new EqualsHashCodeContract<ClassWithIncorrectlyImplementedHashCode>() {
                @Override
                public ClassWithIncorrectlyImplementedHashCode equal() {
                    return new ClassWithIncorrectlyImplementedHashCode(1);
                }
                @Override
                public ClassWithIncorrectlyImplementedHashCode nonEqual() {
                    return new ClassWithIncorrectlyImplementedHashCode(2);
                }
                @Override
                public ClassWithIncorrectlyImplementedHashCode subType() {
                    return new ClassWithIncorrectlyImplementedHashCode(1) {};
                }
            }));
            fail();
        } catch (ExpectationFailedException e) {
            assertTrue(e.getMessage().startsWith("hashCodes must equal for equal objects"));
        }
    }
    
    @Test
    public void testClassWithIncorrectlyImplementedEqualsIsNotAccepted() {
        ClassWithIncorrectlyImplementedEquals context = new ClassWithIncorrectlyImplementedEquals();
        try {
            spec.specify(context, spec.satisfies(new EqualsHashCodeContract<ClassWithIncorrectlyImplementedEquals>() {
                @Override
                public ClassWithIncorrectlyImplementedEquals equal() {
                    return new ClassWithIncorrectlyImplementedEquals();
                }
                @Override
                public ClassWithIncorrectlyImplementedEquals nonEqual() {
                    return new ClassWithIncorrectlyImplementedEquals();
                }
                @Override
                public ClassWithIncorrectlyImplementedEquals subType() {
                    return new ClassWithIncorrectlyImplementedEquals() {};
                }
            }));
            fail();
        } catch (ExpectationFailedException e) {
            assertTrue(e.getMessage().contains("does not equal"));
        }
    }
    
    @Test
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
    
    @Test
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
    
    @Test
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
    
    private static class ClassWithIncorrectlyImplementedHashCode {
        private final int id;
        private static int count = 0;
        
        public ClassWithIncorrectlyImplementedHashCode(int id) {
            this.id = id;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (obj == null || !(obj.getClass().equals(ClassWithIncorrectlyImplementedHashCode.class))) {
                return false;
            }
            ClassWithIncorrectlyImplementedHashCode other = (ClassWithIncorrectlyImplementedHashCode) obj;
            return id == other.id;
        }
        
        @Override
        public int hashCode() {
            return count++;
        }
    }
    
    private static class ClassWithIncorrectlyImplementedEquals {
        @Override
        public boolean equals(Object obj) {
            return false;
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
