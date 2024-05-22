package tests;

import java.io.IOException;
import java.util.HashMap;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import constants.FileConstants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.AddUserPojo;
import utils.DataUtils;
import utils.RestUtils;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;


public class ApiPortalTest extends BaseTest{
	
	@BeforeTest
	public void initialize() throws IOException {
		RestAssured.baseURI =DataUtils.getTestData("$.baseUrl").toString();
		//"https://us-central1-qa01-tekarch-accmanager.cloudfunctions.net";
	}
	
	@Test
	public void loginRequestTest_TC01() throws IOException {
	
		Object payload = DataUtils.getTestData("$.payloads.login");
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		Response  loginRes = RestUtils.postReq(headers, payload, DataUtils.getTestData("$.endpoints.login").toString());
		RestUtils.validateSchema(loginRes, FileConstants.LOGIN_SCHEMA_FILE_PATH);
//		assertEquals(loginRes.statusCode(), 201); //this is from Assert-testng
		int statusCode = loginRes.statusCode();
		assertThat(statusCode, equalTo(201)); //using hamcrest's 'assertThat', matcher 'equalsTo'
	}
	@Test
	public void addDataRequestTest_TC02() throws IOException {
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
	public void getDataRequestTest_TC03() throws JsonMappingException, JsonProcessingException {
		//getData request
		ObjectMapper objMapper = new ObjectMapper();
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("token", BaseTest.generateToken());	
		Response getDataRes = RestUtils.getReq(headers, "/getdata");
		String data = getDataRes.jsonPath().get("[0]");
		System.out.println(data);
		objMapper.readValue(data, AddUserPojo.class);
		Assert.assertEquals(getDataRes.statusCode(), 200);
	}
	
//	@Test
//	public void updateDataRequestTest_TC04() {
//		//updateData request
//		HashMap<String, String> updateDataHeaders = new HashMap<String, String>();
//		updateDataHeaders.put("Content-Type", "application/json");
//		updateDataHeaders.put("token", BaseTest.generateToken());
//		String putData = "{\"accountno\": \"TA-5162024\", \"departmentno\": 4, \"salary\": 1047, \"pincode\": 123456, \"userid\": \"molxcZ6lEBMy8w7EA45P\", \"id\": \"ZWAxKB4XeFmTtQgsG2NJ\"}";
//		Response updateDataRes = RestUtils.putReq(updateDataHeaders, putData, "/updateData");		
//		Assert.assertEquals(updateDataRes.statusCode(), 200);
//	}
//	
//	@Test
//	public void deleteDataRequestTest_TC05() {
//		//deleteData request
//		HashMap<String, String> deleteDataHeaders = new HashMap<String, String>();
//		deleteDataHeaders.put("Content-Type", "application/json");
//		deleteDataHeaders.put("token", BaseTest.generateToken());
//		String delData = "{\"id\": \"WOB0dFNTT6TxZWZ8wttl\", \"userid\": \"molxcZ6lEBMy8w7EA45P\"}";
//		Response deleteDataRes =RestUtils.deleteReq(deleteDataHeaders, delData,  "/deleteData");
//		Assert.assertEquals(deleteDataRes.statusCode(), 200);
//	}
	
	
}


























































