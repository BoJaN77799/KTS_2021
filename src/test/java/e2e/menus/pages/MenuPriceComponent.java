package e2e.menus.pages;

import e2e.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MenuPriceComponent {

    private WebDriver driver;

    @FindBy(xpath="//input[@id='item-prices-input']")
    private WebElement itemPriceInput;

    @FindBy(xpath="//button[@id='item-prices-create']")
    private WebElement itemPriceCreateButton;

    @FindBy(xpath="//button[@id='item-prices-cancel']")
    private WebElement itemPriceCancelButton;

    public MenuPriceComponent(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getItemPriceInput() {
        return Utilities.visibilityWait(this.driver, this.itemPriceInput, 10);
    }

    public void setItemPriceInput(String text) {
        WebElement el = getItemPriceInput();
        el.clear();
        el.sendKeys(text);
    }

    public void itemPriceCreateButtonClick() {
        Utilities.clickableWait(this.driver, this.itemPriceCreateButton, 10).click();
    }

    public void setItemPriceCancelButtonClick() {
        Utilities.clickableWait(this.driver, this.itemPriceCancelButton, 10).click();
    }
}
