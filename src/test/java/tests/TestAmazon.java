package tests;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.HomePage;
import pages.ProductResultsPage;
import pages.ProductsPage;


public class TestAmazon extends BaseTest {
	HomePage home;
	ProductsPage productsPage;
	ProductResultsPage productsResultsPage;
	
	@BeforeClass
	public void initPages() {
		home = new HomePage(driver());
		productsPage = new ProductsPage(driver());
		productsResultsPage = new ProductResultsPage(driver());
	}

	@BeforeMethod
	public void launchApp(){

		home.launchApp(baseUrl());
	}


	@Test
	public void testAmazon() {
		String aboutItemText;
		home.clickOnAllLInkHambergerMenu();
		getReport().log(LogStatus.PASS,"Able to click on All Menu or Hamburger icon");
		home.clickOnTVApplieance();
		getReport().log(LogStatus.PASS, "Able to click on TV Appliance Menu");
		home.clickOnTelevisions();
		getReport().log(LogStatus.PASS, "Able to click on Televisions Menu");
		productsPage.selectBrand();
		if (productsPage.isResultsHeadingShown()) {
			getReport().log(LogStatus.PASS, "Able to select Brand successfully");
		} else
			getReport().log(LogStatus.FAIL, "Unable to see the item list page");
		productsResultsPage.sortProductListByPriceInDescendingOrder();
		getReport().log(LogStatus.INFO, "Able to sort price by descending order successfully");
		productsResultsPage.clickOnSecondHighestResult();
		getReport().log(LogStatus.PASS, "Able to click the second item successfully");
		aboutItemText = productsResultsPage.switchWindowAndVerifyAboutThisItemSection();
		if (aboutItemText.equals("")){
			getReport().log(LogStatus.FAIL, "About Item section is empty");

		}
		Assert.assertNotEquals(aboutItemText,"","About Item section is empty");
		getReport().log(LogStatus.PASS, "Able to fetch About Item text successfully");
		System.out.println(aboutItemText);
		getReport().log(LogStatus.PASS, aboutItemText);
	}
}
