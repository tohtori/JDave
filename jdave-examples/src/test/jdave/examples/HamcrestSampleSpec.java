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

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.List;

import jdave.Specification;
import jdave.Where;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;

/**
 * @author Joni Freeman
 */
@RunWith(JDaveRunner.class)
public class HamcrestSampleSpec extends Specification<List<Person>> {
    public class SampleContext {
        private List<Person> persons;
        
        public List<Person> create() {
            persons = Arrays.asList(new Person("John", "Doe", 35), new Person("Jill", "Doe", 31));
            return persons;
        }
        
        @SuppressWarnings("unchecked")
        public void sample() {
            specify(persons.get(0), is(Person.class));
            specify(persons.get(0), hasProperty("firstname", equalTo("John")));
            specify(persons.get(0), allOf(
                    hasProperty("firstname", equalTo("John")),
                    hasProperty("surname", equalTo("Doe"))));
            specify(persons.get(0).getAge(), is(greaterThan(30)));
        }
        
        public void collectionSample() {
            specify(persons, new Where<Person>() {{ each(item.getSurname(), is(equalTo("Doe"))); }});
            specify(persons, new Where<Person>() {{ each(item.getAge(), is(greaterThan(30))); }});
        }
    }
}