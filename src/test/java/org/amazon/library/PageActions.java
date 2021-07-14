package org.amazon.library;

import org.amazon.base.BaseMethods;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageActions extends BaseMethods{

	WebDriverWait wait;
	WebDriver driver;
	
	public PageActions(WebDriver driver){
		wait = new WebDriverWait(driver, 20);
	}
	
	public void enterDataIntoTextBox(WebElement element, String data) {
		wait.until(ExpectedConditions.visibilityOf(element));
		element.clear();
		element.sendKeys(data);
	}
	
	public void clickButton(WebElement element) {
		wait.until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}
	
	public void selectDataUsingVisibleText(WebElement element, String text) {
		wait.until(ExpectedConditions.textToBePresentInElement(element, text));
		Select s = new Select(element);
		s.selectByVisibleText(text);
	}
	
	public void selectDataUsingValue(WebElement element, String value) {
		wait.until(ExpectedConditions.textToBePresentInElement(element, value));
		Select s = new Select(element);
		s.selectByValue(value);
	}

}
