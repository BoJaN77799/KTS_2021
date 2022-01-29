package e2e.users.pages;

import e2e.Utilities;
import e2e.commonPages.GeneralPage;
import e2e.utils.Constants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

    @FindBy(id = "saveChangesBtn")
    protected  WebElement saveButton;

    public UserViewPage(WebDriver driver) {
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

    public void clickSave(){
        WebElement field = Utilities.clickableWait(this.driver, saveButton, 10);
        field.click();
    }
}
