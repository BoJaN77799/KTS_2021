package e2e.orders.pages;

import e2e.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SearchAndFilterComponentPage {

    protected WebDriver driver;

    @FindBy(xpath = "//input[@id='search']")
    protected WebElement searchInput;

    @FindBy(xpath = "//button[@id='buttonSearch']")
    protected WebElement searchButton;

    @FindBy(xpath = "(//mat-select)[1]")
    protected WebElement sortSelect;

    @FindBy(xpath = "//mat-option")
    protected List<WebElement> sortSelectOptions;

    public SearchAndFilterComponentPage() {}

    public SearchAndFilterComponentPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getSearchInput() {
        return Utilities.visibilityWait(driver, this.searchInput, 10);
    }

    public void setSearchInput(String text) {
        WebElement el = getSearchInput();
        el.clear();
        el.sendKeys(text);
    }

    public void searchButtonClick() {
        Utilities.clickableWait(driver, this.searchButton, 10).click();
    }

    public void sortSelectClick() {
        Utilities.clickableWait(driver, this.sortSelect, 10).click();
    }

    public void nameAscendingOptionClick() {
        Utilities.clickableWait(driver, this.sortSelectOptions.get(1), 10).click();
    }

    public void nameDescendingOptionClick() {
        Utilities.clickableWait(driver, this.sortSelectOptions.get(2), 10).click();
    }

    public void priceAscendingOptionClick() {
        Utilities.clickableWait(driver, this.sortSelectOptions.get(3), 10).click();
    }

    public void priceDescendingOptionClick() {
        Utilities.clickableWait(driver, this.sortSelectOptions.get(4), 10).click();
    }

}
