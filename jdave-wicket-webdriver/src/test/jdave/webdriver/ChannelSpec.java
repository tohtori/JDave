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
package jdave.webdriver;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;

/**
 * @author Juha Karemo
 */
@RunWith(JDaveRunner.class)
public class ChannelSpec extends Specification<Channel> {
    public class AnyChannel {
        private WicketWebDriver webDriver = mock(WicketWebDriver.class);

        public Channel create() {
            WebDriverHolder.set(webDriver);
            return new Channel();
        }

        public void assumesBrowserHasProcessedAjaxResponseIfChannelStatusListeningJavascriptReturnsNull() {
            checking(new Expectations() {{
                one(webDriver).executeScript(with(any(String.class)), with(any(Object.class))); will(returnValue(null));
            }});
            context.waitForAjax();
        }

        public void assumesBrowserHasNotYetProcessedAjaxResponseIfChannelStatusListeningJavascriptReturnsTrue() {
            checking(new Expectations() {{
                one(webDriver).executeScript(with(any(String.class)), with(any(Object.class))); will(returnValue(true));
                one(webDriver).executeScript(with(any(String.class)), with(any(Object.class))); will(returnValue(false));
            }});
            context.waitForAjax();
        }
        
        public void assumesBrowserHasProcessedAjaxResponseIfChannelStatusListeningJavascriptReturnsFalse() {
            checking(new Expectations() {{
                one(webDriver).executeScript(with(any(String.class)), with(any(Object.class))); will(returnValue(false));
            }});
            context.waitForAjax();
        }

        public void destroy() {
            WebDriverHolder.clear();
        }
    }
}
