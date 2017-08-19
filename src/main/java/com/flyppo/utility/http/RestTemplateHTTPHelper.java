package com.flyppo.utility.http;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class RestTemplateHTTPHelper {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    
    private RestTemplateHTTPHelper() {
    }

    /**
     * adds required headers for userservice and makes an HTTP POST via spring rest template
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public static String postRestTemplate(String url, String json) {
        
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Accept", "application/json");

        // make REST call
        return REST_TEMPLATE.exchange(url, 
                HttpMethod.POST, 
                new HttpEntity<String>(json, requestHeaders), 
                String.class)
                .getBody();
    }   
    
    /**
     * adds required headers for userservice and makes an HTTP POST
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public static String post(String url, String json) throws IOException {
        
        return postRestTemplate(url, json);
    }
}
