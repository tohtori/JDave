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

import jdave.DefaultLifecycleListener;

import org.apache.wicket.Application;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.protocol.http.WicketFilter;
import org.mortbay.jetty.servlet.Dispatcher;
import org.mortbay.jetty.servlet.FilterHolder;
import org.mortbay.jetty.servlet.WebApplicationContext;
import org.mortbay.jetty.servlet.WebApplicationHandler;
import org.openqa.selenium.server.SeleniumServer;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**
 * @author Janne Hietam&auml;ki
 */
final class SeleniumLifecycleListener<T extends MarkupContainer> extends DefaultLifecycleListener {
    public static final int DEFAULT_PORT = 4444;
    private final SeleniumSpecification<T> specification;
    SeleniumServer server;

    SeleniumLifecycleListener(SeleniumSpecification<T> specification) {
        this.specification = specification;
    }

    @Override
    public void afterContextDestroy(Object contextInstance) {
        server.stop();
    }

    protected String getDefaultURL() {
        return "http://localhost:" + DEFAULT_PORT;
    }

    @Override
    public void afterContextInstantiation(Object contextInstance) {
        try {

            // Kludge to disable Jetty XML validating which causes class loading
            // issues
            System.setProperty("org.mortbay.xml.XmlParser.NotValidating", "true");

            WebApplicationContext web = new WebApplicationContext();

            WebApplicationHandler handler = new WebApplicationHandler();

            FilterHolder holder = handler.defineFilter("wicketFilter", WicketFilter.class.getName());
            holder.setInitParameter(WicketFilter.APP_FACT_PARAM, SeleniumTestApplicationFactory.class.getName().toString());
            handler.addFilterPathMapping("/*", "wicketFilter", Dispatcher.__ALL);

            web.setAttribute(SeleniumSpecification.COMPONENTFACTORY, specification.getMarkupContainerFactory());
            web.setContextPath("wicket");
            web.addHandler(handler);

            server = new SeleniumServer();
            server.getServer().addContext(web);
            server.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        setSelenium(new DefaultSelenium("localhost", DEFAULT_PORT, "*firefox", getDefaultURL()));
        getSelenium().start();
    }

    public T createContext() {
        getSelenium().open(getDefaultURL() + "/wicket");
        Application.set(specification.getMarkupContainerFactory().getApplication());
        return specification.context;
    }

    protected void setSelenium(Selenium selenium) {
        specification.selenium = selenium;
    }

    protected Selenium getSelenium() {
        return specification.selenium;
    }
}