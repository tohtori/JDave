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
package jdave.mock;

import jdave.ContainmentSupport;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.States;
import org.jmock.internal.ExpectationBuilder;
import org.jmock.lib.legacy.ClassImposteriser;

/**
 * Note, most of these methods are copied from jmock MockObjectTestCase.
 * We do not want to derive from junit's TestCase as MockObjectTestCase
 * does.
 * 
 * @author Joni Freeman
 */
public class MockSupport extends ContainmentSupport {
    private final Mockery mockery = new Mockery();

    protected MockSupport() {
        mockery.setExpectationErrorTranslator(JDaveErrorTranslator.INSTANCE); 
        mockery.setImposteriser(ClassImposteriser.INSTANCE);
    }

    public Mockery mockery() {
        return mockery;
    }
    
    public void verifyMocks() {
        mockery.assertIsSatisfied();
    }
    
    /**
     * Sets the result returned for the given type when no return value has been explicitly
     * specified in the expectation.
     *
     * @param type
     *    The type for which to return <var>result</var>.
     * @param result
     *    The value to return when a method of return type <var>type</var>
     *    is invoked for which an explicit return value has has not been specified.
     */
    public void setDefaultResultForType(Class<?> type, Object result) {
        mockery.setDefaultResultForType(type, result);
    }
    
    /**
     * Specify expectations upon the mock objects in the test.
     */
    public void checking(ExpectationBuilder expectations) {
        mockery.checking(expectations);
    }

    /**
     * Create a mock object of type T with an explicit name.
     *
     * @param typeToMock
     *  The type to be mocked
     * @param name
     *  The name of the new mock object that is used to identify the mock object
     *  in error messages
     * @return
     *  A new mock object of type
     */
    public <T> T mock(Class<T> typeToMock, String name) {
        return mockery.mock(typeToMock, name);
    }

    /**
     * Create a mock object of type T with a name derived from its type.
     *
     * @param typeToMock
     *  The type to be mocked
     * @return
     *  A new mock object of type
     */
    public <T> T mock(Class<T> typeToMock) {
        return mockery.mock(typeToMock);
    }
    
    /**
     * Create a dummy mock object of type T with a name derived from its type.
     * All method invocations of dummy are ignored.
     *
     * @param typeToMock
     *  The type to be mocked
     * @param name
     *  The name of the new mock object that is used to identify the mock object
     *  in error messages
     * @return
     *  A new dummy mock object of type
     */
    public <T> T dummy(Class<T> typeToMock, String name) {
        return ignore(mock(typeToMock, name));
    }
    
    /**
     * Create a dummy mock object of type T with a name derived from its type.
     * All method invocations of dummy are ignored.
     *
     * @param typeToMock
     *  The type to be mocked
     * @return
     *  A new dummy mock object of type
     */
    public <T> T dummy(Class<T> typeToMock) {
        return ignore(mock(typeToMock));
    }

    private <T> T ignore(final T dummy) {
        checking(new Expectations() {{ 
            ignoring(dummy);
        }});
        return dummy;
    }

    /**
     * Returns a new sequence that is used to constrain the order in which
     * expectations can occur.
     *
     * @param name
     *     The name of the sequence.
     * @return
     *     A new sequence with the given name.
     */
    public Sequence sequence(String name) {
        return mockery.sequence(name);
    }
    
    /**
     * Returns a new state machine that is used to constrain the order in which
     * expectations can occur.
     *
     * @param name
     *     The name of the state machine.
     * @return
     *     A new state machine with the given name.
     */
    public States states(String name) {
        return mockery.states(name);
    }
}
