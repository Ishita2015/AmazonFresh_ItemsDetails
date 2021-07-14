package org.amazon.pages;

import java.io.IOException;
import org.amazon.library.PageActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage {

	public PageActions action;
	public HomePage(WebDriver driver) {
		action = new PageActions(driver);
	}

	@FindBy(xpath="//select[@id='searchDropdownBox']") WebElement categoryDropdown;
	@FindBy(xpath="//input[@id='twotabsearchtextbox']") WebElement searchTextbox;
	@FindBy(id="nav-search-submit-button") WebElement submitButton;
	
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
