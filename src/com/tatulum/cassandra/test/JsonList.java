package com.tatulum.cassandra.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonList {
	public static void main(String[] args) throws MalformedURLException, IOException, JSONException {
        String url = "http://api.tvmaze.com/shows/1/episodes";
        URL foo = new URL(url);
        String genreJson = IOUtils.toString(foo.openStream());
        
        //System.out.println(genreJson);
        ArrayList<String> files = getJsonFiles(genreJson);
        for(String file : files) {
        	JSONObject json = new JSONObject(file);
        	System.out.println(json.get("name"));
        }
        
        //JSONObject json = new JSONObject(genreJson);
        // get the title
        //System.out.println(json.get("name"));
    }
	
	private static ArrayList<String> getJsonFiles(String full) {
		ArrayList<String> files = new ArrayList<>();
		ArrayList<Integer> separators = new ArrayList<>();
		int openBracketCounter = 0;
		
		for(int i = 0; i < full.length(); i++) {
			char c = full.charAt(i);
			switch (c) {
			case '{':
				openBracketCounter++;
				if(openBracketCounter == 1) {
					separators.add(i);
				}
				break;
			case '}':
				openBracketCounter--;
				break;
			case ',':
				if(openBracketCounter == 0) {
					separators.add(i);
				}
				break;
			}
		}
		
		Iterator<Integer> it = separators.iterator();
		while(it.hasNext()) {
			int start = it.next();
			int end = full.length();
			if(it.hasNext()) {
				end = it.next();
			}
			files.add((full.substring(start, end)));
		}
		
		return files;
	}
}
