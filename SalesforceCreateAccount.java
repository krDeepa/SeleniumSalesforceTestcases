package com.training.salesforce;

import static org.testng.Assert.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class SalesforceCreateAccount extends SalesforceValidLogin  {

	public static void main(String[] args)  {
		
		try {
			//createAccount();
			//createView();
			//editView();
			//mergeAccounts();
			accountReport();
			
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}

	}
	//TC10 CreateAccount
	public static void createAccount() throws InterruptedException {
	
			driver=login();
			driver.findElement(By.linkText("Accounts")).click();
			driver.findElement(By.id("createNew")).click();
			driver.findElement(By.linkText("Account")).click();
			String accntName="AutoAccount"+System.currentTimeMillis();
			driver.findElement(By.id("acc2")).sendKeys(accntName);
			WebElement typeDropdown=driver.findElement(By.id("acc6"));
			Select type=new Select(typeDropdown);
			type.selectByVisibleText("Technology Partner");
			WebElement priorityDropdown=driver.findElement(By.id("00Nbm00003QKyFe"));
			Select priority=new Select(priorityDropdown);
			priority.selectByValue("High");
			driver.findElement(By.xpath("//td[@id='topButtonRow']/input[@title='Save']")).click();	
			WebElement newAccnt=driver.findElement(By.xpath("//div[@class='content']/h2"));
			if(newAccnt.getText().equals(accntName)) {
				System.out.println("TC10 is passed");
			}
			Thread.sleep(2000);
			driver.close();			
	}	
	//TC11 Createnewview
	public static void createView() throws InterruptedException {
		
		driver=login();
		driver.findElement(By.linkText("Accounts")).click();
		Thread.sleep(2000);
		String AccountWindow=driver.getWindowHandle();
		driver.findElement(By.linkText("Create New View")).click();		
		String viewName="AutoView"+ System.currentTimeMillis();
		driver.findElement(By.id("fname")).sendKeys(viewName);
		String uniViewName="UniqueAutoView";
		driver.findElement(By.id("devname")).sendKeys(uniViewName);
		driver.findElement(By.name("save")).click();
		Thread.sleep(3000);
		driver.switchTo().window(AccountWindow);			
		Thread.sleep(3000);
		WebElement view=driver.findElement(By.xpath("//select[@title='View:']"));		
		Select viewDropdown=new Select(view);
		String displayedView=viewDropdown.getFirstSelectedOption().getText();
		//System.out.println(displayedView);
		if(displayedView.equals(viewName)) {
			System.out.println("TC11 is passed");
		}
		
		driver.close();		
	}
	
	//TC12 Editview
	public static void editView() throws InterruptedException {
		driver=login();
		driver.findElement(By.linkText("Accounts")).click();
		Thread.sleep(2000);
		String AccountWindow=driver.getWindowHandle();
		WebElement view=driver.findElement(By.xpath("//select[@title='View:']"));		
		Select viewDropdown=new Select(view);
		viewDropdown.selectByIndex(4);
		Thread.sleep(2000);
		driver.findElement(By.linkText("Edit")).click();
		String viewName="AutoView"+ System.currentTimeMillis();
		WebElement viewNameBox=driver.findElement(By.id("fname"));
		viewNameBox.clear();
		viewNameBox.sendKeys(viewName);
		WebElement field=driver.findElement(By.id("fcol1"));
		Select fieldDropdown=new Select(field);
		fieldDropdown.selectByVisibleText("Account Name");
		WebElement operator=driver.findElement(By.id("fop1"));
		Select operatorDropdown=new Select(operator);
		operatorDropdown.selectByVisibleText("contains");
		WebElement value=driver.findElement(By.id("fval1"));
		value.clear();
		value.sendKeys("a");
		WebElement fieldsToDisplay=driver.findElement(By.id("colselector_select_0"));
		Select fieldsToDisplayDropdown=new Select(fieldsToDisplay);
		fieldsToDisplayDropdown.selectByVisibleText("Last Activity");
		driver.findElement(By.xpath("//a[@id='colselector_select_0_right']/img[@class='rightArrowIcon']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//input[@value=' Save '])[2]")).click();
		Thread.sleep(3000);
		driver.switchTo().window(AccountWindow);			
		Thread.sleep(3000);
		WebElement view1=driver.findElement(By.xpath("//select[@title='View:']"));		
		Select viewDropdown1=new Select(view1);
		String displayedView=viewDropdown1.getFirstSelectedOption().getText();
		//System.out.println(displayedView);
		assertTrue(displayedView.equals(viewName), "View name does not match");
		boolean isDisplayed=driver.findElement(By.xpath("//div[@title='Last Activity']")).isDisplayed();
		if(isDisplayed) {
			System.out.println("Last Activity column is added in the view");			
		}
		java.util.List<WebElement> accountNames=driver.findElements(By.xpath("//div[@class='x-grid3-cell-inner x-grid3-col-Name']/a/span"));
		boolean flag=true;
		for(WebElement element:accountNames) {
			
			if(!(element.getText().contains("a") || element.getText().contains("A"))) {
				System.out.println(element.getText());
				System.out.println("Account name does not contain a!!");
				flag=false;
			}			
		}
		if(flag) {
			System.out.println("TC12 is passed");
		}
		driver.close();
	}
	
	//TC13 MergeAccounts
	public static void mergeAccounts() throws InterruptedException {		
		createAccount();
		createAccount();
		driver=login();
		driver.findElement(By.linkText("Accounts")).click();
		Thread.sleep(2000);
		String AccountWindow=driver.getWindowHandle();
		driver.findElement(By.linkText("Merge Accounts")).click();
		driver.findElement(By.id("srch")).sendKeys("Auto");
		driver.findElement(By.xpath("//input[@value='Find Accounts']")).click();
		Thread.sleep(2000);		
		driver.findElement(By.id("cid0")).click();
		driver.findElement(By.id("cid1")).click();
		String toBeMerged=driver.findElement(By.xpath("(//tr[@class='dataRow even first']/td)[1]")).getText();
		driver.findElement(By.xpath("(//input[@title='Next'])[2]")).click();
		driver.findElement(By.xpath("(//input[@title='Merge'])[1]")).click();
		driver.switchTo().alert().accept();
		Thread.sleep(3000);
		driver.switchTo().window(AccountWindow);
		Thread.sleep(2000);
		WebElement merAccountName=driver.findElement(By.xpath("(//table[@class='list']//th[@scope='row']/a)[1]"));		
		String mergedAccount=merAccountName.getText();
		//System.out.println(mergedAccount);
		//String mergedAccount=driver.findElement(By.xpath("//a[@accesskey='1']/span")).getText(); //recent items
		assertTrue(toBeMerged.equals(mergedAccount), "Account names do not match!");	
		System.out.println("TC 13 is Passed");
		driver.close();
	}
	
	//TC14 Create account report
	public static void accountReport() throws InterruptedException {
		driver=login();
		driver.findElement(By.linkText("Accounts")).click();
		Thread.sleep(2000);
		driver.findElement(By.linkText("Accounts with last activity > 30 days")).click();
		Thread.sleep(2000);	
		//WebElement dateField=driver.findElement(By.id("ext-gen148"));
		WebElement dateFieldIcon=driver.findElement(By.id("ext-gen149"));
		dateFieldIcon.click();		
		explicitWait(driver.findElement(By.xpath("(//div[@class='x-combo-list-inner']//div[@class='x-combo-list-item'])[1]")), 4);
		WebElement createdDate=driver.findElement(By.xpath("(//div[@class='x-combo-list-inner']//div[@class='x-combo-list-item'])[1]"));
		createdDate.click();
		//dateField.sendKeys(createdDate.getText());
		LocalDate today=LocalDate.now();		
		//dd (Lowercase): Represents the day of the month (01–31)
		//DD(Uppercase): Represents the day of the year (001–366, often called the Julian day).
		DateTimeFormatter formatter=DateTimeFormatter.ofPattern("MM/dd/yyyy");
		String formattedDate=today.format(formatter);
		//System.out.println(formattedDate);
		WebElement startDate=driver.findElement(By.name("startDate"));
		startDate.sendKeys(formattedDate);
		startDate.sendKeys(Keys.TAB);
		WebElement endDate=driver.findElement(By.name("endDate"));
		endDate.sendKeys(formattedDate);
		endDate.sendKeys(Keys.TAB);
		driver.findElement(By.xpath("(//button[text()='Save'])[1]")).click();	
		//String ParentWindow=driver.getWindowHandle();
		Set <String> handles=driver.getWindowHandles();
		for(String str:handles) {
			driver.switchTo().window(str);
		}
		Thread.sleep(2000);
		String reportNameExpected="LastActivity"+System.currentTimeMillis();
		driver.findElement(By.id("saveReportDlg_reportNameField")).sendKeys(reportNameExpected);		
		driver.findElement(By.id("saveReportDlg_DeveloperName")).sendKeys(reportNameExpected);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[text()='Save and Run Report']")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div[@id='stateCountryPicklistWarning_buttons']/input[1]")).click();
		//Thread.sleep(2000);
		driver.switchTo().defaultContent();
		Thread.sleep(2000);
		String reportNameActual=driver.findElement(By.xpath("//div[@class='content']/h1")).getText();
		//System.out.println(reportNameActual);
		assertTrue(reportNameExpected.equals(reportNameActual), "Report names do not match");
		System.out.println("TC14 is passed");
		driver.close();
	}

}
