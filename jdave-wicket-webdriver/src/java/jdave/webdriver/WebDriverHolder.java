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
    private static ThreadLocal<WebDriver> user1WebDriver = new ThreadLocal<WebDriver>();
    private static ThreadLocal<WebDriver> user2WebDriver = new ThreadLocal<WebDriver>();
    private static ThreadLocal<Boolean> user1IsActive = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return true;
        }
    };

    public static WebDriver get() {
        if (user1IsActive.get()) {
            return user1WebDriver.get();
        }
        return user2WebDriver.get();
    }

    public static void set(WebDriver webDriver) {
        user1WebDriver.set(webDriver);
    }
    
    public static void setForUser2(WebDriver webDriver) {
        user2WebDriver.set(webDriver);
    }
    
    public static void switchToUser1() {
        user1IsActive.set(true);
    }
    
    public static void switchToUser2() {
        if(user2WebDriver.get() == null) {
            throw new IllegalStateException("You need to set WebDriver for user2 before executing this method.");
        }
        user1IsActive.set(false);
    }

    public static void clear() {
        user1WebDriver.remove();
        user2WebDriver.remove();
        user1IsActive.remove();
    }
}
