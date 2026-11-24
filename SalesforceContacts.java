package com.training.salesforce;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Set;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class SalesforceContacts extends SalesforceValidLogin {

	public static void main(String[] args) {
		try {
			//newContact();
			//newView();
			//recentlyCreated();
			myContacts();
			//viewContact();
			//contactViewError();
			//cancelContactView();
			//saveAndNew();
			
		} catch (InterruptedException e) {	
			e.printStackTrace();
		}
	}
	
	//TC 25 Create new contact
	public static void newContact() throws InterruptedException {
		driver=login();
		driver.findElement(By.linkText("Contacts")).click();
		driver.findElement(By.name("new")).click();
		driver.findElement(By.id("name_lastcon2")).sendKeys("dkr");
		driver.findElement(By.id("con4")).sendKeys("Auto");
		driver.findElement(By.className("lookupIcon")).click();
		Thread.sleep(4000);
		String parentWindow=driver.getWindowHandle();
		Set<String> handles=driver.getWindowHandles();
		for(String handle:handles) {
			driver.switchTo().window(handle);
		}
		Thread.sleep(2000);
		WebElement lookupFrame=driver.findElement(By.id("resultsFrame"));
		driver.switchTo().frame(lookupFrame);
		driver.findElement(By.xpath("//tr[@class='dataRow even first']//a")).click();
		Thread.sleep(2000);
		driver.switchTo().window(parentWindow);
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//input[@title='Save'])[1]")).click();
		driver.switchTo().defaultContent();
		Thread.sleep(1000);
		String contactName=driver.findElement(By.xpath("//div[@class='content']/h2")).getText();
		assertTrue(contactName.equals("dkr"), "Contact name is incorrect");
		System.out.println("TC 25 is passed");
		driver.close();
	}
	
	//TC 26 Create new view in the Contact Page
	public static void newView() throws InterruptedException {
		driver=login();
		driver.findElement(By.linkText("Contacts")).click();
		driver.findElement(By.linkText("Create New View")).click();
		String viewName="View"+System.currentTimeMillis();
		driver.findElement(By.id("fname")).sendKeys(viewName);
		//driver.findElement(By.id("devname")).sendKeys(viewName);
		driver.findElement(By.xpath("(//td[@class='pbButtonb']/input[@title='Save'])[1]")).click();		
		Thread.sleep(2000);
		WebElement contact=driver.findElement(By.className("title"));
		Select contactDropdown=new Select(contact);
		WebElement selectedView=contactDropdown.getFirstSelectedOption();
		assertTrue(selectedView.getText().equals(viewName),"View name does not match");
		System.out.println("TC26 is passed");		
		driver.close();		
	}
	//TC 27 Check recently created contact in the Contact Page
		public static void recentlyCreated() throws InterruptedException {
			driver=login();
			driver.findElement(By.linkText("Contacts")).click();			
			WebElement recent=driver.findElement(By.id("hotlist_mode"));
			Select recentDropdown=new Select(recent);
			recentDropdown.selectByIndex(0);
			Thread.sleep(4000);
			//String recentContact=driver.findElement(By.xpath("//tr[@class='dataRow even first']/th/a")).getText();
			String recentContact=driver.findElement(By.xpath("//tr[contains(@class,'first')]/th/a")).getText();
			assertTrue(recentContact.equals("dkr"),"Name does not match");
			System.out.println("TC27 is passed");		
			driver.close();		
		} 
		
	//TC 28Check 'My contacts' view in the Contact Page
		public static void myContacts() throws InterruptedException {
			driver=login();
			driver.findElement(By.linkText("Contacts")).click();	
			WebElement view=driver.findElement(By.name("fcf"));
			Select viewDropdown=new Select(view);
			viewDropdown.selectByIndex(1);			
			Thread.sleep(2000);
			WebElement view1=driver.findElement(By.name("fcf"));
			Select viewDropdown1=new Select(view1);
			viewDropdown1.selectByVisibleText("My Contacts");
			Thread.sleep(2000);
			//String recentContact=driver.findElement(By.xpath("//tr[contains(@class,'first')]/th/a")).getText();
			String recentContact=driver.findElement(By.xpath("(//div[@class='x-grid3-cell-inner x-grid3-col-FULL_NAME']/a/span)[1]")).getText();
			assertTrue(recentContact.equals("dkr"),"Name does not match");
			System.out.println("TC28 is passed");
			driver.close();
		}
		//TC 29 View a contact in the contact Page
		public static void viewContact() throws InterruptedException {
			driver=login();
			driver.findElement(By.linkText("Contacts")).click();	
			WebElement view=driver.findElement(By.id("fcf"));					
			Thread.sleep(1000);
			driver.findElement(By.xpath("//tr[contains(@class,'first')]/th/a")).click();
			Thread.sleep(2000);
			String details=driver.findElement(By.xpath("//td[@class='pbTitle']/h2[text()='Contact Detail']")).getText();
			assertTrue(details.equals("Contact Detail"),"Name does not match");
			System.out.println("TC29 is passed");
			driver.close();
		}
		
		//TC30 Check the Error message if, the required information is not entered while creating a New view in Contacts
		public static void contactViewError() throws InterruptedException {
			driver=login();
			driver.findElement(By.linkText("Contacts")).click();
			driver.findElement(By.linkText("Create New View")).click();			
			driver.findElement(By.id("devname")).sendKeys("EFGH");
			driver.findElement(By.xpath("(//td[@class='pbButtonb']/input[@title='Save'])[1]")).click();		
			Thread.sleep(2000);
			String errorMsgActual=driver.findElement(By.xpath("(//div[@class='requiredInput']/div[@class='errorMsg'])[1]")).getText();
			String errorMsgExpected="Error: You must enter a value";
			assertTrue(errorMsgExpected.equals(errorMsgActual), "Error message is not displayed");
			System.out.println("TC30 is passed");		
			driver.close();				
		}	
		
		//TC 31 Check the Cancel button works fine in Create New View
		public static void cancelContactView() throws InterruptedException {
			driver=login();
			driver.findElement(By.linkText("Contacts")).click();
			driver.findElement(By.linkText("Create New View")).click();			
			driver.findElement(By.id("fname")).sendKeys("ABCD");
			driver.findElement(By.id("devname")).sendKeys("EFGH");
			driver.findElement(By.xpath("(//td[@class='pbButtonb']/input[@title='Cancel'])[1]")).click();		
			Thread.sleep(2000);
			WebElement view=driver.findElement(By.id("fcf"));
			Select viewDropdown=new Select(view);
			WebElement selectedView=viewDropdown.getFirstSelectedOption();
			assertFalse(selectedView.getText().equals("ABCD"),"View is created!!");
			System.out.println("TC31 is passed");		
			driver.close();	
		}
		//TC32 Check the Save and New button works in New Contact page
		public static void saveAndNew() throws InterruptedException {
			driver=login();
			driver.findElement(By.linkText("Contacts")).click();
			driver.findElement(By.name("new")).click();
			driver.findElement(By.id("name_lastcon2")).sendKeys("dkr");
			driver.findElement(By.id("con4")).sendKeys("Auto");
			driver.findElement(By.className("lookupIcon")).click();
			Thread.sleep(4000);
			String parentWindow=driver.getWindowHandle();
			Set<String> handles=driver.getWindowHandles();
			for(String handle:handles) {
				driver.switchTo().window(handle);
			}
			Thread.sleep(2000);
			WebElement lookupFrame=driver.findElement(By.id("resultsFrame"));
			driver.switchTo().frame(lookupFrame);
			driver.findElement(By.xpath("//tr[@class='dataRow even first']//a")).click();
			Thread.sleep(2000);
			driver.switchTo().window(parentWindow);
			Thread.sleep(2000);
			driver.findElement(By.xpath("(//input[@title='Save & New'])[1]")).click();
			//driver.switchTo().defaultContent();
			Thread.sleep(2000);
			String contactName=driver.findElement(By.xpath("//div[@class='content']/h2")).getText();
			assertTrue(contactName.equals("New Contact"), "New contact page did not open");
			String recentContact=driver.findElement(By.xpath("(//span[@class='mruText'])[1]")).getText();
			assertTrue(recentContact.equals("dkr"), "New contact is not created");
			System.out.println("TC 32 is passed");
			driver.close();
			
		}
}
