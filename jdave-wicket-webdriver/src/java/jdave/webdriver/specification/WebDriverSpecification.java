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
package jdave.webdriver.specification;

import jdave.Specification;
import jdave.webdriver.Browser;
import jdave.webdriver.WebDriverHolder;

import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @author Marko Sibakov
 */
public abstract class WebDriverSpecification<T> extends Specification<T> {
    private Browser browser = new Browser();

    @Override
    public final void create() {
        onBeforeCreate();
        WebDriverHolder.set(new FirefoxDriver());
        browser.open();
        onCreate();
    }

    public void onBeforeCreate() {
    }

    public void onCreate() {
    }

    @Override
    public final void destroy() throws Exception {
        try {
            onBeforeDestroy();
            if (closeBrowserAfterTest()) {
                browser.close();
                WebDriverHolder.clear();
            }
        } finally {
            onDestroy();
        }
    }

    public void onDestroy() {
    }

    public void onBeforeDestroy() {
    }

    protected boolean closeBrowserAfterTest() {
        return true;
    }
}
