package com.training.salesforce;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;



public class SalesforceUserMenu extends SalesforceValidLogin {		
	
	public static void main(String[] args)  {
		//SalesforceUserMenu SaleObj=new SalesforceUserMenu();
		//SaleObj.login();		
		//SaleObj.userMenu();		
		try {
			//userMenu();
			myProfile();
			//mySetting();
			//developerConsole();
			//logout();
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}		
	}	
	
	/*TC 05 Select user menu for <username> drop down */
	public static void userMenu() throws InterruptedException {	
		driver=login();
		LinkedList<String> expectedMenu=new LinkedList<String>(Arrays.asList("My Profile","My Settings","Developer Console","Switch to Lightning Experience","Logout"));
		WebElement userName= driver.findElement(By.id("userNavLabel"));
		userName.click();
		Thread.sleep(1000);	
		String myProfile=driver.findElement(By.xpath("//a[text()='My Profile']")).getText();
		Assert.assertEquals(myProfile,expectedMenu.get(0),"UserMenu options are not as expected");	
		String mySettings=driver.findElement(By.xpath("//a[text()='My Settings']")).getText();
		Assert.assertEquals(mySettings,expectedMenu.get(1),"UserMenu options are not as expected");
		String console=driver.findElement(By.xpath("//a[text()='Developer Console']")).getText();
		Assert.assertEquals(console,expectedMenu.get(2),"UserMenu options are not as expected");
		String mode=driver.findElement(By.xpath("//a[text()='Switch to Lightning Experience']")).getText();
		Assert.assertEquals(mode,expectedMenu.get(3),"UserMenu options are not as expected");
		String logout=driver.findElement(By.xpath("//a[text()='Logout']")).getText();
		Assert.assertEquals(logout,expectedMenu.get(4),"UserMenu options are not as expected");
		System.out.println("TC 05 is passed");
		driver.close();		
	}
	/* TC 06 Select "My Profile" option from user menu for <username> drop down*/
	public static void myProfile() throws InterruptedException {
		driver=login();
		Thread.sleep(2000);
		WebElement userName= driver.findElement(By.id("userNavLabel"));
		if(userName.getText().contains("Deepa")) {
			System.out.println("Username at user menu is validated successfully");
		}
		userName.click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[@title='My Profile']")).click();
		Thread.sleep(1000);
		
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
		lastName.sendKeys("K.R.");
		String lName=lastName.getAttribute("value");
		//System.out.println("lName is "+lName);
		driver.findElement(By.xpath("//input[@value='Save All']")).click();	
		Thread.sleep(2000);
		driver.switchTo().window(MyProfilePage);
		Thread.sleep(3000);
		//System.out.println(driver.getTitle());	
		WebElement nameElement=driver.findElement(By.xpath("//div[@class='chatterBreadcrumbs']/span[3]"));		
		String name=nameElement.getText();
		System.out.println("Actual name "+name);
		System.out.println("Expected name "+(fName+' '+lName));
		if(name.trim().equals(fName+' '+lName)) {
		System.out.println("User name is as excpected");
		}	
		//Post 	
		driver.findElement(By.xpath("//span[text()='Post']")).click();	
		Thread.sleep(2000);	
		WebElement iframe1=driver.findElement(By.xpath("//iframe[@class='cke_wysiwyg_frame cke_reset']"));
		driver.switchTo().frame(iframe1);
		
		//System.out.println("Switched to frame " +iframe1.toString());		
		WebElement postShareBox=driver.findElement(By.xpath("//body[contains(text(),'Share')]"));
		postShareBox.click();
		postShareBox.sendKeys("Posting text through Selenium automation");		
		
		driver.switchTo().window(MyProfilePage);		
		//JavascriptExecutor javaScExe=(JavascriptExecutor) driver;
		//javaScExe.executeScript("window.scrollTo(0,0);");
		Thread.sleep(3000);		
		WebElement shareButton=driver.findElement(By.id("publishersharebutton"));
		//javaScExe.executeScript("arguments[0].click();", shareButton);
		shareButton.click();
		Thread.sleep(2000);	
		String postText=driver.findElement(By.xpath("(//div[@class='cxfeeditemtextwrapper']/div[1]/span/p)[1]")).getText();		
		if(postText.equals("Posting text through Selenium automation")) {
			System.out.println("Post is successully validated");
		}
		//Test step - upload a file
		driver.findElement(By.xpath("//span[text()='File']")).click();		
		driver.findElement(By.linkText("Upload a file from your computer")).click();
		WebElement chooseFile=driver.findElement(By.xpath("//div[@class='requiredInput']/input[@type='file']"));
		File fileToUpload=new File("src/test/resources/testfile.txt");
		String fileAbsPath=fileToUpload.getAbsolutePath();
		//System.out.println(fileAbsPath);
		chooseFile.sendKeys(fileAbsPath);		
		WebElement fileShareButton=driver.findElement(By.id("publishersharebutton"));
		fileShareButton.click();
		Thread.sleep(5000);
		String fileUploaded=driver.findElement(By.xpath("//div[@class='preamblecontainer displayblock']/span[contains(text(),'posted a file.')]")).getText();		
		if(fileUploaded.contains("posted a file.")) {
			System.out.println("File is successully uploaded");
		}
		
		// Test step - upload a photo
		WebElement profilePhoto=driver.findElement(By.xpath("//div[@id='photoSection']/span[@id='displayBadge']"));
		Actions action=new Actions(driver);	
		action.moveToElement(profilePhoto).build().perform();		
		WebElement update=driver.findElement(By.xpath("//a[text()='Update']"));
		explicitWait(update, 5);			
		action.moveToElement(update).click().build().perform();		
		Thread.sleep(4000);
		//WebElement uploadPhotoFrame=driver.findElement(By.xpath("//iframe[@title='sessionserver']"));
		//driver.switchTo().frame(uploadPhotoFrame);
		//Thread.sleep(2000);
		WebElement chooseFileFrame=driver.findElement(By.id("uploadPhotoContentId"));
		driver.switchTo().frame(chooseFileFrame);
		WebElement choosePhoto=driver.findElement(By.xpath("//form[@method='post']/input[@class='fileInput']"));
		File picToUpload=new File("src/test/resources/DKR.png");
		String photoAbsPath=picToUpload.getAbsolutePath();
		choosePhoto.sendKeys(photoAbsPath);
		//wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("(//div[@class='uploadButtonPanel']/input[@value='Save'])[2]")))).click();
		WebElement saveButton=driver.findElement(By.xpath("(//div[@class='uploadButtonPanel']/input[@value='Save'])[2]"));
		explicitWait(saveButton, 8);
		//JavascriptExecutor javaScExe=(JavascriptExecutor) driver;
		//javaScExe.executeScript("arguments[0].click();", saveButton);		
		saveButton.click();
		Thread.sleep(3000);
		WebElement saveAgain=driver.findElement(By.xpath("(//div[@class='uploadButtonPanel']/input[@value='Save'])[1]"));
		explicitWait(saveAgain, 6);
		saveAgain.click();
		Thread.sleep(4000);
		System.out.println("TC06 is Passed");
		driver.close();
	}
	/*TC 07 Select "My settings" option from user menu for <username> drop down*/
	public static void mySetting() throws InterruptedException {
		driver=login();
		//Thread.sleep(1000);
		WebElement userName= driver.findElement(By.id("userNavLabel"));
		userName.click();		
		driver.findElement(By.xpath("//a[@title='My Settings']")).click();
		Thread.sleep(1000);		
		driver.findElement(By.id("PersonalInfo_font")).click();
		driver.findElement(By.id("LoginHistory_font")).click();
		driver.findElement(By.xpath("//a[contains(text(),'login history')]")).click();
		//need to verify if download was successful.
		driver.findElement(By.id("PersonalInfo_font")).click();
		driver.findElement(By.id("DisplayAndLayout_font")).click();
		driver.findElement(By.id("CustomizeTabs_font")).click();
		WebElement customAppDropdown=driver.findElement(By.id("p4"));
		Select dropdownOptions=new Select(customAppDropdown);
		dropdownOptions.selectByVisibleText("Salesforce Chatter");
		WebElement availableTabs=driver.findElement(By.id("duel_select_0"));
		Select availableTabsOptions=new Select(availableTabs);
		availableTabsOptions.selectByVisibleText("Reports");
		driver.findElement(By.xpath("//a[@id='duel_select_0_right']/img")).click();
		Thread.sleep(1000);
		WebElement optionsReport=driver.findElement(By.xpath("//select[@id='duel_select_1']/option[text()='Reports']"));
		String tabName=optionsReport.getAttribute("value");
		//System.out.println(tabName);
		if(tabName.equalsIgnoreCase("Report")) {
			System.out.println("Reports is added in Selected Tabs list");
		}
		driver.findElement(By.xpath("//td[@id='bottomButtonRow']/input[@name='save']")).click();
		
		
		driver.findElement(By.id("tsidButton")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[text()='Salesforce Chatter']")).click();
		Thread.sleep(2000);
		String reportTabSC=driver.findElement(By.xpath("//a[text()='Reports']")).getText();
		if(reportTabSC.equals("Reports")) {
			System.out.println("Reports tab is present in Sales Chatter page");
		}
		driver.findElement(By.id("tsidButton")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[text()='Sales']")).click();
		Thread.sleep(2000);
		String reportTabSales=driver.findElement(By.xpath("//a[text()='Reports']")).getText();
		if(reportTabSales.equals("Reports")) {
			System.out.println("Reports tab is present in Sales page");
		}
		driver.findElement(By.id("tsidButton")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[text()='Marketing CRM Classic']")).click();
		Thread.sleep(2000);
		String reportTabMC=driver.findElement(By.xpath("//a[text()='Reports']")).getText();
		if(reportTabMC.equals("Reports")) {
			System.out.println("Reports tab is present in Marketing CRM Classic page");
		}
		driver.findElement(By.id("tsidButton")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[text()='Content']")).click();
		Thread.sleep(2000);
		String reportTabContent=driver.findElement(By.xpath("//a[text()='Reports']")).getText();
		if(reportTabContent.equals("Reports")) {
			System.out.println("Reports tab is present in Content page");
		}
		
		WebElement userName1= driver.findElement(By.id("userNavLabel"));
		userName1.click();		
		driver.findElement(By.xpath("//a[@title='My Settings']")).click();
		Thread.sleep(1000);		
		driver.findElement(By.id("EmailSetup_font")).click();
		driver.findElement(By.id("EmailSettings_font")).click();
		driver.findElement(By.id("sender_name")).clear();
		driver.findElement(By.id("sender_name")).sendKeys("Deepa");
		driver.findElement(By.id("sender_email")).clear();
		driver.findElement(By.id("sender_email")).sendKeys("deepa@abc.com");
		driver.findElement(By.id("auto_bcc1")).click();
		driver.findElement(By.xpath("//td[@id='bottomButtonRow']/input[@name='save']")).click();
		Thread.sleep(1000);
		driver.switchTo().alert().accept();
		Thread.sleep(1000);
		driver.findElement(By.id("EmailSetup_font")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("CalendarAndReminders_font")).click();		
		driver.findElement(By.id("Reminders_font")).click();
		driver.findElement(By.id("testbtn")).click();	
		Thread.sleep(2000);
		Set <String> handles=driver.getWindowHandles();
		for(String handle:handles) {
			driver.switchTo().window(handle);
		}		
		driver.findElement(By.id("dismiss_all")).click();
		System.out.println("TC07 Passed");
		driver.quit();
		
	}
	/*TC 08 Select "Developers Console" option from user menu for <username> drop down*/
	public static void developerConsole() throws InterruptedException {
		driver=login();	
		WebElement userName= driver.findElement(By.id("userNavLabel"));
		explicitWait(userName,3);
		userName.click();
		driver.findElement(By.xpath("//a[text()='Developer Console']")).click();
		Set<String> handles= driver.getWindowHandles();
		for(String handle:handles) {
			driver.switchTo().window(handle);
		}
		Thread.sleep(5000);
		driver.findElement(By.xpath("//span[text()='Progress']")).click();
		Thread.sleep(1000);
		driver.close();
		System.out.println("TC08 Passed");
		driver.quit();
	}
	/* TC 09 Select "Logout" option from user menu for <username> drop down*/
	public static void logout() throws InterruptedException {
		driver=login();
		//Thread.sleep(2000);
		WebElement userName= driver.findElement(By.id("userNavLabel"));
		userName.click();		
		driver.findElement(By.xpath("//a[text()='Logout']")).click();
		Thread.sleep(2000);
		WebElement loginText=driver.findElement(By.xpath("//h1[text()='Salesforce login']"));
		if(loginText.getText().equals("Salesforce login")) {
			System.out.println("TC 09 Passed");
		}
		driver.close();
	}

}
