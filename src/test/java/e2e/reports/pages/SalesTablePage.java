package e2e.reports.pages;

import e2e.utils.Constants;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;

public class SalesTablePage extends GeneralTablePage{

    public SalesTablePage(WebDriver driver) {
        super(driver);
        this.contentCheck = Arrays.asList("1|Govedja supa|600|2",
                "5|Prsuta|1900|2", "21|Cappuccino|480|2");
        this.url = Constants.BASE_URL + "rest-app/reports/reports-manager/sales-table";
    }

}
