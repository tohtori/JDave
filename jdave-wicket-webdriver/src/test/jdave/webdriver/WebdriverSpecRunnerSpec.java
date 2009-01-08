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

import org.jmock.Expectations;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.laughingpanda.beaninject.Inject;
import org.openqa.selenium.WebDriver;

/**
 * @author Juha Karemo
 */
@RunWith(JDaveRunner.class)
public class WebdriverSpecRunnerSpec extends Specification<WebDriverSpecRunner> {
    public class AnyWebdriverSpecRunner {
        private Runner jdaveRunner = mock(Runner.class);
        private WebDriverFactory webDriverFactory = mock(WebDriverFactory.class);
        private WebDriver webDriver = mock(WebDriver.class);
        private Browser browser = mock(Browser.class);

        public WebDriverSpecRunner create() {
            WebDriverSpecRunner runner = new WebDriverSpecRunner(WebdriverSpecRunnerSpec.class);
            Inject.field("jDaveRunner").of(runner).with(jdaveRunner);
            Inject.field("webDriverFactory").of(runner).with(webDriverFactory);
            Inject.field("browser").of(runner).with(browser);
            return runner;
        }
        
        public void delegatesGetDescriptionToJDaveRunner() {
            final Description description = mock(Description.class);
            checking(new Expectations() {{
                one(jdaveRunner).getDescription(); will(returnValue(description));
            }});
            specify(context.getDescription(), does.equal(description));
        }

        public void delegatesRunToJDaveRunnerUsingSingleBrowsingSession() {
            final RunNotifier runNotifier = mock(RunNotifier.class);
            checking(new Expectations() {{
                one(webDriverFactory).createFireFoxDriver(); will(returnValue(webDriver));
                one(browser).open();
                one(jdaveRunner).run(runNotifier);
                one(browser).close();
            }});
            context.run(runNotifier);
            specify(WebDriverHolder.get(), does.equal(webDriver));
        }
        
        public void closesBrowserIfRunThrowsExpection() {
            final RunNotifier runNotifier = mock(RunNotifier.class);
            checking(new Expectations() {{
                one(webDriverFactory).createFireFoxDriver(); will(returnValue(webDriver));
                one(browser).open();
                one(jdaveRunner).run(runNotifier); will(throwException(new RuntimeException("oops")));
                one(browser).close();
            }});
            specify(new Block() {
                public void run() throws Throwable {
                    context.run(runNotifier);
                }
            }, does.raise(RuntimeException.class, "oops"));
        }

        public void destroy() {
            WebDriverHolder.clear();
        }
    }
}
