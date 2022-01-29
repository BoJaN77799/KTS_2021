package e2e.users.pages;

import e2e.Utilities;
import e2e.commonPages.CommonHeader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends CommonHeader {

    @FindBy(xpath = "//input[@id='email']")
    protected WebElement emailInput;

    @FindBy(xpath = "//input[@id='password']")
    protected WebElement passwordInput;

    @FindBy(xpath = "//button[@id='login-button']")
    protected WebElement loginButton;

    @FindBy(xpath = "//small[@id='danger-label-email']")
    protected WebElement dangerTextEmail;

    @FindBy(xpath = "//small[@id='danger-label-pass']")
    protected WebElement dangerTextPassword;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getEmailInput() { return Utilities.visibilityWait(driver, this.emailInput, 10); }

    public void setEmailInput(String text) {
        WebElement el = this.emailInput;
        el.clear();
        el.sendKeys(text);
    }

    public WebElement getPasswordInput() {
        return Utilities.visibilityWait(driver, this.passwordInput, 10);
    }

    public void setPasswordInput(String text) {
        WebElement el = this.passwordInput;
        el.clear();
        el.sendKeys(text);
    }

    public WebElement getLoginButton() { return this.loginButton; }

    public void loginButtonClick() { Utilities.clickableWait(driver, this.loginButton, 10).click(); }

    public WebElement getDangerTextEmail() { return Utilities.visibilityWait(driver, this.dangerTextEmail, 10); }

    public WebElement getDangerTextPassword() { return Utilities.visibilityWait(driver, this.dangerTextPassword, 10); }
}
