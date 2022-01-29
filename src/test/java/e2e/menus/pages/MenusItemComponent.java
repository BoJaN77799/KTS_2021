package e2e.menus.pages;

import e2e.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MenusItemComponent {

    private WebDriver driver;

    @FindBy(xpath = "//div[@class='card']")
    private List<WebElement> cardsOfItem;

    @FindBy(xpath = "(//app-menus-item-card)[1]")
    private WebElement firstItemCard;

    @FindBy(xpath = "(//app-menus-item-card)[1]//button")
    private List<WebElement> firstItemCardButtons;

    @FindBy(xpath = "//mat-select[@id='select-menu-item-add']")
    private WebElement menuItemAddSelect;

    @FindBy(xpath = "//button[@id='cancel-menu-item-add']")
    private WebElement menuItemAddCancelButton;

    @FindBy(xpath = "//button[@id='add-menu-item-add']")
    private WebElement menuItemAddButton;

    @FindBy(xpath = "//span[@class='mat-option-text']")
    private List<WebElement> menuItemAddContent;

    public MenusItemComponent(WebDriver driver) {
        this.driver = driver;
    }


    public boolean checkContentOfFirstCard(String text) {
        WebElement el = Utilities.visibilityWait(this.driver, this.firstItemCard, 10);
        return el.findElement(By.xpath("//h3")).getText().contains(text);
    }

    public boolean checkContentOfFirstCardText(String text) {
        WebElement el = Utilities.visibilityWait(this.driver, this.firstItemCard, 10);
        return el.findElement(By.xpath("//p")).getText().contains(text);
    }

    public void firstItemCardButtonsClick(int index) {
        Utilities.visibilityWait(this.driver, this.firstItemCard, 10);
        List<WebElement> elements = Utilities.visibilityOfElements(this.driver, this.firstItemCardButtons, 10);
        Utilities.clickableWait(this.driver, elements.get(index), 10).click();
    }

    public void menuItemAddSelectClick() {
        Utilities.clickableWait(this.driver, this.menuItemAddSelect, 10).click();
    }

    public void menuItemAddCancelButtonClick() {
        Utilities.clickableWait(this.driver, this.menuItemAddCancelButton, 10).click();
    }

    public void menuItemAddButton() {
        Utilities.clickableWait(this.driver, this.menuItemAddButton, 10).click();
    }

    public void menuItemAddContentClick(int index) {
        List<WebElement> elements = Utilities.visibilityOfElements(this.driver, this.menuItemAddContent, 10);
        Utilities.clickableWait(this.driver, elements.get(index), 10).click();
    }
}
