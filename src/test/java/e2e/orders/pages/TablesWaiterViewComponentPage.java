package e2e.orders.pages;

import e2e.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TablesWaiterViewComponentPage {

    WebDriver driver;

    @FindBy(xpath = "//*[@tableId='10']/..")
    private WebElement table10Circle;

    @FindBy(xpath = "//*[@tableId='11']/..")
    private WebElement table11Circle;

    @FindBy(xpath = "//*[@tableId='12']/..")
    private WebElement table12Circle;

    @FindBy(xpath = "//*[@tableId='13']/..")
    private WebElement table13Circle;

    @FindBy(xpath = "//*[@tableId='14']/..")
    private WebElement table14Circle;

    public TablesWaiterViewComponentPage() { }

    public TablesWaiterViewComponentPage(WebDriver driver) {
        this.driver = driver;
    }

    public void table11CircleClick() {
        Utilities.clickableWait(driver, this.table11Circle, 10).click();
    }

    public void table10CircleClick() {
        Utilities.clickableWait(driver, this.table10Circle, 10).click();
    }

    public void table12CircleClick() {
        Utilities.clickableWait(driver, this.table12Circle, 10).click();
    }

    public void table13CircleClick() {
        Utilities.clickableWait(driver, this.table13Circle, 10).click();
    }

    public void table14CircleClick() {
        Utilities.clickableWait(driver, this.table14Circle, 10).click();
    }

}
