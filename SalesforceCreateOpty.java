package com.training.salesforce;

import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SalesforceCreateOpty extends SalesforceValidLogin {
	static String methodName;

	public static void main(String[] args) {
		
		try {
			//optyDropdown();
			//createOpty();
			optyPipelineReport();
			//stuckOptyReport();
			//qtrlySummaryReport();
			
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}

	}
	//TC15 opportunities drop down
	public static void optyDropdown() throws InterruptedException {
		driver=login();
		driver.findElement(By.linkText("Opportunities")).click();
		WebElement dropdown=driver.findElement(By.xpath("//select[@name='fcf']"));
		Select selectDropdown=new Select(dropdown);
		List<WebElement> dropdownValues=selectDropdown.getOptions();
		LinkedList<String> actualList=new LinkedList<String>();
		for(WebElement element:dropdownValues) {
			actualList.add(element.getText());			
			//System.out.println(element.getText());
		}		
		LinkedList<String> ExpectedList=new LinkedList<String>(Arrays.asList("All Opportunities", "Closing Next Month", "Closing This Month", "My Opportunities","New Last Week", "New This Week","Opportunity Pipeline","Private", "Recently Viewed Opportunities", "Won"));
		methodName=Thread.currentThread().getStackTrace()[1].getMethodName();	
		dropdown.click();
		assertTrue(ExpectedList.equals(actualList),"Dropdown list is not as expected");		
		captureScreenshot(driver,methodName);
		System.out.println("TC15 is passed");	
		driver.close();
	}
	//TC16 Create a new Opty
		public static void createOpty() throws InterruptedException {
			driver=login();
			driver.findElement(By.linkText("Opportunities")).click();
			driver.findElement(By.xpath("//input[@title='New']")).click();
			driver.findElement(By.id("opp3")).sendKeys("SampleOpportunity");
			driver.findElement(By.id("opp4")).sendKeys("sample");
			driver.findElement(By.xpath("//span[@class='dateFormat']/a")).click();
			WebElement stage=driver.findElement(By.id("opp11"));
			Select stageDropdown=new Select(stage);
			stageDropdown.selectByIndex(2);
			driver.findElement(By.id("opp12")).clear();
			driver.findElement(By.id("opp12")).sendKeys("30");
			WebElement lead=driver.findElement(By.id("opp6"));
			Select leadDropdown=new Select(lead);
			leadDropdown.selectByIndex(2);
			driver.findElement(By.id("opp17")).sendKeys("user");
			driver.findElement(By.name("save")).click();
			Thread.sleep(3000);
			driver.switchTo().defaultContent();
			String optyName=driver.findElement(By.xpath("//div[@class='content']/h2")).getText();			
			assertTrue(optyName.equals("SampleOpportunity"), "Opportunity name does not match");
			methodName=Thread.currentThread().getStackTrace()[1].getMethodName();	
			captureScreenshot(driver,methodName);
			System.out.println("TC16 is passed");
			driver.close();
		}
		
		//TC17 Test Opportunity Pipeline Report
		public static void optyPipelineReport() throws InterruptedException {
			driver=login();
			driver.findElement(By.linkText("Opportunities")).click();
			driver.findElement(By.linkText("Opportunity Pipeline")).click();
			String expectedReportName="Opportunity Pipeline";
			String actualReportName=driver.findElement(By.xpath("//div[@class='content']/h1")).getText();
			assertTrue(expectedReportName.equals(actualReportName), "Report name is not as expected");
			methodName=Thread.currentThread().getStackTrace()[1].getMethodName();	
			captureScreenshot(driver,methodName);
			System.out.println("TC17 is passed");
			driver.close();			
		}

		//TC18 Test Stuck Opportunities Report
		public static void stuckOptyReport() throws InterruptedException {
			driver=login();
			driver.findElement(By.linkText("Opportunities")).click();
			driver.findElement(By.linkText("Stuck Opportunities")).click();
			String expectedReportName="Stuck Opportunities";
			String actualReportName=driver.findElement(By.xpath("//div[@class='content']/h1")).getText();
			assertTrue(expectedReportName.equals(actualReportName), "Report name is not as expected");
			methodName=Thread.currentThread().getStackTrace()[1].getMethodName();	
			captureScreenshot(driver,methodName);
			System.out.println("TC18 is passed");
			driver.close();			
		} 
		//TC19 Test Quarterly Summary Report
		public static void qtrlySummaryReport() throws InterruptedException {
			driver=login();
			driver.findElement(By.linkText("Opportunities")).click();
			WebElement interval=driver.findElement(By.id("quarter_q"));
			Select intervalDropdown=new Select(interval);
			intervalDropdown.selectByValue("curnext1");
			Thread.sleep(1000);
			WebElement include=driver.findElement(By.id("open"));
			Select includeDropdown=new Select(include);
			includeDropdown.selectByIndex(3);
			driver.findElement(By.xpath("//input[@title='Run Report']")).click();
			String expectedReportName="Opportunity Report";	
			String actualReportName=driver.findElement(By.xpath("//div[@class='content']/h1")).getText();
			assertTrue(expectedReportName.equals(actualReportName), "Report name is not as expected");
			methodName=Thread.currentThread().getStackTrace()[1].getMethodName();	
			captureScreenshot(driver,methodName);
			System.out.println("TC19 is passed");
			driver.close();			
		} 

		
		
		
		
	
	
	
}



