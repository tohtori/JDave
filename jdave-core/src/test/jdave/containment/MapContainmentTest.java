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
package jdave.containment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import jdave.ExpectationFailedException;
import jdave.Specification;
import jdave.util.FluentMap;
import jdave.util.Maps;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Joni Freeman
 */
public class MapContainmentTest {
    private Specification<Void> spec;
    private FluentMap<Integer, String> map;

    @Before
    public void setUp() throws Exception {
        map = Maps.map(1, "1").map(2, "2");
        spec = new Specification<Void>() {
        };
    }

    public void testProvidesWayToCheckIfMapContainsKeyValuePairs() {
        spec.specify(map, spec.maps(1, 2).to("1", "2"));
    }

    @Test
    public void testFailsIfMapDoesNotContainKeyValuePair() {
        try {
            spec.specify(map, spec.maps(1, 2).to("1", "3"));
            fail();
        } catch (final ExpectationFailedException e) {
            assertEquals("no mapping 2 -> 3", e.getMessage());
        }
    }

    @Test
    public void testFailsIfMapDoesNotContainKey() {
        try {
            spec.specify(map, spec.maps(3, 2).to("1", "2"));
            fail();
        } catch (final ExpectationFailedException e) {
            assertEquals("no mapping 3 -> 1", e.getMessage());
        }
    }
}
