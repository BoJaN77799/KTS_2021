package e2e.reports.pages;

import e2e.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;
import java.util.List;

public class SalesTablePage extends GeneralReportPage{

    @FindBy(xpath = "//table//thead//tr//th")
    private List<WebElement> tableHeaderContent;

    @FindBy(xpath = "//table//tbody//tr")
    private List<WebElement> tableBodyContent;

    private List<String> contentCheck;

    public SalesTablePage(WebDriver driver) {
        super(driver);
        this.contentCheck = Arrays.asList("1|Govedja supa|600|2",
                "5|Prsuta|1900|2", "21|Cappuccino|480|2");
    }

    public boolean getTableContentCheck() {
        while (true) {
            try {
                List<WebElement> rowsOfTable = Utilities.visibilityOfElements(driver, this.tableBodyContent, 10);
                if (rowsOfTable.size() != this.contentCheck.size())
                    return false;
                for (int i = 0; i < rowsOfTable.size(); ++i) {
                    List<WebElement> colsOfRow = rowsOfTable.get(i).findElements(By.tagName("td"));
                    String[] cols = this.contentCheck.get(i).split("\\|");
                    if (colsOfRow.size() != cols.length)
                        return false;
                    if (!(colsOfRow.get(0).getText().equals(cols[0]) && colsOfRow.get(1).getText().equals(cols[1])
                        && colsOfRow.get(2).getText().equals(cols[2]) && colsOfRow.get(3).getText().equals(cols[3])))
                        return false;
                }
                return true;
            } catch (StaleElementReferenceException ignored) {
            }
        }
    }

    public boolean sortButtonClickAndCheck(int index, boolean direction) { // true-asc false-desc
        Utilities.clickableWait(driver, this.tableHeaderContent.get(index), 10).click();
        List<WebElement> rowsOfTable = Utilities.visibilityOfElements(driver, this.tableBodyContent, 10);
        for (int i = 0; i < rowsOfTable.size(); ++i) {
            if (i == rowsOfTable.size() - 1)
                break;
            int result = 0;
            try {
                result = Integer.parseInt(rowsOfTable.get(i).findElements(By.tagName("td")).get(index).getText())
                        - Integer.parseInt(rowsOfTable.get(i+1).findElements(By.tagName("td")).get(index).getText());
            } catch (NumberFormatException ex) {
                result = rowsOfTable.get(i).findElements(By.tagName("td")).get(index).getText()
                        .compareTo(rowsOfTable.get(i+1).findElements(By.tagName("td")).get(index).getText());
            }
            if (direction && result > 0)
                return false;
            if (!direction && result < 0)
                return false;
        }
        return true;
    }
}
