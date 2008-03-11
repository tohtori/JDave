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
package jdave.wicket;

import java.io.Serializable;
import java.util.Iterator;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.markup.repeater.util.ModelIteratorAdapter;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * @author Joni Freeman
 */
public class PageWithItems extends WebPage {
    public PageWithItems(final IModel items) {
        add(new ListView("listView", items) {
            @Override
            protected void populateItem(ListItem item) {
                item.add(new Label("item", item.getModelObject().toString()));
            }
        });
        add(new RefreshingView("refreshingView") {
            @Override
            protected Iterator<?> getItemModels() {
                return new ModelIteratorAdapter(((Iterable<?>) items.getObject()).iterator()) {
                    @Override
                    protected IModel model(Object object) {
                        return new Model((Serializable) object);
                    }                    
                };
            }

            @Override
            protected void populateItem(Item item) {
                item.add(new Label("item", item.getModelObject().toString()));
            }
        });
        add(new WebMarkupContainer("container", new CompoundPropertyModel(this))
            .add(new WebMarkupContainer("innerContainer")));
    }
}
