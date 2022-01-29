package e2e.commonPages;

import e2e.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class AdminPage extends CommonHeader{

    @FindBy(id = "usersLink")
    private WebElement usersLink;

    @FindBy(id = "tablesLink")
    private WebElement tablesLink;

    public AdminPage(WebDriver driver) {
        super(driver);
    }

    public void usersLinkClick() {
        Utilities.clickableWait(driver, this.usersLink, 10).click();
    }

    public void tablesLinkClick() {
        Utilities.clickableWait(driver, this.tablesLink, 10).click();
    }

}
