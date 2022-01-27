package e2e.reports.pages;

import e2e.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class GeneralReportPage {

    protected WebDriver driver;

    @FindBy(xpath = "//input[@id='start']")
    protected WebElement inputDateStart;

    @FindBy(xpath = "//input[@id='end']")
    protected WebElement inputDateEnd;

    @FindBy(xpath = "//button[@id='reports-button']")
    protected WebElement reportsButton;

    public GeneralReportPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getInputDateStart() {
        return Utilities.visibilityWait(driver, this.inputDateStart, 10);
    }

    public void setInputDateStartText(String text) {
        WebElement el = getInputDateStart();
        el.clear();
        el.sendKeys(text);
    }

    public WebElement getInputDateEnd() {
        return Utilities.visibilityWait(driver, this.inputDateEnd, 10);
    }

    public void setInputDateEndText(String text) {
        WebElement el = getInputDateEnd();
        el.clear();
        el.sendKeys(text);
    }

    public void reportsButtonClick() {
        Utilities.clickableWait(driver, this.reportsButton, 10).click();
    }
}
