package e2e.utils;

import e2e.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TableSortContentCheckUtils {

    public static boolean checkTableContent(WebDriver driver, List<WebElement> tableBodyContent,
                                            List<String> contentCheck) {
        while (true) {
            try {
                List<WebElement> rowsOfTable = Utilities.visibilityOfElements(driver, tableBodyContent, 10);
                if (rowsOfTable.size() != contentCheck.size())
                    return false;
                for (int i = 0; i < rowsOfTable.size(); ++i) {
                    List<WebElement> colsOfRow = Utilities.visibilityOfElements(driver,
                            rowsOfTable.get(i).findElements(By.tagName("td")), 10);
                    String[] cols = contentCheck.get(i).split("\\|");
                    if (colsOfRow.size() != cols.length)
                        return false;
                    for (int j = 0; j < cols.length; ++j) {
                        System.out.print(colsOfRow.get(j).getText() + " ");
                        if (!(colsOfRow.get(j).getText().equals(cols[j])))
                            return false;
                    }
                    System.out.println();
                }
                return true;
            } catch (StaleElementReferenceException ignored) {
            }
        }

    }

    public static boolean checkAllSortTable(WebDriver driver, List<WebElement> tableHeaderContent,
                                            List<WebElement> tableBodyContent) {
        List<WebElement> headers = Utilities.visibilityOfElements(driver, tableHeaderContent, 10);
        boolean direction = true;
        int index = 0;
        for (int i = 0; i < headers.size() * 2; ++ i) {
            if (!(checkSpecificSortTable(driver, index, direction, tableHeaderContent, tableBodyContent)))
                return false;
            direction = !direction;
            if (i % 2 != 0)
                ++ index;
        }
        return true;
    }

    public static boolean checkSpecificSortTable(WebDriver driver, int index, boolean direction,
                                                 List<WebElement> tableHeaderContent, List<WebElement> tableBodyContent) {
        Utilities.clickableWait(driver, tableHeaderContent.get(index), 10).click();
        List<WebElement> rowsOfTable = Utilities.visibilityOfElements(driver, tableBodyContent, 10);
        for (int i = 0; i < rowsOfTable.size(); ++i) {
            if (i == rowsOfTable.size() - 1)
                break;
            int result;
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
