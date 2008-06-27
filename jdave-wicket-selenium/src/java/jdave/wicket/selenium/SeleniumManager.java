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

import jdave.DefaultContextObjectFactory;
import jdave.DefaultLifecycleListener;
import jdave.IContextObjectFactory;

import org.apache.wicket.Application;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.protocol.http.WebApplication;
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
final class SeleniumManager<T extends MarkupContainer> extends DefaultLifecycleListener implements IContextObjectFactory<T> {
    public static final int DEFAULT_PORT = 4444;
    public final static String MANAGER_KEY = "SeleniumSpecification";

    final SeleniumSpecification<T> specification;
    SeleniumServer server;
    private WebApplicationContext web;
    private Object contextClass;
    private WebApplication application;

    SeleniumManager(SeleniumSpecification<T> specification) {
        this.specification = specification;
    }

    @Override
    public void afterContextDestroy(Object contextInstance) {
        server.stop();
    }

    protected String getDefaultURL() {
        return "http://localhost:" + DEFAULT_PORT;
    }

    public void start() {
        try {
            // Kludge to disable Jetty XML validating which causes class loading
            // issues
            System.setProperty("org.mortbay.xml.XmlParser.NotValidating", "true");
            web = new WebApplicationContext();

            WebApplicationHandler handler = new WebApplicationHandler();

            FilterHolder holder = handler.defineFilter("wicketFilter", WicketFilter.class.getName());
            holder.setInitParameter(WicketFilter.APP_FACT_PARAM, SeleniumTestApplicationFactory.class.getName().toString());
            handler.addFilterPathMapping("/*", "wicketFilter", Dispatcher.__ALL);

            web.setAttribute(MANAGER_KEY, this);
            web.setContextPath("wicket");
            web.addHandler(handler);

            server = new SeleniumServer();
            server.getServer().addContext(web);
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        Selenium selenium = new DefaultSelenium("localhost", DEFAULT_PORT, "*firefox", getDefaultURL());
        selenium.start();
        specification.selenium = selenium;
    }

    public T newContextObject(Object context) throws Exception {
        contextClass = context;
        specification.selenium.open(getDefaultURL() + "/wicket");
        specification.selenium.waitForPageToLoad("500");
        return specification.context;
    }

    public void afterContextInstantiation(Object contextInstance) {
        Application.set(application);
    }

    public T createContext() {
        try {
            specification.context = specification.be = new DefaultContextObjectFactory<T>().newContextObject(contextClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return specification.context;
    }

    public void init(SeleniumWebApplication application) {
        this.application = application;
        specification.wicket.setApplication(application);

        try {
            specification.onCreate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}