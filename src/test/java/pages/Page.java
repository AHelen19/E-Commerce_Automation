package pages;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;



public abstract class Page {

	WebDriver driver;
	WebDriverWait wait;
	Logger log;
	
	public Page(WebDriver driver) {
		this.driver = driver;
		//15 timeout in seconds
		log = LogManager.getLogger(Page.class.getName());
	}
	
	public abstract WebElement getElement(By locator);
	public abstract List<WebElement> getElements(By locator);
	public abstract void waitForElementPresent(By locator);

	public abstract  void waitForElementToBeInteractable(By locator);
	
}
