package com.nava.challengue.nava;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.util.List;
import java.util.Map;

import com.opencsv.exceptions.CsvValidationException;

public class ProcessDataMain {

	public static void main(String[] args) throws CsvValidationException, NumberFormatException, IOException {
		DataFileDAO dataService = new DataFileDAO();
		//String name = "booleanmeasures";
		
		List<Map<String, Object>>  hmList = dataService.readTextFile("booleanmeasures");
		
		for(Map<String, Object> obj:hmList) {
			String rawData = obj.toString();
			String type = "application/json";
			URL url = new URL("https://2swdepm0wa.execute-api.us-east-1.amazonaws.com/prod/NavaInterview/measures");
			String encodedData = URLEncoder.encode( rawData, "UTF-8" ); 
			URLConnection con = url.openConnection();
			HttpURLConnection http = (HttpURLConnection)con;
			http.setRequestMethod("POST"); // PUT is another valid option
			http.setDoOutput(true);
			http.setRequestProperty( "Content-Type", type );
			http.setRequestProperty( "Content-Length", String.valueOf(encodedData.length()));
			OutputStream os = http.getOutputStream();
			os.write(encodedData.getBytes());
			
			BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                    con.getInputStream()));
			String decodedString;
			while ((decodedString = in.readLine()) != null) {
				System.out.println(decodedString);
			}
			in.close();
		}
	}

}
