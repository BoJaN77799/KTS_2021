package e2e.reports.tests;

import e2e.commonPages.LoginPage;
import e2e.commonPages.ManagerPage;
import e2e.reports.pages.SalesTablePage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.*;

public class ReportsTest {

    private static WebDriver driver;

    private static LoginPage loginPage;
    private static ManagerPage managerPage;
    private static SalesTablePage salesTablePage;

    @BeforeAll
    public static void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.navigate().to("http://localhost:4200/");

        loginPage = PageFactory.initElements(driver, LoginPage.class);
        managerPage = PageFactory.initElements(driver, ManagerPage.class);
        salesTablePage = PageFactory.initElements(driver, SalesTablePage.class);
    }

    @Test
    public void testReports() {
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());
        // login as manager
        loginPage.setEmailInput("manager@maildrop.cc");
        loginPage.setPasswordInput("manager");
        loginPage.loginButtonClick();

        managerPage.reportsNavBarClick();
        managerPage.reportsLinksClickIndex(0);
        assertEquals("http://localhost:4200/rest-app/reports/reports-manager/sales-table", driver.getCurrentUrl());

        salesTablePage.reportsButtonClick(); // empty list
        // togle date-picker
        salesTablePage.dateFormClick();
        salesTablePage.dateFormToggleClick();
        salesTablePage.previousMonthButtonClick();
        salesTablePage.dateFromButtonClick();
        salesTablePage.dateToButtonClick();
        salesTablePage.reportsButtonClick();

        // check table content
        assertTrue(salesTablePage.getTableContentCheck());

        assertTrue(salesTablePage.sortButtonClickAndCheck(0, true));
        assertTrue(salesTablePage.sortButtonClickAndCheck(0, false));
        assertTrue(salesTablePage.sortButtonClickAndCheck(1, true));
        assertTrue(salesTablePage.sortButtonClickAndCheck(1, false));
        assertTrue(salesTablePage.sortButtonClickAndCheck(2, true));
        assertTrue(salesTablePage.sortButtonClickAndCheck(2, false));
        assertTrue(salesTablePage.sortButtonClickAndCheck(3, true));
        assertTrue(salesTablePage.sortButtonClickAndCheck(3, false));


        managerPage.reportsNavBarClick();
        managerPage.reportsLinksClickIndex(1);
        assertEquals("http://localhost:4200/rest-app/reports/reports-manager/sales-chart", driver.getCurrentUrl());

        salesTablePage.dateFormClick();
        salesTablePage.dateFormToggleClick();
        salesTablePage.previousMonthButtonClick();
        salesTablePage.dateFromButtonClick();
        salesTablePage.dateToButtonClick();
        salesTablePage.reportsButtonClick();

        // logout
        //managerPage.logOutLinkClick();
    }

    @AfterAll
    public static void closeSelenium() {
        // driver.quit();
    }


}
