package com.training.salesforce;

import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SalesforceLeads extends SalesforceValidLogin {

	public static void main(String[] args) {
		try {
			//gotoLead();
			//leadsSelectView();
			//leadsDefaultView();
			todayLeadView();
			//newLead();
			
		} catch (InterruptedException e) {		
			e.printStackTrace();
			
		}

	}	
	//TC20-leadsTab
	public static void gotoLead() throws InterruptedException {
		driver=login();
		driver.findElement(By.linkText("Leads")).click();	
		System.out.println("TC20 is passed");
		driver.close();
	}
	
	//TC21-leadsSelectView
	public static void leadsSelectView() throws InterruptedException {
		driver=login();
		driver.findElement(By.linkText("Leads")).click();
		List<String> expectedList=new LinkedList<String>(Arrays.asList("All Open Leads","My Unread Leads","Recently Viewed Leads","Today's Leads","View - Custom 1","View - Custom 2"));
		WebElement view=driver.findElement(By.id("fcf"));
		view.click();
		Select viewDropdown=new Select(view);
		List<WebElement> listOptions=viewDropdown.getOptions();
		List<String> actualList=new LinkedList<String>();		
		for(WebElement element:listOptions) {
			actualList.add(element.getText());			
		}			
	    assertTrue(expectedList.equals(actualList), "Dropdown values are not as expected");
	    System.out.println("TC21 is passed");
	    driver.close();		
	}	
	//TC22-defaultView
	public static void leadsDefaultView() throws InterruptedException {
		driver=login();
		driver.findElement(By.linkText("Leads")).click();
		WebElement view=driver.findElement(By.id("fcf"));
		Select viewDropdown=new Select(view);
		viewDropdown.selectByVisibleText("Today's Leads");
		Thread.sleep(3000);
		driver.findElement(By.id("userNavLabel")).click();
		driver.findElement(By.linkText("Logout")).click();
		driver.close();
		driver=login();
		driver.findElement(By.linkText("Leads")).click();
		driver.findElement(By.name("go")).click();
		Thread.sleep(2000);
		WebElement view1=driver.findElement(By.name("fcf"));
		Select view1Dropdown=new Select(view1);
		String selectedOption=view1Dropdown.getFirstSelectedOption().getText();
		assertTrue(selectedOption.equals("Today's Leads"),"View is not as expected");
		System.out.println("TC22 is passed");
		driver.close();
	}	
	//TC23-List item "Todays Leads" work
		public static void todayLeadView() throws InterruptedException {
			driver=login();
			driver.findElement(By.linkText("Leads")).click();
			WebElement view=driver.findElement(By.id("fcf"));
			Select viewDropdown=new Select(view);
			viewDropdown.selectByVisibleText("Today's Leads");
			//Thread.sleep(3000);			
			driver.findElement(By.name("go")).click();
			Thread.sleep(2000);
			WebElement view1=driver.findElement(By.name("fcf"));
			Select view1Dropdown=new Select(view1);
			String selectedOption=view1Dropdown.getFirstSelectedOption().getText();
			assertTrue(selectedOption.equals("Today's Leads"),"View is not as expected");
			System.out.println("TC23 is passed");
			driver.close();
		}
	//TC24 Check "New" button on Leads Home
		public static void newLead() throws InterruptedException {
			driver=login();
			driver.findElement(By.linkText("Leads")).click();
			driver.findElement(By.name("new")).click();
			Thread.sleep(2000);
			driver.findElement(By.id("name_lastlea2")).sendKeys("ABCD");
			driver.findElement(By.id("lea3")).sendKeys("ABCD");
			driver.findElement(By.xpath("(//input[@name='save'])[1]")).click();
			Thread.sleep(1000);
			String leadName=driver.findElement(By.xpath("//div[@class='content']/h2")).getText();
			assertTrue(leadName.equals("ABCD"),"Lead name not as expected");
			System.out.println("TC 24 is passed");
			driver.close();
		}
		
}
