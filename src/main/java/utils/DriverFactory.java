package utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverFactory {

    private WebDriver driver = null;

    public WebDriver getDriver(String browser) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        String hubUrl = FileConfig.getProperty("hubUrl");
        if (browser.equalsIgnoreCase("chrome")) {
            // It will launch the Chrome browser in the automation script
            // webdrivermanager checks for the latest version of the specified WebDriver
            // binary.
            // If the binaries are not present on the machine, then it will download the
            // WebDriver binaries.
            WebDriverManager.chromedriver().setup();
            capabilities.setBrowserName("chrome");
            capabilities.setPlatform(getPlatform());
            ChromeOptions options = new ChromeOptions();
            options.merge(capabilities);
            //driver = new ChromeDriver();
            try {
                driver = new RemoteWebDriver(new URL(hubUrl), options);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else if (browser.equalsIgnoreCase("firefox")) {
            // Setting up the firefox browser for automation
            WebDriverManager.firefoxdriver().setup();
            capabilities.setBrowserName("firefox");
            capabilities.setPlatform(getPlatform());
            FirefoxOptions options = new FirefoxOptions();
            options.merge(capabilities);
            //driver = new ChromeDriver();
            try {
                driver = new RemoteWebDriver(new URL(hubUrl), options);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
           // driver = new FirefoxDriver();
        }
        driver.manage().window().maximize();
        System.out.println("Maximize the browser window using driver.manager().maximize functionality");
        // sets the time to wait for a page to load completely before throwing an error
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Long.parseLong(FileConfig.getProperty("pageLoadTimeOut"))));
        System.out.println("setting the pageLoadtimeout to wait for the page to load before throwing an error");
        // This timeout is used to specify the amount of time the driver should wait
        // while searching for an element if it is not immediately present
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(FileConfig.getProperty("implicitWait"))));
        System.out.println(
                "The driver is configured to implicitly wait for " + FileConfig.getProperty("implicitWait") + " seconds while searching for an element if it is not immediately found");
        // This method will clear Cache before starting to googling
        removeAllCookies();
        return driver;
    }

    private void removeAllCookies() {
        driver.manage().deleteAllCookies(); // delete all cookies
        System.out.println("Deleting all cookies of the browser");
        try {
            // wait 10 seconds to clear cookies.
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    private Platform getPlatform() {
        if (System.getProperty("os.name").toLowerCase().contains("win"))
            return Platform.WINDOWS;
        else if (System.getProperty("os.name").toLowerCase().contains("mac"))
            return Platform.MAC;
        else if (System.getProperty("os.name").toLowerCase().contains("linux"))
            return Platform.LINUX;
        else  return Platform.ANY;

    }
}
