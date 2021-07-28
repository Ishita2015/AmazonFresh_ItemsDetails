package com.webautomation.amazonfresh.testcases;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.webautomation.amazonfresh.base.BaseMethods;
import com.webautomation.amazonfresh.library.PropertyReader;

public class TC004_ItemsPerPageResults extends BaseMethods {
	
	private static final String NEXT_BUTTON = "//a[contains(text(),'Next')]";
	private static final String ITEM_SEARCH_RESULTS = "//span[@class='a-size-base-plus a-color-base a-text-normal']";
	
	@Test
	public void calculatePageDetails() {
		try {
			getHome().selectAndApplyPincode(PropertyReader.configReader("Pincode"));
			getHome().selectCategory(PropertyReader.configReader("Category"));
			String[] items = PropertyReader.configReader("Items").split(",");
			int callCount = 1;
			for (String eachItem : items) {
				getHome().enterItemInTextbox(eachItem);
				getHome().clickSubmit();
				
				String details = "";
				int pageCount = 1;
				details = itemsDetails(pageCount, eachItem, details);
				
				List<WebElement> nextPageButton = getDriver().findElements(By.xpath(NEXT_BUTTON));
				while (!nextPageButton.isEmpty()) {
					getDriver().findElement(By.xpath(NEXT_BUTTON)).click();
					pageCount++;
					details = itemsDetails(pageCount, eachItem, details);
					nextPageButton = getDriver().findElements(By.xpath(NEXT_BUTTON));
				}
				String finalDetails = "Total pages for " + eachItem + ": " + pageCount + details;
				writingItemsDetails(pageCount, eachItem, finalDetails, callCount);
				callCount++;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("------- Exception occurred -------" + e);
		}
	}

	private String itemsDetails(int count, String eachItem, String details) throws IOException {
		Set<String> uniqueItems = new HashSet<String>();
		List<WebElement> itemsPerPage = getDriver().findElements(By.xpath(ITEM_SEARCH_RESULTS));
		for (WebElement item : itemsPerPage) {
			uniqueItems.add(item.getText());
		}
		int numberOfItemsPerPage = itemsPerPage.size();
		int numberOfUniqueItemsPerPage = uniqueItems.size();
		details = details + "\n" + "Page " + count + ": " + " " + "Total (" + numberOfItemsPerPage + ") & Unique (" 
				+ numberOfUniqueItemsPerPage + ")";
        return details;
	}

	private void writingItemsDetails(int count, String eachItem, String finalDetails, int callCount)
			throws FileNotFoundException, IOException {
		CSVReader reader = new CSVReader(new FileReader(PropertyReader.configReader("AllItemsFilePath")));
		
		String[] lines = null;
		List<String[]> linesdata = new ArrayList<String[]>();

		int itemLoc = 1;
		while((lines = reader.readNext())!=null) {
			if (callCount==1) {
		        lines = Arrays.copyOf(lines, lines.length + 1);
		        lines[lines.length - 1] = lines[0].equalsIgnoreCase("Category") ? "Item-Info" : "" ;
		        if (lines[0].equalsIgnoreCase(eachItem))  {
		        	if (itemLoc == 1) {
			        	lines[lines.length - 1] = finalDetails;
			        	itemLoc++;
		        	}
		        }
		        linesdata.add(lines);
			}
			else {
		        if (lines[0].equalsIgnoreCase(eachItem))  {
		        	if (itemLoc == 1) {
			        	lines[lines.length - 1] = finalDetails;
			        	itemLoc++;
		        	}
		        }
		        linesdata.add(lines);
			}
		}
		reader.close();
		CSVWriter writer = new CSVWriter(new FileWriter(PropertyReader.configReader("AllItemsFilePath")));
		writer.writeAll(linesdata);
		writer.close();
	}	
}
