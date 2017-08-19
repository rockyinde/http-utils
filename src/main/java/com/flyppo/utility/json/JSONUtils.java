package com.flyppo.utility.json;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Utils for JSON comparison, validation etc-
 * 
 * @author rockyinde
 *
 */
public class JSONUtils {

    private static final ObjectMapper jsonComparisonObjectMapper = new ObjectMapper();

	private JSONUtils() {
	}
	
	/**
	 * computes the diff in two JSON strings and logs the same
	 *     - uses Skyscreamer's JSONAssert library
	 * 
	 * @param s1
	 * @param s2
	 * 
	 * compare mode; NON_EXTENSIBLE
	 *     - ordering of json arrays will be ignored
	 *     - additional fields will not be allowed
	 * 
	 * @throws JSONException
	 */
	public static String logDifferenceInJSONs(String s1, String s2) throws JSONException {
	    
        JSONCompareResult result = 
                JSONCompare.compareJSON(s1, s2, JSONCompareMode.NON_EXTENSIBLE);
        return result.toString();
	}
	
    /**
     * uses the GSON library for JSON comparison
     * 
     * @param json1
     * @param json2
     * @return
     * @throws JSONException 
     */
    public static boolean compareAndLog (String json1, String json2) throws JSONException {

        JSONCompareResult result = 
                JSONCompare.compareJSON(json1, json2, JSONCompareMode.NON_EXTENSIBLE);
        
        if (result == null)
            return false;
        
        if (result.toString() == null || result.toString().isEmpty())
                return true;
            
        return false;
    }

    /**
	 * uses the GSON library for JSON comparison
	 * 
	 * @param json1
	 * @param json2
	 * @return
	 */
	public static boolean checkJSONParserEquality (String json1, String json2) {

	    JsonParser parser = new JsonParser();
	    JsonElement o1 = parser.parse(json1);
	    JsonElement o2 = parser.parse(json2);

	    return o1.equals(o2);
	}
	
    /**
     * uses the codehaus/fasterxml library for JSON comparison
     * 
     * @param json1
     * @param json2
     * @return
     * @throws IOException 
     * @throws JsonProcessingException 
     */
    public static boolean checkObjectMapperEquality (String json1, String json2) throws JsonProcessingException, IOException {

        JsonNode originalNode;
        JsonNode shadowNode;

        originalNode = jsonComparisonObjectMapper.readTree(json1);
        shadowNode = jsonComparisonObjectMapper.readTree(json2);
        
        return originalNode.equals(shadowNode);
    }
    
	/**
	 * compares the two json strings for JSON equality
	 * 
	 * @param original
	 * @param shadow
	 * @return
	 * @throws JSONException
	 */
	public static boolean areJSONStringsEqual (String original, String shadow) throws JSONException {
		
	    JSONObject originalJSON = new JSONObject(original);
        JSONObject shadowJSON = new JSONObject(shadow);
	    
        // if not equal, log the difference
        return compareAndLog(originalJSON.toString(), shadowJSON.toString());
	}
}
