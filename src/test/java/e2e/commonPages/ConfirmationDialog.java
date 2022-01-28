package e2e.commonPages;

import e2e.Utilities;
import e2e.reports.pages.GeneralTablePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ConfirmationDialog extends GeneralTablePage {

    @FindBy(xpath = "//button[@id='confirm-button']")
    private WebElement confirmButton;

    public ConfirmationDialog(WebDriver driver) {
        super(driver);
    }

    public void confirmButtonClick() {
        Utilities.clickableWait(this.driver, confirmButton, 10).click();
    }
}
