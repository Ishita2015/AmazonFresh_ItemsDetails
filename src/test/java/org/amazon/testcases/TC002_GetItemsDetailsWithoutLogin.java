package org.amazon.testcases;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.amazon.base.BaseMethods;
import org.amazon.library.FileActions;
import org.amazon.library.PropertyReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class TC002_GetItemsDetailsWithoutLogin extends BaseMethods {
	
	private static final String IN_STOCK_STATUS = "//div[@id='availability-string']/span";
	private static final String NET_QUANTITY = "//th[contains(text(), 'Net Quantity')]/following-sibling::td";
	private static final String BRAND = "//th[contains(text(), 'Brand')]/following-sibling::td";
	private static final String ITEM_WEIGHT = "//th[contains(text(), 'Item Weight')]/following-sibling::td";
	private static final String SAVINGS = "//tr[@id='almDetailPagePrice_savings_price']/td[2]/span";
	private static final String DELIMITER = "#";
	private static final String MRP = "//tr[@id='almDetailPagePrice_basis_price']/td[2]/span";
	private static final String SALE_PRICE = "//span[@id='priceblock_ourprice']";
	private static final String PRODUCT_TITLE = "//span[@id='productTitle']";
	private final static String ELEMENTS_IN_ONE_PAGE = "//div[@class='a-section a-spacing-micro s-grid-status-badge-container']/a[1]";
	private FileActions fileactions;
	
	@Test
	public void searchItem() throws InterruptedException, IOException {
		try {
			fileactions = new FileActions();
			home.selectCategory(PropertyReader.configReader("category"));
			String[] items = PropertyReader.configReader("items").split(",");

			for (String eachItem : items) {
				getItem(eachItem);
		    }
		} 
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("------- Exception occurred -------" + e);
		}
	}

	private void getItem(String eachItem) throws IOException {
		
		home.enterItemInTextbox(eachItem);
		home.clickSubmit();
		
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(ELEMENTS_IN_ONE_PAGE)));
		List<WebElement> itemsList = driver.findElements(By.xpath(ELEMENTS_IN_ONE_PAGE));
		Set<String> mySet = new HashSet<String>();
		Set<String[]> myFinalSet = new HashSet<String[]>();
		
		for (WebElement webElement : itemsList) {
		    
			if (mySet.size() >= 10) {
				break;
			}
			else {
				StringBuilder sb = new StringBuilder();
				WebDriver newDriver = new ChromeDriver();
				newDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				String itemLink = webElement.getAttribute("href");
				String hyperLink = "=HYPERLINK(\"" + itemLink + "\",\"GoToLink\")";
				
				if (itemLink.length() < 255) {
					newDriver.get(itemLink);
					
					sb.append(eachItem).append(DELIMITER);
					sb.append(newDriver.findElements(By.xpath(PRODUCT_TITLE)).isEmpty() ? "N/A" : newDriver.findElement(By.xpath(PRODUCT_TITLE)).getText()).append(DELIMITER);
					sb.append(newDriver.findElements(By.xpath(BRAND)).isEmpty() ? "N/A" : newDriver.findElement(By.xpath(BRAND)).getText()).append(DELIMITER);
					sb.append(newDriver.findElements(By.xpath(MRP)).isEmpty() ? "N/A" : newDriver.findElement(By.xpath(MRP)).getText().replace("\u20B9", "INR")).append(DELIMITER);
					sb.append(newDriver.findElements(By.xpath(SALE_PRICE)).isEmpty() ? "N/A" : newDriver.findElement(By.xpath(SALE_PRICE)).getText().replace("\u20B9", "INR")).append(DELIMITER);
					sb.append(newDriver.findElements(By.xpath(SAVINGS)).isEmpty() ? "N/A" : newDriver.findElement(By.xpath(SAVINGS)).getText().replace("\u20B9", "INR")).append(DELIMITER);
					sb.append(newDriver.findElements(By.xpath(ITEM_WEIGHT)).isEmpty() ? "N/A" : newDriver.findElement(By.xpath(ITEM_WEIGHT)).getText()).append(DELIMITER);
					sb.append(newDriver.findElements(By.xpath(NET_QUANTITY)).isEmpty() ? "N/A" : newDriver.findElement(By.xpath(NET_QUANTITY)).getText()).append(DELIMITER);
					sb.append(newDriver.findElements(By.xpath(IN_STOCK_STATUS)).isEmpty() ? "No" : "Yes").append(DELIMITER);
					sb.append(hyperLink);

					mySet.add(sb.toString());
				}
				newDriver.close();
			}
		}
		for (String item : mySet) {
			String[] data = item.split(DELIMITER);
			myFinalSet.add(data);
		}
		fileactions.addRecords(myFinalSet);
	}
}