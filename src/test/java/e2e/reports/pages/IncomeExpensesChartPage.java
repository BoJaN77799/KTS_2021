package e2e.reports.pages;

import e2e.utils.Constants;
import e2e.utils.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class IncomeExpensesChartPage extends GeneralReportPage{

    @FindBy(xpath = "//p[@id='expenses']")
    private WebElement expensesParagraph;

    @FindBy(xpath = "//p[@id='income']")
    private WebElement incomeParagraph;


    public IncomeExpensesChartPage(WebDriver driver) {
        super(driver);
        this.url = Constants.BASE_URL + "rest-app/reports/reports-manager/income-expenses-chart";
    }

    public boolean expensesParagraphCheckText(String text) {
        return Utilities.textWait(driver, this.expensesParagraph, text, 10);
    }

    public boolean incomeParagraphCheckText(String text) {
        return Utilities.textWait(driver, this.incomeParagraph, text, 10);
    }

}
