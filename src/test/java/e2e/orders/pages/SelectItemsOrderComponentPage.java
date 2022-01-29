package e2e.orders.pages;

import e2e.Utilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SelectItemsOrderComponentPage {

    private WebDriver driver;

    @FindBy(xpath = "//div[@id='mat-tab-label-0-0']/div")
    private WebElement openFoodTabDiv;

    @FindBy(xpath = "//div[@id='mat-tab-label-0-1']/div")
    private WebElement openDrinkTabDiv;

    @FindBy(xpath = "(//app-cards-container//div[@class='card'])[1]//h2")
    private WebElement firstItemCardH2;

    @FindBy(xpath = "(//app-cards-container//div[@class='card'])[2]//h2")
    private WebElement secondItemCardH2;

    @FindBy(xpath = "//app-cards-container//app-item-card//h2")
    private List<WebElement> itemCardsNamesH2;

    @FindBy(xpath = "//app-cards-container//app-item-card//ul/li[2]") // Price: 300
    private List<WebElement> itemCardsPricesLi;

    public SelectItemsOrderComponentPage() {}

    public SelectItemsOrderComponentPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openFoodTabDivClick() {
        WebElement ele = Utilities.clickableWait(driver, this.openFoodTabDiv, 10);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", ele);
    }

    public void openDrinkTabDivClick() {
        WebElement ele = Utilities.clickableWait(driver, this.openDrinkTabDiv, 10);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", ele);
    }

    public void firstItemCardH2Click() {
        Utilities.clickableWait(driver, this.firstItemCardH2, 10).click();
    }

    public void secondItemCardH2Click() {
        Utilities.clickableWait(driver, this.secondItemCardH2, 10).click();
    }

    public boolean doesItemNamesContainText(String text) {
        Utilities.textWait(driver, firstItemCardH2, "Gorgonzola", 10);
        Utilities.textWait(driver, secondItemCardH2, "Govedja supa", 10);
        List<WebElement> names = Utilities.visibilityOfElements(driver, this.itemCardsNamesH2, 10);

        for(WebElement name : names) {
            System.out.println(name.getText());
            if(!name.getText().contains(text))
                return false;
        }

        return true;
    }

    public boolean areItemsSortedPriceAsc() {
        Utilities.textWait(driver, firstItemCardH2, "Sladoled Kapri", 10);
        Utilities.textWait(driver, secondItemCardH2, "Govedja supa", 10);
        List<WebElement> prices = Utilities.visibilityOfElements(driver, this.itemCardsPricesLi, 10);

        for(int i = 0; i < prices.size() - 1; i++) {
            double currentPrice = getPriceFromLi(prices.get(i));
            double nextPrice = getPriceFromLi(prices.get(i + 1));
            if (currentPrice > nextPrice)
                return false;
        }

        return true;
    }

    public boolean areItemsSortedPriceDesc() {
        Utilities.textWait(driver, firstItemCardH2, "Gorgonzola", 10);
        Utilities.textWait(driver, secondItemCardH2, "Pohovana palacinka", 10);
        List<WebElement> prices = Utilities.visibilityOfElements(driver, this.itemCardsPricesLi, 10);

        for(int i = 0; i < prices.size() - 1; i++) {
            double currentPrice = getPriceFromLi(prices.get(i));
            double nextPrice = getPriceFromLi(prices.get(i + 1));
            if (currentPrice < nextPrice)
                return false;
        }

        return true;
    }

    public boolean areItemsSortedNameAsc() {
        Utilities.textWait(driver, firstItemCardH2, "Gorgonzola", 10);
        Utilities.textWait(driver, secondItemCardH2, "Govedja supa", 10);
        List<WebElement> names = Utilities.visibilityOfElements(driver, this.itemCardsNamesH2, 10);

        for(int i = 0; i < names.size() - 1; i++) {
            String currentName = names.get(i).toString();
            String nextName = names.get(i + 1).toString();
            if(currentName.compareTo(nextName) > 0)
                return false;
        }

        return true;
    }

    public boolean areItemsSortedNameDesc() {
        Utilities.textWait(driver, firstItemCardH2, "Sladoled Kapri", 10);
        Utilities.textWait(driver, secondItemCardH2, "Pohovana palacinka", 10);
        List<WebElement> names = Utilities.visibilityOfElements(driver, this.itemCardsNamesH2, 10);

        for(int i = 0; i < names.size() - 1; i++) {
            String currentName = names.get(i).toString();
            String nextName = names.get(i + 1).toString();
            if(currentName.compareTo(nextName) < 0)
                return false;
        }

        return true;
    }

    private double getPriceFromLi(WebElement priceLi)  { // [Price: 300.0]
        return Double.parseDouble(priceLi.getText().split(" ")[1]);
    }

}
