package com.webautomation.amazonfresh.base;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.webautomation.amazonfresh.library.PropertyReader;
import com.webautomation.amazonfresh.pages.HomePage;
import com.webautomation.amazonfresh.pages.LoginPage;

public class BaseMethods {
	
	private WebDriver driverBase;
	private LoginPage login;
	private HomePage home;
	
	@BeforeClass
	public void startBrowser() throws IOException {
		try {
			System.setProperty("webdriver.chrome.driver", "./Driver/chromedriver.exe");
			driverBase = new ChromeDriver();
			driverBase.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			driverBase.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driverBase.get(PropertyReader.configReader("URL"));
			driverBase.manage().window().maximize();
			
			setLogin(PageFactory.initElements(driverBase, LoginPage.class));
			setHome(PageFactory.initElements(driverBase, HomePage.class));
	    }
		catch (Exception e) {
			System.out.println("---Exception occured during starting browser--- " + e);
		}
	}
	
	public WebDriver getDriver() {
		return driverBase;
	}
	
	public void setDriver(WebDriver driver) {
		this.driverBase=driver;
	}

	public HomePage getHome() {
		return home;
	}

	public void setHome(HomePage home) {
		this.home = home;
	}

	public LoginPage getLogin() {
		return login;
	}

	public void setLogin(LoginPage login) {
		this.login = login;
	}
	
	@AfterClass
	public void closeBrowser() {
		driverBase.quit();
	}
}
