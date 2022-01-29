package e2e.users.tests;

import e2e.users.pages.LoginPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static e2e.utils.Constants.*;

public class LoginTest {
    private static WebDriver driver;

    private static LoginPage loginPage;

    @BeforeAll
    public static void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.navigate().to("http://localhost:4200/");

        loginPage = PageFactory.initElements(driver, LoginPage.class);
    }

    @Test
    public void testLogin() {
        // all fields empty
        assertFalse(loginPage.getLoginButton().isEnabled());

        // set invalid email
        loginPage.setEmailInput("aaa");
        assertFalse(loginPage.getLoginButton().isEnabled());

        clearInputField(loginPage.getEmailInput()); // clearing email input

        assertFalse(loginPage.getLoginButton().isEnabled());
        assertEquals("Email can't be empty!", loginPage.getDangerTextEmail().getText());

        // set invalid password
        loginPage.setPasswordInput("beeee");
        assertFalse(loginPage.getLoginButton().isEnabled());

        clearInputField(loginPage.getPasswordInput()); // clearing password input

        assertFalse(loginPage.getLoginButton().isEnabled());
        assertEquals("Password can't be empty!", loginPage.getDangerTextPassword().getText());

        // set invalid email
        loginPage.setEmailInput("aaa");
        assertFalse(loginPage.getLoginButton().isEnabled());
        // set invalid password
        loginPage.setPasswordInput("beeee");
        assertTrue(loginPage.getLoginButton().isEnabled());

        loginPage.loginButtonClick();
        assertTrue(loginPage.isSnackBarContainsMessage("Bad credentials."));

        clearInputField(loginPage.getEmailInput()); // clearing email input
        clearInputField(loginPage.getPasswordInput()); // clearing password input

        // set valid email
        loginPage.setEmailInput(COOK_EMAIL);
        assertFalse(loginPage.getLoginButton().isEnabled());
        // set valid password
        loginPage.setPasswordInput(COOK_PASSWORD);
        assertTrue(loginPage.getLoginButton().isEnabled());

        loginPage.loginButtonClick();
        assertTrue(loginPage.isSnackBarContainsMessage("Successful login!"));
        assertEquals("http://localhost:4200/rest-app/orders/orders-page", driver.getCurrentUrl());

        // logout
        loginPage.logOutLinkClick();
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());
    }

    public void clearInputField(WebElement webElement){
        webElement.sendKeys(Keys.CONTROL + "a");
        webElement.sendKeys(Keys.DELETE);
        driver.findElement(By.xpath("//html")).click();
    }

    @AfterAll
    public static void closeSelenium() {
        driver.quit();
    }
}
