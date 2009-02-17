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
