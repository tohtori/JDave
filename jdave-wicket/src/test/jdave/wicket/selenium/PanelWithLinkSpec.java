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
package jdave.wicket.selenium;

import jdave.junit4.JDaveRunner;
import jdave.wicket.selenium.SeleniumSpecification;

import org.apache.wicket.model.IModel;
import org.junit.runner.RunWith;

/**
 * @author Janne Hietam&auml;ki
 */
@RunWith(JDaveRunner.class)
public class PanelWithLinkSpec extends SeleniumSpecification<PanelWithLink> {

    public class WhenNewPanelIsCreated {
        public PanelWithLink create() {
            return startComponent();
        }

        public void linkCanBeClicked() {
            selenium.click("link");
            selenium.waitForPageToLoad("500");
            specify(context.get("value").getModelObject(), does.equal(1));
        }
    }

    @Override
    protected PanelWithLink newComponent(String id, IModel model) {
        return new PanelWithLink(id, model);
    }
}
