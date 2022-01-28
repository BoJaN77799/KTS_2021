package e2e.commonPages;

import e2e.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BarmanCookHeader extends CommonNotificationHeader {

    @FindBy(xpath = "//a[@id='navbarDropdown']")
    protected WebElement optionsLink;

    @FindBy(xpath = "//a[@id='new-orders']")
    protected WebElement newOrdersLink;

    @FindBy(xpath = "//a[@id='my-orders']")
    protected WebElement myOrdersLink;

    @FindBy(xpath = "//a[@id='create-drink']")
    protected WebElement createDrink;


    public BarmanCookHeader(WebDriver driver) {
        super(driver);
    }

    public void optionsLinkClick() {
        Utilities.clickableWait(driver, this.optionsLink, 10).click();
    }

    public void newOrdersLinkClick() {Utilities.clickableWait(driver, this.newOrdersLink, 10).click();}

    public void myOrdersLinkClick() {
        Utilities.clickableWait(driver, this.myOrdersLink, 10).click();
    }

    public void createDrinkLinkClick() {
        Utilities.clickableWait(driver, this.createDrink, 10).click();
    }


}
