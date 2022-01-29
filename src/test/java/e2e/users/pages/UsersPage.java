package e2e.users.pages;

import e2e.Utilities;
import e2e.commonPages.GeneralPage;
import e2e.utils.Constants;
import org.checkerframework.checker.guieffect.qual.UIType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class UsersPage extends GeneralPage {

    @FindBy(id = "searchFieldInput")
    protected WebElement searchFieldInput;

    @FindBy(id = "userTypeSelect")
    protected WebElement userTypeSelect;

    @FindBy(id = "clearSearchField")
    protected  WebElement clearSFBtn;
    //headers
    @FindBy(xpath = "//th[@name='headerId']")
    protected  WebElement idHeader;

    @FindBy(xpath = "//th[@name='headerFN']")
    protected  WebElement firstNameHeader;

    @FindBy(xpath = "//th[@name='headerLN']")
    protected  WebElement lastNameHeader;

//    @FindBy(xpath = "//th[@name='headerUT']")
//    protected  WebElement userTypeHeader;
//
//    @FindBy(xpath = "//th[@name='headerEmail']")
//    protected  WebElement emailHeader;
//
//    @FindBy(xpath = "//th[@name='headerTelephone']")
//    protected  WebElement telephoneHeader;

    @FindBy(xpath = "//a[@class='page-link']")
    protected List<WebElement> usersNavigationPages;

    //cells
    @FindBy(xpath = "//td[@name='idCell']")
    protected List<WebElement> shownCellIds;

    @FindBy(xpath = "//td[@name='firstNameCell']")
    protected List<WebElement> shownCellFirstName;

    @FindBy(xpath = "//td[@name='lastNameCell']")
    protected List<WebElement> shownCellLastName;

//    @FindBy(xpath = "//td[@name='emailCell']")
//    protected List<WebElement> shownCellEmail;
//
//    @FindBy(xpath = "//td[@name='userTypeCell']")
//    protected List<WebElement> shownCellUserType;
//
//    @FindBy(xpath = "//td[@name='telephoneCell']")
//    protected List<WebElement> shownCellTelephone;

    @FindBy(id = "delBtn3")
    protected  WebElement deleteButton;

    @FindBy(id = "createNewUser")
    protected  WebElement createButton;

    public UsersPage(WebDriver driver) {
        super(driver);
        this.url = Constants.BASE_URL + "rest-app/users/users-search";
    }

    public void clearSearchField(){
        WebElement field = Utilities.clickableWait(this.driver, clearSFBtn, 10);
        field.clear();
        field.click();
    }

    public void addTextToSearchField(String text){
        WebElement field = Utilities.visibilityWait(this.driver, searchFieldInput, 10);
        field.clear();
        field.sendKeys(text);
    }

    public void clickOnSelectUserType(int option){
        WebElement field = Utilities.clickableWait(this.driver, this.userTypeSelect, 10);
        field.click();
        WebElement selectOption = Utilities.clickableWait(this.driver, By.xpath("//mat-option[@id='mat-option-" + option +
                "']"), 10);
        selectOption.click();
    }

    public boolean checkNumberOfPages(int num){
        List<WebElement> els = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfAllElements(usersNavigationPages));

        boolean checker = false;
        for (int i=0; i< num; i++){
            checker = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.textToBePresentInElement(els.get(i+1), Integer.toString(i+1)));
        }

        return checker;
    }

    public boolean checkIdsInOrder(List<String> ids){
        boolean checker = false;
        for (int i=0; i< ids.size(); i++){
            checker = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//tr[@id='" + ids.get(i) + "']//td[@name='idCell']"), ids.get(i)));
        }
        return checker;
    }

    public boolean checkNamesInOrder(List<String> names, List<String> ids){
        boolean checker = false;
        for (int i=0; i< names.size(); i++){
            checker = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//tr[@id='" + ids.get(i) + "']//td[@name='firstNameCell']"),
                            names.get(i)));
        }
        return checker;
    }

    public boolean checkLastNamesInOrder(List<String> names, List<String> ids){
        boolean checker = false;
        for (int i=0; i< names.size(); i++){
            checker = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//tr[@id='" + ids.get(i) + "']//td[@name='lastNameCell']"),
                            names.get(i)));
        }
        return checker;
    }

    public boolean checkUserTypeInOrder(List<String> userTypes, List<String> ids){
        boolean checker = false;
        for (int i=0; i< ids.size(); i++){
            checker = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//tr[@id='" + ids.get(i) + "']//td[@name='userTypeCell']"),
                            userTypes.get(i)));
        }
        return checker;
    }

    public void paginationClick(String page){
        List<WebElement> els = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfAllElements(usersNavigationPages));
        int index = 0;
        if (page.equalsIgnoreCase("next")){
            index = els.size()-1;
        }else if (page.equalsIgnoreCase("previous")){
            index = 0;
        }else{
            index = Integer.parseInt(page);
        }
        WebElement nextBtn = Utilities.clickableWait(driver, els.get(index), 10);
        nextBtn.click();
    }

    public void clickSortId(){
        WebElement webElement = Utilities.clickableWait(driver, idHeader, 10);
        webElement.click();
    }

    public void clickSortFirstName(){
        WebElement webElement = Utilities.clickableWait(driver, firstNameHeader, 10);
        webElement.click();
    }

    public void clickSortLastName(){
        WebElement webElement = Utilities.clickableWait(driver, lastNameHeader, 10);
        webElement.click();
    }

    public boolean checkSortById(boolean asc){
        List<WebElement> webElements = Utilities.visibilityOfElements(driver, shownCellIds, 10);
        int previous = asc ? 0 : Integer.MAX_VALUE;
        for (WebElement webElement : webElements){
            int next = Integer.parseInt(webElement.getText());
            if (asc) {
                if (next < previous)
                    return false;
            }else{
                if (next > previous)
                    return false;
            }
            previous = next;
        }

        return true;
    }

    public boolean checkSortByFirstName(boolean asc){
        List<WebElement> webElements = Utilities.visibilityOfElements(driver, shownCellFirstName, 10);
        String previous = asc ? "A" : "w";
        for (WebElement webElement : webElements){
            String res = webElement.getText();
            if (asc) {
                if (previous.compareTo(res) > 0)
                    return false;
            }else{
                if (previous.compareTo(res) < 0)
                    return false;
            }
            previous = res;
        }

        return true;
    }

    public boolean checkSortByLastName(boolean asc){
        List<WebElement> webElements = Utilities.visibilityOfElements(driver, shownCellLastName, 10);
        String previous = asc ? "A" : "w";
        for (WebElement webElement : webElements){
            String res = webElement.getText();
            if (asc) {
                if (previous.compareTo(res) > 0)
                    return false;
            }else{
                if (previous.compareTo(res) < 0)
                    return false;
            }
            previous = res;
        }

        return true;
    }

    public void clickOnDelete(){
        WebElement webElement = Utilities.visibilityWait(driver, deleteButton, 10);
        webElement.click();
    }

    public void clickOnCreate(){
        WebElement webElement = Utilities.visibilityWait(driver, createButton, 10);
        webElement.click();
    }

    public void clickOnUpdate(String id){
        WebElement webElement = Utilities.visibilityWait(driver, By.xpath("//tr[@id='" + id + "']"), 10).get(0);
        webElement.click();
    }

    public boolean checkIfDeleted(String id){
        return (new WebDriverWait(driver, 10)).until(ExpectedConditions
                .textToBePresentInElementLocated(By.xpath("//tr[@id='" + id + "']//td[@name='activeCell']"),
                        "false"));
    }

    public boolean checkOnUpdateOfUser(String id, String newfirstName, String newlastName, String newtelephone){
        boolean checker = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//tr[@id='" + id + "']//td[@name='firstNameCell']"),
                        newfirstName));
        if (!checker)
            return false;

        checker = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//tr[@id='" + id + "']//td[@name='lastNameCell']"),
                        newlastName));
        if (!checker)
            return false;

        checker = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//tr[@id='" + id + "']//td[@name='telephoneCell']"),
                        newtelephone));
        return checker;
    }

    public boolean checkOnCreateOfUser(String newfirstName, String newlastName, String newtelephone, String email, String userType){
        boolean checker = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//td[@name='firstNameCell']"),
                        newfirstName));
        if (!checker)
            return false;

        checker = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//td[@name='lastNameCell']"),
                        newlastName));
        if (!checker)
            return false;

        checker = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//td[@name='telephoneCell']"),
                        newtelephone));

        if (!checker)
            return false;

        checker = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//td[@name='emailCell']"),
                        email));

        if (!checker)
            return false;

        checker = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//td[@name='userTypeCell']"),
                        userType));

        return checker;
    }
}
