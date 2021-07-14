package org.amazon.testcases;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import org.amazon.library.PropertyReader;
import org.testng.annotations.Test;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class TC005_GetTop5ItemsDetails {

	@Test
	public void getTop5Items() {
		try {

			String[] lines = null;
			List<String[]> finalLines = new ArrayList<String[]>();
			
			String[] items = PropertyReader.configReader("items").split(",");
			
			for (String each : items) {
				int count = 1;
				CSVReader reader = new CSVReader(new FileReader("./ResultsData/AllItemsDetails.csv"));
				while ((lines = reader.readNext())!= null) {
					
					if (!lines[4].equalsIgnoreCase("N/A")) {
						if (each.equalsIgnoreCase(items[0]) && lines[0].equalsIgnoreCase("Category")) {
							finalLines.add(lines);
						}
						else {
							int spaceIndex = lines[4].indexOf(" ");
							String price = lines[4].substring(spaceIndex+1);
							if (lines[0].equalsIgnoreCase(each) && Float.parseFloat(price) < 300.00) {
								finalLines.add(lines);
								count++;
								if (count > 5) { break; }
					        }   
						}
					}
				 }
				 reader.close();
			}
			
			CSVWriter writer = new CSVWriter(new FileWriter("./ResultsData/TopFiveItemDetails.csv"));
			writer.writeAll(finalLines);
			writer.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("------- Exception occurred -------" + e);
		}
	}
}
