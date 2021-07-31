package com.webautomation.amazonfresh.pages;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.webautomation.amazonfresh.library.PageActions;

public class HomePage {

	public PageActions action;
	public HomePage(WebDriver driver) {
		action = new PageActions(driver);
	}

	@FindBy(xpath="//span[@id='glow-ingress-line2']") WebElement selectAddressDropdown;
	@FindBy(xpath="//input[@id='GLUXZipUpdateInput']") WebElement pincodeTextbox;
	@FindBy(xpath="//span[@id='GLUXZipUpdate']") WebElement applyButton;
	
	@FindBy(xpath="//input[@type='submit']") WebElement closeAlertButton;
	
	@FindBy(xpath="//select[@id='searchDropdownBox']") WebElement categoryDropdown;
	@FindBy(xpath="//input[@id='twotabsearchtextbox']") WebElement searchTextbox;
	@FindBy(id="nav-search-submit-button") WebElement submitButton;
	
	public void selectAndApplyPincode(String pincode) {
		action.clickButton(selectAddressDropdown);
		action.enterDataIntoTextBox(pincodeTextbox, pincode);
		action.clickButton(applyButton);
	}
	
	public void closeAlertButton() {
		action.clickButton(closeAlertButton);
	}
	
	public void selectCategory(String category) throws IOException {
		action.selectDataUsingVisibleText(categoryDropdown, category);
	}
	
	public void enterItemInTextbox(String item) {
		action.enterDataIntoTextBox(searchTextbox, item);
	}	
	
	public void clickSubmit() {
		action.clickButton(submitButton);
	}	
}
