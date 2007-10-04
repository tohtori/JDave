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
package jdave.examples;

import jdave.ExpectationFailedException;
import jdave.Not;
import jdave.Specification;

/**
 * A sample which extends default vocabulary with:
 * <code>
 * specify(a, should.approximate(b)) 
 * </code>
 * and 
 * <code>
 * specify(a, should.not().approximate(b)) 
 * </code>
 * 
 * @author Joni Freeman
 */
public abstract class ExtendedSpecification<T> extends Specification<T> {
    protected ExtendedSpecification<T> should = this;
    protected ExtendedSpecification<T> does = this;
    protected ExtendedSpecification<T> must = this;
    
    public void specify(double actual, Approximation approximation) {
        approximation.approximate(actual);
    }
    
    public void specify(double actual, NotApproximation notApproximation) {
        notApproximation.approximate(actual);
    }
    
    public Approximation approximate(double expected) {
        return new Approximation(expected);
    }
    
    @Override
    public ExtendedNot<T> not() {
        super.not();
        return new ExtendedNot<T>(this);
    }
}

class ExtendedNot<T> extends Not<T> {
    public ExtendedNot(Specification<T> specification) {
        super(specification);
    }
    
    public NotApproximation approximate(double expected) {
        return new NotApproximation(expected);
    }
}

class Approximation {
    private final double expected;

    Approximation(double expected) {
        this.expected = expected;        
    }
    
    public void approximate(double actual) {
        if (Math.abs(actual - expected) > 0.01) {
            throw new ExpectationFailedException(actual + " is not an approximation of " + expected);
        }
    }
}

class NotApproximation {
    private final double expected;

    NotApproximation(double expected) {
        this.expected = expected;        
    }
    
    public void approximate(double actual) {
        if (Math.abs(actual - expected) < 0.01) {
            throw new ExpectationFailedException(actual + " is an approximation of " + expected);
        }
    }
}
