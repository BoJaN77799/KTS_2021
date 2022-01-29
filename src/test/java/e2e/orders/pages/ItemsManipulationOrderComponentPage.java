package e2e.orders.pages;

import e2e.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ItemsManipulationOrderComponentPage {

    private WebDriver driver;

    @FindBy(xpath = "//app-items-manipulation//a[1]")
    private WebElement firstOrderedElementAnchor;

    @FindBy(xpath = "//app-items-manipulation//a[1]//button[2]")
    private WebElement firstOrderedElementPlusButton;

    @FindBy(xpath = "//app-items-manipulation//a[1]//button[4]")
    private WebElement firstOrderedElementPriorityDownButton;

    @FindBy(xpath = "//app-items-manipulation//a[1]//button[5]")
    private WebElement firstOrderedElementPriorityUpButton;

    @FindBy(xpath = "//app-items-manipulation//a[1]//span[1]")
    private WebElement firstOrderedElementQuantitySpan;

    @FindBy(xpath = "//app-items-manipulation//a[2]")
    private WebElement secondOrderedElementAnchor;

    @FindBy(xpath = "//app-items-manipulation//a[2]//button[1]")
    private WebElement secondOrderedElementMinusButton;

    @FindBy(xpath = "//app-items-manipulation//a[2]//span[1]")
    private WebElement secondOrderedElementQuantitySpan;

    @FindBy(xpath = "//app-items-manipulation//a[3]")
    private WebElement thirdOrderedElementAnchor;

    @FindBy(xpath = "//app-items-manipulation//a[3]//button[3]")
    private WebElement thirdOrderedElementDeleteButton;

    @FindBy(xpath = "(//app-items-manipulation//p)[2]")
    private WebElement priceParagraph;

    public ItemsManipulationOrderComponentPage() {}

    public ItemsManipulationOrderComponentPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isFirstOrderedItemAnchorTextPresent(String text) {
        return Utilities.textWait(driver, firstOrderedElementAnchor, text, 10);
    }

    public void firstOrderedElementPlusButtonClick() {
        Utilities.clickableWait(driver, this.firstOrderedElementPlusButton, 10).click();
    }

    public void firstOrderedElementPriorityDownButtonClick() {
        Utilities.clickableWait(driver, this.firstOrderedElementPriorityDownButton, 10).click();
    }

    public void firstOrderedElementPriorityUpButtonClick() {
        Utilities.clickableWait(driver, this.firstOrderedElementPriorityUpButton, 10).click();
    }

    public boolean isFirstOrderedElementQuantitySpanTextPresent(String text) {
        return Utilities.textWait(driver, firstOrderedElementQuantitySpan, text, 10);
    }

    public boolean isSecondOrderedItemAnchorTextPresent(String text) {
        return Utilities.textWait(driver, secondOrderedElementAnchor, text, 10);
    }

    public void secondOrderedElementMinusButtonClick() {
        Utilities.clickableWait(driver, this.secondOrderedElementMinusButton, 10).click();
    }

    public boolean isSecondOrderedElementQuantitySpanTextPresent(String text) {
        return Utilities.textWait(driver, secondOrderedElementQuantitySpan, text, 10);
    }

    public boolean isThirdOrderedItemAnchorTextPresent(String text) {
        return Utilities.textWait(driver, thirdOrderedElementAnchor, text, 10);
    }

    public void thirdOrderedElementDeleteButtonClick() {
        Utilities.clickableWait(driver, this.thirdOrderedElementDeleteButton, 10).click();
    }

    public boolean isPriceParagraphTextPresent(String text) {
        return Utilities.textWait(driver, this.priceParagraph, text, 10);
    }
}