package tests;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.response.Response;
import utils.RestUtils;

public class BaseTest {
	
	static Logger logger =LogManager.getLogger();
	
	public static String generateToken() {
		String token = "";
		String payload = "{\"username\": \"pallavibhakare19@tekarch.com\", \"password\": \"Admin123\"} ";
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		try {
			Response  loginRes = RestUtils.postReq(headers, payload, "/login");
			loginRes.prettyPrint();
			token = loginRes.jsonPath().get("[0].token");
		} catch (Exception e) {
			logger.error("Error occured while generating token: "+e.getMessage());
			e.printStackTrace();
		}				
		return token;
	}
	
	
}
