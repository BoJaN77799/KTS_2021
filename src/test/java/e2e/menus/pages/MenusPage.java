package e2e.menus.pages;

import e2e.Utilities;
import e2e.commonPages.GeneralPage;
import org.openqa.selenium.WebDriver;
import e2e.utils.Constants;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MenusPage extends GeneralPage {

    @FindBy(xpath = "//button[@id='create-new-menu']")
    private WebElement createNewMenuButton;

    @FindBy(xpath = "//button[@id='create-menu']")
    private WebElement createMenuButton;

    @FindBy(xpath = "//button[@id='cancel-create-menu']")
    private WebElement cancelCreatingMenuButton;

    @FindBy(xpath = "//input[@id='input-name-menu']")
    private WebElement inputNewMenu;

    @FindBy(xpath = "//select//option")
    private List<WebElement> menusContent;

    @FindBy(xpath = "//button[@id='activate-menu']")
    private WebElement activateMenuButton;

    @FindBy(xpath = "//button[@id='deactivate-menu']")
    private WebElement deactivateMenuButton;

    @FindBy(xpath = "//mat-select[@id='select-for-update-menu']")
    private WebElement selectUpdateMenu;

    @FindBy(xpath = "//span[@class='mat-option-text']")
    private List<WebElement> updateMenuContent;

    @FindBy(xpath = "//button[@id='update-menu']")
    private WebElement updateMenuButton;

    @FindBy(xpath = "//button[@id='cancel-update-menu']")
    private WebElement cancelUpdateMenuButton;

    @FindBy(xpath = "//mat-select[@id='all-active-menus-select']")
    private WebElement allActiveMenusSelect;

    @FindBy(xpath = "//span[@class='mat-option-text']")
    private List<WebElement> allActiveMenusContent;

    public MenusPage(WebDriver driver) {
        super(driver);
        this.url = Constants.BASE_URL + "rest-app/menus/menus-manager";
    }

    public void createNewMenuButtonClick() {
        Utilities.clickableWait(this.driver, this.createNewMenuButton, 10).click();
    }

    public void createMenuButtonClick() {
        Utilities.clickableWait(this.driver, this.createMenuButton, 10).click();
    }

    public void cancelCreatingMenuButtonClick() {
        Utilities.clickableWait(this.driver, this.cancelCreatingMenuButton, 10).click();
    }

    public WebElement getInputNewMenu() {
        return Utilities.visibilityWait(this.driver, this.inputNewMenu, 10);
    }

    public boolean checkContent(String text) {
        List<WebElement> elements =  Utilities.visibilityOfElements(this.driver, this.menusContent, 10);
        for (WebElement el : elements) {
            System.out.println(el.getText());
            if (el.getText().equalsIgnoreCase(text))
                return true;
        }

        return false;
    }

    public void setInputNewMenu(String text) {
        WebElement el = getInputNewMenu();
        el.clear();
        el.sendKeys(text);
    }

    public void activateMenuButtonClick() {
        Utilities.clickableWait(this.driver, this.activateMenuButton, 10).click();
    }

    public void deactivateMenuButtonClick() {
        Utilities.clickableWait(this.driver, this.deactivateMenuButton, 10).click();
    }

    public void selectUpdateMenuClick() {
        Utilities.clickableWait(this.driver, this.selectUpdateMenu, 10).click();
    }

    public void selectMenuForUpdate(int index) {
        List<WebElement> elements = Utilities.visibilityOfElements(this.driver, this.updateMenuContent, 30);
        Utilities.clickableWait(this.driver, elements.get(index), 10).click();
    }

    public void updateMenuButtonClick() {
        Utilities.clickableWait(this.driver, this.updateMenuButton, 10).click();
    }

    public void cancelUpdateMenuButtonClick() {
        Utilities.clickableWait(this.driver, this.cancelUpdateMenuButton, 10).click();
    }

    public void allActiveMenusSelectClick() {
        Utilities.clickableWait(this.driver, this.allActiveMenusSelect, 10).click();
    }

    public boolean allActiveMenusContentTextCheck(String text) {
        List<WebElement> elements = Utilities.visibilityOfElements(this.driver, this.allActiveMenusContent, 10);
        for (WebElement el : elements) {
            if (Utilities.visibilityWait(this.driver, el, 10).getText().equalsIgnoreCase(text))
                return true;
        }
        return false;
    }

    public void selectMenuFromAllActiveMenusContent(int index) {
        List<WebElement> elements = Utilities.visibilityOfElements(this.driver, this.allActiveMenusContent, 10);
        Utilities.clickableWait(this.driver, elements.get(index), 10).click();
    }
}
