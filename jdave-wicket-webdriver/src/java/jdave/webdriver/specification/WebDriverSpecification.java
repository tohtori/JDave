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

import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @author Marko Sibakov
 */
public abstract class WebDriverSpecification<T> extends Specification<T> {
    @Override
    public void create() throws IOException {
        WebDriverHolder.set(new FirefoxDriver());
        onCreate();
    }

    public void onCreate() {
    }

    @Override
    public void destroy() throws Exception {
        WebDriverHolder.get().close();
        onDestroy();
    }

    public void onDestroy() {
    }
}
