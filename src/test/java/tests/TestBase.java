package tests;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import utils.DriverFactory;
import utils.TestConfig;

import java.lang.reflect.Method;

public class TestBase {
	Logger log = LogManager.getLogger(TestBase.class.getName());

	private WebDriver driver;
	private DriverFactory driverFactory;
	private String baseUrl;
	ExtentReports extentReport;
	ExtentTest extentTest;

	@BeforeSuite
	public void initSuite() {
       TestConfig.loadConfig();
		log.info("loading configuration file that is setup for dev environment");
		baseUrl = TestConfig.getProperty("baseUrl");
		extentReport = new ExtentReports("./TestReport.html",true);
	}

	/**
	 * initialize the driver factory and get driver based on the runtType and
	 *
	 * @param browser - browser type
	 */
	@Parameters("browser")
	@BeforeClass(alwaysRun = true)
	public void initDriver(String browser) {
		driverFactory = new DriverFactory();
		log.info("Creating driver factory object");
		log.info("extracting browser from environment variable");
		if(System.getenv("runtype").equals("local")){
			driver = driverFactory.getDriver(browser);
		}else if(System.getenv("runtype").equals("remote")){
			driver = driverFactory.getRemoteWebDriver(browser);
		}else if(System.getenv("runtype").equals("docker")){
			driver = driverFactory.getRemoteWebDriverDocker(System.getenv("hub"));
		}

		log.info("getting driver based on the the run type");

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

	@AfterClass(alwaysRun = true)
	public void tearDriver() {
		driverFactory.quitDriver();
		log.info("Quiting the driver");
	}

	@AfterSuite
	public void clearReport(){
		extentReport.flush();
		extentReport.close();
	}
}
