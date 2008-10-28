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

import jdave.Block;
import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.apache.commons.lang.StringUtils;
import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * @author Marko Sibakov
 */
@RunWith(JDaveRunner.class)
public class DropDownChoiceSpec extends Specification<DropDownChoice> {
	public class DropDownChoiceThatContainsOptions {
		private static final String OPTIONS = " foo \n lol \n";
		private static final String OPTION_NAME = "foo";
		private WebElement webElement = mock(WebElement.class);

		public DropDownChoice create() {
			DropDownChoice textBox = new DropDownChoice(webElement);
			return textBox;
		}

		public void optionCanBeSelected() {
			checking(new Expectations() {{
                one(webElement).sendKeys(OPTION_NAME);
                one(webElement).getText(); will(returnValue(OPTIONS));
		    }});
			context.select(OPTION_NAME);
		}

		public void raisesExceptionIfOptionIsNotFound() {
			checking(new Expectations() {{
		            never(webElement).sendKeys(OPTION_NAME);
		            one(webElement).getText(); will(returnValue(StringUtils.EMPTY));
		    }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.select("fooooo");
                }
            }, does.raiseExactly(NoSuchElementException.class));
		}
	}
}