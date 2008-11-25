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

import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * @author Marko Sibakov
 */
public class DropDownChoice {
    private WebElement webElement;

    public DropDownChoice(WebElement webElement) {
        this.webElement = webElement;
    }

    public void select(String selected) {
        List<WebElement> options = webElement.getChildrenOfType("option");
        boolean found = false;        
        for (WebElement option : options) {
            String value = option.getText();
            if (selected.equals(value)) {
                option.setSelected();
                found = true;
                break;
            }
        }
        if (!found) {
            throw new NoSuchElementException("Option '" + selected + "' not found from DropDownChoice");
        }
    }
    
	public String getValue() {
        return webElement.getValue();
    }

    public String getText() {
        return webElement.getText();
    }
}
