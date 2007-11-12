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
package jdave.runner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

/**
 * @author Joni Freeman
 */
public class ResolutionTest {
    @Test
    public void testIncludesGroupIfItIsIncluded() throws Exception {
        Resolution resolution = new Resolution(WhenGroupsAreIncluded.class.getAnnotation(Groups.class));
        assertTrue(resolution.includes(Arrays.asList("g1", "g2")));
    }
    
    @Test
    public void testExcludesGroupIfItIsNotIncluded() throws Exception {
        Resolution resolution = new Resolution(WhenGroupsAreIncluded.class.getAnnotation(Groups.class));
        assertFalse(resolution.includes(Arrays.asList("g3")));
    }
    
    @Test
    public void testExcludesGroupIfItIsIncludedButAlsoExcluded() throws Exception {
        Resolution resolution = new Resolution(WhenGroupsAreExcluded.class.getAnnotation(Groups.class));
        assertFalse(resolution.includes(Arrays.asList("g1")));
    }
    
    @Test
    public void testIncludesGroupIfItIsIncludedAndNotExcluded() throws Exception {
        Resolution resolution = new Resolution(WhenGroupsAreExcluded.class.getAnnotation(Groups.class));
        assertTrue(resolution.includes(Arrays.asList("g2", "g3")));
    }
    
    @Groups(include={ "g1", "g2" })
    public static class WhenGroupsAreIncluded {
    }
    
    @Groups(include={ Groups.ALL  }, exclude={ "g1" })
    public static class WhenGroupsAreExcluded {
    }
}
