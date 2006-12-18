/*
 * Copyright (c) 2006 Reaktor Innovations Oy
 * All rights reserved.
 */
package jdave.examples;

import java.util.ArrayList;
import java.util.Collection;

import jdave.Specification;
import jdave.junit3.JDaveSuite;
import junit.framework.Test;

/**
 * @author Joni Freeman
 */
public class ExampleSuite extends JDaveSuite {
    public ExampleSuite() {
        specs(specs());
    }
    
    private Collection<Class<? extends Specification<?>>> specs() {
        Collection<Class<? extends Specification<?>>> specs = new ArrayList<Class<? extends Specification<?>>>();
        specs.add(StackSpec.class);
        return specs;
    }
    
    public static Test suite() {
        return new ExampleSuite();
    }
}
