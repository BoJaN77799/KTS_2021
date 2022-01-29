package e2e.commonPages;

import e2e.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ModalDialogForQuantityInputPage {

    private WebDriver driver;

    @FindBy(xpath = "//mat-dialog-container//input")
    private WebElement numberInput;

    @FindBy(xpath = "//mat-dialog-container//button[2]")
    private WebElement confirmButton;

    public ModalDialogForQuantityInputPage() {}

    public ModalDialogForQuantityInputPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getNumberInput() {
        return Utilities.visibilityWait(driver, this.numberInput, 10);
    }

    public void setNumberInput(String text) {
        WebElement el = getNumberInput();
        el.clear();
        el.sendKeys(text);
    }

    public void confirmButtonClick(String text) throws InterruptedException {
        Utilities.textValueWait(driver, this.numberInput, text, 10);
        Utilities.clickableWait(driver, this.confirmButton, 10).click();
    }
}
