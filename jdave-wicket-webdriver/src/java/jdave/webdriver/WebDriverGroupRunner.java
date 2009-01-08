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

import jdave.junit4.JDaveGroupRunner;

/**
 * @author Juha Karemo
 */
public class WebDriverGroupRunner extends JDaveGroupRunner {
    private Browser browser = new Browser();
    private WebDriverFactory webDriverFactory = new WebDriverFactory();

    public WebDriverGroupRunner(Class<?> suite) {
        super(suite);
    }

    @Override
    protected void onBeforeRun() {
        WebDriverHolder.set(webDriverFactory.createFireFoxDriver());
        browser.open();
    }

    @Override
    protected void onAfterRun() {
        browser.close();
    }
}
