package e2e.orders.pages;

import e2e.Utilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateOrderPage {

    private WebDriver driver;

    @FindBy(xpath = "//button[@id='createOrder']")
    private WebElement createOrderButton;

    @FindBy(xpath = "//button[@id='cancel']")
    private WebElement cancelOrderButton;

    public CreateOrderPage() { }

    public CreateOrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickCreateOrderButton() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        WebElement ele = Utilities.clickableWait(driver, this.createOrderButton, 10);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", ele);
    }

    public void clickCancelOrderButton() {
        Utilities.clickableWait(driver, this.cancelOrderButton, 10).click();
    }

}
