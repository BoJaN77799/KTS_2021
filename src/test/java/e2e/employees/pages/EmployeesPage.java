package e2e.employees.pages;

import e2e.utils.Constants;
import e2e.Utilities;
import e2e.commonPages.GeneralPage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class EmployeesPage extends GeneralPage {

    @FindBy(xpath = "//div[@class='card']")
    private List<WebElement> employeeCards;

    @FindBy(xpath = "//button[@id='cancel-button']")
    private WebElement closeProfileInfoButton;

    @FindBy(xpath = "//button[@id='salaries-button']")
    private WebElement salariesButton;

    @FindBy(xpath = "//button[@id='bonuses-button']")
    private WebElement bonusesButton;

    @FindBy(xpath = "//button[@id='money-view-button']")
    private WebElement addSalariesOrBonusesButton;

    @FindBy(xpath = "//input[@id='money-view-input']")
    private WebElement salariesBonusesInput;

    @FindBy(xpath = "//button[@id='money-view-cancel-button']")
    private WebElement closeSalariesBonusesDialogButton;
//
//    private Wait<WebDriver> wait;

    public EmployeesPage(WebDriver driver) {
        super(driver);
        this.url = Constants.BASE_URL + "rest-app/employees/employees-manager";
//        this.wait = new FluentWait<>(driver)
//                .withTimeout(Duration.ofSeconds(30))
//                .pollingEvery(Duration.ofSeconds(5))
//                .ignoring(NoSuchElementException.class);
    }

    public void employeeCardClick(int index) {
        Utilities.clickableWait(this.driver, this.employeeCards.get(index), 10).click();
    }

    public void closeProfileInfoButtonClick() {
        Utilities.clickableWait(this.driver, this.closeProfileInfoButton, 10).click();
    }

    public void salariesButtonClick() {
//        WebElement el = wait.until(new Function<WebDriver, WebElement>() {
//            public WebElement apply(WebDriver driver) {
//                return driver.findElement(By.xpath("//button[@id='salaries-button']"));
//            }
//        });
//        el.click();
        Utilities.clickableWait(this.driver, this.salariesButton, 10).click();
    }

    public void bonusesButtonClick() {
        Utilities.clickableWait(this.driver, this.bonusesButton, 10).click();
    }

    public void addSalariesOrBonusesButtonClick() {
        Utilities.clickableWait(this.driver, this.addSalariesOrBonusesButton, 10).click();
    }

    public WebElement getSalariesBonusesInput() {
        return Utilities.visibilityWait(this.driver, this.salariesBonusesInput, 10);
    }

    public void setSalariesBonusesInput(String value) {
        WebElement el = getSalariesBonusesInput();
        el.clear();
        el.sendKeys(value);
    }

    public void closeSalariesBonusesDialogButtonClick() {
        Utilities.clickableWait(this.driver, this.closeSalariesBonusesDialogButton, 10).click();
    }
}
