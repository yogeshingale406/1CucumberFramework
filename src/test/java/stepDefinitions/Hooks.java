package stepDefinitions;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.support.ui.WebDriverWait;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import cucumber.TestContext;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import dataProviders.ConfigFileReader;
import managers.FileReaderManager;
import managers.WebDriverManager;
import utility.ExcelReader;

public class Hooks {

	public static WebDriver driver=null;
	public static ExcelReader excel=null;

	public static Logger app_logs= Logger.getLogger("devpinoyLogger");
	public static WebDriverWait wait;
	public static boolean allWebElementPresent ;
	public static String errorMessage ; 
	//for report generation
	public static ExtentReports extReport;
	public static ExtentTest extTest;
	public static String testName;
	
	TestContext testContext;
	protected Hooks(){
	
	}
	 
	public Hooks(TestContext context) {
		testContext = context;
	}
	@Before
	public void init() throws IOException {
		if(driver==null){

			PropertyConfigurator.configure(System.getProperty("user.dir")+"/Configs/log4j.properties");;
			//System.out.println(System.getProperty("user.dir")+"/Configs/log4j.properties");
			app_logs.debug("Loading log4j property file");
			//loading config file
			FileReaderManager.getInstance().getConfigReader();

			excel= new ExcelReader("H:\\New folder\\ccleaner_4.4.0.exe\\Selenium\\workspaceWebdriver\\1DataDrivenFramework\\src\\dd_properties\\testdata.xlsx");
			app_logs.debug("Loading excel file");

			//To verify pages
			allWebElementPresent = true;
			errorMessage = ""; 

			//For extends report
			extReport = new ExtentReports (System.getProperty("user.dir") +"/test-output/STMextReport.html", true);
			app_logs.debug("Extended report initialized.");
			//System.out.println("Extended report initialized.");
			extReport
			.addSystemInfo("Environment","QA Environment")
			.addSystemInfo("Host Name", "Sherkhan")
			.addSystemInfo("User Name", "Yogesh Ingale");
			//loading the external xml file (i.e., extReport-config.xml) which was placed under the base directory
			//You could find the xml file below. Create xml file in your project and copy past the code mentioned below
			extReport.loadConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml"));

			driver = testContext.getWebDriverManager().getDriver();
		
			
			wait = new WebDriverWait(driver, 5);
			//driver.manage().window().fullscreen();

		}
	}


	@After
	public void afterSuite() {

		//		TestUtil.mailReport();


		// writing everything to document
		//flush() - to write or update test information to your report. 
		extReport.flush();
		//Call close() at the very end of your session to clear all resources. 
		//If any of your test ended abruptly causing any side-affects (not all logs sent to extReportReports, information missing), 
		//this method will ensure that the test is still appended to the report with a warning message.
		//You should call close() only once, at the very end (in @AfterSuite for example) as it closes the underlying stream. 
		//Once this method is called, calling any extReport method will throw an error.
		//close() - To close all the operation
		//extReport.close();
		
		//testContext.getWebDriverManager().closeDriver();
	}

}
