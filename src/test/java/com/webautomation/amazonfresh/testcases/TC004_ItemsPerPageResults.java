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
	private static final String SEARCH_RESULTS = "//span[@class='a-size-base-plus a-color-base a-text-normal']";
	
	@Test
	public void calculatePageDetails() {
		try {
			home.selectCategory(PropertyReader.configReader("category"));
			String[] items = PropertyReader.configReader("items").split(",");
			int callCount = 1;
			for (String eachItem : items) {
				home.enterItemInTextbox(eachItem);
				home.clickSubmit();
				
				String details = "";
				int count = 1;
				details = itemsDetails(count, eachItem, details);
				
				List<WebElement> nextPageButton = driver.findElements(By.xpath(NEXT_BUTTON));
				while (!nextPageButton.isEmpty()) {
					driver.findElement(By.xpath(NEXT_BUTTON)).click();
					count++;
					details = itemsDetails(count, eachItem, details);
					nextPageButton = driver.findElements(By.xpath(NEXT_BUTTON));
				}
				String finalDetails = "Total pages for " + eachItem + ": " + count + details;
				writingItemsDetails(count, eachItem, finalDetails, callCount);
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
		List<WebElement> itemsPerPage = driver.findElements(By.xpath(SEARCH_RESULTS));
		for (WebElement item : itemsPerPage) {
			uniqueItems.add(item.getText());
		}
		int numberOfItemsPerPage = itemsPerPage.size();
		int numberOfUniqueItemsPerPage = uniqueItems.size();
		details = details + ", " + "Page " + count + ": " + " " + "TotalItems-" + numberOfItemsPerPage + " & " +
				 "UniqueItems-" + numberOfUniqueItemsPerPage;
        return details;
	}

	private void writingItemsDetails(int count, String eachItem, String finalDetails, int callCount)
			throws FileNotFoundException, IOException {
		CSVReader reader = new CSVReader(new FileReader("./ResultsData/AllItemsDetails.csv"));
		
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
		CSVWriter writer = new CSVWriter(new FileWriter("./ResultsData/AllItemsDetails.csv"));
		writer.writeAll(linesdata);
		writer.close();
	}	
}
