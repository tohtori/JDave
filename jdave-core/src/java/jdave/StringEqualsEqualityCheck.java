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
package jdave;

import jdave.equality.EqualsEqualityCheck;
import jdave.util.Diff;

/**
 * @author Joni Freeman
 */
public class StringEqualsEqualityCheck extends EqualsEqualityCheck {
    public StringEqualsEqualityCheck(String expected) {
        super(expected);
    }
    
    @Override
    public String error(Object actual) {
        if (actual == null || !actual.getClass().equals(String.class)) {
            return super.error(actual);
        }
        Diff diff = Diff.diff((String) actual, (String) expected);
        return "The given strings do not match:\n" + diff.verbose();
    }
}
