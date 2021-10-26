package com.cg.REST_API1;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import Files.Payload;


public class Add_Place_API {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		validate if add place API is working as expected 
		//Add Place -> update place with new address-> get place to validate if new address is present in response

			//given- all the input 
			//when -submit the API- resource , http method
			//then - validate the respone
			RestAssured.baseURI="https://rahulshettyacademy.com";
			String Res=given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json").body(Payload.newPayload()).when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP")).header("Server","Apache/2.4.18 (Ubuntu)").extract().response().asString();
System.out.println(Res);
JsonPath js=new JsonPath(Res);//For parsing the json 
String palceID=js.getString("place_id");
System.out.println(palceID);//Add palce code 

//Update address
String address= "70 winter walk, USA";
given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json").body("{\r\n"
		+ "\"place_id\":\""+palceID+"\",\r\n"
		+ "\"address\":\""+address+"\",\r\n"
		+ "\"key\":\"qaclick123\"\r\n"
		+ "}\r\n"
		+ " \r\n"
		+ "").when().put("/maps/api/place/update/json").then().assertThat().log().all().statusCode(200).body("msg",equalTo("Address successfully updated"));
//GET Place
String GetPlace=given().log().all().queryParam("key", "qaclick123")
.queryParam("place_id","palceID")
.when().get("/maps/api/place/get/json")
.then().assertThat().log().all().statusCode(200).extract().response().asString();

JsonPath js1=new JsonPath(GetPlace);
String actualResult=js1.getString("address");
System.out.println(actualResult);
//Using assertion 
Assert.assertEquals(actualResult,address );


}
}
