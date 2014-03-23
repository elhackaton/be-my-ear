package com.chustaware.bemyear;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Sample {
	
	private int id;
	private String name;
	private String icon;
	private ArrayList<Double> pattern;
	private boolean enabled;
	
	public Sample(int id, String nombre, String icon, ArrayList<Double> pattern, boolean enabled) {
		this.id = id;
		this.name = nombre;
		this.icon = icon;
		this.pattern = pattern;
		this.enabled = enabled;
	}
	
	public Sample(int id, String nombre, String icon, String pattern, boolean enabled) {
		this.id = id;
		this.name = nombre;
		this.icon = icon;
		this.pattern = stringToArrayList(pattern);
		this.enabled = enabled;
	}
	
	public Sample(ArrayList<Double> pattern) {
		this.name = "";
		this.icon = "";
		this.pattern = pattern;
		this.enabled = true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getIcon() {
		return icon;
	}

	public void setIcono(String icon) {
		this.icon = icon;
	}

	public ArrayList<Double> getPattern() {
		return pattern;
	}
	
	public String getPatternAsString() {
		return arrayListToString(this.pattern);
	}
	
	public void setPattern(String pattern) {
		this.pattern = stringToArrayList(pattern);	
	}
	
	private String arrayListToString(ArrayList<Double> pattern) {
		JSONObject json = new JSONObject();
		
		try {
			json.put("pattern", new JSONArray(this.pattern));
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
		return json.toString();
	}
	
	private ArrayList<Double> stringToArrayList(String pattern) {
		
		JSONObject json = null;
		JSONArray items = null;
		ArrayList<Double> doubles = null;
		try {
			json = new JSONObject(pattern);
			items = json.optJSONArray("pattern");
			doubles = new ArrayList<Double>();

			for (int i=0; i<items.length(); i++) {
				doubles.add(items.getDouble(i));
			}
			return doubles;
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setPattern(ArrayList<Double> pattern) {
		this.pattern = pattern;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
