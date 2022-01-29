package e2e.commonPages;

import e2e.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class PaginationComponentPage {

    private WebDriver driver;

    @FindBy(xpath = "//app-pagination//a[.='Previous']")
    private WebElement previousAnchor;

    @FindBy(xpath = "//app-pagination//a[.='Next']")
    private WebElement nextAnchor;

    @FindBy(xpath = "//a[@class='page-link']")
    private List<WebElement> paginationElements;

    public PaginationComponentPage() {}

    public PaginationComponentPage(WebDriver driver) {
        this.driver = driver;
    }

    public void previousAnchorClick() {
        Utilities.clickableWait(driver, this.previousAnchor, 10).click();
    }

    public void nextAnchorClick() {
        Utilities.clickableWait(driver, this.nextAnchor, 10).click();
    }

    public List<WebElement> getPaginationElements() {
        return Utilities.visibilityOfElements(this.driver, this.paginationElements, 10);
    }

}
