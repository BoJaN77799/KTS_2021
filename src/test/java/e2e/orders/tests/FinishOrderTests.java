package e2e.orders.tests;

import e2e.Utilities;
import e2e.commonPages.CommonHeader;
import e2e.commonPages.FloorChangeComponentPage;
import e2e.orders.pages.*;
import e2e.users.pages.LoginPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FinishOrderTests {

    private static WebDriver driver;

    private static LoginPage loginPage;

    private static TablesWaiterViewComponentPage tablesWaiterViewComponentPage;

    private static FloorChangeComponentPage floorChangeComponentPage;

    private static UpdateOrderPage updateOrderPage;

    private static TableViewWithOrderItemsComponentPage tableViewWithOrderItemsComponentPage;

    private static CommonHeader commonHeader;

    @BeforeAll
    public static void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.navigate().to("http://localhost:4200/");

        loginPage = PageFactory.initElements(driver, LoginPage.class);
        floorChangeComponentPage = PageFactory.initElements(driver, FloorChangeComponentPage.class);
        tablesWaiterViewComponentPage = PageFactory.initElements(driver, TablesWaiterViewComponentPage.class);
        updateOrderPage = PageFactory.initElements(driver, UpdateOrderPage.class);
        tableViewWithOrderItemsComponentPage = PageFactory.initElements(driver, TableViewWithOrderItemsComponentPage.class);
        commonHeader = PageFactory.initElements(driver, CommonHeader.class);
    }

    @Test
    public void testFinishOrderFromTableView() {
        // Login
        loginPage.setEmailInput("waiter_milorad@maildrop.cc");
        loginPage.setPasswordInput("waiter");
        loginPage.loginButtonClick();
        Utilities.urlWait(driver, "http://localhost:4200/rest-app/tables/tables-waiter", 10);
        assertEquals("http://localhost:4200/rest-app/tables/tables-waiter", driver.getCurrentUrl());

        // Change floor
        floorChangeComponentPage.nextButtonClick();
        tablesWaiterViewComponentPage.table11CircleClick();
        tableViewWithOrderItemsComponentPage.finishOrderButtonClick();

        // Log out
        commonHeader.logOutLinkClick();
        Utilities.urlWait(driver, "http://localhost:4200/rest-app/auth/login", 10);
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());
    }

    @Test
    public void testFinishOrderFromUpdateOrder() {
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());

        // Login
        loginPage.setEmailInput("waiter_milorad@maildrop.cc");
        loginPage.setPasswordInput("waiter");
        loginPage.loginButtonClick();
        Utilities.urlWait(driver, "http://localhost:4200/rest-app/tables/tables-waiter", 10);
        assertEquals("http://localhost:4200/rest-app/tables/tables-waiter", driver.getCurrentUrl());

        // Change floor
        floorChangeComponentPage.nextButtonClick();
        tablesWaiterViewComponentPage.table14CircleClick();
        tableViewWithOrderItemsComponentPage.updateOrderButtonClick();
        Utilities.urlWait(driver, "http://localhost:4200/rest-app/orders/update-order-page/14", 10);
        assertEquals("http://localhost:4200/rest-app/orders/update-order-page/14", driver.getCurrentUrl());

        // Finish order
        updateOrderPage.clickFinishOrderButton();
        Utilities.urlWait(driver, "http://localhost:4200/rest-app/tables/tables-waiter", 10);
        assertEquals("http://localhost:4200/rest-app/tables/tables-waiter", driver.getCurrentUrl());

        // Log out
        commonHeader.logOutLinkClick();
        Utilities.urlWait(driver, "http://localhost:4200/rest-app/auth/login", 10);
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());
    }

    @AfterAll
    public static void closeSelenium() {
        driver.quit();
    }
}
