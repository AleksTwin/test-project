package com.test.rest.logging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.ws.rs.*;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/logging")
public class Logging {

	@POST
	@Path("/checkin")
	@Consumes("application/json")
	public JSONObject storeCheckin(JSONObject jsonObj){
		
		try {
		    jsonObj.put("time", getCurrentServerTime());
		} catch (JSONException ex) {
		    System.out.println(ex);
		}
		
		//TODO# Write data in storage 
		return jsonObj;
	}
	
	@POST
	@Path("/checkout")
	@Consumes("application/json")
	public JSONObject storeCheckout(JSONObject jsonObj){
		
		try {
		    jsonObj.put("time", getCurrentServerTime());
		} catch (JSONException ex) {
		    System.out.println(ex);
		}
		
		//TODO# Write data in storage
		return jsonObj;
	}
	
	// Returns current server time in ISO8601 format
	private String getCurrentServerTime(){
	    TimeZone tz = TimeZone.getTimeZone("UTC");
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	    df.setTimeZone(tz);
	    String nowAsISO = df.format(new Date());
		return nowAsISO;
	}
	
}
