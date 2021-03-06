/*
 * Copyright 2006 the original author or authors.
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

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import java.util.Iterator;
import org.hamcrest.StringDescription;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Joni Freeman
 */
public class AnyContainmentTest extends ContainmentTest {
    @Before
    public void setUp() throws Exception {
        containment = new AnyContainment<Integer>(new Iterable<Integer>() {
            public Iterator<Integer> iterator() {
                return asList(1, 2, 3).iterator();
            }
        });
    }

    @Test
    public void testIsInPartialList() {
        assertTrue(containment.matches(asList(2)));
    }

    @Test
    public void testIsNotInListWhichDoesNotHaveAnyEqualElements() {
        assertFalse(containment.matches(asList(4)));
    }

    @Test
    public void describesWell() {
        final StringDescription description = new StringDescription();
        containment.matches(asList(4, 5, 6));
        containment.describeTo(description);
        assertThat(description.toString(),
                is("The specified collection '[4, 5, 6]' does not contain '[1, 2, 3]'"));
    }

    @Test
    public void canBeUsedAsAMatcher() {
        assertThat(asList(1, 2, 3), containment);
    }
}
