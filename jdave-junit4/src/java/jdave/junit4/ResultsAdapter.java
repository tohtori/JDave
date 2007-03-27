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
package jdave.junit4;

import java.lang.reflect.Method;

import jdave.ExpectationFailedException;
import jdave.runner.IBehaviorResults;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

/**
 * The ResultsAdapter receives failure notifications from JDave and forwards
 * them to JUnit's RunNotifier.
 * 
 * @author Lasse Koskela
 */
public class ResultsAdapter implements IBehaviorResults {
    private final RunNotifier notifier;
    private final Description desc;
    private int errorCount;
    
    ResultsAdapter(RunNotifier notifier, Description desc) {
        this.notifier = notifier;
        this.desc = desc;
    }

    public void error(Method m, Throwable e) {
        errorCount++;
        notifier.fireTestFailure(new Failure(desc, e));
    }

    public void expected(Method m) {
        // ignored
    }

    public void unexpected(Method m, ExpectationFailedException e) {
        notifier.fireTestFailure(new Failure(desc, e));
    }
    
    public int getErrorCount() {
        return errorCount;
    }
}