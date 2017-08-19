package com.flyppo.utility.shadow;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.flyppo.utility.exception.HTTPException;

/**
 * providies utility methods to make (async) shadow calls 
 * @author rockyinde
 *
 */
public class ShadowHelper {
    
    private static final Properties SHADOW_PROPERTIES; 
    
    private static final int SHADOW_PERCENTAGE;
    
    /**
     * executor service for making async shadow calls
     */
    private static ExecutorService shadowExecutorService;

    // load properties
    static {

        SHADOW_PROPERTIES = getPropertiesFromFile("/shadow.properties");
        SHADOW_PERCENTAGE = Integer.parseInt(SHADOW_PROPERTIES.getProperty("shadow.percentage"));    
    }

    private ShadowHelper(){
    }
 
    public static Properties getProperties() {
        return SHADOW_PROPERTIES;
    }
    
    /**
     * determines whether a shadow call needs to be made
     * currently returns true for 10% of the requests
     * @return
     */
    public static boolean shouldMakeShadowRequest () {
        
        if (System.currentTimeMillis() % 100 < SHADOW_PERCENTAGE)
            return true;
        
        return false;
    }
    
    /**
     * submits an async shadow request for /getUserDetailsByCookie shadow API
     * @param request
     * @param body
     * @return
     */
    public static void runAsyncShadowTask (Runnable runnable) throws HTTPException {
        
        if (shadowExecutorService == null) {
            
            shadowExecutorService = Executors.newFixedThreadPool(
                    Integer.parseInt(
                    SHADOW_PROPERTIES.getProperty("executor.pool.size")));
        }
        
        try {
            shadowExecutorService.submit(runnable);
        } catch (Exception e) {
            throw new HTTPException("error while submitting a task to executor service", e);
        }
    }

    /**
     * Load property file into Properties object. File should be present in the
     * classpath
     * 
     * @param fileName
     *            Filename to laod properties from.
     * @return Properties object if file is found in the classpath else null.
     */
    public static Properties getPropertiesFromFile(String fileName) {
        Properties properties = null;
        try {
            properties = new Properties();
            properties.load(ShadowHelper.class.getResourceAsStream(fileName));
        } catch (IOException e) {
        }
        return properties;
    }
}
