package e2e.reports.tests;

import e2e.commonPages.LoginPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReportsTest {

    private static WebDriver driver;

    private static LoginPage loginPage;

    @BeforeAll
    public static void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.navigate().to("http://localhost:4200/");

        loginPage = PageFactory.initElements(driver, LoginPage.class);
    }

    @Test
    public void testReports() {
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());
        // ulogujemo se kao manager
        loginPage.setEmailInput("manager@maildrop.cc");
        loginPage.setPasswordInput("manager");
        loginPage.loginButtonClick();

        //assertEquals("http://localhost:4200/rest-app/employees/employees-manager", driver.getCurrentUrl());
    }

    @AfterAll
    public static void closeSelenium() {
        // driver.quit();
    }
}
