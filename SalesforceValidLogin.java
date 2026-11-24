package com.training.salesforce;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SalesforceValidLogin {
	static WebDriver driver;
	static WebDriverWait wait;
	
	public static void explicitWait(WebElement element, int time) {
		wait=new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public static WebDriver login() throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		
		//Workaround by saving the chrome profile to avoid OTP trigger.
		ChromeOptions options=new ChromeOptions();
		// 1. Point to the root "User Data" directory
        //options.addArguments("user-data-dir=C:\\Users\\dkrde\\AppData\\Local\\Google\\Chrome\\User Data");
        options.addArguments("--user-data-dir=C:\\SeleniumChromeProfile");
        // 2. Specify the target profile directory,Use "Default" or "Profile 1", "Profile 2", etc. depending on your target profile
        options.addArguments("profile-directory=Profile 2");
        // 3. Essential stability flags for custom profiles / sandboxing
       // options.addArguments("--no-sandbox");
       // options.addArguments("--disable-dev-shm-usage");
        // Optional: Avoid "invalid argument: user data directory is already in use" bugs
        //options.addArguments("--remote-allow-origins=*");
		driver=new ChromeDriver(options);
		
		//driver=new ChromeDriver();
		driver.get("https://login.salesforce.com/?login=1");
		driver.manage().window().maximize();
		//WebElement logInwithUserName=driver.findElement(By.xpath("//a[text()='Log In with Username']"));
		//logInwithUserName.click();
		//Thread.sleep(2000);
		WebElement username=driver.findElement(By.id("username"));
		username.sendKeys("dkrdeepa.d7ac923d3afe@agentforce.com");
		WebElement loginButton=driver.findElement(By.id("Login"));
		loginButton.click();		
		driver.findElement(By.id("password")).sendKeys("Salesforce1");
		WebElement login1=driver.findElement(By.id("Login"));
		login1.click();
		//Thread.sleep(16000);
		//driver.findElement(By.id("RememberDeviceCheckbox")).click();
		//driver.findElement(By.id("save")).click();	
		Thread.sleep(2000);		
		return driver;
	}
	public static void captureScreenshot(WebDriver driver,String method) {		
		TakesScreenshot screenshot=(TakesScreenshot)driver;
		File srcFile=screenshot.getScreenshotAs(OutputType.FILE);		
		 //DateTimeFormatter and LocalDateTime are thread-safe and accurate
        String timeStamp1=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));        
		String imagePath="Screenshorts\\screenshot"+"_"+timeStamp1+"_" +method+ ".jpg";
		File destFile=new File(imagePath);		
		try {
			FileUtils.copyFile(srcFile, destFile);
			
		} catch (IOException e) {			
			e.printStackTrace();
		}	
	}


	
}
