package com.test.rest.logging;

import org.junit.Test;
import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class IntegrationTest {

	@Test
	public void testCheckinCreatePOST() throws Exception {
		given().
				contentType("application/json").
				body("{\"name\":\"henrik\"}").
		expect().
				contentType("application/json").
				body("any { it.key == 'time' }", is(true)).
		when().
				post("http://localhost:8080/com.test.rest/api/logging/checkin");

	}

	@Test
	public void testCheckinReadGET() throws Exception {
		expect().
				contentType("application/json").
				body("checkins.any { it.containsKey('name') }", is(true)).
				body("checkins.name", hasItem("henrik")).
				body("checkins.any { it.containsKey('time') }", is(true)).
		when().
				get("http://localhost:8080/com.test.rest/api/logging/checkin");
	}
}
