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

import java.util.Arrays;

import jdave.Block;
import jdave.Specification;
import jdave.junit4.JDaveRunner;
import jdave.webdriver.Channel;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.laughingpanda.beaninject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebElement;

/**
 * @author Marko Sibakov
 * @author Petteri Parkkila
 */
@RunWith(JDaveRunner.class)
public class DropDownChoiceSpec extends Specification<DropDownChoice> {
    private WebElement webElement = mock(WebElement.class);
    private Channel channel = mock(Channel.class);
    private DropDownChoice dropDownChoice = new DropDownChoice(webElement);
    private WebElement option1 = mock(WebElement.class, "option1");
    private WebElement option2 = mock(WebElement.class, "option2");

    public class AnyDropDownChoice {
		private static final String INVALID_OPTION = "bar";

		public DropDownChoice create() {
            return dropDownChoice;
		}

        public void validOptionCanBeSelected() {
            Inject.field("channel").of(dropDownChoice).with(channel);
            final String selection = "lo";
            checking(new Expectations() {{
                one(webElement).findElements(By.tagName("option")); will(returnValue(Arrays.asList(option1, option2)));
                one(option1).getText(); will(returnValue("foo"));
                one(option2).getText(); will(returnValue(selection));
                one(option2).setSelected();
                one(channel).waitForAjax();
            }});
            context.select("lo");
        }

		public void raisesExceptionIfInvalidOptionIsSelected() {
			checking(new Expectations() {{
                one(webElement).findElements(By.tagName("option")); will(returnValue(Arrays.asList(option1, option2)));
                one(option1).getText(); will(returnValue("foo"));
                one(option2).getText(); will(returnValue("lo"));
		    }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.select(INVALID_OPTION);
                }
            }, does.raiseExactly(NoSuchElementException.class));
		}
		
		public void returnsValue() {
		    checking(new Expectations() {{
		        one(webElement).getValue(); will(returnValue("1"));
		    }});
            specify(context.getValue(), must.equal("1"));
		}

        public void returnsText() {
            checking(new Expectations() {{
                one(webElement).getText(); will(returnValue("text"));
            }});
            specify(context.getText(), must.equal("text"));
        }
	}
    
    public class DropDownChoiceWithSelectedItem {
        private String selection = "lo";
        
        public DropDownChoice create() {
            Inject.field("channel").of(dropDownChoice).with(channel);
            checking(new Expectations() {{
                allowing(webElement).findElements(By.tagName("option")); will(returnValue(Arrays.asList(option1, option2)));
                allowing(option1).getText(); will(returnValue("foo"));
                allowing(option2).getText(); will(returnValue(selection));
                one(option2).setSelected();
                one(channel).waitForAjax();
            }});
            dropDownChoice.select("lo");
            return dropDownChoice;
        }

        public void returnsSelectedAsText() {
            checking(new Expectations() {{
                one(option1).getAttribute("selected"); will(returnValue(null));
                one(option2).getAttribute("selected"); will(returnValue("selected"));
            }});
            specify(context.getSelected(), does.equal(selection));
        }
        
        public void throwsNotFoundExceptionWhenNoSelectedAttributeFound() {
            specify(new Block() {
                public void run() throws Throwable {
                    checking(new Expectations() {{
                        one(option1).getAttribute("selected"); will(returnValue(null));
                        one(option2).getAttribute("selected"); will(returnValue(null));
                    }});
                    context.getSelected();
                }
            }, does.raise(NotFoundException.class));
        }
    }
}