package e2e.orders.tests;

import com.app.RestaurantApp.enums.ItemType;
import e2e.Utilities;
import e2e.users.pages.LoginPage;
import e2e.orders.pages.OrderInfoPage;
import e2e.orders.pages.OrdersPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static e2e.utils.Constants.*;

public class InfoOrderTest {

    private static WebDriver driver;

    private static OrderInfoPage orderInfoPage;
    private static OrdersPage ordersPage;
    private static LoginPage loginPage;

    @BeforeAll
    public static void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.navigate().to("http://localhost:4200/");

        orderInfoPage = PageFactory.initElements(driver, OrderInfoPage.class);
        ordersPage = PageFactory.initElements(driver, OrdersPage.class);
        loginPage = PageFactory.initElements(driver, LoginPage.class);
    }

    @Test
    public void testInfoOrder() {
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());
        loginPage.setEmailInput(BARMAN_EMAIL);
        loginPage.setPasswordInput(BARMAN_PASSWORD);
        loginPage.loginButtonClick();

        Utilities.urlWait(driver, "http://localhost:4200/rest-app/orders/orders-page", 10);
        assertEquals("http://localhost:4200/rest-app/orders/orders-page", driver.getCurrentUrl());

        //assertTrue(ordersPage.getTableNewOrdersBodyContent().size() >= 1);
        ordersPage.infoButtonFirstRowButtonClick();

        assertEquals("Details: Order on Table 13", orderInfoPage.getOrderInfoTitle());
        assertEquals("Order was created at January 13th 2022.", orderInfoPage.getOrderCreatedInfoTitle());

        assertEquals(1, orderInfoPage.getTableOrderItemsBodyContent().size());
        List<WebElement> cells = orderInfoPage.getFirstRowCells();
        assertEquals(ItemType.DRINK.toString(), cells.get(0).getText());
        assertEquals("Bravo orange", cells.get(1).getText());
        assertEquals("220", cells.get(2).getText());
        assertEquals("2", cells.get(3).getText());
        assertEquals("0", cells.get(4).getText());

        assertEquals("Pozuri, zedan sam za stolom 13.", orderInfoPage.getNoteElementText());

        Actions action = new Actions(driver);
        action.sendKeys(Keys.ESCAPE).build().perform(); // gasenje modala

        ordersPage.logOutLinkClick();
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());

    }

    @AfterAll
    public static void closeSelenium() {
        driver.quit();
    }
}