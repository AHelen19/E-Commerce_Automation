package pages;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class BasePage extends Page {

    protected WebDriver driver;

    /**
     * Constructor for BasePage class
     *
     * @param driver - driver
     */
    public BasePage(WebDriver driver) {
        //Initialize the driver

        super(driver);
        this.driver = driver;
        //initialize all the web elements located by @FindBy annotation in this class and its subclass
        PageFactory.initElements(driver, this);
    }

    /**
     * This method will wait and element before throwing exception with the following values:
     *
     * @param time
     * @param element
     * @return
     * @throws Exception
     */
    public WebElement waitForElement(long time, WebElement element) throws Exception {
        //creating webDriverWait object to wait for an element for specified period - time
        //But it will be polling every 1 seconds to check the element's state
        System.out.println("Checking if the element can be clicked every 1 second and this activity will " +
                "last a maximum of the given time - " + time);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
        return wait.ignoring(NoSuchElementException.class)
                .pollingEvery(Duration.ofSeconds(1))
                .until(ExpectedConditions.elementToBeClickable(element));
    }


    public WebElement waitForStaleElementToBeClickable(long time, WebElement element) throws Exception {
        //creating webDriverWait object to wait for an element for specified period - time
        //But it will be polling every 1 seconds to check the element's state
        System.out.println("Checking wether the element can be clicked every 1 second and this activity will last a maximum of the give time - " + time);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
        return wait.ignoring(StaleElementReferenceException.class)
                .pollingEvery(Duration.ofSeconds(1))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * This method will launch the app by accepting
     *
     * @param url as param
     */
    public void launchApp(String url) {
        driver.get(url);
        System.out.println("launching  " + url + " Site");

    }

    /**
     * A method to get Element by accepting By
     *
     * @param locator
     * @return
     */
    @Override
    public WebElement getElement(By locator) {
        WebElement element = null;
        try {
            element = driver.findElement(locator);

        } catch (Exception e) {
            System.out.println("There is issue in finding element by locator " + locator);
            e.printStackTrace();
        }
        return element;
    }

    /**
     * A method to get list of Elements by accepting By
     *
     * @param locator
     * @return - list of web elements
     */
    @Override
    public List<WebElement> getElements(By locator) {
        List<WebElement> elementsList = null;
        try {
            waitForElement(10, driver.findElement(locator));
            elementsList = driver.findElements(locator);

        } catch (Exception e) {
            System.out.println("There is issue in finding elements by locator " + locator);
            e.printStackTrace();
        }
        return elementsList;
    }

    /**
     * Method for checking element presence before
     *
     * @param locator - locator to find element
     */
    @Override
    public void waitForElementPresent(By locator) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {
            System.out.println("Some exception or error occured while waiting for element  " + locator.toString());
        }

    }

    /**
     * Method for checking element is interactable or not
     *
     * @param locator - locator to find element
     */
    @Override
    public void waitForElementToBeInteractable(By locator) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            System.out.println("Some exception or error occured while waiting for element  " + locator.toString());
        }
    }
}