package e2e.employees.tests;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

public class DummyTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.navigate().to("http://localhost:4200/");
    }

    @Test
    public void testDummy() {
        assertTrue(true);
        assertEquals("http://localhost:4200/rest-app/auth/login", driver.getCurrentUrl());
    }

    @AfterAll
    public static void closeSelenium() {
        // driver.quit();
    }
}
