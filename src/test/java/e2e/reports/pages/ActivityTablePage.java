package e2e.reports.pages;

import e2e.utils.Constants;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;

public class ActivityTablePage extends GeneralTablePage{

    public ActivityTablePage(WebDriver driver) {
        super(driver);
        this.contentCheck = Arrays.asList("3|Marko|Bjelica|WAITER|4",
                "4|Darko|Tica|WAITER|5", "6|Veljko|Tomic|COOK|4",
                "7|Cvetko|Anova|COOK|4", "8|Dunjica|Slatkica|BARMAN|4", "9|Mica|Bradina|BARMAN|3");
        this.url = Constants.BASE_URL + "rest-app/reports/reports-manager/activity-table";
    }

}
