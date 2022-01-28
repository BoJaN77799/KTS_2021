package e2e.utils;

import org.openqa.selenium.WebDriver;

public abstract class GeneralPage {

    protected WebDriver driver;

    protected String url;

    public GeneralPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean getUrl() {
        return Utilities.urlWait(driver, this.url, 10);
    }
}
