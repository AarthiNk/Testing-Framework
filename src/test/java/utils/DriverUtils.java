package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class DriverUtils {

    public static WebElement findElementByID(WebDriver driver,String selectable) {
        return driver.findElement(new By.ById(selectable));
    }

    public static WebElement findElementByID(WebElement element,String selectable) {
        return element.findElement(new By.ById(selectable));
    }

    public static List<WebElement> findElementsByClass(WebDriver driver,String selectable) {
        return driver.findElements(new By.ByClassName(selectable));
    }

    public static List<WebElement> findElementsByClass(WebElement element,String selectable) {
        return element.findElements(new By.ByClassName(selectable));
    }

    public static WebElement findElementByClass(WebDriver driver,String selectable) {
        return driver.findElement(new By.ByClassName(selectable));
    }

    public static WebElement findElementByClass(WebElement element,String selectable) {
        return element.findElement(new By.ByClassName(selectable));
    }

    public static WebElement getElementByTagName(WebElement element, String selectable) {
        return element.findElement(new By.ByTagName(selectable));
    }
}
