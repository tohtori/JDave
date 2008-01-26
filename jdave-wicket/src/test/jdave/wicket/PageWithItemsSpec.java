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
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import jdave.junit4.JDaveRunner;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import org.junit.runner.RunWith;

/**
 * @author Joni Freeman
 * @author Timo Rantalaiho
 */
@RunWith(JDaveRunner.class)
public class PageWithItemsSpec extends ComponentSpecification<PageWithItems> {
    public class WhenListHasItems {
        private List<Integer> list = Arrays.asList(0, 1, 2);
        
        public PageWithItems create() {
            return startComponent(new Model((Serializable) list));
        }
        
        public void theItemsCanBeReferencedEasilyInListView() {
            ListItem item = itemAt((ListView) context.get("listView"), 1);
            specify(item.get("item").getModelObject(), does.equal("1"));
        }
        
        public void theItemsCanBeReferencedEasilyInRefreshingView() {
            Item item = itemAt((RefreshingView) context.get("refreshingView"), 1);
            specify(item.get("item").getModelObject(), does.equal("1"));
        }
        
        public void theModelObjectsCanBeCollectedFromComponents() {
            ListView listView = (ListView) context.get("listView");
            specify(modelObjects(listView.iterator()), containsInOrder(0, 1, 2));
        }
        
        public void theModelObjectsCanBeUsedInContainmentExpectations() {
            ListView listView = (ListView) context.get("listView");
            specify(listView, containsInOrder(0, 1, 2));
        }
        
        public void theModelObjectsCanBeUsedInNegativeContainmentExpectations() {
            ListView listView = (ListView) context.get("listView");
            specify(listView, does.not().containAll(0, 1, 2, 4));
        }
        
        public void theFirstItemCanBePickedUsingHamcrestMatcher() {
            Item item = selectFirst(Item.class).which(is(0)).from(context);
            specify(item.get("item").getModelObject(), does.equal("0"));
        }
        
        public void allItemsCanBePickedUsingHamcrestMatcher() {
            @SuppressWarnings("unchecked")
            List<Item> items = selectAll(Item.class).which(is(anyOf(is(0), is(1)))).from(context);
            specify(modelObjects(items.iterator()), containsInOrder(0, 1));
        }

        public void theFirstItemCanBePickedUsingItsId() {
            Label item = selectFirst(Label.class, "item").from(context);
            specify(item.getModelObjectAsString(), is("0"));
        }

        public void allItemsWithSameIdCanBePickedUsingTheirId() {
            List<Label> items = selectAll(Label.class, "item").from(context);
            List<String> modelObjects = collectModelObjects(items);
            specify(modelObjects, containsInOrder("0", "1", "2", "0", "1", "2"));
        }

        public void selectingFirstWithIdAlsoHonorsHamcrestMatchers() {
            Label two = selectFirst(Label.class, "item").which(is("2")).from(context);
            specify(two.getModelObjectAsString(), does.equal("2"));
        }

        public void selectingAllWithIdAlsoHonorsHamcrestMatchers() {
            List<Label> twos = selectAll(Label.class, "item").which(is("2")).from(context);
            specify(collectModelObjects(twos), containsExactly("2", "2"));
        }

        private List<String> collectModelObjects(List<Label> items) {
            List<String> modelObjects = new ArrayList<String>();
            for (Label item : items) {
                modelObjects.add(item.getModelObjectAsString());
            }
            return modelObjects;
        }
    }
    
    @Override
    protected PageWithItems newComponent(String id, IModel model) {
        return new PageWithItems(model);
    }
}
