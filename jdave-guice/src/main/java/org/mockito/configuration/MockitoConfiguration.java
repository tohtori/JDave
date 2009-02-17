/*
 * Copyright 2009 the original author or authors.
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
package org.mockito.configuration;

import jdave.guice.internal.GuiceAnnotationEngine;
import org.mockito.ReturnValues;
import org.mockito.internal.returnvalues.SmartNullReturnValues;

/**
 * This is how Mockito is configured.
 */
public class MockitoConfiguration implements IMockitoConfiguration {
    public AnnotationEngine getAnnotationEngine() {
        return new GuiceAnnotationEngine();
    }

    // FIXME this should be configurable even when using jdave-guice
    public ReturnValues getReturnValues() {
        return new SmartNullReturnValues();
    }
}
