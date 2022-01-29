package e2e.users.pages;

import e2e.Utilities;
import e2e.commonPages.GeneralPage;
import e2e.utils.Constants;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class UserViewPage extends GeneralPage {

    @FindBy(id = "profileViewFN")
    protected WebElement firstNameInput;

    @FindBy(id = "profileViewLN")
    protected WebElement lastNameInput;

    @FindBy(id = "profileViewAddress")
    protected WebElement addressInput;

    @FindBy(id = "profileViewTelephone")
    protected WebElement telephoneInput;

    @FindBy(id = "changePasswordBtn")
    protected WebElement changePassword;

    @FindBy(id = "saveChangesBtn")
    protected  WebElement saveButton;

    //change password
    @FindBy(id = "oldPwInput")
    protected WebElement oldPwInput;

    @FindBy(id = "newPwInput")
    protected WebElement newPwInput;

    @FindBy(id = "saveChangePasswordBtn")
    protected  WebElement saveChangesChangePwButton;

    public UserViewPage(WebDriver driver) {
        super(driver);
        this.url = Constants.BASE_URL + "rest-app/users/users-search";
    }

    public boolean checkTextinFN(String text){
        return Utilities.textValueWait(driver, firstNameInput, text, 10);
    }

    public boolean checkTextinLN(String text){
        return Utilities.textValueWait(driver, lastNameInput, text, 10);
    }

    public boolean checkTextinAddress(String text){
        return Utilities.textValueWait(driver, addressInput, text, 10);
    }

    public boolean checkTextinTelephone(String text){
        return Utilities.textValueWait(driver, telephoneInput, text, 10);
    }

    public void clickChangePassword(){
        WebElement webElement = Utilities.clickableWait(driver, changePassword, 10);
        webElement.click();
    }

    public void clickEsc(){
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ESCAPE).build().perform();
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

    public void clickSave(){
        WebElement field = Utilities.clickableWait(this.driver, saveButton, 10);
        field.click();
    }

    public void addTextToOldPw(String text){
        WebElement field = Utilities.visibilityWait(this.driver, oldPwInput, 10);
        field.clear();
        field.sendKeys(text);
    }

    public void addTextToNewPw(String text){
        WebElement field = Utilities.visibilityWait(this.driver, newPwInput, 10);
        field.clear();
        field.sendKeys(text);
    }

    public void clickSaveChangePass(){
        WebElement field = Utilities.clickableWait(this.driver, saveChangesChangePwButton, 10);
        field.click();
    }
}
