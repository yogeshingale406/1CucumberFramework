package utility;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import com.google.common.base.Function;

import cucumber.TestContext;
import stepDefinitions.Hooks;

import org.junit.*;

public class TestUtil extends Hooks {

	
	public TestUtil(TestContext context) {
		super(context);
	}

	

	//Checking whether the test case is executable or not
	public static boolean isExecutable(String tcid) {
		for(int rownum=2; rownum<=excel.getRowCount("test_suite"); rownum++){
			if(excel.getCellData("test_suite", "testName", rownum).equals(tcid)){
				if(excel.getCellData("test_suite", "runmode", rownum).equals("Y")){
					Hooks.testName=excel.getCellData("test_suite", "Description", rownum);
					return true;
				}
				else{
					Hooks.testName=excel.getCellData("test_suite", "Description", rownum);
					return false;
				}
				
			}
		}
		return false;
	}
	//Highlighting web element 
	
	public static void highlinghtElement(WebElement elem,String color) {
		((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid "+color+"'", elem);
	}
	
	//Checking whether the single element is displayed and enabled or not
	public static void isElementPresent(String Xpath) {
		try{
			WebElement elem = driver.findElement(By.xpath(Xpath));
			if(elem.isDisplayed() && elem.isEnabled()){
				highlinghtElement(elem,"yellow");	
				//if then any one of the element from list is invalid then test should be failed
				if(allWebElementPresent == true){
					allWebElementPresent= true;
					
				}
				else
					allWebElementPresent = false;
			}
			else{
				errorMessage = errorMessage + "Element with xpath "+Xpath+" is not either displayed or enabled or both\n";
				allWebElementPresent= false;
				
			}
		}catch(NoSuchElementException e){
		//	System.out.println("Catch..\n"+e.getLocalizedMessage());
			errorMessage = errorMessage + "NoSuchElementException: Element with xpath "+Xpath+" is not present\n";
			
			allWebElementPresent= false;
		}
	}
	//Method to verify elements on any page passed to method
		public static void isElementPresent(Object[][] elementList) {
			allWebElementPresent = true;
			//System.out.println(elementList.length);
			for(int i=0;i<elementList.length;i++)
			{
				//System.out.println(object.getProperty(String.valueOf(elementList[i][0]).trim()));
				//isElementPresent(object.getProperty(String.valueOf(elementList[i][0]).trim()));
			}
		}
		
		public static void checking_allWebElementPresentMethod() {
			if(allWebElementPresent){
				app_logs.debug(driver.getCurrentUrl()+" is a valid page");
				System.out.println(driver.getCurrentUrl()+" is a valid page");
			}
			else{
				app_logs.debug(driver.getCurrentUrl()+" is not a valid page");
				//System.out.println(driver.getCurrentUrl()+" is not a valid page");
				/*try {
					TestUtil.captureScreenshot("invalid_"+siteName.split("[.]")[1]);
				} catch (IOException e) {
					e.printStackTrace();
				}*/
				System.out.println(driver.getCurrentUrl()+" is not a valid page due to following: \n"+errorMessage);
				Assert.fail(driver.getCurrentUrl()+" is not a valid page due to following: \n"+errorMessage);
				

			}
		}
	//Checking whether the page has all the required element present or not  
	public static void verifyPageWithElement(Object[][] elementList) {
		
		isElementPresent(elementList);
		checking_allWebElementPresentMethod();
		

	}
	public static void verifyPageWithElement(String Xpath) {
		
		isElementPresent(Xpath);
		checking_allWebElementPresentMethod();
	}
	
	
	
	//Compare text
	public static boolean compareString(String actual,String expected) {


		if(actual.equals(expected) && allWebElementPresent==true){

			return true;
		}
		else{
			errorMessage = errorMessage + "actual label present is ["+ actual+"] but expected ["+ expected+"]\n";
			return false;

		}
	}
	public static boolean compareString(String language,String actual,String expected) {


		if(actual.equals(expected) && allWebElementPresent==true){

			return true;
		}
		else{
			if(!actual.equals(expected)) //if and only if both text are different
				errorMessage = errorMessage + "actual label present on "+language+" is not same as expected label\n";
			return false;

		}
	}
	

	//Getting data for test case from respective sheet of excel file 
	public static Object[][] getData(String sheetName){
		//	System.out.println(System.getProperty("user.dir"));
		if(excel==null)
			excel=new utility.ExcelReader(System.getProperty("user.dir")+"src/dd_properties/testdata.xlsx");

		int rows = excel.getRowCount(sheetName);
		int cols =  excel.getColumnCount(sheetName);
		Object data[][] = new Object[rows-1][cols];

		for(int rowNum=2; rowNum<=rows; rowNum++)
			for(int colNum=0; colNum<cols;colNum++){
				data[rowNum-2][colNum]=excel.getCellData(sheetName, colNum, rowNum);

			}
		return data;
	}

	//Capturing screenshot
	public static String captureScreenshot() throws IOException{

		Calendar cal = new GregorianCalendar();
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		int sec = cal.get(Calendar.SECOND);
		int min = cal.get(Calendar.MINUTE);
		int date = cal.get(Calendar.DATE);
		int day = cal.get(Calendar.HOUR_OF_DAY);

		String mailscreenshotpath = null;
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

		try {
			mailscreenshotpath = System.getProperty("user.dir")+"\\screenshot\\"+year+"_"+date+"_"+(month+1)+"_"+day+"_"+min+"_" +sec+".jpeg";
			FileUtils.copyFile(scrFile, new File(mailscreenshotpath));
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("Error while capturing screenshot");
		}
		return mailscreenshotpath;
		/*System.getProperty("user.dir")+*/

	}
	//Overloading capture screenshot method to give file name
	public static String captureScreenshot(String fileName) throws IOException{

		Calendar cal = new GregorianCalendar();
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		int sec = cal.get(Calendar.SECOND);
		int min = cal.get(Calendar.MINUTE);
		int date = cal.get(Calendar.DATE);
		int day = cal.get(Calendar.HOUR_OF_DAY);
		String mailscreenshotpath = null;

		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

		try {
			// dot (.) in string path denotes root path of project 
			mailscreenshotpath = ".\\screenshot\\"+fileName+"_"+year+"_"+date+"_"+(month+1)+"_"+day+"_"+min+"_" +sec+".jpeg";
			//mailscreenshotpath = methodName+"_"+year+"_"+date+"_"+(month+1)+"_"+day+"_"+min+"_" +sec+".jpeg";
			FileUtils.copyFile(scrFile, new File(mailscreenshotpath));
			//FileUtils.copyFile(scrFile, new File("C:\\WebDriverBasics\\TestNGListenersOnFailure\\Screenshot"+mailscreenshotpath));
		} catch (Exception e) {
		//	e.printStackTrace();
			System.out.println("Error while capturing screenshot");
		}
		return mailscreenshotpath;

	}
	// Creating Reports.zip file of given directory
	public static void zip(String dirPath){
		try
		{
			File inFolder=new File(dirPath);
			File outFolder=new File("Reports.zip");
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(outFolder)));
			BufferedInputStream in = null;
			byte[] data  = new byte[1000];
			String files[] = inFolder.list();
			for (int i=0; i<files.length; i++)
			{
				in = new BufferedInputStream(new FileInputStream
						(inFolder.getPath() + "/" + files[i]), 1000);  
				out.putNextEntry(new ZipEntry(files[i])); 
				int count;
				while((count = in.read(data,0,1000)) != -1)
				{
					out.write(data, 0, count);
				}
				out.closeEntry();
			}
			out.flush();
			out.close();
			System.out.println("Zip file created...");

		}
		catch(Exception e)
		{
			e.printStackTrace();
		} 
	}
	
