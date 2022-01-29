package e2e.tables.pages;

import e2e.Utilities;
import e2e.commonPages.GeneralPage;
import e2e.utils.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class TablesAdminPage extends GeneralPage {

    @FindBy(xpath = "//*[@tableId='1']")
    private WebElement table1;

    @FindBy(xpath = "//*[@tableId='15']")
    private WebElement tableCreated15;

    @FindBy(xpath = "//*[@tableType='new'][1]")
    private WebElement tableCreateNew;

    @FindBy(xpath = "//*[@name='delete3']")
    private WebElement tableDelete;

    public TablesAdminPage(WebDriver driver) {
        super(driver);
        this.url = Constants.BASE_URL + "rest-app/tables/tables-admin";
    }

    public boolean moveTable1(){
        WebElement circle = Utilities.visibilityWait(driver, table1, 10);

        double oldX = Double.parseDouble(circle.getAttribute("cx"));
        double oldY = Double.parseDouble(circle.getAttribute("cy"));

        Actions builder = new Actions(driver);
        Action dragAndDrop = builder.clickAndHold(circle).moveByOffset(60, 150).release().build();
        dragAndDrop.perform();

        circle = Utilities.visibilityWait(driver, table1, 10);

        double newX = Double.parseDouble(circle.getAttribute("cx"));
        double newY = Double.parseDouble(circle.getAttribute("cy"));

        return newX != oldX || newY != oldY;
    }

    public void createTable(){
        WebElement circle = Utilities.visibilityWait(driver, tableCreateNew, 10);

        Actions builder = new Actions(driver);
        Action dragAndDrop = builder.clickAndHold(circle).moveByOffset(-500, 0).release().build();
        dragAndDrop.perform();
    }

    public boolean checkIfTableExits(String id){
        Utilities.visibilityWait(driver, By.xpath("//*[@tableId='" + id + "']"), 10);
        return true;
    }

    public boolean deleteTable(String id){
        WebElement delbtn = Utilities.visibilityWait(driver, By.xpath("//*[@name='delete" + id + "']"), 10).get(0);
        delbtn.click();

        return Utilities.invisibilityWait(driver, driver.findElement(By.xpath("//*[@tableId='" + id + "']")), 10);
    }
}
