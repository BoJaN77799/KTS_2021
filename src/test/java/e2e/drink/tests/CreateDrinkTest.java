package e2e.drink.tests;

import e2e.Utilities;
import e2e.commonPages.BarmanPage;
import e2e.drink.pages.CreateDrinkPage;
import e2e.commonPages.LoginPage;
import jdk.jshell.execution.Util;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Objects;

import static e2e.commonPages.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateDrinkTest {

    private static WebDriver driver;

    private static BarmanPage barmanPage;
    private static CreateDrinkPage createDrinkPage;
    private static LoginPage loginPage;

    @BeforeAll
    public static void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.navigate().to("http://localhost:4200/");

        barmanPage = PageFactory.initElements(driver, BarmanPage.class);
        createDrinkPage = PageFactory.initElements(driver, CreateDrinkPage.class);
        loginPage = PageFactory.initElements(driver, LoginPage.class);
    }

    @Test
    public void testCreateDrink() {
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());
        // ulogujemo se kao manager
        loginPage.setEmailInput(BARMAN_EMAIL);
        loginPage.setPasswordInput(BARMAN_PASSWORD);
        loginPage.loginButtonClick();

        Utilities.urlWait(driver, "http://localhost:4200/rest-app/orders/orders-page", 10);
        assertEquals("http://localhost:4200/rest-app/orders/orders-page", driver.getCurrentUrl());

        barmanPage.optionsLinkClick();
        barmanPage.createDrinkLinkClick();

        assertEquals("http://localhost:4200/rest-app/items/items-page/drink-create", driver.getCurrentUrl());

        List<String> inputs = List.of("Gin", "199", "Alkoholno pice", "0.5", "Mnogo dobro udara, kad mnogo dobro popijes.");
        createDrinkPage.setFromInputs(inputs);
        createDrinkPage.submitForm();

        Utilities.urlWait(driver, "http://localhost:4200/rest-app/orders/orders-page", 10);
        assertEquals("http://localhost:4200/rest-app/orders/orders-page", driver.getCurrentUrl());

        assertTrue(barmanPage.isSnackBarContainsMessage("Drink successfully created"));
        barmanPage.logOutLinkClick();
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());
    }

    @AfterAll
    public static void closeSelenium() {
        driver.quit();
    }
}
