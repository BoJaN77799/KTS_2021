package e2e.reports.pages;

import e2e.Utilities;
import e2e.commonPages.GeneralPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class GeneralReportPage extends GeneralPage {

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

    @FindBy(xpath = "//input[@id='mat-date-range-input-0']")
    protected WebElement dateFromInput;

    @FindBy(xpath = "//input[@id='end']")
    protected  WebElement dateToInput;

    @FindBy(xpath = "//button[@id='reports-button']")
    protected WebElement reportsButton;

    public GeneralReportPage(WebDriver driver) {
        super(driver);
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

    public WebElement getDateFromInput() {
        return Utilities.visibilityWait(driver, this.dateFromInput, 10);
    }

    public void setDateFromInput(String text) {
        WebElement el = getDateFromInput();
        el.clear();
        el.sendKeys(text);
    }

    public WebElement getDateToInput() {
        return Utilities.visibilityWait(driver, this.dateToInput, 10);
    }

    public void setDateToInput(String text) {
        WebElement el = getDateToInput();
        el.clear();
        el.sendKeys(text);
    }

    public void reportsButtonClick() {
        Utilities.clickableWait(driver, this.reportsButton, 10).click();
    }

    public  void setupDateFrom() {
        this.setDateToInput("1/14/2022");
        this.setDateFromInput("1/15/2022");
        this.dateFormClick();
        this.dateFormToggleClick();
        this.previousMonthButtonClick();
        this.dateFromButtonClick();
        this.dateToButtonClick();
    }

}
