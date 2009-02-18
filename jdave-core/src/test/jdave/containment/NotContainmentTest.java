/*
 * Copyright 2009 the original author or authors.
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
import static org.junit.Assert.assertThat;
import java.util.Iterator;
import org.hamcrest.StringDescription;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Tuomas Kärkkäinen
 */
public class NotContainmentTest extends ContainmentTest {

    @Before
    public void setUp() throws Exception {
        containment = new NotContainment(new AnyContainment(new Iterable<Integer>() {
            public Iterator<Integer> iterator() {
                return asList(1, 2, 3).iterator();
            }
        }));
    }

    @Test
    public void canBeUsedAsAMatcher() {
        assertThat(asList(5), containment);
    }

    @Test
    public void describesWell() {
        final StringDescription description = new StringDescription();
        containment.matches(asList(1));
        containment.describeTo(description);
        assertThat(description.toString(),
                is("The specified collection '[1]' contains '[1, 2, 3]'"));
    }

}
