package e2e.commonPages;

import e2e.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {

    private WebDriver driver;

    @FindBy(xpath = "//input[@id='email']")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@id='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@id='login-button']")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getEmailInput() {
        return Utilities.visibilityWait(driver, this.emailInput, 10);
    }

    public void setEmailInput(String text) {
        WebElement el = getEmailInput();
        el.clear();
        el.sendKeys(text);
    }

    public WebElement getPasswordInput() {
        return Utilities.visibilityWait(driver, this.passwordInput, 10);
    }

    public void setPasswordInput(String text) {
        WebElement el = getPasswordInput();
        el.clear();
        el.sendKeys(text);
    }

    public void loginButtonClick() {
        Utilities.clickableWait(driver, this.loginButton, 10).click();
    }
}
