/*
 * Copyright 2007 the original author or authors.
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
package jdave.wicket.selenium;

import org.apache.wicket.protocol.http.WebApplication;

/**
 * @author Janne Hietam&auml;ki
 */
public class SeleniumWebApplication extends WebApplication {
    @Override
    public Class<?> getHomePage() {
        return SeleniumTestWebPage.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void init() {
        SeleniumManager manager = (SeleniumManager) getServletContext().getAttribute(SeleniumManager.MANAGER_KEY);
        manager.init(this);
    }
}
