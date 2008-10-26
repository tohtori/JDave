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
public class TextBoxSpec extends Specification<TextBox> {
    public class AnyTextBox {
        private WebElement webElement = mock(WebElement.class);
        private Channel channel = mock(Channel.class);

        public TextBox create() {
            TextBox textBox = new TextBox(webElement);
            Inject.field("channel").of(textBox).with(channel);
            return textBox;
        }

        public void canBeCleared() {
            checking(new Expectations() {{
               one(webElement).clear();
               one(channel).waitForAjax();
            }});
            context.clear();
        }
        
        public void canBeTyped() {
            checking(new Expectations() {{
               one(webElement).sendKeys("text");
               one(channel).waitForAjax();
            }});
            context.type("text");
        }
        
        public void returnsText() {
            checking(new Expectations() {{
                one(webElement).getValue(); will(returnValue("value"));
             }});
             specify(context.getText(), does.equal("value"));
        }
    }
}
