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
package jdave.examples.wicket;

import java.util.Arrays;
import java.util.Collections;
import jdave.junit4.JDaveRunner;
import jdave.wicket.ComponentSpecification;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.tester.FormTester;
import static org.hamcrest.Matchers.is;
import org.jmock.Expectations;
import org.junit.runner.RunWith;

/**
 * @author Joni Freeman
 */
@RunWith(JDaveRunner.class)
public class DictionarySpec extends ComponentSpecification<DictionaryPanel,Void> {
    private DictionaryPanel dictionary;
    private IDictionaryService service = mock(IDictionaryService.class);
    
    public class WhenDictionaryContainsGivenWord {
        public DictionaryPanel create() {
            checking(new Expectations() {{ 
                one(service).lookup("hello"); will(returnValue(Arrays.asList("hei", "moi")));
            }});
            return dictionary = startComponent();
        }
        
        public void thePanelListsAllWords() {
            query("hello");
            RefreshingView<String> words = (RefreshingView<String>) dictionary.get("words");
            specify(words, containsInOrder("hei", "moi"));
            specify(selectFirst(Label.class, "word").from(dictionary).getDefaultModelObjectAsString(), is("hei"));
            specify(selectAll(Label.class, "word").from(dictionary).size(), does.equal(2));
        }
    }
    
    public class WhenDictionaryDoesNotContainGivenWord {
        public DictionaryPanel create() {
            checking(new Expectations() {{ 
                one(service).lookup("hello"); will(returnValue(Collections.emptyList()));
            }});
            return dictionary = startComponent();
        }
        
        public void thePanelDisplaysNoHitsMessage() {
            query("hello");
            FeedbackMessage message = dictionary.getFeedbackMessage();
            specify(message.getMessage(), should.equal("No hits."));
        }        
    }
    
    private void query(String wordToLookup) {
        FormTester form = wicket.newFormTester(dictionary.getId() + ":form");
        form.setValue("query", wordToLookup);
        form.submit();
    }

    @Override
    protected DictionaryPanel newComponent(String id, IModel<Void> model) {
        return new DictionaryPanel(id) {
            @Override
            protected IDictionaryService getService() {
                return service;
            }            
        };
    }
}
