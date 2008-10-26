package jdave.webdriver.elements;

import jdave.webdriver.Channel;

import org.openqa.selenium.WebElement;

/**
 * @author Juha Karemo
 */
public class Link {
    private WebElement webElement;
    private Channel channel = new Channel();

    Link(WebElement webElement) {
        this.webElement = webElement;
    }

    public void click() {
        webElement.click();
        channel.waitForAjax();
    }
}
