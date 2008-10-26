package jdave.webdriver.elements;

import jdave.webdriver.WebDriverHolder;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author Juha Karemo
 */
public class Find {
    private static WebDriver webDriver = WebDriverHolder.get();

    public static Link link(By by) {
        return new Link(webDriver.findElement(by));
    }
}
