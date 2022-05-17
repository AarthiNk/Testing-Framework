import constants.UrlConstants;
import constants.WebElementsConstants;
import io.qameta.allure.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

import static utils.DriverUtils.*;

/**
 * For web automation
 * @author Aarthi
 */
@Epic("Web Automation")
@Feature("Jquery suite testings")
public class WebAssignment {
        WebDriver driver = new ChromeDriver();
        WebElement interactionElement;

        WebElement controlGroupElement;

    /**
     * To setup the chrome driver and open the site in a browser
     * @author Aarthi
     * @throws InterruptedException
     */
    @BeforeTest
        public void setup() throws InterruptedException {
            synchronized (driver) {
                driver.get(UrlConstants.WEB_AUTOMATION_URL);
                driver.wait(100);
                findElementsByClass(driver,WebElementsConstants.WIDGET).forEach(webElement -> {
                    boolean isInteractionElement = findElementByClass(webElement,WebElementsConstants.WIDGET_TITLE).getText().equalsIgnoreCase("Interactions");
                    if(isInteractionElement){
                         interactionElement = webElement;
                    }
                    boolean isControlElement = findElementByClass(webElement,WebElementsConstants.WIDGET_TITLE).getText().equalsIgnoreCase("Widgets");
                    if(isControlElement){
                         controlGroupElement = webElement;
                    }
                });
            }
        }

    /**
     * To Quit the browser
     * @throws InterruptedException
     */
    @AfterTest
        public void setDown() throws InterruptedException {
            synchronized (driver) {
                driver.quit();
            }
        }

    /**
     * To open the droppable and drag and drop
     * @author Aarthi
     * @throws InterruptedException
     */
    @Test (priority = 0, description="sample description for Report")
        @Severity(SeverityLevel.CRITICAL)
        @Step("Go to droppable and drag & drop")
        public void testCaseDragAndDrop() throws InterruptedException {
            synchronized (driver)
            {
            getElementByTagName(interactionElement,  WebElementsConstants.UL).findElements(new By.ByTagName( WebElementsConstants.LI)).get(1).click();
            driver.wait(1000);
            driver.switchTo().frame(0);
            //WebElement on which drag and drop operation needs to be performed
            WebElement fromElement = findElementByID(driver,WebElementsConstants.DRAGGABLE);
            //WebElement to which the above object is dropped
            WebElement toElement = findElementByID(driver,WebElementsConstants.DROPPABLE);
            //Creating object of Actions class to build composite actions
            Actions builder = new Actions(driver);
            //Building a drag and drop action
            builder.clickAndHold(fromElement)
                    .moveToElement(toElement)
                    .release(toElement)
                    .build().perform();

            String verifyText = findElementByID(driver,WebElementsConstants.DROPPABLE).findElement(new By.ByTagName("P")).getText();
            Assert.assertEquals(verifyText,"Dropped!");
            validator(driver, "Droppable | jQuery UI");
            }
        }


    /**
     * To open selectable and selct item 1 3 & 7
     * @throws InterruptedException
     */
    @Test
    public void testCaseSelectItems() throws InterruptedException {
        synchronized (driver)
        {
            interactionElement.findElement(new By.ByTagName( WebElementsConstants.UL)).findElements(new By.ByTagName( WebElementsConstants.LI)).get(3).click();
            driver.wait(1000);
            driver.switchTo().frame(0);
            List<WebElement> items = findElementByID(driver,WebElementsConstants.SELECTABLE).findElements(new By.ByTagName( WebElementsConstants.LI));

            items.forEach((webElement) -> {
                if(webElement.getText().equalsIgnoreCase("Item 1")|| webElement.getText().equalsIgnoreCase("Item 3") || webElement.getText().equalsIgnoreCase("Item 7")){
                    Actions action=new Actions(driver);
                    action.keyDown(Keys.CONTROL).build().perform();
                    webElement.click();
                }
            });
            driver.wait(1000);
            String firstItem = findElementsByClass(driver,"ui-selected").get(0).getText();
            Assert.assertEquals(firstItem, "Item 1");
            validator(driver, "Selectable | jQuery UI");
        }
    }


    @Test
    public void testCaseControlGroup() throws InterruptedException {
        synchronized (driver)
        {
            getElementByTagName(controlGroupElement,  WebElementsConstants.UL).findElements(new By.ByTagName( WebElementsConstants.LI)).get(4).click();
            driver.wait(1000);
            driver.switchTo().frame(0);

            WebElement selectElement = findElementByID(driver,"car-type");
            ((JavascriptExecutor) driver).executeScript("document.getElementById('car-type').style.display='block';");
            selectElement.click();
            Select s = new Select(selectElement);
            s.selectByIndex(3);

            selectValues("controlgroup", "Automatic");
            selectValues("controlgroup-vertical", "Standard");

            driver.wait(2000);
            validator(driver, "Controlgroup | jQuery UI");
        }
    }


    /**
     * To iterate web element
     * @param className
     * @param values
     */
    private void selectValues(String className, String values) {
        List<WebElement> verticalItems = findElementByClass(driver,className).findElements(new By.ByXPath("label"));

        verticalItems.forEach((webElement) -> {
            if(webElement.getText().equalsIgnoreCase(values) || webElement.getText().equalsIgnoreCase("Insurance")){
                webElement.click();
            }
        });
    }

    /**
     * To validate the output
     * @param driver
     * @param title
     * @throws InterruptedException
     */
    private void validator(WebDriver driver, String title) throws InterruptedException {
        Assert.assertEquals(driver.getTitle(), title);
        driver.navigate().back();
        driver.wait(100);
    }
}
