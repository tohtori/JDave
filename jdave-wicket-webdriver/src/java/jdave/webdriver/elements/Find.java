package jdave.webdriver.elements;

import jdave.webdriver.WebDriverHolder;

import org.openqa.selenium.By;

/**
 * @author Juha Karemo
 */
public class Find {
    public static Link link(By by) {
        return new Link(WebDriverHolder.get().findElement(by));
    }

    public static TextBox textBox(By by) {
        return new TextBox(WebDriverHolder.get().findElement(by));
    }
}
