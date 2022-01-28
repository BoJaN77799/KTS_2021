package e2e.commonPages;

import e2e.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CommonHeader {
    protected WebDriver driver;

    @FindBy(xpath = "//a[@id='logout']")
    protected WebElement logOutLink;

    @FindBy(xpath = "//a[@id='profile']")
    protected WebElement profileLink;

    @FindBy(xpath = "//snack-bar-container/div/div/simple-snack-bar/span")
    protected WebElement snackBarSpan;

    public CommonHeader(WebDriver driver) {
        this.driver = driver;
    }

    public void profileLinkClick() {
        Utilities.clickableWait(driver, this.profileLink, 10).click();
    }

    public void logOutLinkClick() {
        Utilities.clickableWait(driver, this.logOutLink, 10).click();
    }

    public WebElement getSnackBar() { return Utilities.visibilityWait(driver, this.snackBarSpan, 10); }

    public boolean isSnackBarContainsMessage(String message){ return Utilities.textWait(driver, snackBarSpan, message, 10); }
}
