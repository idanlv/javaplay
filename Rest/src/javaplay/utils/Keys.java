package javaplay.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Keys {
	private static Logger _logger = Logger.getLogger(Keys.class.getName()); 
	
	private static String _environment = null;
	private static JSONObject _keys = null; 
	private static final String DEFAULT_KEYS_FILE = "keys.json";
	private static final String DEFAULT_ENVIRONMENT = "local";
	private static final String JAVAPLAY_ENV = "JAVAPLAY_ENV";
	private static final String JAVAPLAY_KEYS_FILE = "JAVAPLAY_KEYS_FILE";
	
	/***
	 * Retrieves key's String value
	 * @param key Key to retrieve
	 * @return value
	 * @throws FileNotFoundException in case keys file wasn't found
	 * @throws IOException in case can't read keys file
	 * @throws ParseException in case keys file isn't a valid JSON file
	 */
	public static String getString(String key) throws FileNotFoundException, IOException, ParseException {
		Object obj = get(key);
		
		if (obj instanceof String) {
			return obj.toString();
		} else {
			throw new IllegalArgumentException(String.format("Key '%s' is not a String object", key));
		}
	}

	/***
	 * Retrieves key's Map value
	 * @param key Key to retrieve
	 * @return value
	 * @throws FileNotFoundException in case keys file wasn't found
	 * @throws IOException in case can't read keys file
	 * @throws ParseException in case keys file isn't a valid JSON file
	 */
	public static Map<String, String> getMap(String key) throws FileNotFoundException, IOException, ParseException {
		Object obj = get(key);
		
		if (obj instanceof JSONObject) {
			JSONObject valueObject = (JSONObject)obj;
			
			Map<String, String> map = new HashMap<String, String>();
		    
			for (Object item : valueObject.keySet()) {		    	
				String innerKey = item.toString();
				Object value = valueObject.get(innerKey);

				map.put(innerKey, value.toString());
		    }
		    
		    return map;	
		} else {
			throw new IllegalArgumentException(String.format("Key '%s' is not a JSONObject object", key));
		}
	}
	
	private static Object get(String key) throws FileNotFoundException, IOException, ParseException {
		// TODO: Add start configuration that does this
		if (_environment == null) {
			loadEnvironment();
			loadKeysFile();
		}
		
		if (_keys.containsKey(_environment)) {
			JSONObject keysInEnvironment = (JSONObject)_keys.get(_environment);
			
			if (keysInEnvironment.containsKey(key)) {
				return keysInEnvironment.get(key);
			} else {
				throw new IllegalArgumentException(String.format("Key %s does not exist in keys file", key));
			}			
		} else {
			throw new IllegalArgumentException(String.format("Key %s does not exist in keys file", key));	
		}

	}
	
	private static void loadKeysFile() throws FileNotFoundException, IOException, ParseException {
		String value = System.getenv(JAVAPLAY_KEYS_FILE);
        
		if (value == null) {
			_logger.log(Level.WARNING, String.format("Could not find environment variable '%s', using default instead", JAVAPLAY_KEYS_FILE));
			value = DEFAULT_KEYS_FILE;
		} 
        
		JSONParser parser = new JSONParser();
        
		_keys = (JSONObject)parser.parse(new FileReader(value));
	}
	
	private static void loadEnvironment() {
		_environment = System.getenv(JAVAPLAY_ENV);
        
		if (_environment == null) {
			_logger.log(Level.WARNING, String.format("Could not find environment variable '%s', using default instead", JAVAPLAY_ENV));
			_environment = DEFAULT_ENVIRONMENT;
		}
	}
}
