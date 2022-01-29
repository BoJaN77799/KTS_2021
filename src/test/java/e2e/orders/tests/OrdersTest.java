package e2e.orders.tests;

import e2e.Utilities;
import e2e.users.pages.LoginPage;
import e2e.orders.pages.OrdersPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static e2e.utils.Constants.*;

public class OrdersTest {

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
    public void testNewOrders() {
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());
        // ulogujemo se kao barman
        loginPage.setEmailInput(BARMAN_EMAIL);
        loginPage.setPasswordInput(BARMAN_PASSWORD);
        loginPage.loginButtonClick();

        Utilities.urlWait(driver, "http://localhost:4200/rest-app/orders/orders-page", 10);
        assertEquals("http://localhost:4200/rest-app/orders/orders-page", driver.getCurrentUrl());

        ordersPage.optionsLinkClick();
        ordersPage.newOrdersLinkClick();

        assertEquals("http://localhost:4200/rest-app/orders/orders-page", driver.getCurrentUrl()); // that's the same url

        assertEquals(5, ordersPage.getTableNewOrdersBodyContent().size());
        List<WebElement> cells = ordersPage.getTableNewOrdersBodyContent().get(0).findElements(By.xpath("//td"));
        assertEquals("14", cells.get(0).getText());
        assertEquals("January 13th 2022", cells.get(1).getText());
        assertEquals("Pozuri, zedan sam za stolom 13.", cells.get(2).getText());
        assertEquals("13", cells.get(3).getText());
        assertEquals("1", cells.get(4).getText());

        ordersPage.logOutLinkClick();
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());
    }

    @Test
    public void testMyOrders() {
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());
        // ulogujemo se kao barman
        loginPage.setEmailInput(BARMAN_EMAIL);
        loginPage.setPasswordInput(BARMAN_PASSWORD);
        loginPage.loginButtonClick();

        //assertEquals("http://localhost:4200/rest-app/orders/orders-page", driver.getCurrentUrl());

        ordersPage.optionsLinkClick();
        ordersPage.myOrdersLinkClick();

        assertEquals(String.format("http://localhost:4200/rest-app/orders/my-orders-page/%d", BARMAN_ID), driver.getCurrentUrl()); // that's the same url

        // assertEquals(1, ordersPage.getTableMyOrdersBodyContent().size());
        int size = ordersPage.getTableMyOrdersBodyContent().size();
        List<WebElement> cells = ordersPage.getTableMyOrdersBodyContent().get(size-1).findElements(By.xpath("//td"));
        assertEquals("12", cells.get(0).getText());
        assertEquals("January 13th 2022", cells.get(1).getText());
        assertEquals("Pozuri, zedan sam.", cells.get(2).getText());
        assertEquals("4", cells.get(3).getText());

        ordersPage.logOutLinkClick();
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());
    }

    @AfterAll
    public static void closeSelenium() {
        driver.quit();
    }
}