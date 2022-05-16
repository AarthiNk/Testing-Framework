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


@Epic("Web Automation")
@Feature("Jquery suite testings")
public class WebAssignment {
        WebDriver driver = new ChromeDriver();
        WebElement interactionElement;

        WebElement controlGroupElement;

        @BeforeTest
        public void setup() throws InterruptedException {
            synchronized (driver) {
                driver.get("https://jqueryui.com/");
                driver.wait(100);
                driver.findElements(new By.ByClassName("widget")).forEach(webElement -> {
                    boolean isInteractionElement = webElement.findElement(new By.ByClassName("widget-title")).getText().equalsIgnoreCase("Interactions");
                    if(isInteractionElement){
                         interactionElement = webElement;
                    }
                    boolean isControlElement = webElement.findElement(new By.ByClassName("widget-title")).getText().equalsIgnoreCase("Widgets");
                    if(isControlElement){
                         controlGroupElement = webElement;
                    }
                });
            }
        }

        @AfterTest
        public void setDown() throws InterruptedException {
            synchronized (driver) {
                driver.quit();
            }
        }

        @Test (priority = 0, description="Invalid Login Scenario with wrong username and password.")
        @Severity(SeverityLevel.CRITICAL)
        @Step("Go to droppable and drag & drop")
        public void testCaseDragAndDrop() throws InterruptedException {
            synchronized (driver)
            {
            interactionElement.findElement(new By.ByTagName("ul")).findElements(new By.ByTagName("li")).get(1).click();
            driver.wait(1000);
            driver.switchTo().frame(0);
            //WebElement on which drag and drop operation needs to be performed
            WebElement fromElement = driver.findElement(new By.ById("draggable"));
            //WebElement to which the above object is dropped
            WebElement toElement = driver.findElement(new By.ById("droppable"));
            //Creating object of Actions class to build composite actions
            Actions builder = new Actions(driver);
            //Building a drag and drop action
            builder.clickAndHold(fromElement)
                    .moveToElement(toElement)
                    .release(toElement)
                    .build().perform();

            String verifyText = driver.findElement(new By.ById("droppable")).findElement(new By.ByTagName("P")).getText();
            Assert.assertEquals(verifyText,"Dropped!");
            validator(driver, "Droppable | jQuery UI");
            }
        }



    @Test
    public void testCaseSelectItems() throws InterruptedException {
        synchronized (driver)
        {
            interactionElement.findElement(new By.ByTagName("ul")).findElements(new By.ByTagName("li")).get(3).click();
            driver.wait(1000);
            driver.switchTo().frame(0);
            List<WebElement> items = driver.findElement(new By.ById("selectable")).findElements(new By.ByTagName("li"));

            items.forEach((webElement) -> {
                if(webElement.getText().equalsIgnoreCase("Item 1")|| webElement.getText().equalsIgnoreCase("Item 3") || webElement.getText().equalsIgnoreCase("Item 7")){
                    Actions action=new Actions(driver);
                    action.keyDown(Keys.CONTROL).build().perform();
                    webElement.click();
                }
            });
            driver.wait(1000);
            String firstItem = driver.findElements(new By.ByClassName("ui-selected")).get(0).getText();
            Assert.assertEquals(firstItem, "Item 1");
            validator(driver, "Selectable | jQuery UI");
        }
    }


    @Test
    public void testCaseControlGroup() throws InterruptedException {
        synchronized (driver)
        {
            controlGroupElement.findElement(new By.ByTagName("ul")).findElements(new By.ByTagName("li")).get(4).click();
            driver.wait(1000);
            driver.switchTo().frame(0);

            WebElement selectElement = driver.findElement(new By.ById("car-type"));
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

    private void selectValues(String className, String values) {
        List<WebElement> verticalItems = driver.findElement(new By.ByClassName(className)).findElements(new By.ByXPath("label"));

        verticalItems.forEach((webElement) -> {
            if(webElement.getText().equalsIgnoreCase(values) || webElement.getText().equalsIgnoreCase("Insurance")){
                webElement.click();
            }
        });
    }

    private void validator(WebDriver driver, String title) throws InterruptedException {
        Assert.assertEquals(driver.getTitle(), title);
        driver.navigate().back();
        driver.wait(100);
    }
}
