package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductsPage extends BasePage{

    @FindBy(xpath = "//span[text()='Samsung']")
    WebElement sumSungBrand;
    @FindBy(xpath = "//span[contains(text(),'RESULT')]")
    WebElement resultsHeading;

    public ProductsPage(WebDriver driver) {
        super(driver);

    }

    public void selectBrand(){
        try {
            waitForElement(10,this.sumSungBrand).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isResultsHeadingShown() {

         try {
           return waitForElement(10, resultsHeading).isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
        }
         return false;
    }
}
