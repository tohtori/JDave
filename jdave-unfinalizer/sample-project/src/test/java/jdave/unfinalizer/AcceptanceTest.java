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
package jdave.unfinalizer;

import jdave.Block;
import jdave.Specification;
import jdave.junit4.JDaveRunner;
import jdave.unfinalizer.fake.AnotherClassWithFinalMethod;
import jdave.unfinalizer.fake.AnotherFinalClass;

import org.jmock.Expectations;
import org.junit.runner.RunWith;

/**
 * @author Tuomas Karkkainen
 */
@RunWith(JDaveRunner.class)
public class AcceptanceTest extends Specification<Void> {
    @Override
    public void create() throws Exception {
        Unfinalizer.unfinalize(getClass().getName());
    }

    public class WhenClassIsFinal {
        public void itIsMadeNonFinal() throws Throwable {
            specify(new Block() {
                public void run() throws Throwable {
                    mock(AnotherFinalClass.class);
                }
            }, should.not().raiseAnyException());
        }
    }

    public class WhenAMethodIsFinal {
        public void theMethodIsMadeNonFinal() {
            final AnotherClassWithFinalMethod mock = mock(AnotherClassWithFinalMethod.class);
            checking(new Expectations() {
                {
                    one(mock).finalMethod();
                }
            });
            mock.finalMethod();
        }
    }
}
