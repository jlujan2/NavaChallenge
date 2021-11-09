package com.nava.challengue.nava;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.opencsv.exceptions.CsvValidationException;

//@RestController
public class SpreadSheetGenerator {

	@Autowired
	DataFileDAO dataService;
	
	@GetMapping(path="/nava/{name}")
	public Map<String, Object> generateValues(@PathVariable String name) throws IOException, CsvValidationException, NumberFormatException {
		
		return dataService.readTextFile(name).get(0);
	}
	
	//@PostMapping(path="https://2swdepm0wa.execute-api.us-east-1.amazonaws.com/prod/NavaInterview/measures")
	public void saveRecord(@RequestBody Object obj, String name) {
		//List<Map<String, Object>>  hmList = dataService.readTextFile(name);
		
		
	}
}
