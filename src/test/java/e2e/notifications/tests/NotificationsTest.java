package e2e.notifications.tests;

import e2e.Utilities;
import e2e.commonPages.LoginPage;
import e2e.notifications.pages.NotificationsPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static e2e.commonPages.Constants.*;

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

    @AfterAll
    public static void closeSelenium() {
        driver.quit();
    }
}
