package javaplay.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Keys {
	private static String _environment = null;
	private static JSONObject _keys = null; 
	
	/***
	 * Retrieves key's value
	 * @param key Key to retrieve
	 * @return value
	 * @throws FileNotFoundException in case keys file wasn't found
	 * @throws IOException in case can't read keys file
	 * @throws ParseException in case keys file isn't a valid json file
	 */
	public static String get(String key) throws FileNotFoundException, IOException, ParseException {
		if (_environment == null) {
			loadEnvironment();
			loadKeysFile();
		}
		
		if (_keys.containsKey(_environment)) {
			JSONObject envKey = (JSONObject)_keys.get(_environment);
			
			if (envKey.containsKey(key)) {
				return envKey.get(key).toString();		
			} else {
				throw new IllegalArgumentException(String.format("Key %s does not exist in keys file", key));
			}			
		} else {
			throw new IllegalArgumentException(String.format("Key %s does not exist in keys file", key));	
		}
	}
	
	private static void loadKeysFile() throws FileNotFoundException, IOException, ParseException {
		String value = System.getenv("JAVAPLAY_KEYS_FILE");
        
        if (value == null) {
        	value = "keys.json";
        } 
        
        JSONParser parser = new JSONParser();
        
        Object obj = parser.parse(new FileReader(value));
        _keys = (JSONObject)obj;
	}
	
	private static void loadEnvironment() {
        String value = System.getenv("JAVAPLAY_ENV");
        
        if (value == null) {
        	_environment = "local";
        } else {
        	_environment = value;
        }
	}
}
