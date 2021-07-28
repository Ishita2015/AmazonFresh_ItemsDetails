package com.webautomation.amazonfresh.testcases;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.webautomation.amazonfresh.base.BaseMethods;
import com.webautomation.amazonfresh.library.FileActions;
import com.webautomation.amazonfresh.library.PropertyReader;

public class TC002_GetItemsDetailsWithoutLogin extends BaseMethods {
	
	private static final String CLICK_ENTER = "//span[@id='GLUXZipUpdate']";
	private static final String ENTER_PINCODE = "//input[@id='GLUXZipUpdateInput']";
	private static final String CLICK_SELECR_ADDRESS = "//span[@id='glow-ingress-line2']";
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
			getHome().selectAndApplyPincode(PropertyReader.configReader("Pincode"));
			getHome().selectCategory(PropertyReader.configReader("Category"));
			String[] items = PropertyReader.configReader("Items").split(",");
			for (String eachItem : items) {
				getItem(eachItem);
		    }
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("------- Exception occurred -------" + e);
		}
	}

	private void getItem(String eachItem) throws IOException, InterruptedException {
		
		getHome().enterItemInTextbox(eachItem);
		getHome().clickSubmit();

		WebDriverWait wait = new WebDriverWait(getDriver(), 20);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(ELEMENTS_IN_ONE_PAGE)));
		List<WebElement> itemsList = getDriver().findElements(By.xpath(ELEMENTS_IN_ONE_PAGE));
		Set<String[]> itemsDetailsData = new HashSet<String[]>();
		
		for (WebElement webElement : itemsList) { 
			if (itemsDetailsData.size() >= 6) { break; }
			else {
				StringBuilder sb = new StringBuilder();
				WebDriver driverTC002 = new ChromeDriver();
				driverTC002.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
				driverTC002.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
				String itemLink = webElement.getAttribute("href");
				String hyperLink = "=HYPERLINK(\"" + itemLink + "\",\"GoToLink\")";
				
				driverTC002.get(PropertyReader.configReader("URL"));
				WebDriverWait newWait = new WebDriverWait(driverTC002, 20);
				newWait.until(ExpectedConditions.elementToBeClickable(By.xpath(CLICK_SELECR_ADDRESS)));
				driverTC002.findElement(By.xpath(CLICK_SELECR_ADDRESS)).click();
				newWait.until(ExpectedConditions.elementToBeClickable(By.xpath(ENTER_PINCODE)));
				driverTC002.findElement(By.xpath(ENTER_PINCODE)).sendKeys(PropertyReader.configReader("Pincode"));
				newWait.until(ExpectedConditions.elementToBeClickable(By.xpath(CLICK_ENTER)));
				driverTC002.findElement(By.xpath(CLICK_ENTER)).click();	
				Thread.sleep(3000);
				driverTC002.get(itemLink);
				
				sb.append(eachItem).append(DELIMITER);
				sb.append(driverTC002.findElements(By.xpath(PRODUCT_TITLE)).isEmpty() ? "N/A" : driverTC002.findElement(By.xpath(PRODUCT_TITLE)).getText()).append(DELIMITER);
				sb.append(driverTC002.findElements(By.xpath(BRAND)).isEmpty() ? "N/A" : driverTC002.findElement(By.xpath(BRAND)).getText()).append(DELIMITER);
				sb.append(driverTC002.findElements(By.xpath(MRP)).isEmpty() ? "N/A" : driverTC002.findElement(By.xpath(MRP)).getText().replace("\u20B9", "INR ")).append(DELIMITER);
				sb.append(driverTC002.findElements(By.xpath(SALE_PRICE)).isEmpty() ? "N/A" : driverTC002.findElement(By.xpath(SALE_PRICE)).getText().replace("\u20B9", "INR ")).append(DELIMITER);
				sb.append(driverTC002.findElements(By.xpath(SAVINGS)).isEmpty() ? "N/A" : driverTC002.findElement(By.xpath(SAVINGS)).getText().replace("\u20B9", "INR ")).append(DELIMITER);
				sb.append(driverTC002.findElements(By.xpath(ITEM_WEIGHT)).isEmpty() ? "N/A" : driverTC002.findElement(By.xpath(ITEM_WEIGHT)).getText()).append(DELIMITER);
				sb.append(driverTC002.findElements(By.xpath(NET_QUANTITY)).isEmpty() ? "N/A" : driverTC002.findElement(By.xpath(NET_QUANTITY)).getText()).append(DELIMITER);
				sb.append(driverTC002.findElements(By.xpath(IN_STOCK_STATUS)).isEmpty() ? "No" : "Yes").append(DELIMITER);
				sb.append(itemLink.length() < 255 ? hyperLink : "Link too long");

				driverTC002.close();

				String[] data = sb.toString().split(DELIMITER);
				itemsDetailsData.add(data);
		   }
		}
		fileactions.addRecords(itemsDetailsData);
	}
}