package com.training.salesforce;

import static org.testng.Assert.assertTrue;

import java.awt.Desktop.Action;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class SalesforceRandomScenarios extends SalesforceValidLogin {
	
	public static void main(String[] args) {
		
		try {
			verifyUsername();
			//editLastName();
			//tabCustomization();
			//blockEvent();
			//blockRecurringEvent();
			
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
	}
	//TC 33 Verify if the firstname and lastname of the loggedin user is displayed
	public static void verifyUsername() throws InterruptedException {
		driver=login();
		driver.findElement(By.linkText("Home")).click();		
		Thread.sleep(2000);
		WebElement user=driver.findElement(By.xpath("//h1[@class='currentStatusUserName']/a"));
		String currentUser=user.getText();
		String expectedUser=driver.findElement(By.id("userNavLabel")).getText();
		assertTrue(expectedUser.equals(currentUser),"Username is incorrect");
		user.click();
		Thread.sleep(1000);	
		String currentUrl=driver.getCurrentUrl();
		System.out.println(currentUrl);
		assertTrue(currentUrl.contains("UserProfile"), "User Profile page is not displayed");
		System.out.println("TC33 is Passed");
		driver.close();
	} 
	
	//TC 34 Verify the edited lastname is updated at various places
	public static void editLastName() throws InterruptedException {
		driver=login();
		Thread.sleep(2000);
		driver.findElement(By.linkText("Home")).click();		
		Thread.sleep(2000);
		driver.findElement(By.xpath("//h1[@class='currentStatusUserName']/a")).click();
		String MyProfilePage=driver.getWindowHandle();
		driver.findElement(By.xpath("//img[@title='Edit Profile']")).click();
		Thread.sleep(2000);		
		WebElement editProfileFrame=driver.findElement(By.xpath("//div[@id='contactInfoInner']/iframe[@id='contactInfoContentId']"));
		driver.switchTo().frame(editProfileFrame);
		//System.out.println("Switched to frame "+editProfileFrame.toString());
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[contains(text(),'About')]")).click();
		String fName=driver.findElement(By.id("firstName")).getAttribute("value");
		//System.out.println("fName is "+fName);
		WebElement lastName=driver.findElement(By.id("lastName"));		
		lastName.clear();
		lastName.sendKeys("Abcd");
		String lName=lastName.getAttribute("value");
		String expectedName=fName+' '+lName;
		//System.out.println(expectedName);
		driver.findElement(By.xpath("//input[@value='Save All']")).click();	
		Thread.sleep(3000);
		driver.switchTo().window(MyProfilePage);
		Thread.sleep(4000);
		String actualName=driver.findElement(By.id("tailBreadcrumbNode")).getText();
		//System.out.println(actualName);
		assertTrue(actualName.trim().equals(expectedName.trim()), "Username is not as expected");
		String userMenuName=driver.findElement(By.id("userNavLabel")).getText();
		//System.out.println(userMenuName);
		assertTrue(expectedName.trim().equals(userMenuName.trim()), "Username is not as expected");
		driver.findElement(By.linkText("Home")).click();		
		Thread.sleep(2000);
		String currentUser=driver.findElement(By.xpath("//h1[@class='currentStatusUserName']/a")).getText();
		assertTrue(expectedName.trim().equals(currentUser.trim()), "Username is not as expected");
		System.out.println("TC34 is Passed");
		driver.close();
	}
	//TC35 Verify the tab customization
	public static void tabCustomization() throws InterruptedException {
		driver=login();
		Thread.sleep(2000);
		driver.findElement(By.className("allTabsArrow")).click();
		Thread.sleep(2000);
		driver.findElement(By.name("customize")).click();
		Thread.sleep(2000);
		WebElement selectTabs=driver.findElement(By.id("duel_select_1"));
		Select tabsDropdown=new Select(selectTabs);
		tabsDropdown.selectByVisibleText("Libraries");
		driver.findElement(By.xpath("//a[@id='duel_select_0_left']/img")).click();
		driver.findElement(By.xpath("//input[@title='Save']")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("userNavLabel")).click();
		driver.findElement(By.linkText("Logout")).click();
		driver.close();
		driver=login();
		Thread.sleep(2000);
		List<WebElement> tabList=driver.findElements(By.xpath("//ul[@id='tabBar']/li"));
		Boolean flag=true;
		for(WebElement element:tabList) {
			if(element.getText().equals("Libraries")) {
				System.out.println("TC35 is failed");
				flag=false;
				break;
			}
		}
		if(flag) {
			System.out.println("TC35 is Passed");
		}
		driver.close();
		
	}
	//TC36 Blocking an event in the calendar
	public static void blockEvent() throws InterruptedException {
		driver=login();
		Thread.sleep(2000);
		driver.findElement(By.linkText("Home")).click();		
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[@class='pageDescription']/a")).click();
		driver.findElement(By.xpath("//div[@id='p:f:j_id25:j_id61:28:j_id64']/a")).click();
		Thread.sleep(2000);
		driver.findElement(By.className("comboboxIcon")).click();
		String parentWindow=driver.getWindowHandle();
		Set<String> handles=driver.getWindowHandles();
		for(String handle:handles) {
			driver.switchTo().window(handle);
		}
		Thread.sleep(2000);
		driver.findElement(By.xpath("//li[@class='listItem4']/a")).click();
		Thread.sleep(2000);
		driver.switchTo().window(parentWindow);
		driver.findElement(By.id("EndDateTime_time")).click();
		driver.findElement(By.id("timePickerItem_42")).click();
		driver.findElement(By.name("save")).click();
		Thread.sleep(2000);
		String text=driver.findElement(By.xpath("//a[@title='Busy - Other']/span")).getText();
		assertTrue(text.equals("Other"),"Event does not match");
		System.out.println("TC36 is Passed");
		driver.close();		
	}
	//TC37 Blocking an event in the calender with weekly recurrance
		public static void blockRecurringEvent() throws InterruptedException {
			driver=login();
			Thread.sleep(2000);
			driver.findElement(By.linkText("Home")).click();		
			Thread.sleep(2000);
			driver.findElement(By.xpath("//span[@class='pageDescription']/a")).click();
			driver.findElement(By.xpath("//div[@id='p:f:j_id25:j_id61:20:j_id64']/a")).click();
			Thread.sleep(2000);
			driver.findElement(By.className("comboboxIcon")).click();
			String parentWindow=driver.getWindowHandle();
			Set<String> handles=driver.getWindowHandles();
			for(String handle:handles) {
				driver.switchTo().window(handle);
			}
			Thread.sleep(2000);
			driver.findElement(By.xpath("//li[@class='listItem4']/a")).click();
			Thread.sleep(2000);
			driver.switchTo().window(parentWindow);
			driver.findElement(By.id("EndDateTime_time")).click();
			driver.findElement(By.id("timePickerItem_38")).click();
			driver.findElement(By.id("IsRecurrence")).click();
			driver.findElement(By.id("rectypeftw")).click();
			driver.findElement(By.id("16")).click();						
			String startDate=driver.findElement(By.id("RecurrenceStartDateTime")).getAttribute("value");
			System.out.println(startDate);
			LocalDate date=LocalDate.now();
			LocalDate endDate=date.plusDays(14);
			DateTimeFormatter formatter=DateTimeFormatter.ofPattern("MM/dd/yyyy");
			String formattedEndDate=endDate.format(formatter);
			System.out.println(formattedEndDate);
			driver.findElement(By.id("RecurrenceEndDateOnly")).sendKeys(formattedEndDate);
			driver.findElement(By.name("save")).click();
			/*Thread.sleep(15000);			
			Set<String> windowHandles=driver.getWindowHandles();
			for(String windowHandle:windowHandles) {
				driver.switchTo().window(windowHandle);	
				System.out.println(driver.getTitle());
			}
			Thread.sleep(3000);			
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("dismiss_all")))).click();*/
			driver.switchTo().defaultContent();
			Thread.sleep(3000);
			String text1=driver.findElement(By.xpath("//a[@title='Busy - Other']/span")).getText();		
			assertTrue(text1.equals("Other"),"Event does not match");
			String text=driver.findElement(By.xpath("(//div[@class='multiLineEventBlock dragContentPointer']/span/img)[1]")).getAttribute("alt");
			assertTrue(text.equals("Recurring Event"),"Event does not match");			
			driver.findElement(By.xpath("//a[@title='Month View']/img")).click();
			WebElement eventIcon=driver.findElement(By.xpath("//span[@class='eventIcon']/img"));						
			String event=eventIcon.getAttribute("title");			
			//System.out.println(event);
			assertTrue(event.equals("Recurring Event"),"Recurring Event is not created");
			Actions mouseEvent =new Actions(driver);
			mouseEvent.moveToElement(eventIcon);
			Thread.sleep(2000);
			System.out.println("TC37 is Passed");
			driver.close();		
		}
	
}
