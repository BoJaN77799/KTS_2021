package e2e.orders.tests;

import e2e.Utilities;
import e2e.commonPages.FloorChangeComponentPage;
import e2e.commonPages.ModalDialogForQuantityInputPage;
import e2e.commonPages.PaginationComponentPage;
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

public class CreateOrderTests {

    private static WebDriver driver;

    private static LoginPage loginPage;

    private static TablesWaiterViewComponentPage tablesWaiterViewComponentPage;

    private static FloorChangeComponentPage floorChangeComponentPage;

    private static SearchAndFilterComponentPage searchAndFilterComponentPage;

    private static SelectItemsOrderComponentPage selectItemsOrderComponentPage;

    private static ModalDialogForQuantityInputPage modalDialogForQuantityInputPage;

    private static ItemsManipulationOrderComponentPage itemsManipulationOrderComponentPage;

    private static PaginationComponentPage paginationComponentPage;

    private static CreateOrderPage createOrderPage;

    @BeforeAll
    public static void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.navigate().to("http://localhost:4200/");

        loginPage = PageFactory.initElements(driver, LoginPage.class);
        floorChangeComponentPage = PageFactory.initElements(driver, FloorChangeComponentPage.class);
        tablesWaiterViewComponentPage = PageFactory.initElements(driver, TablesWaiterViewComponentPage.class);
        searchAndFilterComponentPage = PageFactory.initElements(driver, SearchAndFilterComponentPage.class);
        selectItemsOrderComponentPage = PageFactory.initElements(driver, SelectItemsOrderComponentPage.class);
        modalDialogForQuantityInputPage = PageFactory.initElements(driver, ModalDialogForQuantityInputPage.class);
        itemsManipulationOrderComponentPage = PageFactory.initElements(driver, ItemsManipulationOrderComponentPage.class);
        paginationComponentPage = PageFactory.initElements(driver, PaginationComponentPage.class);
        createOrderPage = PageFactory.initElements(driver, CreateOrderPage.class);
    }

    @Test
    public void createOrderTest() throws InterruptedException {
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());

        // Login
        loginPage.setEmailInput("waiter_milorad@maildrop.cc");
        loginPage.setPasswordInput("waiter");
        loginPage.loginButtonClick();
        Utilities.urlWait(driver, "http://localhost:4200/rest-app/tables/tables-waiter", 10);
        assertEquals("http://localhost:4200/rest-app/tables/tables-waiter", driver.getCurrentUrl());

        // Change floor
        floorChangeComponentPage.nextButtonClick();
        tablesWaiterViewComponentPage.table12CircleClick();
        Utilities.urlWait(driver, "http://localhost:4200/rest-app/orders/create-order-page/12", 10);
        assertEquals("http://localhost:4200/rest-app/orders/create-order-page/12", driver.getCurrentUrl());

        // Add food one
        selectItemsOrderComponentPage.firstItemCardH2Click();
        modalDialogForQuantityInputPage.setNumberInput("2");
        modalDialogForQuantityInputPage.setNumberInput("2");
        modalDialogForQuantityInputPage.confirmButtonClick("2");
        itemsManipulationOrderComponentPage.firstOrderedElementPlusButtonClick();
        itemsManipulationOrderComponentPage.firstOrderedElementPriorityUpButtonClick();
        itemsManipulationOrderComponentPage.firstOrderedElementPriorityDownButtonClick();
        assertTrue(itemsManipulationOrderComponentPage.isFirstOrderedItemAnchorTextPresent("Govedja supa (300) [Default]"));
        assertTrue(itemsManipulationOrderComponentPage.isFirstOrderedElementQuantitySpanTextPresent("3"));

        // Add drink one from second page
        selectItemsOrderComponentPage.openDrinkTabDivClick();
        selectItemsOrderComponentPage.firstItemCardH2Click();
        modalDialogForQuantityInputPage.setNumberInput("2");
        modalDialogForQuantityInputPage.setNumberInput("2");
        modalDialogForQuantityInputPage.confirmButtonClick("2");
        assertTrue(itemsManipulationOrderComponentPage.isSecondOrderedItemAnchorTextPresent("Bravo orange (220)"));
        itemsManipulationOrderComponentPage.secondOrderedElementMinusButtonClick();
        assertTrue(itemsManipulationOrderComponentPage.isSecondOrderedElementQuantitySpanTextPresent("1"));

        // Add food two and delete it
        selectItemsOrderComponentPage.openFoodTabDivClick();
        selectItemsOrderComponentPage.secondItemCardH2Click();
        modalDialogForQuantityInputPage.setNumberInput("5");
        modalDialogForQuantityInputPage.setNumberInput("5");
        modalDialogForQuantityInputPage.confirmButtonClick("5");
        assertTrue(itemsManipulationOrderComponentPage.isThirdOrderedItemAnchorTextPresent("Gorgonzola (650) [Default]"));
        itemsManipulationOrderComponentPage.thirdOrderedElementDeleteButtonClick();
        assertTrue(itemsManipulationOrderComponentPage.isPriceParagraphTextPresent("1120"));

        // Sorting test
        searchAndFilterComponentPage.sortSelectClick();
        searchAndFilterComponentPage.nameAscendingOptionClick();
        assertTrue(selectItemsOrderComponentPage.areItemsSortedNameAsc());

        searchAndFilterComponentPage.sortSelectClick();
        searchAndFilterComponentPage.nameDescendingOptionClick();
        assertTrue(selectItemsOrderComponentPage.areItemsSortedNameDesc());

        searchAndFilterComponentPage.sortSelectClick();
        searchAndFilterComponentPage.priceAscendingOptionClick();
        assertTrue(selectItemsOrderComponentPage.areItemsSortedPriceAsc());

        searchAndFilterComponentPage.sortSelectClick();
        searchAndFilterComponentPage.priceDescendingOptionClick();
        assertTrue(selectItemsOrderComponentPage.areItemsSortedPriceDesc());

        // Search test
        searchAndFilterComponentPage.setSearchInput("Go");
        searchAndFilterComponentPage.searchButtonClick();
        assertTrue(selectItemsOrderComponentPage.doesItemNamesContainText("Go"));

        // Create order
        createOrderPage.clickCreateOrderButton();
        Utilities.urlWait(driver, "http://localhost:4200/rest-app/tables/tables-waiter", 10);
        assertEquals("http://localhost:4200/rest-app/tables/tables-waiter", driver.getCurrentUrl());
    }

    @AfterAll
    public static void closeSelenium() {
        driver.quit();
    }

}
