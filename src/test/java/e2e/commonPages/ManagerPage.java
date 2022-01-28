package e2e.commonPages;

import e2e.utils.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ManagerPage extends CommonHeader{

    @FindBy(xpath = "//a[@id='home']")
    private WebElement homeLink;

    @FindBy(xpath = "//a[@id='navbarDropdown']")
    private WebElement reportsNavBar;

    @FindBy(xpath = "//ul[@id='reports-navlist']//li")
    private List<WebElement> reportsLinks;

    @FindBy(xpath = "//a[@id='employees']")
    private WebElement employeesLink;

    @FindBy(xpath = "//a[@id='menus']")
    private WebElement menusLink;

    public ManagerPage(WebDriver driver) {
        super(driver);
    }

    public void homeLinkClick() {
        Utilities.clickableWait(driver, this.homeLink, 10).click();
    }

    public void reportsNavBarClick() {
        Utilities.clickableWait(driver, this.reportsNavBar, 10).click();
    }

    public void employeesLinkClick() {
        Utilities.clickableWait(driver, this.employeesLink, 10).click();
    }

    public void menusLinkClick() {
        Utilities.clickableWait(driver, this.menusLink, 10).click();
    }

    public void reportsLinksClickIndex(int index) {
        Utilities.clickableWait(driver, this.reportsLinks.get(index), 10).click();
    }
}
