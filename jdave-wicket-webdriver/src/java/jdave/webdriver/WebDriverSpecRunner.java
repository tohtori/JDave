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

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

/**
 * @author Juha Karemo
 */
public class WebDriverSpecRunner extends Runner {
    protected Runner jDaveRunner;
    protected WebDriverFactory webDriverFactory = new WebDriverFactory();
    protected Browser browser = new Browser();

    public WebDriverSpecRunner(Class<? extends Specification<?>> spec) {
        jDaveRunner = new JDaveRunner(spec);
    }

    @Override
    public Description getDescription() {
        return jDaveRunner.getDescription();
    }

    @Override
    public void run(RunNotifier notifier) {
        WebDriverHolder.set(webDriverFactory.createFireFoxDriver());
        try {
            browser.open();
            jDaveRunner.run(notifier);
        } finally {
            browser.close();
        }
    }
}
