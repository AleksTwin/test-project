package com.test.rest.logging;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

public class DbOperation {

	/**
	 * Stores incoming record in data base.
	 * 
	 * @param
	 * @throws JSONException
	 * */
	@SuppressWarnings("deprecation")
	public static void storeDataInDB(JSONObject jsonObj, String state)
			throws JSONException {

		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		CheckinEntity checkinEntity = new CheckinEntity();
		checkinEntity.setName(jsonObj.getString("name"));
		checkinEntity.setTime(jsonObj.getString("time"));
		checkinEntity.setState(state);

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(checkinEntity);
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Gets records from data base, specified by checkin state.
	 * 
	 * @param
	 * @return JSONObject
	 * @throws JSONException
	 * */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static JSONObject getCheckinsFromDB(String state) throws JSONException {

		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(CheckinEntity.class);
		criteria.add(Restrictions.eq("state", state));
		List<CheckinEntity> checkins = (List<CheckinEntity>) criteria.list();
		session.getTransaction().commit();
		session.close();

		JSONArray jsonCollection = new JSONArray();
		JSONObject json = new JSONObject();
		for (CheckinEntity checkin : checkins)
			jsonCollection.put(convertToJSON(checkin));
		json.put(state + "s", jsonCollection);

		return json;
	}

	/**
	 * Gets records from data base, specified by checkin state and per user.
	 * 
	 * @param
	 * @return JSONObject
	 * @throws JSONException
	 * */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static JSONObject getUserCheckinsFromDB(String state, String name) throws JSONException {

		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(CheckinEntity.class);
		criteria.add(Restrictions.eq("state", state));
		criteria.add(Restrictions.eq("name", name));
		List<CheckinEntity> checkins = (List<CheckinEntity>) criteria.list();
		session.getTransaction().commit();
		session.close();

		ArrayList<String> timeList = new ArrayList<String>();
		JSONObject json = new JSONObject();
		for (CheckinEntity checkin : checkins)
			timeList.add(checkin.getTime());
		json.put("(" + name + ")" + " " + state + "s", timeList);

		return json;
	}

	
	/**
	 * Converts stored entity to JSON
	 * 
	 * @param
	 * @return JSONObject
	 * @throws JSONException
	 * */
	public static JSONObject convertToJSON(CheckinEntity checkinEntity)
			throws JSONException {

		JSONObject json = new JSONObject();
		json.put("name", checkinEntity.getName());
		json.put("time", checkinEntity.getTime());
		return json;
	}

}
