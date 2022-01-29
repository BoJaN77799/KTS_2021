package e2e.orders.pages;

import e2e.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TableViewWithOrderItemsComponentPage {

    WebDriver driver;

    @FindBy(xpath = "//button[.='Update order']/..")
    private WebElement updateOrderButton;

    @FindBy(xpath = "//button[.='Finish order']/..")
    private WebElement finishOrderButton;

    @FindBy(xpath = "(//button[.=' Mark as delivered'])[1]") // Deliver first order item that can be delivered
    private WebElement deliverOrderItemButton;

    public TableViewWithOrderItemsComponentPage() { }

    public TableViewWithOrderItemsComponentPage(WebDriver driver) {
        this.driver = driver;
    }

    public void finishOrderButtonClick() {
        Utilities.clickableWait(driver, this.finishOrderButton, 10).click();
    }

    public void updateOrderButtonClick() {
        Utilities.clickableWait(driver, this.updateOrderButton, 10).click();
    }

    public void deliverOrderItemButtonClick() {
        Utilities.clickableWait(driver, this.deliverOrderItemButton, 10).click();
    }

}
