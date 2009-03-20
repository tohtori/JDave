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

import java.util.List;

import javax.swing.text.html.HTML.Tag;

import jdave.Specification;
import jdave.junit4.JDaveRunner;
import jdave.webdriver.WebDriverHolder;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Juha Karemo
 */
@RunWith(JDaveRunner.class)
public class CountSpec extends Specification<Void> {
    public class AnyCount {
        private WebDriver webDriver = mock(WebDriver.class);
        private WebElement foundElement = mock(WebElement.class);
        private By by = mock(By.class);

        public void create() {
            WebDriverHolder.set(webDriver);
        }

        @SuppressWarnings("unchecked")
        public void returnsCountOfTagsFromParent() {
            checking(new Expectations() {{
                one(webDriver).findElement(by); will(returnValue(foundElement));
                List<WebElement> foundChildren = mock(List.class);
                one(foundElement).findElements(By.tagName("tr")); will(returnValue(foundChildren));
                one(foundChildren).size(); will(returnValue(2));
            }});
            specify(Count.of(Tag.TR).fromParent(by), does.equal(2));
        }

        public void destroy() {
            WebDriverHolder.clear();
        }
    }
}
