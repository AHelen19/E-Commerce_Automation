package utils;

import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.ImmutableCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverFactory {

    private WebDriver driver = null;
    private RemoteWebDriver remoteWebDriver;
    public WebDriver getDriver(String browser) {
        try{
            //String browser = TestConfig.getBrowser();
            if(browser.equalsIgnoreCase("chrome")){
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            }else if(browser.equalsIgnoreCase("firefox")){
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            }
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(TestConfig.getProperty("pageLoadTimeOut"))));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(TestConfig.getProperty("implicitWait"))));
            driver.manage().window().maximize();
            //driver.manage().window().setSize( new Dimension(1600,900));
        }catch (Exception e){
            // Setup Log
            e.printStackTrace();
        }
        return driver;
    }
    public WebDriver getRemoteWebDriver(String browser){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        try{
           // String browser = TestConfig.getBrowser();
            URL url = new URL("http://127.0.0.1:4444");
            if(browser.equalsIgnoreCase("chrome")){
                capabilities.setBrowserName("chrome");
                capabilities.setPlatform(getPlatform());
                ChromeOptions options = new ChromeOptions();
                options.merge(capabilities);
                remoteWebDriver = new RemoteWebDriver(url,options);
            }else if(browser.equalsIgnoreCase("firefox")){
                capabilities.setBrowserName("firefox");
                capabilities.setPlatform(getPlatform());
                FirefoxOptions options = new FirefoxOptions();
                options.merge(capabilities);
                remoteWebDriver = new RemoteWebDriver(url,options);
            }

            remoteWebDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(TestConfig.getProperty("pageLoadTimeOut"))));
            remoteWebDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(TestConfig.getProperty("implicitWait"))));
            // TO DO -- Set Window Size
        }catch (Exception e){
            // Setup Log
            e.printStackTrace();
        }
        return remoteWebDriver;
    }

    public WebDriver getRemoteWebDriverDocker(String hub_ip){
        try{
            String browser = TestConfig.getBrowser();
            ImmutableCapabilities capabilities = null;
            URL url = new URL("http://"+hub_ip+":4444");

            if(browser.equalsIgnoreCase("chrome")){
                WebDriverManager.chromedriver().setup();
                capabilities = new ImmutableCapabilities("browserName", "chrome");
            }else if(browser.equalsIgnoreCase("firefox")){
                WebDriverManager.firefoxdriver().setup();
                capabilities = new ImmutableCapabilities("browserName", "firefox");
            }
            remoteWebDriver = new RemoteWebDriver(url,capabilities);
            remoteWebDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(TestConfig.getProperty("pageLoadTimeOut"))));
            remoteWebDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(TestConfig.getProperty("implicitWait"))));
            // TO DO -- Set Window Size
        }catch (Exception e){
            // Setup Log
            e.printStackTrace();
        }
        return remoteWebDriver;
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
