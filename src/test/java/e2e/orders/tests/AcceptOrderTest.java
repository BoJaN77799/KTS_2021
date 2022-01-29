package e2e.orders.tests;

import e2e.Utilities;
import e2e.users.pages.LoginPage;
import e2e.orders.pages.OrdersPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static e2e.utils.Constants.*;

public class AcceptOrderTest {

    private static WebDriver driver;

    private static OrdersPage ordersPage;
    private static LoginPage loginPage;

    @BeforeAll
    public static void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.navigate().to("http://localhost:4200/");

        ordersPage = PageFactory.initElements(driver, OrdersPage.class);
        loginPage = PageFactory.initElements(driver, LoginPage.class);
    }

    @Test
    public void testAcceptOrderAsBarman() {
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());
        // ulogujemo se kao barman
        loginPage.setEmailInput(BARMAN_EMAIL);
        loginPage.setPasswordInput(BARMAN_PASSWORD);
        loginPage.loginButtonClick();

        Utilities.urlWait(driver, "http://localhost:4200/rest-app/orders/orders-page", 10);
        assertEquals("http://localhost:4200/rest-app/orders/orders-page", driver.getCurrentUrl());

        assertTrue(ordersPage.getTableNewOrdersBodyContent().size() >= 1);
        ordersPage.acceptButtonFirstRowButtonClick();

        assertTrue(ordersPage.isSnackBarContainsMessage("Order successfully accepted!"));
        ordersPage.logOutLinkClick();
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());

    }

    @Test
    public void testAcceptOrderAsCook() {
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());
        // ulogujemo se kao cook
        loginPage.setEmailInput(COOK_EMAIL);
        loginPage.setPasswordInput(COOK_PASSWORD);
        loginPage.loginButtonClick();

        Utilities.urlWait(driver, "http://localhost:4200/rest-app/orders/orders-page", 10);
        assertEquals("http://localhost:4200/rest-app/orders/orders-page", driver.getCurrentUrl());

        assertTrue(ordersPage.getTableNewOrdersBodyContent().size() >= 1);
        ordersPage.acceptButtonFirstRowButtonClick();

        assertTrue(ordersPage.isSnackBarContainsMessage("Order successfully accepted!"));
        ordersPage.logOutLinkClick();
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());

    }

    @AfterAll
    public static void closeSelenium() {
        driver.quit();
    }
}
