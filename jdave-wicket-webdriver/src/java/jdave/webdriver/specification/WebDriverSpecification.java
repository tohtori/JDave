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

import java.io.IOException;

import jdave.Specification;
import jdave.webdriver.WebDriverHolder;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxLauncher;
import org.openqa.selenium.firefox.internal.ProfilesIni;

/**
 * @author Marko Sibakov
 */
public abstract class WebDriverSpecification<T> extends Specification<T> {
    private static final String FIREFOX_PROFILE_NAME = "WebDriver";

    @Override
    public final void create() throws IOException {
        initFirefoxProfile();
        WebDriverHolder.set(new FirefoxDriver());
        onCreate();
    }

    public void onCreate() {
    }

    @Override
    public final void destroy() throws Exception {
        WebDriver webDriver = WebDriverHolder.get();
        webDriver.manage().deleteAllCookies();
        webDriver.quit();
        WebDriverHolder.clear();
        onDestroy();
    }

    public void onDestroy() {
    }

    private void initFirefoxProfile() throws IOException {
        FirefoxBinary binary = new FirefoxBinary();
        FirefoxLauncher launcher = new FirefoxLauncher(binary);
        ProfilesIni profiles = new ProfilesIni();
        if (profiles.getProfile(FIREFOX_PROFILE_NAME) == null) {
            launcher.createBaseWebDriverProfile(FIREFOX_PROFILE_NAME);
        }
    }
}
