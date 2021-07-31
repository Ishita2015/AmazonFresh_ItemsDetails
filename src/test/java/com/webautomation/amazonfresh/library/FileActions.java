package com.webautomation.amazonfresh.library;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.opencsv.CSVWriter;

public class FileActions {

		public FileActions() throws IOException {
			
			CSVWriter cw = createWriter(false);
			Set<String[]> itemDetailsFileHeaders = new HashSet<String[]>();
			
			itemDetailsFileHeaders.add(PropertyReader.configReader("FileHeaders").split(","));
			writeAndClose(cw, itemDetailsFileHeaders);
		}
		
		public void addRecords(Set<String[]> itemDetailsFileRecords) throws IOException {
			CSVWriter cw = createWriter(true);
			writeAndClose(cw, itemDetailsFileRecords);
		}

		private CSVWriter createWriter(boolean append) throws IOException {
			File f = new File(PropertyReader.configReader("AllItemsFilePath"));
			FileWriter fw = new FileWriter(f, append);
			CSVWriter cw = new CSVWriter(fw);
			return cw;
		}
		
		private void writeAndClose(CSVWriter cw, Set<String[]> fileRecords) throws IOException {
			cw.writeAll(fileRecords);
		    cw.flush();
		    cw.close();
		}
		
}
