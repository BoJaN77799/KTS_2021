package e2e.menus.tests;

import e2e.commonPages.ConfirmationDialog;
import e2e.commonPages.ManagerPage;
import e2e.commonPages.PaginationComponentPage;
import e2e.menus.pages.MenuPriceComponent;
import e2e.menus.pages.MenusItemComponent;
import e2e.menus.pages.MenusPage;
import e2e.users.pages.LoginPage;
import e2e.utils.Constants;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MenusTest {

    private static WebDriver driver;

    private static LoginPage loginPage;
    private static ManagerPage managerPage;
    private static MenusPage menusPage;
    private static ConfirmationDialog confirmationDialog;
    private static MenusItemComponent menusItemComponent;
    private static PaginationComponentPage paginationComponentPage;
    private static MenuPriceComponent menuPriceComponent;

    @BeforeAll
    public static void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.navigate().to(Constants.BASE_URL);

        loginPage = PageFactory.initElements(driver, LoginPage.class);
        managerPage = PageFactory.initElements(driver, ManagerPage.class);
        menusPage = PageFactory.initElements(driver, MenusPage.class);
        confirmationDialog = PageFactory.initElements(driver, ConfirmationDialog.class);
        menusItemComponent = PageFactory.initElements(driver, MenusItemComponent.class);
        paginationComponentPage = PageFactory.initElements(driver, PaginationComponentPage.class);
        menuPriceComponent = PageFactory.initElements(driver, MenuPriceComponent.class);
    }

    @Test
    @Order(1)
    public void testAddItemToNewMenu() throws InterruptedException {
        loginPage.setEmailInput(Constants.MANAGER_EMAIL);
        loginPage.setPasswordInput(Constants.MANAGER_PASSWORD);
        loginPage.loginButtonClick();

        managerPage.menusLinkClick();

        // creating new menu
        menusPage.createNewMenuButtonClick();
        menusPage.setInputNewMenu("Novi meni");
        menusPage.createMenuButtonClick();
        confirmationDialog.confirmButtonClick();
        menusPage.cancelCreatingMenuButtonClick();
        Thread.sleep(1000); // for loading all elements
        // load inactive
        menusPage.allActiveMenusSelectClick();
        menusPage.selectMenuForUpdate(4);

        // adding first element to new menu
        menusItemComponent.firstItemCardButtonsClick(1);
        Thread.sleep(1000); // for loading all elements
        menusItemComponent.menuItemAddSelectClick();
        menusItemComponent.menuItemAddContentClick(3);
        menusItemComponent.menuItemAddButton();
        confirmationDialog.confirmButtonClick();


        menusPage.allActiveMenusSelectClick();
        menusPage.selectMenuFromAllActiveMenusContent(3);
        assertTrue(menusItemComponent.checkContentOfFirstCard("10 Cheese-Cake"));

        managerPage.logOutLinkClick();
    }

    @Test
    @Order(2)
    public void testRemoveItemFromMenuDeactivateMenu() throws InterruptedException {
        loginPage.setEmailInput(Constants.MANAGER_EMAIL);
        loginPage.setPasswordInput(Constants.MANAGER_PASSWORD);
        loginPage.loginButtonClick();

        managerPage.menusLinkClick();

        // select "Novi meni"
        menusPage.allActiveMenusSelectClick();
        menusPage.selectMenuForUpdate(3);

        // remove item and confirm
        menusItemComponent.firstItemCardButtonsClick(1);
        confirmationDialog.confirmButtonClick();
        Thread.sleep(1000); // to change content of pagination
        assertEquals(2, paginationComponentPage.getPaginationElements().size());
        // deactivate menu
        menusPage.deactivateMenuButtonClick();
        Thread.sleep(1000);
        menusPage.selectUpdateMenuClick();
        menusPage.selectMenuForUpdate(3);
        menusPage.updateMenuButtonClick();
        confirmationDialog.confirmButtonClick();
        menusPage.cancelUpdateMenuButtonClick();
        Thread.sleep(1000);
        menusPage.allActiveMenusSelectClick();
        assertFalse(menusPage.allActiveMenusContentTextCheck("Novi meni"));
        menusPage.selectMenuFromAllActiveMenusContent(0);
        managerPage.logOutLinkClick();
    }

    @Test
    @Order(3)
    public void changePriceOfSelectedItem() throws InterruptedException {
        loginPage.setEmailInput(Constants.MANAGER_EMAIL);
        loginPage.setPasswordInput(Constants.MANAGER_PASSWORD);
        loginPage.loginButtonClick();

        managerPage.menusLinkClick();

        menusPage.allActiveMenusSelectClick();
        menusPage.selectMenuForUpdate(0);
        menusItemComponent.firstItemCardButtonsClick(0);
        menuPriceComponent.setItemPriceInput("400");
        menuPriceComponent.itemPriceCreateButtonClick();
        confirmationDialog.confirmButtonClick();
        menuPriceComponent.setItemPriceCancelButtonClick();

        Thread.sleep(1000); // load from back
        menusItemComponent.checkContentOfFirstCardText("400");

        managerPage.logOutLinkClick();
    }

    @AfterAll
    public static void closeSelenium() {
        driver.quit();
    }
}
