package org.amazon.library;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.opencsv.CSVWriter;

public class FileActions {

		public FileActions() throws IOException {
			
			CSVWriter cw = createWriter(false);
			Set<String[]> mySetHeaders = new HashSet<String[]>();
			
			String lineHeaders = "Category#Item-Description#Brand#MRP#Sale-Price#Savings#Weight#Net-Quantity#In-Stock#Link";
			String[] recordsHeaders = lineHeaders.split("#");
			mySetHeaders.add(recordsHeaders);
			writeAndClose(cw, mySetHeaders);
		}
		
		public void addRecords(Set<String[]> records) throws IOException {
			CSVWriter cw = createWriter(true);
			writeAndClose(cw, records);
		}

		private CSVWriter createWriter(boolean append) throws IOException {
			File f = new File("./ResultsData/AllItemsDetails.csv");
			FileWriter fw = new FileWriter(f, append);
			CSVWriter cw = new CSVWriter(fw);
			return cw;
		}
		
		private void writeAndClose(CSVWriter cw, Set<String[]> records) throws IOException {
			cw.writeAll(records);
		    cw.flush();
		    cw.close();
		}
		
}
