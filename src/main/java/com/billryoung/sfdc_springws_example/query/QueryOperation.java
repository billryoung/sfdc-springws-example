package com.billryoung.sfdc_springws_example.query;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.billryoung.sfdc_springws_example.login.LoginResponse;

/**
 * Provides a simple query operation.
 * Note, I didn't implement queryMore, which you may need if you go to use this for real and need a large result back.
 * For more on query: https://developer.salesforce.com/docs/atlas.en-us.api_rest.meta/api_rest/resources_query.htm
 * 
 * @author billryoung
 */
@Service
public class QueryOperation {

	private static final Logger log = LoggerFactory.getLogger(QueryOperation.class);
	
	@Value("${salesforce.api_version:43.0}")
	private String api_version;
	
	/**
	 * Runs a query.
	 * 
	 * @param loginResponse - Uses the access token and instance url.
	 *        Example of a valid instance URL is like://YourDomainNameHere.my.salesforce.com
	 * @param query - SOQL query to run
	 * @param responseType - QueryRecords class type to get back
	 * @return Instance of responseType holding the query results
	 */
	public <T> T query(LoginResponse loginResponse, String query, Class<T> responseType) {
		
		//build the url
		String url = loginResponse.getInstance_url()
				+"/services/data/"
				+ "v"+api_version
				+ "/query/?q={q}";
		log.debug("query: url="+url);
		
		//create a Map for variables in the URL
		//namely the query
		Map<String, String> uriVariables = new HashMap<String, String>();
		uriVariables.put("q", query);

		//create the headers - need to pass access token from login
		HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+loginResponse.getAccess_token());

        //create our response
        //body is empty string, since there is no body for this request
        HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);

        //need a template to request things from
        RestTemplate restTemplate = new RestTemplate();

        //issue the GET
        ResponseEntity<T> responseEntity = restTemplate.exchange(
        		url, //url to request
        		HttpMethod.GET, //GET operation
        		requestEntity, //request empty body and http headers (access token)
        		responseType, //class type to parse response into
        		uriVariables); //variables to replace in the url
        log.debug("query: responseEntity="+responseEntity);
        
        return responseEntity.getBody();
	}
	
	/*
	 * You can also get back a string instead of responseType pass String.class
	 * and you'll get back ResponseEntity<String>
	 * whose body has the raw JSON reply
	 * useful to check if your not getting a field to decode into the response object
	 * 
	 * Note: you can't use the getFor b/c there's not a way to send the http headers with that method
	 * so you use exchange and specify the GET operation  
	 */

}
