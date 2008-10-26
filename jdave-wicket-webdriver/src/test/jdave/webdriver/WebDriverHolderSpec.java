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
package jdave.webdriver;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;

/**
 * @author Juha Karemo
 */
@RunWith(JDaveRunner.class)
public class WebDriverHolderSpec extends Specification<Void> {
    private WicketWebDriver webDriver = mock(WicketWebDriver.class);

    public class InitialisedWebDriverHolder {
        public void create() {
            WebDriverHolder.set(webDriver);
        }

        public void canBeCleared() {
            WebDriverHolder.clear();
            specify(WebDriverHolder.get(), does.equal(null));
        }

        public void returnsWebDriver() {
            specify(WebDriverHolder.get(), does.equal(webDriver));
        }

        public void canBeResetted() {
            WicketWebDriver newWebDriver = mock(WicketWebDriver.class, "new");
            WebDriverHolder.set(newWebDriver);
            specify(WebDriverHolder.get(), does.equal(newWebDriver));
        }

        public void destroy() {
            WebDriverHolder.clear();
        }
    }
}
