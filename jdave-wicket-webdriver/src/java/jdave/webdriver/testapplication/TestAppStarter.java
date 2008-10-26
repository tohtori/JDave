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
package jdave.webdriver.testapplication;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.security.SslSocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;


/**
 * @author Marko Sibakov
 */
public class TestAppStarter {
    private static final int PORT = 8080;
    private static final String CONTEXT_PATH = "/";
    private static final String WAR = "src/main/webapp";
    private Server server;

    public void start() {
        server = new Server();
        SslSocketConnector sslConnector = new SslSocketConnector();
        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(PORT);
        server.setConnectors(new Connector[] { connector, sslConnector });
        WebAppContext app = new WebAppContext();
        app.setContextPath(CONTEXT_PATH);
        app.setWar(WAR);
        server.addHandler(app);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
