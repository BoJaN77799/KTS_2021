package e2e.food.tests;

import e2e.Utilities;
import e2e.commonPages.CookPage;
import e2e.food.pages.CreateFoodPage;
import e2e.users.pages.LoginPage;
import e2e.utils.TableSortContentCheckUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.Arrays;
import java.util.List;

import static e2e.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateFoodTest {

    private static WebDriver driver;

    private static CookPage cookPage;
    private static CreateFoodPage createFoodPage;
    private static LoginPage loginPage;

    @BeforeAll
    public static void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.navigate().to("http://localhost:4200/");

        cookPage = PageFactory.initElements(driver, CookPage.class);
        createFoodPage = PageFactory.initElements(driver, CreateFoodPage.class);
        loginPage = PageFactory.initElements(driver, LoginPage.class);
    }

    @Test
    public void testCreateDrink() throws InterruptedException {
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());
        // ulogujemo se kao manager
        loginPage.setEmailInput(COOK_EMAIL);
        loginPage.setPasswordInput(COOK_PASSWORD);
        loginPage.loginButtonClick();

        Utilities.urlWait(driver, "http://localhost:4200/rest-app/orders/orders-page", 10);
        assertEquals("http://localhost:4200/rest-app/orders/orders-page", driver.getCurrentUrl());

        cookPage.optionsLinkClick();
        cookPage.createFoodLinkClick();

        assertEquals("http://localhost:4200/rest-app/items/items-page/food-create", driver.getCurrentUrl());

        List<String> inputs = List.of("Piletina u umaku od gljiva", "550", "Mesa", "20", "Mnogo dobro izgleda, kad se najedes jos bolje.", "Ubaci sastojke i mjesaj");
        createFoodPage.setFromInputs(inputs);
        List<String> content = Arrays.asList(
                "Secer|not allergen",
                "Brasno|not allergen",
                "Voda|not allergen",
                "Prezle|not allergen");
        assertTrue(TableSortContentCheckUtils.checkTableContent(driver, createFoodPage.getTableIngredientsBodyContent(), content));

        createFoodPage.submitForm();

        Utilities.urlWait(driver, "http://localhost:4200/rest-app/orders/orders-page", 10);
        assertEquals("http://localhost:4200/rest-app/orders/orders-page", driver.getCurrentUrl());

        assertTrue(cookPage.isSnackBarContainsMessage("Food successfully created"));
        cookPage.logOutLinkClick();
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());
    }

    @AfterAll
    public static void closeSelenium() {
        driver.quit();
    }
}
