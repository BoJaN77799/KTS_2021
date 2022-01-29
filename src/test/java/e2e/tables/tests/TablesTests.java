package e2e.tables.tests;

import e2e.commonPages.AdminPage;
import e2e.commonPages.FloorChangeComponentPage;
import e2e.tables.pages.TablesAdminPage;
import e2e.users.pages.LoginPage;
import e2e.utils.Constants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;


import static org.junit.jupiter.api.Assertions.assertTrue;

public class TablesTests {
    private static WebDriver driver;

    private static TablesAdminPage tablesPage;

    private static LoginPage loginPage;

    private static AdminPage adminPage;

    private static FloorChangeComponentPage floorChangeComponentPage;

    @BeforeAll
    public static void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.navigate().to(Constants.BASE_URL);

        loginPage = PageFactory.initElements(driver, LoginPage.class);
        tablesPage = PageFactory.initElements(driver, TablesAdminPage.class);
        adminPage = PageFactory.initElements(driver, AdminPage.class);
        floorChangeComponentPage = PageFactory.initElements(driver, FloorChangeComponentPage.class);
    }

    @Test
    public void tablesTests() {
        // login as admin
        loginPage.setEmailInput(Constants.ADMIN_EMAIL);
        loginPage.setPasswordInput(Constants.ADMIN_PASSWORD);
        loginPage.loginButtonClick();

        //go to users page
        adminPage.tablesLinkClick();
        assertTrue(tablesPage.getUrl());

        //move table 1
        assertTrue(tablesPage.moveTable1());

        //create table
        tablesPage.createTable();
        assertTrue(tablesPage.checkIfTableExits("15")); //id kreiranog ce biti 15

        //delete table
        assertTrue(tablesPage.deleteTable("3"));

        floorChangeComponentPage.nextButtonClick();
        assertTrue(tablesPage.checkIfTableExits("11"));
        assertTrue(tablesPage.checkIfTableExits("12"));
        assertTrue(tablesPage.checkIfTableExits("13"));
        assertTrue(tablesPage.checkIfTableExits("14"));
        floorChangeComponentPage.previousButtonClick();
    }
}
