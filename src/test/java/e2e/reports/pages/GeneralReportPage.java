package e2e.reports.pages;

import e2e.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class GeneralReportPage {

    protected WebDriver driver;

    @FindBy(xpath = "//div[@id='date-form']//mat-form-field")
    protected WebElement dateForm;

    @FindBy(xpath = "//mat-datepicker-toggle")
    protected WebElement dateFormToggle;

    @FindBy(xpath = "//button" +
            "[@class='mat-focus-indicator mat-calendar-previous-button mat-icon-button mat-button-base']")
    protected WebElement previousMonthButton;

    @FindBy(xpath = "//td[@data-mat-row='1' and @data-mat-col='1']")
    protected WebElement dateFromButton;

    @FindBy(xpath = "//td[@data-mat-row='2' and @data-mat-col='4']")
    protected  WebElement dateToButton;

    @FindBy(xpath = "//button[@id='reports-button']")
    protected WebElement reportsButton;

    public GeneralReportPage(WebDriver driver) {
        this.driver = driver;
    }

    public void dateFormClick() {
        Utilities.clickableWait(driver, this.dateForm, 10).click();
    }

    public void dateFormToggleClick() {
        Utilities.clickableWait(driver, this.dateFormToggle, 10).click();
    }

    public void dateFromButtonClick() {
        Utilities.clickableWait(driver, this.dateFromButton, 10).click();
    }

    public void dateToButtonClick() {
        Utilities.clickableWait(driver, this.dateToButton, 10).click();
    }

    public void previousMonthButtonClick() {
        Utilities.clickableWait(driver, this.previousMonthButton, 10).click();
    }

    public void reportsButtonClick() {
        Utilities.clickableWait(driver, this.reportsButton, 10).click();
    }
}
