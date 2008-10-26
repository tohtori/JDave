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
import jdave.webdriver.Fields;
import jdave.webdriver.WebDriverHolder;
import jdave.webdriver.WicketWebDriver;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author Juha Karemo
 */
@RunWith(JDaveRunner.class)
public class FindSpec extends Specification<Void> {
    private WicketWebDriver webDriver = mock(WicketWebDriver.class);
    private WebElement foundElement = mock(WebElement.class);
    private By by = mock(By.class);

    public class AnyFind {
        public void create() {
            WebDriverHolder.set(webDriver);
        }
        
        public void returnsLink() {
            checking(new Expectations() {{
                one(webDriver).findElement(by); will(returnValue(foundElement));
            }});
            Link link = Find.link(by);
            specify(Fields.getValue(link, "webElement"), does.equal(foundElement));
        }
    
        public void destroy() {
            WebDriverHolder.clear();
        }
    }
}
