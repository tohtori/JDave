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

import java.util.Arrays;
import java.util.List;

import jdave.Specification;
import jdave.junit4.JDaveRunner;
import jdave.util.Fields;

import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;

/**
 * @author Juha Karemo
 */
@RunWith(JDaveRunner.class)
public class WebElementsSpec extends Specification<List<WebElement>> {
    public class ListOfWebElements {
        private WebElement webElement = mock(WebElement.class, "we1");
        private WebElement webElement2 = mock(WebElement.class, "we2");

        public List<WebElement> create() {
            return Arrays.asList(webElement, webElement2);
        }

        public void canBeConvertedToLinks() {
            List<Link> links = WebElements.asLinks(context);
            specify(Fields.get(links.get(0), "webElement"), does.equal(webElement));
            specify(Fields.get(links.get(1), "webElement"), does.equal(webElement2));
        }
    }
}