	//Sending mail using object of monitoringMail class
	public static void mailReport() {
		//Creating zip file of screenshot folder
		TestUtil.zip(System.getProperty("user.dir")+"/screenshot");
		//Creating object of monitoringMail class
		monitoringMail mail =new monitoringMail();
		try {
			//Sending mail 
			mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, TestConfig.messageBody, TestConfig.attachmentPath, TestConfig.attachmentName);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	//Fluent wait function
	public static void fluentWaitForElement(WebElement waitForElement) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
		wait.pollingEvery(500, TimeUnit.MILLISECONDS);
		wait.withTimeout(5, TimeUnit.SECONDS);
		wait.ignoring(NoSuchElementException.class);
		

		Function<WebDriver, WebElement> function = new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver arg0) {
				return	waitForElement;
			}
		};
		
		wait.until(function);
	}
	//Fluent wait function
		public static void fluentWaitForElement(WebElement waitForElement, int timeToWait) {
			FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
			wait.pollingEvery(500, TimeUnit.MILLISECONDS);
			wait.withTimeout(timeToWait, TimeUnit.SECONDS);
			wait.ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
			

			Function<WebDriver, WebElement> function = new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver arg0) {
					return	waitForElement;
				}
			};
			
			wait.until(function);
		}
	//Wait till done
		public static void waitTillDone(WebElement ele) {
			 boolean breakIt = true;
			while (true) {
			    breakIt = true;
			  
			    try {
			    	ele.isDisplayed();
			    	 // System.out.println(pageEmailCount);
			    } catch (Exception e) {
			        if (e.getMessage().contains("element is not attached")) {
			            breakIt = false;
			        }
			    }
			    if (breakIt) {
			        break;
			    }

			}

		}

}
