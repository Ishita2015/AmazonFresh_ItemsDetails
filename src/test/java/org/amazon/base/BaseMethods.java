package org.amazon.base;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.amazon.library.PropertyReader;
import org.amazon.pages.HomePage;
import org.amazon.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

public class BaseMethods {
	
	public WebDriver driver;
	public LoginPage login;
	public HomePage home;
	
	@BeforeClass
	public void startBrowser() throws IOException {
		
		try {
			System.setProperty("webdriver.chrome.driver", "./Driver/chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.get(PropertyReader.configReader("URL"));
			driver.manage().window().maximize();
			
			login = PageFactory.initElements(driver, LoginPage.class);
			home = PageFactory.initElements(driver, HomePage.class);
	    }
		catch (Exception e) {
			System.out.println("---Exception occured during starting browser--- " + e);
		}
	}
	
	@AfterClass
	public void closeBrowser() {
		driver.quit();
	}
}
