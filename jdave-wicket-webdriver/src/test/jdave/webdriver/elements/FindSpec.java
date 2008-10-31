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
import jdave.util.Fields;
import jdave.webdriver.WebDriverHolder;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Juha Karemo
 * @author Marko Sibakov
 */
@RunWith(JDaveRunner.class)
public class FindSpec extends Specification<Void> {
    public class AnyFind {
        private WebDriver  webDriver = mock(WebDriver.class);
        private WebElement foundElement = mock(WebElement.class);
        private By by = mock(By.class);
        
        public void create() {
            WebDriverHolder.set(webDriver);
        }
        
        public void returnsLink() {
            checking(new Expectations() {{
                one(webDriver).findElement(by); will(returnValue(foundElement));
            }});
            Link link = Find.link(by);
            specify(Fields.get(link, "webElement"), does.equal(foundElement));
        }

        public void returnsTextBox() {
            checking(new Expectations() {{
                one(webDriver).findElement(by); will(returnValue(foundElement));
            }});
            TextBox textBox = Find.textBox(by);
            specify(Fields.get(textBox, "webElement"), does.equal(foundElement));
        }
        
        public void returnsDropDownChoice() {
            checking(new Expectations() {{
                one(webDriver).findElement(by); will(returnValue(foundElement));
            }});
            DropDownChoice dropDownChoice = Find.dropDownChoice(by);
            specify(Fields.get(dropDownChoice, "webElement"), does.equal(foundElement));
        }

        public void destroy() {
            WebDriverHolder.clear();
        }
    }
}
