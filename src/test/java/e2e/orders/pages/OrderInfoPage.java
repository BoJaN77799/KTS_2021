package e2e.orders.pages;

import e2e.commonPages.BarmanCookHeader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class OrderInfoPage extends BarmanCookHeader {

    @FindBy(xpath = "//p[@class='title']")
    private WebElement orderInfoTitle;

    @FindBy(xpath = "//p[@class='text-label']")
    private WebElement orderCreatedInfoTitle;

    @FindBy(xpath = "//table[@id='order-items']/thead/tr/th")
    private List<WebElement> tableOrderItemsHeaderContent;

    @FindBy(xpath = "//table[@id='order-items']/tbody/tr")
    private List<WebElement> tableOrderItemsBodyContent;

    @FindBy(xpath = "//table[@id='order-items']/tbody/tr/td")
    private List<WebElement> firstRowCells;

    @FindBy(xpath = "//div[@class='content']")
    private WebElement noteElement;

    @FindBy(xpath = "//td[@id='finish-cell']")
    private WebElement finishCell;

    public OrderInfoPage(WebDriver driver) { super(driver); }

    public List<WebElement> getTableOrderItemsBodyContent(){ return tableOrderItemsBodyContent; }

    public List<WebElement> getFirstRowCells() { return firstRowCells; }

    public String getOrderInfoTitle() {
        return orderInfoTitle.getText();
    }

    public String getOrderCreatedInfoTitle() {
        return orderCreatedInfoTitle.getText();
    }

    public String getNoteElementText() { return noteElement.getText(); }

    public WebElement getFinishCell(){ return finishCell; }
}
