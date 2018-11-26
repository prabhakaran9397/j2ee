package com.prabs;

import java.util.Map;
import java.util.Scanner;

import com.prabs.json.reader.JsonParser;
import com.prabs.json.writer.PrintPretty;

public class Driver {

	public static void main(String[] args) {
		String inputJson = getJsonFromStdin();
		Map<String, Object> outputJson = (new JsonParser()).parse(inputJson);
		(new PrintPretty()).printJson(outputJson);
	}
	
	private static String getJsonFromStdin () {
		Scanner scanner = new Scanner(System.in);
		String lines = new String();
		while(scanner.hasNextLine()) {
			lines += scanner.nextLine();
		}
		scanner.close();
		return lines;
	}

}
/*
{
   "hello": 1, 
   "world": null, 
   "test": {
      "test": {
         "hhhh": true
      }, 
      "array": ["polo", "bolo"], 
      "gh": {
         "hhhh": true
      }
   }
}
*/
