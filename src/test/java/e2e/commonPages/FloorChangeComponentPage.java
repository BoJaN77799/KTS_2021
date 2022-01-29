package e2e.commonPages;

import e2e.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FloorChangeComponentPage {

    private WebDriver driver;

    @FindBy(xpath = "(//button[@class='btn btn-primary'])[1]")
    private WebElement previousButton;

    @FindBy(xpath = "(//button[@class='btn btn-primary'])[2]")
    private WebElement nextButton;

    @FindBy(xpath = "//div[contains(text(),'Floor')]")
    private WebElement floorDiv;

    public FloorChangeComponentPage() {}

    public FloorChangeComponentPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean floorDivTextPresent(String text) {
        return Utilities.textWait(driver, this.floorDiv, text, 10);
    }

    public void previousButtonClick() {
        Utilities.clickableWait(driver, this.previousButton, 10).click();
    }

    public void nextButtonClick() {
        Utilities.clickableWait(driver, this.nextButton, 10).click();
    }

}
