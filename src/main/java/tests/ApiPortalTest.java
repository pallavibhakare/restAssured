package tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

import constants.FileConstants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.AddUserPojo;
import utils.DataUtils;
import utils.RestUtils;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class ApiPortalTest extends BaseTest{
	
	@BeforeTest
	public void initialize() throws IOException {
		RestAssured.baseURI = DataUtils.readLoginTestData("app.url");		
	}
	
//	@BeforeMethod
//	public void generateTokenForTests() {
//		BaseTest.generateToken();
//	}
	
	@Test
	public void loginRequestTest_TC01() {
		//login request
		String filePath = FileConstants.LOGIN_REQUEST_FILE_PATH;
		String payload = "{\"username\": \"pallavibhakare19@tekarch.com\", \"password\": \"Admin123\"} ";
		
//		With Json Object
//		JsonObject jsonObjcet = new JsonObject();
//		jsonObject.addProperty("username", "pallavibhakare19@tekarch.com");
//		jsonObject.addProperty("password", "Admin123");
//		Response  loginRes = RestUtils.postReq(headers, jsonObject, "/login");
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		Response  loginRes = RestUtils.postReq(headers, payload, "/login");
//		long responseTime = loginRes.getTimeIn(TimeUnit.MICROSECONDS);
		RestUtils.validateSchema(loginRes, filePath);
		Assert.assertEquals(loginRes.statusCode(), 201);
	}
	@Test
	public void addDataRequestTest_TC02() throws JsonProcessingException {
		//addData request
		
		//Serialization and deserialization
		AddUserPojo userInfo = new AddUserPojo("TA-46577", "5", "7645", "56783");
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		String payload = objMapper.writeValueAsString(userInfo);
//		System.out.println(payload);
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("token", BaseTest.generateToken());
		Response addUserRes = RestUtils.postReq(headers, payload, "/addData");				
		Assert.assertEquals(addUserRes.statusCode(), 201);
	}
	@Test
	public void updateDataRequestTest_TC03() {
		//updateData request
		HashMap<String, String> updateDataHeaders = new HashMap<String, String>();
		updateDataHeaders.put("Content-Type", "application/json");
		updateDataHeaders.put("token", BaseTest.generateToken());
		String putData = "{\"accountno\": \"TA-5162024\", \"departmentno\": 4, \"salary\": 1047, \"pincode\": 123456, \"userid\": \"molxcZ6lEBMy8w7EA45P\", \"id\": \"ZWAxKB4XeFmTtQgsG2NJ\"}";
		Response updateDataRes = RestUtils.putReq(updateDataHeaders, putData, "/updateData");		
		Assert.assertEquals(updateDataRes.statusCode(), 200);
	}
	@Test
	public void getDataRequestTest_TC04() {
		//getData request
		HashMap<String, String> getDataHeaders = new HashMap<String, String>();
		getDataHeaders.put("Content-Type", "application/json");
		getDataHeaders.put("token", BaseTest.generateToken());	
		Response getDataRes = RestUtils.getReq(getDataHeaders, "/getdata");
		Assert.assertEquals(getDataRes.statusCode(), 200);
	}
	@Test
	public void deleteDataRequestTest_TC05() {
		//deleteData request
		HashMap<String, String> deleteDataHeaders = new HashMap<String, String>();
		deleteDataHeaders.put("Content-Type", "application/json");
		deleteDataHeaders.put("token", BaseTest.generateToken());
		String delData = "{\"id\": \"WOB0dFNTT6TxZWZ8wttl\", \"userid\": \"molxcZ6lEBMy8w7EA45P\"}";
		Response deleteDataRes =RestUtils.deleteReq(deleteDataHeaders, delData,  "/deleteData");
		Assert.assertEquals(deleteDataRes.statusCode(), 200);
	}
	
	
}
