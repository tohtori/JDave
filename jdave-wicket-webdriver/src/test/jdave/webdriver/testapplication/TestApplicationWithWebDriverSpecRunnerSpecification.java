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
package jdave.webdriver.testapplication;

import java.util.List;

import jdave.Group;
import jdave.Specification;
import jdave.webdriver.WebDriverSpecRunner;
import jdave.webdriver.WebDriverHolder;
import jdave.webdriver.elements.Find;
import jdave.webdriver.elements.Link;

import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author Juha Karemo
 */
@Group("lulz")
@RunWith(WebDriverSpecRunner.class)
public class TestApplicationWithWebDriverSpecRunnerSpecification extends Specification<Void> {
    private TestAppStarter testAppStarter;

    @Override
    public void create() {
        testAppStarter = new TestAppStarter();
        testAppStarter.start();
    }

    public class TestPage {
        private WebDriver webDriver;

        public void create() {
            webDriver = WebDriverHolder.get();
            webDriver.navigate().to("http://localhost:8080");
        }

        public void containsBodyText() {
            specify(webDriver.getPageSource().contains("foo"));
        }
        
        public void containsTwoLinksOfSameClassName() {
            List<Link> links = Find.links(By.className("test"));
            specify(links.size(), does.equal(2));
            links.get(0).click();
            specify(webDriver.getPageSource().contains("link clicked"));
        }
    }
    
    public class TestPageWithChildPage {
        private WebDriver webDriver;

        public void create() {
            webDriver = WebDriverHolder.get();
            webDriver.navigate().to("http://localhost:8080");
            Link link = Find.link(By.name("openChildPageLink"));
            link.click();
            WebDriverHolder.set(webDriver.switchTo().window("childPage"));
        }

        public void containsCloseLink() {
            Link closeLink = Find.link(By.name("close-link"));
            specify(closeLink, isNotNull());
        }
    }

    @Override
    public void destroy() {
        testAppStarter.stop();
    }
}