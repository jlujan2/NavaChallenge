package com.nava.challengue.nava;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

@Service
public class DataFileDAO extends DataFile{

	@Autowired
	DataFileRepository repo;
	
	public List<DataFile> getAllDataFiles(){
		return repo.findAll();
	}
	
	public List<Map<String, Object>> readTextFile(String fileName) throws IOException, CsvValidationException, NumberFormatException {
		//Read CSV
		
		String path = "C:\\Users\\Juan Carlos\\Desktop\\NavaBackendChallengue\\nava\\src\\main\\resources\\schema\\" + fileName+".csv";
		CSVReader reader = new CSVReader(new FileReader(path));
		
		List<Column> columns = new ArrayList<>();
		String[] lineInArray;
	      while ((lineInArray = reader.readNext()) != null) {
	    	  Column c = new Column();
	    	  c.setName(lineInArray[0]);
	    	  c.setWidth(Integer.parseInt(lineInArray[1]));
	    	  c.setDataType(lineInArray[2]);
	    	  columns.add(c);
	      }
		
		
		//Read file
		path = "C:\\Users\\Juan Carlos\\Desktop\\NavaBackendChallengue\\nava\\src\\main\\resources\\data\\" + fileName+".txt";
		File file = new File(path);
		
		BufferedReader br
        = new BufferedReader(new FileReader(file));
		
		String st;
		
		List<Map<String, Object>> dataFilesParsed = new ArrayList<>(); 
		
		while ((st = br.readLine()) != null) {
			dataFilesParsed.add(getValues(columns, st));
		}
		
		br.close();
		return dataFilesParsed;
	}
	private Integer getInteger(String content) {
		StringBuilder sb = new StringBuilder();
		
		for(Character c:content.toCharArray()) {
			if(Character.isDigit(c)) {
				sb.append(c);
			}
		}
		return Integer.parseInt(sb.toString());
	}
	
	private Map<String, Object> getValues(List<Column> columns, String line)
	{
		Map<String, Object> hm = new HashMap<>();
		Integer size;
		String name;
		String type;
		String content;
		StringBuilder sb = new StringBuilder(line);
		for(Column c: columns) {
			size = c.getWidth();
			name = c.getName();
			type = c.getDataType();
			
			content =  sb.substring(0, size);
			
			if(type.toLowerCase().equals("text"))
				hm.put(name, content.replace("\\s+",""));
			else if(type.toLowerCase().equals("integer"))
				hm.put(name, getInteger(content));
			else 
				hm.put(name, "1".equals(content.substring(0, 1)));
			
			sb.delete(0, size);
		}
		
		return hm;
	}
	
}
