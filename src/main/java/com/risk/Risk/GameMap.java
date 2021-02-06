package com.risk.Risk;

import java.util.HashMap;

public class GameMap {
	private String d_mapName;
	private boolean d_valid;
	private HashMap<String,Continent> d_continents; 
	private HashMap<String,Country> d_countries;
	
	public GameMap() {
		d_mapName= "";
		d_continents= new HashMap<String,Continent>();
		d_countries= new HashMap<String,Country>();
		d_valid= false;
	}
	
	public GameMap(String p_mapName){
		d_mapName= p_mapName;
		d_continents= new HashMap<String,Continent>();
		d_countries= new HashMap<String,Country>();
		d_valid= false;
	}
	
	public String getMapName() {
		return d_mapName;
	}

	public void setMapName(String p_mapName) {
		d_mapName = p_mapName;
	}

	public boolean isValid() {
		return d_valid;
	}

	public void setValid(boolean p_valid) {
		d_valid = p_valid;
	}

	public HashMap<String, Continent> getContinents() {
		return d_continents;
	}

	public void setContinents(HashMap<String, Continent> p_continents) {
		d_continents = p_continents;
	}

	public HashMap<String, Country> getCountries() {
		return d_countries;
	}

	public void setCountries(HashMap<String, Country> p_countries) {
		d_countries = p_countries;
	}
	
}
