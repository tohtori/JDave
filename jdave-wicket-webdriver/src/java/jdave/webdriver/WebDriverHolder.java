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

import org.openqa.selenium.WebDriver;

/**
 * @author Juha Karemo
 */
public class WebDriverHolder {
    private static ThreadLocal<WebDriver> currentWebDriver = new ThreadLocal<WebDriver>();

    public static WebDriver get() {
        return currentWebDriver.get();
    }

    public static void set(WebDriver webDriver) {
        currentWebDriver.set(webDriver);
    }

    public static void clear() {
        currentWebDriver.remove();
    }
}
