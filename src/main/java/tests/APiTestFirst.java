package tests;

import java.util.HashMap;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.RestUtils;

public class APiTestFirst {
	public static void main(String[] args) {
		//https://us-central1-qa01-tekarch-accmanager.cloudfunctions.net/login 
		
		RestAssured.baseURI = "https://us-central1-qa01-tekarch-accmanager.cloudfunctions.net";

		//login request
		String payload = "{\"username\": \"pallavibhakare19@tekarch.com\", \"password\": \"Admin123\"} ";
		HashMap<String, String> headers = new HashMap<String, String>();		
		headers.put("Content-Type", "application/json");
		Response  loginRes = RestUtils.postReq(headers, payload, "/login");
		loginRes.prettyPrint();
		System.out.println(loginRes.statusCode());
		String token = loginRes.jsonPath().get("[0].token");
		System.out.println(token);
		
		//addData request
		HashMap<String, String> addUserHeaders = new HashMap<String, String>();
		addUserHeaders.put("Content-Type", "application/json");
		addUserHeaders.put("token", token);
		
		Response addUserRes = RestAssured.given()
						.headers(addUserHeaders)
						.when()
						.body("{\"accountno\": \"TA-5162024\", \"departmentno\": \"1\", \"salary\": \"1038\", \"pincode\": \"123456\"}")
						.post("/addData");
		
		addUserRes.prettyPrint();
		System.out.println("addData Status code: "+addUserRes.statusCode());
		
		
		//updateData request
		HashMap<String, String> updateDataHeaders = new HashMap<String, String>();
		updateDataHeaders.put("Content-Type", "application/json");
		updateDataHeaders.put("token", token);
		Response updateDataRes = RestAssured.given()
				.headers(updateDataHeaders)
				.when()
				.body("{\"accountno\": \"TA-5162024\", \"departmentno\": 4, \"salary\": 1047, \"pincode\": 123456, \"userid\": \"molxcZ6lEBMy8w7EA45P\", \"id\": \"ZWAxKB4XeFmTtQgsG2NJ\"}")
				.put("/updateData");
		
		updateDataRes.prettyPrint();
		System.out.println("updateData Status code: "+updateDataRes.statusCode());
		
		
		//getData request
		HashMap<String, String> getDataHeaders = new HashMap<String, String>();
		getDataHeaders.put("Content-Type", "application/json");
		getDataHeaders.put("token", token);
		
		Response getDataRes = RestAssured.given()
				.headers(getDataHeaders)
				.when()
				.body("")
				.get("/getdata");
		getDataRes.prettyPrint();
		System.out.println("getData Status code: "+getDataRes.statusCode());
		
		//deleteData request
		HashMap<String, String> deleteDataHeaders = new HashMap<String, String>();
		deleteDataHeaders.put("Content-Type", "application/json");
		deleteDataHeaders.put("token", token);
		Response deleteDataRes = RestAssured.given()
				.headers(deleteDataHeaders)
				.when()
				.body("{\"id\": \"sjOPFm489h1wjCe8mK6w\", \"userid\": \"molxcZ6lEBMy8w7EA45P\"}")
				.delete("/deleteData");
		deleteDataRes.prettyPrint();
		System.out.println("deleteData Status code: "+deleteDataRes.statusCode());
		
	}
}
