/*
 * Copyright 2008 the original author or authors.
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
package jdave.webdriver.elements;

import jdave.Specification;
import jdave.junit4.JDaveRunner;
import jdave.webdriver.Channel;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.laughingpanda.beaninject.Inject;
import org.openqa.selenium.WebElement;

/**
 * @author Juha Karemo
 */
@RunWith(JDaveRunner.class)
public class LinkSpec extends Specification<Link> {
    public class AnyLink {
        private WebElement webElement = mock(WebElement.class);
        private Channel channel = mock(Channel.class);

        public Link create() {
            Link link = new Link(webElement);
            Inject.field("channel").of(link).with(channel);
            return link;
        }

        public void canBeClicked() {
            checking(new Expectations() {{
               one(webElement).click();
               one(channel).waitForAjax();
            }});
            context.click();
        }
    }
}
