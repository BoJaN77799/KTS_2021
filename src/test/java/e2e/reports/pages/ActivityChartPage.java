package e2e.reports.pages;

import e2e.utils.Constants;
import org.openqa.selenium.WebDriver;

public class ActivityChartPage extends GeneralReportPage{

    public ActivityChartPage(WebDriver driver) {
        super(driver);
        this.url = Constants.BASE_URL + "rest-app/reports/reports-manager/activity-chart";
    }

}
