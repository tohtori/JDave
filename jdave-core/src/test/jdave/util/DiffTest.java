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
package jdave.util;

import junit.framework.TestCase;

/**
 * @author Joni Freeman
 */
public class DiffTest extends TestCase {
    private static final String NEW_LINE = System.getProperty("line.separator");

    public void testFailsToPrintVerboseMessageWithEqualStrings() {
        Diff diff = Diff.diff("foo", "foo");
        try {
            diff.verbose();
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    public void testPrintsVerboseMessageWithEllipsisIfStringGetChopped() {
        Diff diff = Diff.diff(
                "this is a pretty long string which will get chopped",
                "this is a pretty long different string which will get chopped");
        assertEquals(
                "  Actual: ... a pretty long string which wi..." + NEW_LINE +
                "Expected: ... a pretty long different strin...",
                diff.verbose());
    }

    public void testPrintsVerboseMessageWithoutEllipsisIfStringDoesNotGetChopped() {
        Diff diff = Diff.diff(
                "this is a string",
                "this is another string");
        assertEquals(
                "  Actual: this is a string" + NEW_LINE +
                "Expected: this is another string",
                diff.verbose());
    }

    public void testPrintsVerboseMessageIfStringIsEmpty() {
        Diff diff = Diff.diff("", "this is another string");
        assertEquals(
                "  Actual: " + NEW_LINE +
                "Expected: this is another...",
                diff.verbose());
    }

    public void testPrintsVerboseMessageIfActualIsNull() {
        Diff diff = Diff.diff(null, "this is another string");
        assertEquals(
                "  Actual: <<null>>" + NEW_LINE +
                "Expected: this is another...",
                diff.verbose());
    }
    
    public void testPrintsVerboseMessageIfExpectedIsNull() {
        Diff diff = Diff.diff("this is a string", null);
        assertEquals(
                "  Actual: this is a strin..." + NEW_LINE +
                "Expected: <<null>>",
                diff.verbose());
    }
}
