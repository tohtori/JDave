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
package jdave.examples;

import jdave.examples.swing.AlbumPanelSpec;
import jdave.junit3.JDaveSuite;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Example junit3 suite. Recommended way is to use junit4 runners.
 * For junit4 there's no need to create a suite. Just tag the specs
 * with @RunWith(JDaveRunner.class) annotation.
 * 
 * @author Joni Freeman
 */
public class ExampleSuite extends TestSuite {
    public ExampleSuite() throws Exception {
        addTest(new JDaveSuite(StackSpec.class));
        addTest(new JDaveSuite(ObservableSpec.class));
        addTest(new JDaveSuite(ContainmentSampleSpec.class));
        addTest(new JDaveSuite(ContractEnforcementSampleSpec.class));
        addTest(new JDaveSuite(AlbumPanelSpec.class));
        addTest(new JDaveSuite(HamcrestSampleSpec.class));
    }
    
    public static Test suite() throws Exception {
        return new ExampleSuite();
    }
}
