/*
 * Copyright (c) 2006 Reaktor Innovations Oy
 * All rights reserved.
 */
package jdave.examples;

import jdave.junit3.JDaveSuite;
import junit.framework.Test;

/**
 * @author Joni Freeman
 */
public class ExampleSuite extends JDaveSuite {
    public ExampleSuite() {
        super(StackSpec.class);
    }
    
    public static Test suite() {
        return new ExampleSuite();
    }
}
