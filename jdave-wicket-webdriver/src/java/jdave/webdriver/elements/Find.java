package jdave.webdriver.elements;

import jdave.webdriver.WebDriverHolder;

import org.openqa.selenium.By;

/**
 * @author Juha Karemo
 * @author Marko Sibakov
 */
public class Find {
    public static Link link(By by) {
        return new Link(WebDriverHolder.get().findElement(by));
    }

    public static TextBox textBox(By by) {
        return new TextBox(WebDriverHolder.get().findElement(by));
    }

    public static CheckBox checkBox(By by) {
        return new CheckBox(WebDriverHolder.get().findElement(by));
    }

    public static DropDownChoice dropDownChoice(By by) {
        return new DropDownChoice(WebDriverHolder.get().findElement(by));
    }
}
