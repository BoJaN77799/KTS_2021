package e2e.notifications.pages;

import e2e.Utilities;
import e2e.commonPages.CommonNotificationHeader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class NotificationsPage extends CommonNotificationHeader {

    @FindBy(xpath = "//mat-icon//span[@id='mat-badge-content-0']")
    private WebElement matIconNotificationsSize;

    @FindBy(xpath = "//button[@id='seen-all']")
    private WebElement buttonSeenAll;

    @FindBy(xpath = "(//div[@class='scroll-area']//div/div/div/div/button)[1]")
    private WebElement buttonSeenOneFirstNotification;

    @FindBy(xpath = "//div[@class='scroll-area']/div/div/div/p")
    private List<WebElement> tableIds;

    @FindBy(xpath = "//div[@class='scroll-area']//div/div/div/div/p")
    private List<WebElement> notificationsContent;


    public NotificationsPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getMatIconNotificationsSize() { return  Utilities.visibilityWait(driver, this.matIconNotificationsSize, 10); }

    public List<WebElement> getTableIds() {  return Utilities.visibilityWait(driver, this.tableIds, 10); }

    public List<WebElement> getNotificationsContent() { return  Utilities.visibilityWait(driver, this.notificationsContent, 10); }

    public void buttonSeenAllClick() { Utilities.clickableWait(driver, this.buttonSeenAll, 10).click();}

    public void buttonSeenOneClick() { Utilities.clickableWait(driver, this.buttonSeenOneFirstNotification, 10).click();}
}
