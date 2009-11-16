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
package jdave.webdriver;

import jdave.Block;
import jdave.Specification;
import jdave.junit4.JDaveRunner;
import jdave.util.Fields;

import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

/**
 * @author Juha Karemo
 */
@RunWith(JDaveRunner.class)
public class WebDriverHolderSpec extends Specification<Void> {
    private WicketWebDriver webDriverForUser1 = mock(WicketWebDriver.class);

    public class InitializedSingleUserWebDriverHolder {
        public void create() {
            WebDriverHolder.set(webDriverForUser1);
        }

        public void doesNotSupportSwitchToUser2Method() {
            specify(new Block() {
                public void run() throws Throwable {
                    WebDriverHolder.switchToUser2();
                }
            }, does.raise(IllegalStateException.class, "You need to set WebDriver for user2 before executing this method."));
        }

        public void canBeCleared() {
            WebDriverHolder.clear();
            specify(WebDriverHolder.get(), does.equal(null));
        }

        public void returnsWebDriver() {
            specify(WebDriverHolder.get(), does.equal(webDriverForUser1));
        }

        public void canBeResetted() {
            WicketWebDriver newWebDriver = mock(WicketWebDriver.class, "new");
            WebDriverHolder.set(newWebDriver);
            specify(WebDriverHolder.get(), does.equal(newWebDriver));
        }
    }
    
    public class InitializedDualUserWebDriverHolder {
        private WebDriver webDriverForUser2 = mock(WebDriver.class, "wd2");

        public void create() {
            WebDriverHolder.set(webDriverForUser1);
            WebDriverHolder.setForUser2(webDriverForUser2);
        }

        public void canSwitchUser() {
            WebDriverHolder.switchToUser2();
            specify(WebDriverHolder.get(), does.equal(webDriverForUser2));
        }

        public void canBeCleared() {
            WebDriverHolder.clear();
            specify(WebDriverHolder.get(), does.equal(null));
            specify(Fields.<ThreadLocal<?>>getStatic(WebDriverHolder.class, "user2WebDriver").get(), does.equal(null));
            specify(Fields.<ThreadLocal<?>>getStatic(WebDriverHolder.class, "user1IsActive").get(), does.equal(true));
        }
    }
    
    public class DualUserWebDriverHolderThatHasUser2Active {
        private WebDriver webDriverForUser2 = mock(WebDriver.class, "wd2");

        public void create() {
            WebDriverHolder.set(webDriverForUser1);
            WebDriverHolder.setForUser2(webDriverForUser2);
            WebDriverHolder.switchToUser2();
        }

        public void canSwitchUser() {
            WebDriverHolder.switchToUser1();
            specify(WebDriverHolder.get(), does.equal(webDriverForUser1));
        }
    }
    
    @Override
    public void destroy() {
        WebDriverHolder.clear();
    }
}
