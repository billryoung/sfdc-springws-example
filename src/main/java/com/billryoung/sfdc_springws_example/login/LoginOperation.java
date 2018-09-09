package com.billryoung.sfdc_springws_example.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


/**
 * Implements a simple username/password login operation.
 * Uses values configured in application.properties
 * For more info on the API, see https://developer.salesforce.com/docs/atlas.en-us.api_rest.meta/api_rest/intro_understanding_username_password_oauth_flow.htm
 * 
 * @author billryoung
 */
@Service
public class LoginOperation {

	private static final Logger log = LoggerFactory.getLogger(LoginOperation.class);

	@Value("${login.login_url}")
	private String login_url;
	
	@Value("${login.grant_type:password}")
	private String grant_type;
	
	@Value("${login.username}")
	private String username;
	
	@Value("${login.password}")
	private String password;
	
	@Value("${login.client_id}")
	private String client_id;
	
	@Value("${login.client_secret}")
	private String client_secret;
	
	
	public LoginResponse login() {
		
		//for login, the values go into the body
		//note: spring auto-converts a MultiValueMap into the body format we need
		//      and that includes escaping characters
		MultiValueMap<String,String> bodyValues = new LinkedMultiValueMap<String,String>();
		bodyValues.add("grant_type", grant_type);
		bodyValues.add("client_id", client_id);
		bodyValues.add("client_secret", client_secret);
		bodyValues.add("username", username);
		bodyValues.add("password", password);
        
        //must specify a content header or doesn't work
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        //wrap the body and headers together into an http entity
        HttpEntity<MultiValueMap<String,String>> requestEntity = new HttpEntity<MultiValueMap<String,String>>(bodyValues, headers);

        //need a template to make a request
        RestTemplate restTemplate = new RestTemplate();
        
        //issue a post
        ResponseEntity<LoginResponse> responseEntity = restTemplate.exchange(
       	     this.login_url, //where to send to
       		 HttpMethod.POST, //POST for login
       	     requestEntity, //what to send (body and headers)
       	     LoginResponse.class);  //what to parse the response into
        						//omitting variables to replace in the URI, none for this call
        
        log.debug("login: responseEntity="+responseEntity.toString());
		
        //note: all subsequent operations will use the returned access token and instance url
        
		return responseEntity.getBody();
	}
	
	/* alt: note
	 we could have also used a restTemplate.postForEntity
	 that would look like:
	 
	 ResponseEntity<LoginResponse> responseEntity = restTemplate.postForEntity(
        		this.login_url, //where to send to
        		requestEntity, //what to send (body and headers)
        		LoginResponse.class //what to parse the response into
        		); //variables to replace in the URI, none for this call
	 
	   Leaving the base example as exchange, since that's a bit more general
	   and this is a codebase meant to show generally how to interact
	 */
		
}
