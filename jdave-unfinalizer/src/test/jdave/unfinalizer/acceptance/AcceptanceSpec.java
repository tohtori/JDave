/*
 * Copyright 2007 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package jdave.unfinalizer.acceptance;

import jdave.Specification;
import jdave.junit4.JDaveRunner;
import jdave.unfinalizer.fake.ClassWithFinalMethod;
import jdave.unfinalizer.fake.FinalClass;

import org.jmock.Expectations;
import org.junit.runner.RunWith;

/**
 * @author Tuomas Karkkainen
 */
@RunWith(JDaveRunner.class)
public class AcceptanceSpec extends Specification<Class<?>> {
    public class WhenClassIsFinal {
        public Class<FinalClass> create() {
            return FinalClass.class;
        }

        public void isMadeNonFinal() {
            mock(FinalClass.class);
        }
    }

    public class WhenMethodIsFinal {
        public Class<ClassWithFinalMethod> create() {
            return ClassWithFinalMethod.class;
        }

        public void theMethodIsMadeNonFinal() {
            final ClassWithFinalMethod mock = mock(ClassWithFinalMethod.class);
            checking(new Expectations() {
                {
                    one(mock).finalMethod();
                }
            });
            mock.finalMethod();
        }
    }
}
