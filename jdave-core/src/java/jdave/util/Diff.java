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

/**
 * @author Joni Freeman
 */
public class Diff {
    private static final int CHOP_AT = 15;
    private final String actual;
    private final String expected;

    public Diff(String actual, String expected) {
        this.actual = actual;
        this.expected = expected;
    }

    public String verbose() {
        int diffIndex = diffIndex();
        if (diffIndex == -1) {
            throw new IllegalArgumentException("the strings equal");
        }
        int diffStart = Math.max(0, diffIndex - CHOP_AT);
        String ellipsisStart = (diffIndex > CHOP_AT ? "..." : "");
        int diffEndActual = Math.min(actual.length(), diffIndex + CHOP_AT);
        String ellipsisEndActual = (actual.length() > diffIndex + CHOP_AT ? "..." : "");
        int diffEndExpected = Math.min(expected.length(), diffIndex + CHOP_AT);
        String ellipsisEndExpected = (expected.length() > diffIndex + CHOP_AT ? "..." : "");
        StringBuilder message = new StringBuilder();
        message.append("  Actual: ");
        message.append(ellipsisStart + actual.substring(diffStart, diffEndActual) + ellipsisEndActual);
        message.append(System.getProperty("line.separator"));
        message.append("Expected: ");
        message.append(ellipsisStart + expected.substring(diffStart, diffEndExpected) + ellipsisEndExpected);
        return message.toString();
    }

    private int diffIndex() {
        if (actual.length() == 0 || expected.length() == 0) {
            return 0;
        }

        for (int i = 0; i < actual.length() && i < expected.length(); ++i) {
            if (actual.charAt(i) != expected.charAt(i)) {
                return i;
            }
        }
        return -1;
    }

    public static Diff diff(String actual, String expected) {
        return new Diff(actual, expected);
    }
}
