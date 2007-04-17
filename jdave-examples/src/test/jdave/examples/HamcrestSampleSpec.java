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
package jdave.examples;

import static org.hamcrest.Matchers.*;
import jdave.Specification;

/**
 * @author Joni Freeman
 */
public class HamcrestSampleSpec extends Specification<Person> {
    public class SampleContext {
        private Person person = new Person("John",  "Doe", 35);
        
        public Person create() {
            return person;
        }
        
        @SuppressWarnings("unchecked")
        public void sample() {
            specify(person, is(Person.class));
            specify(person, hasProperty("firstname", equalTo("John")));
            specify(person, allOf(
                    hasProperty("firstname", equalTo("John")),
                    hasProperty("surname", equalTo("Doe"))));
            specify(person.getAge(), is(greaterThan(30)));
        }
    }
}