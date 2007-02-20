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

import org.jmock.core.Invocation;
import org.jmock.core.Stub;

/**
 * Copied implementation from jmock's <code>ThrowStub</code>. The only difference is that we're not extending from JUnit's <code>junit.framework.Assert</code>.
 * 
 * @author Lasse Koskela
 */
public class ThrowStub implements Stub {

    private Throwable throwable;

    public ThrowStub(Throwable throwable) {
        this.throwable = throwable;
    }

    public Object invoke(Invocation invocation) throws Throwable {
        if (isThrowingCheckedException()) {
            checkTypeCompatiblity(invocation.invokedMethod.getExceptionTypes());
        }

        throwable.fillInStackTrace();
        throw throwable;
    }

    public StringBuffer describeTo(StringBuffer buffer) {
        return buffer.append("throws <").append(throwable).append(">");
    }

    private void checkTypeCompatiblity(Class[] allowedExceptionTypes) {
        for (int i = 0; i < allowedExceptionTypes.length; i++) {
            if (allowedExceptionTypes[i].isInstance(throwable))
                return;
        }

        reportIncompatibleCheckedException(allowedExceptionTypes);
    }

    private void reportIncompatibleCheckedException(Class[] allowedTypes) {
        StringBuffer message = new StringBuffer();

        message.append("tried to throw a ");
        message.append(throwable.getClass().getName());
        message.append(" from a method that throws ");

        if (allowedTypes.length == 0) {
            message.append("no exceptions");
        } else {
            for (int i = 0; i < allowedTypes.length; i++) {
                if (i > 0)
                    message.append(",");
                message.append(allowedTypes[i].getName());
            }
        }

        throw new RuntimeException(message.toString());
    }

    private boolean isThrowingCheckedException() {
        return !(throwable instanceof RuntimeException || throwable instanceof Error);
    }
}
