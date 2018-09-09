package com.billryoung.sfdc_springws_example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.billryoung.sfdc_springws_example.login.LoginOperation;
import com.billryoung.sfdc_springws_example.login.LoginResponse;
import com.billryoung.sfdc_springws_example.query.AccountQueryRecords;
import com.billryoung.sfdc_springws_example.query.QueryOperation;


/**
 * Application class and also defined a command line runner here
 * 
 * Good background reference guides:
 * https://spring.io/guides/gs/consuming-web-service/
 * https://spring.io/guides/gs/spring-boot/
 * https://developer.salesforce.com/docs/atlas.en-us.api_rest.meta/api_rest/intro_what_is_rest_api.htm
 * 
 * @author billryoung
 */
@SpringBootApplication
public class MyApplication {

	private static final Logger log = LoggerFactory.getLogger(MyApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MyApplication.class, args);
    }
    
    @Autowired
    private LoginOperation loginOperation;
    
    @Autowired
    private QueryOperation queryOperation;
    
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
    	
        return args -> { //shortcut way of defining the CommandLineRunner.run method
        	log.debug("commandLineRunner: start");
        	
        	LoginResponse loginResponse = loginOperation.login();
        	System.out.println("loginResponse="+loginResponse);
        	
        	AccountQueryRecords records = queryOperation.query(loginResponse,"select Id, Name from Account limit 10", AccountQueryRecords.class);
        	System.out.println("records="+records);
        	
        	log.debug("commandLineRunner: stop");
        };
    }

}