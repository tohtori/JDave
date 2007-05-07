/*
 * Copyright 2007 the original author or authors.
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
package jdave.mock;

import jdave.Specification;
import junit.framework.TestCase;

import org.jmock.Expectations;

/**
 * @author Pekka Enberg
 */
public class ConcreteClassMockingTest extends TestCase {
    public void testMockReturningInstanceOfFinalClass() {
        Specification<Void> specification = new Specification<Void>() { };
        final PurchaseOrder mock = specification.mock(PurchaseOrder.class, "mock");
        specification.checking(new Expectations() {
            {
                one(mock).getTimestamp();
                will(returnValue(new DateTime()));
            }
        });
        mock.getTimestamp();
    }

    public static class PurchaseOrder {
        DateTime timestamp = new DateTime();

        public DateTime getTimestamp() {
            return timestamp;
        }
    }

    public static final class DateTime {
    }
}
