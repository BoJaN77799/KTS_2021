package e2e.users.pages;

import e2e.Utilities;
import e2e.commonPages.GeneralPage;
import e2e.utils.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateUserPage extends GeneralPage {
    @FindBy(id = "formCreateFN")
    protected WebElement firstNameInput;

    @FindBy(id = "formCreateLN")
    protected WebElement lastNameInput;

    @FindBy(id = "formCreateEmail")
    protected WebElement emailInput;

    @FindBy(id = "formCreateTelephone")
    protected WebElement telephoneInput;

    @FindBy(id = "formCreateAddress")
    protected WebElement addressInput;

    @FindBy(id = "formCreateGender")
    protected WebElement genderSelect;

    @FindBy(id = "formCreateUserType")
    protected WebElement userTypeSelect;

    @FindBy(id = "createUserBtn")
    protected  WebElement createBtn;

    public CreateUserPage(WebDriver driver) {
        super(driver);
        this.url = Constants.BASE_URL + "rest-app/users/users-search";
    }

    public void addTextToFNField(String text){
        WebElement field = Utilities.visibilityWait(this.driver, firstNameInput, 10);
        field.clear();
        field.sendKeys(text);
    }

    public void addTextToLNField(String text){
        WebElement field = Utilities.visibilityWait(this.driver, lastNameInput, 10);
        field.clear();
        field.sendKeys(text);
    }

    public void addTextToAddressField(String text){
        WebElement field = Utilities.visibilityWait(this.driver, addressInput, 10);
        field.clear();
        field.sendKeys(text);
    }

    public void addTextToTelephoneField(String text){
        WebElement field = Utilities.visibilityWait(this.driver, telephoneInput, 10);
        field.clear();
        field.sendKeys(text);
    }

    public void addTextToEmailField(String text){
        WebElement field = Utilities.visibilityWait(this.driver, emailInput, 10);
        field.clear();
        field.sendKeys(text);
    }

    public void clickOnSelectGender(int option){ //nek bude 8
        WebElement field = Utilities.clickableWait(this.driver, this.genderSelect, 10);
        field.click();
        WebElement selectOption = Utilities.clickableWait(this.driver, By.xpath("//mat-option[@id='mat-option-" + option +
                "']"), 10);
        selectOption.click();
    }

    public void clickOnSelectUserType(int option){ //nek bude 11
        WebElement field = Utilities.clickableWait(this.driver, this.userTypeSelect, 10);
        field.click();
        WebElement selectOption = Utilities.clickableWait(this.driver, By.xpath("//mat-option[@id='mat-option-" + option +
                "']"), 10);
        selectOption.click();
    }

    public void clickCreate(){
        WebElement field = Utilities.clickableWait(this.driver, this.createBtn, 10);
        field.click();
    }
}
