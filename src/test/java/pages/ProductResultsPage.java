package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ProductResultsPage extends BasePage {
    @FindBy(css = "select[id='s-result-sort-select']")
    WebElement selectSortByPrice;

    @FindBy(xpath = "//div[contains(@class,'s-search-results')]/div[@data-component-type='s-search-result']")
    List<WebElement> productList;

    @FindBy(css = "span[class='a-price-whole']")
    List<WebElement> priceList;

    @FindBy(xpath = "//h1[contains(text(),'About this item')]")
    WebElement aboutThisItem;

    public ProductResultsPage(WebDriver driver) {
        super(driver);
    }

    public void sortProductListByPriceInDescendingOrder() {

        try {
            waitForElement(10,selectSortByPrice).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Select sortByPriceSelect = new Select(selectSortByPrice);
        sortByPriceSelect.selectByValue("price-desc-rank");
    }

    public void clickOnSecondHighestResult() {
        productList.get(1).click();
    }

    public String switchWindowAndVerifyAboutThisItemSection() {
        String parent = driver.getWindowHandle();
        String itemDescription = "";
        Set<String> s = driver.getWindowHandles();
        // Now iterate using Iterator
        Iterator<String> I1 = s.iterator();
        while (I1.hasNext()) {
            String child_window = I1.next();
            if (!parent.equals(child_window)) {
                driver.switchTo().window(child_window);
                if (aboutThisItem.isDisplayed()) {
                    itemDescription = aboutThisItem.getText();
                }
            }
        }
        driver.switchTo().window(parent);
        return itemDescription;
    }
}
