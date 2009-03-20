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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jdave.webdriver.Channel;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * @author Marko Sibakov
 */
public class DropDownChoice {
    private WebElement webElement;
    private Channel channel = new Channel();

    public DropDownChoice(WebElement webElement) {
        this.webElement = webElement;
    }

    public void select(String selection) {
        Map<String, WebElement> optionsMap = optionsMap(webElement.findElements(By.tagName("option")));
        WebElement selectedWebElement = optionsMap.get(selection);
        if (selectedWebElement != null) {
            selectedWebElement.setSelected();
        } else {
            throw new NoSuchElementException("Option '" + selection + "' not found from DropDownChoice");
        }
        channel.waitForAjax();
    }

    private Map<String, WebElement> optionsMap(List<WebElement> options) {
        LinkedHashMap<String, WebElement> optionsMap = new LinkedHashMap<String, WebElement>();
        for (WebElement webElement : options) {
            optionsMap.put(webElement.getText(), webElement);
        }
        return optionsMap;
    }

    public String getValue() {
        return webElement.getValue();
    }

    public String getText() {
        return webElement.getText();
    }
}