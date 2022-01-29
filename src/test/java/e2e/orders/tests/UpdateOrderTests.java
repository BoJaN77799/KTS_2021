package e2e.orders.tests;

import e2e.Utilities;
import e2e.commonPages.FloorChangeComponentPage;
import e2e.commonPages.ModalDialogForQuantityInputPage;
import e2e.orders.pages.*;
import e2e.users.pages.LoginPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UpdateOrderTests {

    private static WebDriver driver;

    private static LoginPage loginPage;

    private static TablesWaiterViewComponentPage tablesWaiterViewComponentPage;

    private static FloorChangeComponentPage floorChangeComponentPage;

    private static SelectItemsOrderComponentPage selectItemsOrderComponentPage;

    private static ModalDialogForQuantityInputPage modalDialogForQuantityInputPage;

    private static ItemsManipulationOrderComponentPage itemsManipulationOrderComponentPage;

    private static UpdateOrderPage updateOrderPage;

    private static TableViewWithOrderItemsComponentPage tableViewWithOrderItemsComponentPage;

    @BeforeAll
    public static void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.navigate().to("http://localhost:4200/");

        loginPage = PageFactory.initElements(driver, LoginPage.class);
        floorChangeComponentPage = PageFactory.initElements(driver, FloorChangeComponentPage.class);
        tablesWaiterViewComponentPage = PageFactory.initElements(driver, TablesWaiterViewComponentPage.class);
        selectItemsOrderComponentPage = PageFactory.initElements(driver, SelectItemsOrderComponentPage.class);
        modalDialogForQuantityInputPage = PageFactory.initElements(driver, ModalDialogForQuantityInputPage.class);
        itemsManipulationOrderComponentPage = PageFactory.initElements(driver, ItemsManipulationOrderComponentPage.class);
        updateOrderPage = PageFactory.initElements(driver, UpdateOrderPage.class);
        tableViewWithOrderItemsComponentPage = PageFactory.initElements(driver, TableViewWithOrderItemsComponentPage.class);
    }

    @Test
    public void updateOrderTest() throws InterruptedException {
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());

        // Login
        loginPage.setEmailInput("waiter_milorad@maildrop.cc");
        loginPage.setPasswordInput("waiter");
        loginPage.loginButtonClick();
        Utilities.urlWait(driver, "http://localhost:4200/rest-app/tables/tables-waiter", 10);
        assertEquals("http://localhost:4200/rest-app/tables/tables-waiter", driver.getCurrentUrl());

        // Change floor
        floorChangeComponentPage.nextButtonClick();
        tablesWaiterViewComponentPage.table13CircleClick();
        tableViewWithOrderItemsComponentPage.updateOrderButtonClick();
        Utilities.urlWait(driver, "http://localhost:4200/rest-app/orders/update-order-page/13", 10);
        assertEquals("http://localhost:4200/rest-app/orders/update-order-page/13", driver.getCurrentUrl());

        // Add new food
        selectItemsOrderComponentPage.firstItemCardH2Click();
        modalDialogForQuantityInputPage.setNumberInput("2");
        modalDialogForQuantityInputPage.setNumberInput("2");
        modalDialogForQuantityInputPage.confirmButtonClick("2");
        assertTrue(itemsManipulationOrderComponentPage.isThirdOrderedItemAnchorTextPresent("Govedja supa (300) [Default]"));
        assertTrue(itemsManipulationOrderComponentPage.isPriceParagraphTextPresent("1940"));

        // Update order
        updateOrderPage.clickUpdateOrderButton();
        Utilities.urlWait(driver, "http://localhost:4200/rest-app/tables/tables-waiter", 10);
        assertEquals("http://localhost:4200/rest-app/tables/tables-waiter", driver.getCurrentUrl());
    }

    @AfterAll
    public static void closeSelenium() {
        driver.quit();
    }

}
