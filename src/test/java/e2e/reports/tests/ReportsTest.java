package e2e.reports.tests;

import e2e.utils.Constants;
import e2e.users.pages.LoginPage;
import e2e.commonPages.ManagerPage;
import e2e.reports.pages.*;
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
    private static SalesChartPage salesChartPage;
    private static IncomeExpensesChartPage incomeExpensesChartPage;
    private static ActivityTablePage activityTablePage;
    private static ActivityChartPage activityChartPage;

    @BeforeAll
    public static void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.navigate().to(Constants.BASE_URL);

        loginPage = PageFactory.initElements(driver, LoginPage.class);
        managerPage = PageFactory.initElements(driver, ManagerPage.class);
        salesTablePage = PageFactory.initElements(driver, SalesTablePage.class);
        salesChartPage = PageFactory.initElements(driver, SalesChartPage.class);
        incomeExpensesChartPage = PageFactory.initElements(driver, IncomeExpensesChartPage.class);
        activityTablePage = PageFactory.initElements(driver, ActivityTablePage.class);
        activityChartPage = PageFactory.initElements(driver, ActivityChartPage.class);
    }

    @Test
    public void testReports() {
        // login as manager
        loginPage.setEmailInput(Constants.MANAGER_EMAIL);
        loginPage.setPasswordInput(Constants.MANAGER_PASSWORD);
        loginPage.loginButtonClick();

        // go to sales table
        managerPage.reportsNavBarClick();
        managerPage.reportsLinksClickIndex(0);
        assertTrue(salesTablePage.getUrl());
        salesTablePage.setupDateFrom();
        salesTablePage.reportsButtonClick();
        assertTrue(salesTablePage.getTableContentCheck());
        assertTrue(salesTablePage.generalSortAction());

        // go to sales chart
        managerPage.reportsNavBarClick();
        managerPage.reportsLinksClickIndex(1);
        assertTrue(salesChartPage.getUrl());
        salesChartPage.setupDateFrom();
        salesChartPage.reportsButtonClick();

        // go to income expenses chart
        managerPage.reportsNavBarClick();
        managerPage.reportsLinksClickIndex(2);
        assertTrue(incomeExpensesChartPage.getUrl());
        assertTrue(incomeExpensesChartPage.expensesParagraphCheckText("Expenses: 70932.58 RSD"));
        assertTrue(incomeExpensesChartPage.incomeParagraphCheckText("Income: 12130 RSD"));
        incomeExpensesChartPage.setupDateFrom();
        incomeExpensesChartPage.reportsButtonClick();
        assertTrue(incomeExpensesChartPage.expensesParagraphCheckText("Expenses: 11922.58 RSD"));
        assertTrue(incomeExpensesChartPage.incomeParagraphCheckText("Income: 680 RSD"));

        // go to activity table
        managerPage.reportsNavBarClick();
        managerPage.reportsLinksClickIndex(3);
        assertTrue(activityTablePage.getUrl());
        activityTablePage.setupDateFrom();
        activityTablePage.reportsButtonClick();
        assertTrue(activityTablePage.getTableContentCheck());
        assertTrue(activityTablePage.generalSortAction());

        // go to activity chart
        managerPage.reportsNavBarClick();
        managerPage.reportsLinksClickIndex(4);
        assertTrue(activityChartPage.getUrl());
        activityChartPage.setupDateFrom();
        activityChartPage.reportsButtonClick();

        // logout
        managerPage.logOutLinkClick();
    }

    @AfterAll
    public static void closeSelenium() {
        driver.quit();
    }


}
