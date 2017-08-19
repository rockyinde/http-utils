package com.flyppo.utility.http;

import java.io.IOException;

import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * provides a simple interface to exchange: Response DTO with Request DTO
 * 
 * @author rockyinde
 *
 */
@Slf4j
public abstract class BaseConnector {

	private final ObjectMapper objectMapper;
	private final String url;
	private final String basicAuthHeader;

	public BaseConnector (String url, String basicAuthHeader) {
		this.objectMapper = new ObjectMapper();
		this.url = url;
		this.basicAuthHeader = basicAuthHeader;
	}

	public <T, R> R exchange (T requestDTO, Class<R> responseClass) {
		
		String jsonBody = null;
		try {
			jsonBody = objectMapper.writeValueAsString(requestDTO);
		} catch (JsonProcessingException e) {
			
			log.error("could not map hydra request to json string {}", requestDTO, e);
		}
		
		String response = post(jsonBody);

		R responseDTO = null;
		try {
			
			responseDTO = objectMapper.readValue(response, responseClass);
		} catch (IOException e) {

			log.error("could not map hydra request to json string {}", requestDTO, e);
		}
		
		return responseDTO;
	}
	
	public String post (String body) {
		
		String response = null;
		try {

			response = Request.Put(url)
					.addHeader("Authorization", basicAuthHeader)
					.addHeader("Content-Type", "application/json")
					.bodyString(body, ContentType.APPLICATION_JSON).execute()
					.returnContent().toString();
		} catch (IOException e) {
			
			log.error("could not connect with Wallet", e);
		}
		
		return response;
	}
}
