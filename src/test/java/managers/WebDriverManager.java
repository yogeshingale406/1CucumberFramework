package managers;
 
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ByName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import stepDefinitions.Hooks;
 
public class WebDriverManager {
	private WebDriver driver;
	private static String driverType;
	private static String environmentType;
	private static final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";
 
	public WebDriverManager() {
		driverType = FileReaderManager.getInstance().getConfigReader().getBrowser();
		environmentType = FileReaderManager.getInstance().getConfigReader().getEnvironment();
	}
 
	public WebDriver getDriver() {
		if(driver == null) driver = createDriver();
		return driver;
	}
 
	private WebDriver createDriver() {
		   switch (environmentType) {	    
	        case "Local" : driver = createLocalDriver();
	        	break;
	        case "Remote" : driver = createRemoteDriver();
	        	break;
		   }
		   return driver;
	}
 
	private WebDriver createRemoteDriver() {
		throw new RuntimeException("RemoteWebDriver is not yet implemented");
	}
 
	private WebDriver createLocalDriver() {
		switch(FileReaderManager.getInstance().getConfigReader().getBrowser()){

		case "chrome" :

			Hooks.app_logs.debug("Chrome launched");
			System.setProperty("webdriver.chrome.driver",
					"H:/New folder/ccleaner_4.4.0.exe/Selenium/workspaceWebdriver/BrowserDrivers/chromedriver.exe");

			//driver= new HtmlUnitDriver();
			driver= new ChromeDriver(new ChromeOptions().addArguments("--disable-notifications"));
			break;

		case "ie" :
			Hooks.app_logs.debug("IE launched");
				System.setProperty("webdriver.ie.driver",
					"H:/New folder/ccleaner_4.4.0.exe/Selenium/workspaceWebdriver/BrowserDrivers/IEDriverServer32.exe");
			driver=new InternetExplorerDriver(new InternetExplorerOptions().addCommandSwitches("--disable-notifications"));
			break;
		case "firefox" :
			Hooks.app_logs.debug("Firefox launched");
			System.setProperty("webdriver.gecko.driver",
					"H:/New folder/ccleaner_4.4.0.exe/Selenium/workspaceWebdriver/BrowserDrivers/geckodriver.exe");
			driver= new FirefoxDriver(new FirefoxOptions().addArguments("--disable-notifications"));
			break;
	}
        driver.manage().timeouts().implicitlyWait(FileReaderManager.getInstance().getConfigReader().getImplicitlyWait(), TimeUnit.SECONDS);
		return driver;
	}	
	public void navigateTo_HomePage(String applicationUrl) {
		driver.get(applicationUrl);
	}
	public WebElement getElement(String path){
		return driver.findElement(By.xpath(path));
	}
	public List<WebElement> getElements(String path){
		return driver.findElements(By.xpath(path));
	}
	public void closeDriver() {
		driver.close();
		driver.quit();
	}
}