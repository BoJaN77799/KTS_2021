package e2e.notifications.tests;

import e2e.Utilities;
import e2e.users.pages.LoginPage;
import e2e.notifications.pages.NotificationsPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static e2e.utils.Constants.*;

public class NotificationsTest {

    private static WebDriver driver;

    private static NotificationsPage notificationsPage;
    private static LoginPage loginPage;

    @BeforeAll
    public static void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.navigate().to("http://localhost:4200/");

        notificationsPage = PageFactory.initElements(driver, NotificationsPage.class);
        loginPage = PageFactory.initElements(driver, LoginPage.class);
    }

    @Test
    public void testZeroNotifications() {
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());
        // ulogujemo se kao barman
        loginPage.setEmailInput(BARMAN_EMAIL);
        loginPage.setPasswordInput(BARMAN_PASSWORD);
        loginPage.loginButtonClick();

        Utilities.urlWait(driver, "http://localhost:4200/rest-app/orders/orders-page", 10);
        assertEquals("http://localhost:4200/rest-app/orders/orders-page", driver.getCurrentUrl());

        notificationsPage.notificationModalClick();
        assertTrue(notificationsPage.isSnackBarContainsMessage("You don't have any notifications to display!"));
        notificationsPage.logOutLinkClick();
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());
    }

    @Test
    public void testCookNotifications() {
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());
        // ulogujemo se kao cook
        loginPage.setEmailInput(COOK_EMAIL);
        loginPage.setPasswordInput(COOK_PASSWORD);
        loginPage.loginButtonClick();

        Utilities.urlWait(driver, "http://localhost:4200/rest-app/orders/orders-page", 10);
        assertEquals("http://localhost:4200/rest-app/orders/orders-page", driver.getCurrentUrl());

        assertEquals("2", notificationsPage.getMatIconNotificationsSize().getText());

        notificationsPage.notificationModalClick();

        // provjera sadrzaja
        assertEquals("TABLE 1", notificationsPage.getTableIds().get(0).getText());
        assertEquals("TABLE 4", notificationsPage.getTableIds().get(1).getText());

        assertEquals("New order from table 1.", notificationsPage.getNotificationsContent().get(0).getText());
        assertEquals("New order from table 4.", notificationsPage.getNotificationsContent().get(1).getText());

        Actions action = new Actions(driver);
        action.sendKeys(Keys.ESCAPE).build().perform();

        notificationsPage.logOutLinkClick();
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());
    }

    @Test
    public void testCookNotificationsSeenOne() throws InterruptedException {
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());
        // ulogujemo se kao cook
        loginPage.setEmailInput(COOK_EMAIL);
        loginPage.setPasswordInput(COOK_PASSWORD);
        loginPage.loginButtonClick();

        Utilities.urlWait(driver, "http://localhost:4200/rest-app/orders/orders-page", 10);
        assertEquals("http://localhost:4200/rest-app/orders/orders-page", driver.getCurrentUrl());

        assertEquals("2", notificationsPage.getMatIconNotificationsSize().getText());

        notificationsPage.notificationModalClick();

        notificationsPage.buttonSeenOneClick();

        assertTrue(notificationsPage.isSnackBarContainsMessage("Notification set to seen."));
        assertEquals("1", notificationsPage.getMatIconNotificationsSize().getText());

        assertEquals("TABLE 4", notificationsPage.getTableIds().get(0).getText());

        assertEquals("New order from table 4.", notificationsPage.getNotificationsContent().get(0).getText());

        Actions action = new Actions(driver);
        action.sendKeys(Keys.ESCAPE).build().perform();

        notificationsPage.logOutLinkClick();
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());
    }

    @Test
    public void testCookNotificationsSeenAll() {
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());
        // ulogujemo se kao cook
        loginPage.setEmailInput(COOK_EMAIL);
        loginPage.setPasswordInput(COOK_PASSWORD);
        loginPage.loginButtonClick();

        Utilities.urlWait(driver, "http://localhost:4200/rest-app/orders/orders-page", 10);
        assertEquals("http://localhost:4200/rest-app/orders/orders-page", driver.getCurrentUrl());

        notificationsPage.notificationModalClick();

        notificationsPage.buttonSeenAllClick();

        assertTrue(notificationsPage.isSnackBarContainsMessage("All notifications set to seen."));

        notificationsPage.notificationModalClick();
        assertTrue(notificationsPage.isSnackBarContainsMessage("You don't have any notifications to display!"));
        notificationsPage.logOutLinkClick();
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());
    }

    @AfterAll
    public static void closeSelenium() {
        driver.quit();
    }
}
