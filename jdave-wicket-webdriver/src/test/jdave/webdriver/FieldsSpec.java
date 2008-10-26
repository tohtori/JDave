/*
 * Copyright 2008 the original author or authors.
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
package jdave.webdriver;

import jdave.Block;
import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;

/**
 * @author Juha Karemo
 */
@RunWith(JDaveRunner.class)
public class FieldsSpec extends Specification<Void> {
    public class AnyFields {
        public void returnsFieldValueIfFieldExists() {
            specify(Fields.getValue(new FieldsSpecExample(), "existingField"), does.equal(3));
        }

        public void throwsExceptionIfFieldDoesNotExists() {
            specify(new Block() {
                public void run() throws Throwable {
                    Fields.getValue(new FieldsSpecExample(), "nonExistingField");
                }
            }, does.raise(RuntimeException.class));
        }
    }

    public class FieldsSpecExample {
        @SuppressWarnings("unused")
        private int existingField = 3;
    }
}
