package e2e.reports.pages;

import e2e.utils.TableSortContentCheckUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public abstract class GeneralTablePage extends GeneralReportPage{

    @FindBy(xpath = "//table//thead//tr//th")
    private List<WebElement> tableHeaderContent;

    @FindBy(xpath = "//table//tbody//tr")
    private List<WebElement> tableBodyContent;

    protected List<String> contentCheck;

    public GeneralTablePage(WebDriver driver) {
        super(driver);
    }

    public boolean getTableContentCheck() {
        return TableSortContentCheckUtils.checkTableContent(driver, this.tableBodyContent, contentCheck);
    }

    public boolean generalSortAction() {
        return TableSortContentCheckUtils.checkAllSortTable(driver, this.tableHeaderContent,
                this.tableBodyContent);
    }

    public boolean sortButtonClickAndCheck(int index, boolean direction) { // true-asc false-desc
        return TableSortContentCheckUtils.checkSpecificSortTable(driver, index, direction, this.tableHeaderContent,
                this.tableBodyContent);
    }

}
