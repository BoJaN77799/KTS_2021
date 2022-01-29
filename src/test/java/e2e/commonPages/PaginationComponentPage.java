package e2e.commonPages;

import e2e.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


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

    public boolean checkNumberOfPages(int num){
        List<WebElement> els = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfAllElements(this.paginationElements));

        boolean checker = false;
        for (int i=0; i< num; i++){
            checker = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.textToBePresentInElement(els.get(i+1), Integer.toString(i+1)));
        }

        return checker;
    }

}
