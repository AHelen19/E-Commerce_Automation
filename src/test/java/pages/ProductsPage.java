package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductsPage extends BasePage{

    @FindBy(xpath = "//span[text()='Samsung']/parent::a//input")
    WebElement sumSungBrand;

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
}
