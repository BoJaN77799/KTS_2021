package e2e.reports.pages;

import e2e.utils.Constants;
import org.openqa.selenium.WebDriver;

public class SalesChartPage extends GeneralReportPage{

    public SalesChartPage(WebDriver driver) {
        super(driver);
        this.url = Constants.BASE_URL + "rest-app/reports/reports-manager/sales-chart";
    }

}
