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
package jdave.injection;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * @author Joni Freeman
 */
public class InjectionSupportTest {
    private Set<Field> collectedFields = new HashSet<Field>();

    @Test
    public void testVisitsAllFieldsInHierarchy() {
        new InjectionSupport().inject(new TestClass(), new CollectingInjector());
        assertEquals(6, collectedFields.size());
    }
    
    @Test
    public void testVisitsFieldsByVisibilityInHierarchy() {
        new InjectionSupport(Modifier.PRIVATE | Modifier.PROTECTED).inject(new TestClass(), new CollectingInjector());
        assertEquals(4, collectedFields.size());
    }
    
    class CollectingInjector implements IFieldInjector {        
        public void inject(Field field) {
            collectedFields.add(field);
        }
    }
    
    static class TestClass extends TestSuperClass {
        private Object privateField;
        protected Object protectedField;
        public Object publicField;
    }
    
    static class TestSuperClass {
        private Object privateSuperField;
        protected Object protectedSuperField;
        public Object publicSuperField;
    }
}
