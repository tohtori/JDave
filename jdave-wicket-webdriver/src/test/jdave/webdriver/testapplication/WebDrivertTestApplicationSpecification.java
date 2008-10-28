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

import javax.swing.text.html.HTML.Tag;

import jdave.junit4.JDaveRunner;
import jdave.webdriver.WebDriverHolder;
import jdave.webdriver.elements.CheckBox;
import jdave.webdriver.elements.Count;
import jdave.webdriver.elements.DropDownChoice;
import jdave.webdriver.elements.Find;
import jdave.webdriver.elements.TextBox;
import jdave.webdriver.specification.WebDriverSpecification;

import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author Marko Sibakov
 * @author Juha Karemo
 */
@RunWith(JDaveRunner.class)
public class WebDrivertTestApplicationSpecification extends WebDriverSpecification<Void> {
    private TestAppStarter testAppStarter;

    @Override
    public void onCreate() {
        testAppStarter = new TestAppStarter();
        testAppStarter.start();
    }

    public class TestPage {
        private WebDriver webDriver = WebDriverHolder.get();

        public void create() {
            webDriver.navigate().to("http://localhost:8080");
        }

        public void containsBodyText() {
            specify(webDriver.getPageSource().contains("foo"));
        }

        public void containsCheckBox() {
            CheckBox checkbox = Find.checkBox(By.className("testClass"));
            specify(checkbox.isSelected(), does.equal(false));
            checkbox.select();
            specify(webDriver.getPageSource().contains("checkbox clicked"));
            specify(checkbox.isSelected());
            checkbox.unselect();
            specify(checkbox.isSelected(), does.equal(false));
        }
        
        public void containsTwoTableRows() {
            specify(Count.of(Tag.TR).fromParent(By.id("testTable")), does.equal(2));
        }

        public void containsLinkThatCanBeClicked() {
            Find.link(By.linkText("testLink")).click();
            specify(webDriver.getPageSource().contains("link clicked"));
        }
        
        public void containsTextBox() {
            TextBox textBox = Find.textBox(By.name("testTextField"));
            textBox.type("some value");
            specify(textBox.getText(), does.equal("some value"));
            textBox.clear();
            specify(textBox.getText(), does.equal(""));
        }
        
        public void containsDropDownChoice() {
            DropDownChoice dropDownChoice = Find.dropDownChoice(By.name("connective"));
            dropDownChoice.select("and");
        }
    }

    @Override
    public void onDestroy() {
        testAppStarter.stop();
    }
}