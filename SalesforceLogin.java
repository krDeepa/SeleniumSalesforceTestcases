package com.training.salesforce;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


import io.github.bonigarcia.wdm.WebDriverManager;

public class SalesforceLogin {
	public static WebDriver driver;
	final static String url="https://login.salesforce.com";	
	final String user="dkrdeepa.d7ac923d3afe@agentforce.com";
	
	/*TC1 Login Error Message - 1*/
	public void loginError() throws InterruptedException {
		WebDriverManager.chromedriver().setup();		
		WebDriver driver=new ChromeDriver();		
		driver.get(url);
		driver.manage().window().maximize();
		WebElement username=driver.findElement(By.id("username"));
		username.sendKeys(user);
		WebElement login=driver.findElement(By.id("Login"));
		login.click();
		WebElement login1=driver.findElement(By.id("Login"));
		login1.click();
		WebElement errorMsg=driver.findElement(By.id("error"));
		if(errorMsg.getText().equals("Error: Please enter your password.")) {
			System.out.println("TC1 is passed");
		}
		driver.close();
	}
	/*TC2 Login To SalesForce -2*/
	public void loginToSalesforce() throws InterruptedException {
		WebDriverManager.chromedriver().setup();		
		WebDriver driver=new ChromeDriver();		
		driver.get(url);
		driver.manage().window().maximize();
		//WebElement logInwithUserName=driver.findElement(By.xpath("//a[text()='Log In with Username']"));
		//logInwithUserName.click();
		//Thread.sleep(2000);
		WebElement username=driver.findElement(By.id("username"));
		username.sendKeys(user);
		WebElement login=driver.findElement(By.id("Login"));
		login.click();			
		driver.findElement(By.id("password")).sendKeys("Salesforce1");
		WebElement login1=driver.findElement(By.id("Login"));
		login1.click();
		Thread.sleep(16000);
		driver.findElement(By.id("RememberDeviceCheckbox")).click();
		driver.findElement(By.id("save")).click();	
		System.out.println("TC2 is passed");
		driver.close();
		
	}
	/*TC3 Check RemeberMe - 3 */
	public void CheckWithRemMe() throws InterruptedException {
		WebDriverManager.chromedriver().setup();		
		WebDriver driver=new ChromeDriver();		
		driver.get(url);
		driver.manage().window().maximize();
		//WebElement logInwithUserName=driver.findElement(By.xpath("//a[text()='Log In with Username']"));
		//logInwithUserName.click();
		Thread.sleep(2000);
		WebElement username=driver.findElement(By.id("username"));		
		username.sendKeys(user);
		WebElement login=driver.findElement(By.id("Login"));
		login.click();
		WebElement remember=driver.findElement(By.name("rememberUn"));
		remember.click();
		driver.findElement(By.id("password")).sendKeys("Salesforce1");
		WebElement login1=driver.findElement(By.id("Login"));
		login1.click();
		Thread.sleep(14000);
		//driver.findElement(By.id("RememberDeviceCheckbox")).click();
		driver.findElement(By.id("save")).click();
		Thread.sleep(4000);
		driver.findElement(By.id("userNavLabel")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[text()='Logout']")).click();
		Thread.sleep(3000);
		WebElement savedUser=driver.findElement(By.xpath("//span[@id='idcard-identity']"));		
		if(savedUser.getText().equals(user))
		{
			System.out.println("TC3 is passed");			
		}		
		driver.close();
	}
	/*Forgot Password- 4 A*/
	public void forgotPwdA() throws InterruptedException {
		WebDriverManager.chromedriver().setup();		
		WebDriver driver=new ChromeDriver();		
		driver.get(url);
		driver.manage().window().maximize();
		//WebElement logInwithUserName=driver.findElement(By.xpath("//a[text()='Log In with Username']"));
		//logInwithUserName.click();
		//Thread.sleep(2000);
		driver.findElement(By.linkText("Forgot Your Password?")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("un")).sendKeys(user);
		driver.findElement(By.id("continue")).click();
		Thread.sleep(4000);
		if(driver.findElement(By.id("header")).getText().equals("Check Your Email")) {
			Thread.sleep(1000);
			driver.findElement(By.xpath("//a[contains(text(),'Return to Login')]")).click();
			System.out.println("TC 4A is passed");
		}
		driver.close();
	}
	/*Forgot Password- 4 B */
	public void forgotPwdB() throws InterruptedException {
		WebDriverManager.chromedriver().setup();		
		WebDriver driver=new ChromeDriver();
		driver.get(url);
		driver.manage().window().maximize();
		WebElement username=driver.findElement(By.id("username"));		
		username.sendKeys(user);
		WebElement login=driver.findElement(By.id("Login"));
		login.click();
		driver.findElement(By.id("password")).sendKeys("user123");
		WebElement login1=driver.findElement(By.id("Login"));
		login1.click();
		Thread.sleep(1000);
		WebElement errorMsg=driver.findElement(By.id("error"));
		if(errorMsg.getText().equals("Error: Please check your username and password. If you still can't log in, contact your Salesforce administrator.")) {
			System.out.println("TC 4B is passed");
		}
		//driver.close();		
	}

	public static void main(String[] args) throws InterruptedException {
		
		SalesforceLogin L=new SalesforceLogin();
		//L.loginError();
		//L.loginToSalesforce();
		L.CheckWithRemMe();
		//L.forgotPwdA();
		//L.forgotPwdB();
	}
}
