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
package jdave.contract;

import jdave.ExpectationFailedException;
import jdave.IContract;

/**
 * @author Joni Freeman
 */
public class NotContract implements IContract {
    private final IContract contract;

    public NotContract(IContract contract) {
        this.contract = contract;
    }

    public void isSatisfied(Object obj) throws ExpectationFailedException {
        try {
            contract.isSatisfied(obj);
        } catch (ExpectationFailedException e) {
            return;
        }
        throw new ExpectationFailedException("should not satisfy " + 
                contract.getClass().getSimpleName() + " contract");
    }
}
