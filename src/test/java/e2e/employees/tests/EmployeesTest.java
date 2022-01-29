package e2e.employees.tests;

import e2e.commonPages.ConfirmationDialog;
import e2e.commonPages.ManagerPage;
import e2e.employees.pages.EmployeesPage;
import e2e.users.pages.LoginPage;
import e2e.utils.Constants;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;


import static org.junit.jupiter.api.Assertions.*;

public class EmployeesTest {

    private static WebDriver driver;

    private static LoginPage loginPage;
    private static ManagerPage managerPage;
    private static EmployeesPage employeesPage;
    private static ConfirmationDialog confirmationDialog;

    @BeforeAll
    public static void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.navigate().to(Constants.BASE_URL);

        loginPage = PageFactory.initElements(driver, LoginPage.class);
        managerPage = PageFactory.initElements(driver, ManagerPage.class);
        employeesPage = PageFactory.initElements(driver, EmployeesPage.class);
        confirmationDialog = PageFactory.initElements(driver, ConfirmationDialog.class);

    }

    @Test
    public void testEmployees() throws InterruptedException {
        loginPage.setEmailInput(Constants.MANAGER_EMAIL);
        loginPage.setPasswordInput(Constants.MANAGER_PASSWORD);
        loginPage.loginButtonClick();

        // click employee
        assertTrue(employeesPage.getUrl());
        employeesPage.employeeCardClick(2);

        // click on salaries
        Thread.sleep(1000);
        employeesPage.salariesButtonClick();
        employeesPage.setSalariesBonusesInput("8000");
        employeesPage.addSalariesOrBonusesButtonClick();
        confirmationDialog.confirmButtonClick();

        employeesPage.closeSalariesBonusesDialogButtonClick();
        employeesPage.closeProfileInfoButtonClick();

        employeesPage.employeeCardClick(0);

        // click on buttons
        Thread.sleep(1000);
        employeesPage.bonusesButtonClick();
        employeesPage.setSalariesBonusesInput("800");
        employeesPage.addSalariesOrBonusesButtonClick();
        confirmationDialog.confirmButtonClick();

        employeesPage.closeSalariesBonusesDialogButtonClick();
        employeesPage.closeProfileInfoButtonClick();

        assertTrue(employeesPage.checkCardContent("8000"));
        managerPage.logOutLinkClick();
    }

    @AfterAll
    public static void closeSelenium() {
        driver.quit();
    }
}
