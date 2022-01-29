package e2e.commonPages;

import e2e.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CommonNotificationHeader extends CommonHeader {

    @FindBy(xpath = "//a[@id='notifications-modal']")
    protected WebElement notificationModal;

    public CommonNotificationHeader(WebDriver driver) {
        super(driver);
    }

    public void notificationModalClick() {
        Utilities.clickableWait(driver, this.notificationModal, 10).click();
    }
}
