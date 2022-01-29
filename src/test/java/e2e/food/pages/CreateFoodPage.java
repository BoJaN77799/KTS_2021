package e2e.food.pages;

import e2e.Utilities;
import e2e.commonPages.CommonHeader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CreateFoodPage extends CommonHeader {

    @FindBy(xpath = "//form//div[@class='row']//div//div//div/input")
    private List<WebElement> formInputs;

    @FindBy(xpath = "//form//div[@class='row']//div//div//div/textarea")
    private List<WebElement> formTextAreas;

    @FindBy(xpath = "//form//div[@class='row']//div//div//div/mat-select")
    private List<WebElement> formMatSelects;

    @FindBy(xpath = "//form//button[@id='submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//table[@id='ingredients-table']//thead//tr//th")
    private List<WebElement> tableIngredientsHeaderContent;

    @FindBy(xpath = "//table[@id='ingredients-table']//tbody//tr")
    private List<WebElement> tableIngredientsBodyContent;


    public CreateFoodPage(WebDriver driver) { super(driver); }

    public void clickOnSelect(WebElement webElement, int option) {
        WebElement field = Utilities.clickableWait(this.driver, webElement, 10);
        field.click();
        WebElement selectOption = Utilities.clickableWait(this.driver, By.xpath("//mat-option[@id='mat-option-"+option+"']"), 10);
        selectOption.click();
    }

    public void setFromInputs(List<String> inputs) {
        AtomicInteger temp_index = new AtomicInteger();
        formInputs.forEach(webElement -> {
            webElement.clear();
            webElement.sendKeys(inputs.get(temp_index.getAndIncrement()));
        });
        formTextAreas.forEach(webElement -> {
            webElement.clear();
            webElement.sendKeys(inputs.get(temp_index.getAndIncrement()));
        });

        clickOnSelect(formMatSelects.get(0), 2);

        clickOnSelect(formMatSelects.get(1), 3); // Secer
        clickOnSelect(formMatSelects.get(1),  5); // Brasno
        clickOnSelect(formMatSelects.get(1), 8); // Voda
        clickOnSelect(formMatSelects.get(1), 9); // Prezle
    }

    public void submitForm(){
        Utilities.clickableWait(driver, this.submitButton, 10).click();
    }

    public List<WebElement> getTableIngredientsBodyContent(){ return Utilities.visibilityWait(driver, tableIngredientsBodyContent, 10); }
}
