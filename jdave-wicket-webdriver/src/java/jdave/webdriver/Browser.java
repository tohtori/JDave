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
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxLauncher;
import org.openqa.selenium.firefox.internal.ProfilesIni;

/**
 * @author Juha Karemo
 */
public class Browser {
    private static final String DEFAULT_FIREFOX_PROFILE_NAME = "WebDriver";
    private String profileName = DEFAULT_FIREFOX_PROFILE_NAME;

    public Browser() {
    }

    public Browser(String profileName) {
        this.profileName = profileName;
    }

    public void open() {
        initFirefoxProfile();
    }

    public void close() {
        WebDriver webDriver = WebDriverHolder.get();
        Options options = webDriver.manage();
        if (options != null) {
            options.deleteAllCookies();
        }
        webDriver.quit();
    }

    @SuppressWarnings("deprecation")
    private void initFirefoxProfile() {
        FirefoxBinary binary = new FirefoxBinary();
        FirefoxLauncher launcher = new FirefoxLauncher(binary);
        ProfilesIni profiles = new ProfilesIni();
        if (profiles.getProfile(profileName) == null) {
            launcher.createBaseWebDriverProfile(binary, profileName, FirefoxDriver.DEFAULT_PORT);
        }
    }
}
