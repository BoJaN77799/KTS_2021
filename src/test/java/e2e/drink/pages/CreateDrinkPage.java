package e2e.drink.pages;

import e2e.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CreateDrinkPage {

    private WebDriver driver;

    @FindBy(xpath = "//form//div[@class='row']//div//div//div/input")
    private List<WebElement> formInputs;

    @FindBy(xpath = "//form//div[@class='row']//div//div//div/textarea")
    private WebElement descriptionTextArea;

    @FindBy(xpath = "//form//button[@id='submit']")
    private WebElement submitButton;

    public CreateDrinkPage(WebDriver driver) {
        this.driver = driver;
    }

    public void setFromInputs(List<String> inputs){
        AtomicInteger temp_index = new AtomicInteger();
        formInputs.forEach(webElement -> {
            webElement.clear();
            webElement.sendKeys(inputs.get(temp_index.getAndIncrement()));
        });
        descriptionTextArea.clear();
        descriptionTextArea.sendKeys(inputs.get(temp_index.get()));
    }

    public void submitForm(){
        Utilities.clickableWait(driver, this.submitButton, 10).click();
    }
}
