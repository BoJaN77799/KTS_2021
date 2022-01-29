package e2e.orders.pages;

import e2e.Utilities;
import e2e.commonPages.BarmanCookHeader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class OrdersPage extends BarmanCookHeader {

    @FindBy(xpath = "//table[@id='new-orders']//thead//tr//th")
    private List<WebElement> tableNewOrdersHeaderContent;

    @FindBy(xpath = "//table[@id='new-orders']//tbody//tr")
    private List<WebElement> tableNewOrdersBodyContent;

    @FindBy(xpath = "//table[@id='my-orders']//thead//tr//th")
    private List<WebElement> tableMyOrdersHeaderContent;

    @FindBy(xpath = "//table[@id='my-orders']//tbody//tr")
    private List<WebElement> tableMyOrdersBodyContent;

    @FindBy(xpath = "//table[@id='new-orders']//tbody//tr[1]/td/button[@id='accept']")
    private WebElement acceptButtonFirstRow;

    @FindBy(xpath = "//table[@id='new-orders']//tbody//tr[1]/td/button[@id='info']")
    private WebElement infoButtonFirstRow;

    public OrdersPage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getTableNewOrdersBodyContent(){ return Utilities.visibilityWait(driver, tableNewOrdersBodyContent, 10); }

    public List<WebElement> getTableMyOrdersBodyContent() { return Utilities.visibilityWait(driver, tableMyOrdersBodyContent, 10); }

    public void acceptButtonFirstRowButtonClick() { Utilities.clickableWait(driver, this.acceptButtonFirstRow, 10).click();}

    public void infoButtonFirstRowButtonClick() { Utilities.clickableWait(driver, this.infoButtonFirstRow, 10).click();}
}
