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

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.AbstractReadOnlyModel;

/**
 * @author Janne Hietam&auml;ki
 */
public class PageWithLink extends WebPage {
    int counter = 0;

    public PageWithLink() {
        add(new Label("value", new AbstractReadOnlyModel() {

            @Override
            public Object getObject() {
                return counter;
            }

        }));
        add(new Link("link") {
            @Override
            public void onClick() {
                counter++;
            }
        });
    }
}
