package com.webautomation.amazonfresh.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.webautomation.amazonfresh.base.BaseMethods;


public class TC001_GetItemsDetailsWithLogin extends BaseMethods {

	@Test
	public void loginExecution() throws IOException {
		getLogin().clickSignInTab();
		getLogin().enterUsername();
		getLogin().clickContinue();
		getLogin().enterPassword();
		getLogin().clickSubmit();
	}	
	
	@Test 
	public void getItemDetails() throws IOException {
		
	}
}