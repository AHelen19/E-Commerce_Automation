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
		getReport().log(LogStatus.INFO,"Able to click on All Menu or Hamberger icon");
		home.clickOnTVApplieance();
		getReport().log(LogStatus.INFO, "Able to click on TV Appliance Menu");
		home.clickOnTelevisions();
		getReport().log(LogStatus.INFO, "Able to click on Televisions Menu");
		productsPage.selectBrand();
		getReport().log(LogStatus.INFO, "Able to select Brand successfully");
		productsResultsPage.sortProductListByPriceInDescendingOrder();
		productsResultsPage.clickOnSecondHighestResult();
		aboutItemText = productsResultsPage.switchWindowAndVerifyAboutThisItemSection();
		if (aboutItemText.equals("")){
			getReport().log(LogStatus.FAIL, "About Item section is empty");

		}
		Assert.assertNotEquals(aboutItemText,"","About Item section is empty");
		getReport().log(LogStatus.PASS, "Able to fetch About Item text successfully");
		System.out.println(aboutItemText);
		getReport().log(LogStatus.INFO, aboutItemText);
	}
}
