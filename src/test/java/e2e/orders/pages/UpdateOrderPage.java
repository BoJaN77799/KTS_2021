package e2e.orders.pages;

import e2e.Utilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UpdateOrderPage {

    private WebDriver driver;

    @FindBy(xpath = "//button[@id='updateOrder']")
    private WebElement updateOrderButton;

    @FindBy(xpath = "//button[@id='finishOrder']")
    private WebElement finishOrderButton;

    @FindBy(xpath = "//button[@id='cancel']")
    private WebElement cancelOrderButton;

    public UpdateOrderPage() { }

    public UpdateOrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickUpdateOrderButton() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        WebElement ele = Utilities.clickableWait(driver, this.updateOrderButton, 10);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", ele);
    }

    public void clickFinishOrderButton() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        WebElement ele = Utilities.clickableWait(driver, this.finishOrderButton, 10);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", ele);
    }

    public void clickCancelOrderButton() {
        Utilities.clickableWait(driver, this.cancelOrderButton, 10).click();
    }

}
