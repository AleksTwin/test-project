package com.test.rest.logging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.*;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/logging")
public class Logging {

	private static final String CHECKIN = "checkin";
	private static final String CHECKOUT = "checkout";
	
	/**
	 * Checkin endpoint for POST Responses. Adds current server time and stores
	 * in data base.
	 * 
	 * @param
	 * */
	@POST
	@Path("/checkin")
	@Consumes("application/json")
	public JSONObject storeCheckin(JSONObject jsonObj) {
		try {
			jsonObj.put("time", getCurrentServerTime());
			DbOperation.storeDataInDB(jsonObj, CHECKIN);
		} catch (JSONException ex) {
			System.out.println(ex);
		}
		return jsonObj;
	}

	/**
	 * Checkout endpoint for POST Responses. Adds current server time and stores
	 * in data base.
	 * 
	 * @param
	 * */
	@POST
	@Path("/checkout")
	@Consumes("application/json")
	public JSONObject storeCheckout(JSONObject jsonObj) {
		try {
			jsonObj.put("time", getCurrentServerTime());
			DbOperation.storeDataInDB(jsonObj, CHECKOUT);
		} catch (JSONException ex) {
			System.out.println(ex);
		}
		return jsonObj;
	}

	/* ------------------------------------------------------------------------ */
	/**
	 * Checkin endpoint for getting all "checkins" from data base.
	 *
	 * @return JSONObject
	 * */
	@GET
	@Path("/checkin")
	@Produces("application/json")
	public JSONObject getCheckin() {
		JSONObject json = new JSONObject();
		try {
			json = DbOperation.getCheckinsFromDB(CHECKIN);
		} catch (JSONException ex) {
			System.out.println(ex);
		}
		return json;
	}

	/**
	 * Checkout endpoint for getting all "checkouts" from data base.
	 *
	 * @return JSONObject
	 * */
	@GET
	@Path("/checkout")
	@Produces("application/json")
	public JSONObject getCheckout() {
		JSONObject json = new JSONObject();
		try {
			json = DbOperation.getCheckinsFromDB(CHECKOUT);
		} catch (JSONException ex) {
			System.out.println(ex);
		}
		return json;
	}

	/* ------------------------------------------------------------------------ */
	/**
	 * Checkin endpoint for getting "checkins" from data base for particular user.
	 *
	 * @return JSONObject
	 * */
	@GET
	@Path("/checkin/{name}")
	@Produces("application/json")
	public JSONObject getCheckinUser(@PathParam("name") String name) {
		JSONObject json = new JSONObject();
		try {
			json = DbOperation.getUserCheckinsFromDB(CHECKIN, name);
		} catch (JSONException ex) {
			System.out.println(ex);
		}
		return json;
	}

	/**
	 * Checkout endpoint for getting "checkouts" from data base for particular user.
	 *
	 * @return JSONObject
	 * */
	@GET
	@Path("/checkout/{name}")
	@Produces("application/json")
	public JSONObject getCheckoutUser(@PathParam("name") String name) {
		JSONObject json = new JSONObject();
		try {
			json = DbOperation.getUserCheckinsFromDB(CHECKOUT, name);
		} catch (JSONException ex) {
			System.out.println(ex);
		}
		return json;
	}
	
	/* ------------------------------------------------------------------------ */
	/**
	 * Returns current server time in ISO8601 format
	 * 
	 * @return String
	 * */
	private String getCurrentServerTime() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String nowAsISO = df.format(new Date());
		return nowAsISO;
	}

}
