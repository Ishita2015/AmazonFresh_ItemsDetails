package org.amazon.pages;

import java.io.IOException;
import org.amazon.library.PageActions;
import org.amazon.library.PropertyReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
	
	public PageActions action;
	
	public LoginPage(WebDriver driver) {
		action = new PageActions(driver);
	}
	
	@FindBy(xpath="//span[contains(text(),'Account')]") WebElement signInTab;
	@FindBy(xpath="//div[@id='nav-al-signin']/div/a/span") WebElement signInButton;
	@FindBy(id="ap_email") WebElement userNameTextBox;
	@FindBy(id="continue") WebElement continueButton;
	@FindBy(id="ap_password") WebElement passwordTextBox;
	@FindBy(id="signInSubmit") WebElement submitButton;
	
	public void clickSignInTab() {
		action.clickButton(signInTab);
	}
	
	public void clickSignInButton() {
		action.clickButton(signInButton);
	}
	
	public void enterUsername() throws IOException {
		action.enterDataIntoTextBox(userNameTextBox, PropertyReader.configReader("Username"));
	}

	public void clickContinue() {
		action.clickButton(continueButton);
	}
	
	public void enterPassword() throws IOException {
		action.enterDataIntoTextBox(passwordTextBox, PropertyReader.configReader("Password"));
	}
	
	public void clickSubmit() {
		action.clickButton(submitButton);
	}
	
}
