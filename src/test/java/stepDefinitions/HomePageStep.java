package stepDefinitions;


import org.junit.Assert;
import cucumber.TestContext;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import managers.FileReaderManager;



public class HomePageStep {
	
	
	TestContext testContext;
	
	public HomePageStep(TestContext context) {
		testContext = context;
		 
	}

	@When("^browser is opened and user puts given url$")
	public void browser_is_opened_and_user_puts_given_url() throws Throwable {
		testContext.getWebDriverManager().navigateTo_HomePage(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());	
	}
	@Then("^valid web page shall be displayed$")
	public void valid_web_page_shall_be_displayed() throws Throwable {
		Boolean present = testContext.getWebDriverManager().getElement(FileReaderManager.getInstance().getObjectReader().getLocator("homePageIcon")).isDisplayed();
		Assert.assertTrue("Not valid page", present);
		System.out.println("Valid page");
	}

}
