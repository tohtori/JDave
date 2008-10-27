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

import jdave.webdriver.Channel;

import org.openqa.selenium.WebElement;

/**
 * @author Juha Karemo
 */
public class CheckBox {
    private WebElement webElement;
    private Channel channel = new Channel();

    CheckBox(WebElement webElement) {
        this.webElement = webElement;
    }

    public void select() {
        if (!webElement.isSelected()) {
            webElement.click();
            channel.waitForAjax();
        }
    }

    public void unselect() {
        if (webElement.isSelected()) {
            webElement.click();
            channel.waitForAjax();
        }
    }

    public boolean isSelected() {
        return webElement.isSelected();
    }
}
