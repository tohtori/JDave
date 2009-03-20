package jdave.webdriver.elements;

import javax.swing.text.html.HTML.Tag;

import jdave.webdriver.WebDriverHolder;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author Juha Karemo
 */
public class Count {
    private Tag tag;

    Count(Tag tag) {
        this.tag = tag;
    }

    public int fromParent(By by) {
        WebElement parent = WebDriverHolder.get().findElement(by);
        return parent.findElements(By.tagName(tag.toString())).size();
    }

    public static Count of(Tag tag) {
        return new Count(tag);
    }
}
