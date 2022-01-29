package e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.NoSuchElementException;

public class Utilities {

    public static boolean urlWait(WebDriver driver, String url, int wait) {
        return new WebDriverWait(driver, wait).until(ExpectedConditions.urlToBe(url));
    }

    public static boolean titleWait(WebDriver driver, String title, int wait) {
        return new WebDriverWait(driver, wait).until(ExpectedConditions.titleIs(title));
    }

    public static boolean textWait(WebDriver driver, WebElement element, String text, int wait) {
        return new WebDriverWait(driver, wait).until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    public static WebElement visibilityWait(WebDriver driver, WebElement element, int wait) {
        return new WebDriverWait(driver, wait).until(ExpectedConditions.visibilityOf(element));
    }

    public static List<WebElement> visibilityWait(WebDriver driver, By locator, int wait) {
        return new WebDriverWait(driver, wait).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public static WebElement presenceWait(WebDriver driver, By locator, int wait) {
        return new WebDriverWait(driver, wait).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static WebElement clickableWait(WebDriver driver, WebElement element, int wait) {
        return new WebDriverWait(driver, wait).until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement clickableWait(WebDriver driver, By locator, int wait) {
        return new WebDriverWait(driver, wait).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static List<WebElement> visibilityOfElements(WebDriver driver, List<WebElement> elements, int wait) {
        return new WebDriverWait(driver, wait).until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    public static boolean isPresent(WebDriver driver, By locator) {

        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }

    }
}
