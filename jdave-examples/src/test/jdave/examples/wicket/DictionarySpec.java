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

import jdave.examples.wicket.DictionaryPanel;
import jdave.examples.wicket.IDictionaryService;
import jdave.junit4.JDaveRunner;
import jdave.wicket.ComponentSpecification;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.tester.FormTester;
import org.jmock.Expectations;
import org.junit.runner.RunWith;

/**
 * @author Joni Freeman
 */
@RunWith(JDaveRunner.class)
public class DictionarySpec extends ComponentSpecification<DictionaryPanel> {
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
            RefreshingView words = (RefreshingView) dictionary.get("words");
            specify(words, containsInOrder("hei", "moi"));
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
    protected DictionaryPanel newComponent(String id, IModel model) {
        return new DictionaryPanel(id) {
            @Override
            protected IDictionaryService getService() {
                return service;
            }            
        };
    }
}
