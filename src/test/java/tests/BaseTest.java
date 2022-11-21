package tests;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import utils.DriverFactory;
import utils.FileConfig;

import java.lang.reflect.Method;

public class BaseTest {
	Logger log = LogManager.getLogger(BaseTest.class.getName());

	private WebDriver driver;
	private DriverFactory df;
	private String baseUrl;
	ExtentReports extentReport;
	ExtentTest extentTest;

	@BeforeSuite
	public void initSuite() {
       FileConfig.loadConfig("dev");
		log.info("loading configuration file that is setup for dev environment");
		baseUrl = FileConfig.getProperty("baseUrl");
		extentReport = new ExtentReports("./TestReport.html",true);
	}

	/*
	 * initialize the driver factory and get driver based on the browser given here
	 *
	 * @param browser
	 */
	@Parameters("browser")
	@BeforeClass(alwaysRun = true)
	public void initDriver(String browser) {
		df = new DriverFactory();
		log.info("Creating driver factory object");
		// pass browser as system environment variable while invoking the test
		// driver = df.getDriver(System.getenv("browser"));
		log.info("extracting browser from environment variable");
		driver = df.getDriver(browser);
		log.info("getting driver based on the the browser type");

		if (driver == null) {
			log.error("driver is not initialized");
		}
	}

	/*
	 * return driver
	 */
	protected WebDriver driver() {
		return driver;
	}

	/*
	 * return configured base url
	 */
	protected String baseUrl() {

		return this.baseUrl;
	}

	@BeforeMethod
	public void initTest(Method method){

		//extentTest = new ExtentTest(method.getName(), "");
		extentTest =extentReport.startTest("Amazon Test");

	}

	public ExtentTest getReport(){
		return extentTest;
	}

	@AfterMethod
	public void closeReport(){
		extentReport.endTest(extentTest);
	}

	//@AfterClass(alwaysRun = true)
	public void tearDriver() {
		df.quitDriver();
		log.info("Quiting the driver");
	}

	@AfterSuite
	public void clearReport(){
		extentReport.flush();
		extentReport.close();
	}
}
