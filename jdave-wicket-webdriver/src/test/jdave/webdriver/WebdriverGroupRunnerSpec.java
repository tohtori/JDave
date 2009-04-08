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

import jdave.Specification;
import jdave.junit4.JDaveRunner;
import jdave.runner.Groups;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.laughingpanda.beaninject.Inject;
import org.openqa.selenium.WebDriver;

/**
 * @author Juha Karemo
 */
@RunWith(JDaveRunner.class)
public class WebdriverGroupRunnerSpec extends Specification<WebDriverGroupRunner> {
    public class AnyWebDriverGroupRunner {
        private WebDriverFactory webDriverFactory = mock(WebDriverFactory.class);
        private WebDriver webDriver = mock(WebDriver.class);
        private Browser browser = mock(Browser.class);

        public WebDriverGroupRunner create() {
            WebDriverGroupRunner runner = new WebDriverGroupRunner(Suite.class);
            Inject.field("webDriverFactory").of(runner).with(webDriverFactory);
            Inject.field("browser").of(runner).with(browser);
            return runner;
        }

        @Groups(include = "any")
        public class Suite {}

        public void opensBrowserBeforeRun() {
            checking(new Expectations() {{
                one(webDriverFactory).createFireFoxDriver(); will(returnValue(webDriver));
                one(browser).open();
            }});
            context.onBeforeRun();
            specify(WebDriverHolder.get(), does.equal(webDriver));
        }

        public void closesBrowserAfterRun() {
            checking(new Expectations() {{
                one(browser).close();
            }});
            context.onAfterRun();
            specify(WebDriverHolder.get(), does.equal(null));
        }

        public void destroy() {
            WebDriverHolder.clear();
        }
    }
}
