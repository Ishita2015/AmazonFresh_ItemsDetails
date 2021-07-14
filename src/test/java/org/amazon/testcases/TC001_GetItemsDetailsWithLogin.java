package org.amazon.testcases;

import java.io.IOException;

import org.amazon.base.BaseMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;


public class TC001_GetItemsDetailsWithLogin extends BaseMethods {

	@Test
	public void loginExecution() throws IOException {
		login.clickSignInTab();
		login.enterUsername();
		login.clickContinue();
		login.enterPassword();
		login.clickSubmit();
	}	
	
	@Test 
	public void getItemDetails() throws IOException {
		
	}
}