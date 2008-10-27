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
package jdave.webdriver.elements;

import jdave.Specification;
import jdave.junit4.JDaveRunner;
import jdave.webdriver.Channel;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.laughingpanda.beaninject.Inject;
import org.openqa.selenium.WebElement;

/**
 * @author Juha Karemo
 */
@RunWith(JDaveRunner.class)
public class CheckBoxSpec extends Specification<CheckBox> {
    private WebElement webElement = mock(WebElement.class);
    private Channel channel = mock(Channel.class);
    private CheckBox checkBox;

    public class AnyCheckBox {
        public void returnsSelectionStatus() {
            checking(new Expectations() {{
                one(webElement).isSelected(); will(returnValue(false));
             }});
            specify(checkBox.isSelected(), does.equal(false));
        }
    }
    
    public class SelectedCheckBox {
        public CheckBox create() {
            checking(new Expectations() {{
                allowing(webElement).isSelected(); will(returnValue(true));
             }});
            return checkBox;
        }

        public void canBeUnselected() {
            checking(new Expectations() {{
               one(webElement).click();
               one(channel).waitForAjax();
            }});
            context.unselect();
        }
        
        public void doesNothingIfReselected() {
            context.select();
        }
    }
    
    public class UnselectedCheckBox {
        public CheckBox create() {
            checking(new Expectations() {{
                allowing(webElement).isSelected(); will(returnValue(false));
             }});
            return checkBox;
        }

        public void canBeSelected() {
            checking(new Expectations() {{
               one(webElement).click();
               one(channel).waitForAjax();
            }});
            context.select();
        }
        
        public void doesNothingIfUnselected() {
            context.unselect();
        }
    }
    
    @Override
    public void create() throws Exception {
        checkBox = new CheckBox(webElement);
        Inject.field("channel").of(checkBox).with(channel);
    }
}
