package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage{

	@FindBy(css = "a[id='nav-hamburger-menu']")
	WebElement allLink;
	
	@FindBy(xpath = "//div[@id='hmenu-content']//div[text()='TV, Appliances, Electronics']")
	WebElement tvAppliance;
	
	@FindBy(xpath="//ul[@class='hmenu hmenu-visible hmenu-translateX']//a[text()='Televisions']")
	WebElement televisions;

	@FindBy(xpath = "//span[text()='Samsung']/parent::a//input")
	WebElement sumSungBrand;

	public HomePage(WebDriver driver) {
		super(driver);
		
	}

	public void clickOnAllLInkHambergerMenu() {
		try {
			waitForElement(10,this.allLink).click();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void clickOnTVApplieance() {
		try {
			waitForElement(10,this.tvAppliance).click();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
      public void clickOnTelevisions() {
		  try {
			  waitForElement(10,this.televisions).click();
		  } catch (Exception e) {
			  e.printStackTrace();
		  }

	}

}
