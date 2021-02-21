package model;

import java.util.HashMap;

/**
 * GameMap hold the details of map in the game.
 * 
 * Consist of HashMaps to access countries and continents of the map with their names.
 * @author Rucha
 *
 */
public class GameMap {
	private String d_mapName;
	private boolean d_valid;
	private HashMap<String,Continent> d_continents; 
	private HashMap<String,CountryDetails> d_countries;
	
	/**
	 * Create GameMap object without naming the map.
	 */
	public GameMap() {
		this.d_mapName= "";
		this.d_continents= new HashMap<String,Continent>();
		this.d_countries= new HashMap<String,CountryDetails>();
		this.d_valid= false;
	}
	
	/**
	 * Register name of the map.
	 * Initialize HashMaps for maintaining continents and countries.
	 * @param p_mapName Name of map
	 */
	public GameMap(String p_mapName){
		this.d_mapName= p_mapName;
		this.d_continents= new HashMap<String,Continent>();
		this.d_countries= new HashMap<String,CountryDetails>();
		this.d_valid= false;
	}
	
	/**
	 * Return name of the map.
	 * @return return name of the map
	 */
	public String getMapName() {
		return this.d_mapName;
	}

	/**
	 * Set name of the map to the given name.
	 * @param p_mapName Name of the map
	 */
	public void setMapName(String p_mapName) {
		this.d_mapName = p_mapName;
	}

	/**
	 * Getter method to fetch valid variable.
	 * @return return whether the map is valid for game play or not
	 */
	public boolean getValid() {
		return this.d_valid;
	}

	/**
	 * Setter method to set status for validity of the map.
	 * @param p_valid Indicate whether the map is valid for game play or not
	 */
	public void setValid(boolean p_valid) {
		this.d_valid = p_valid;
	}

	/**
	 * Return HashMap maintaining the list of continents in the map.
	 * @return return HashMap maintaining the list of continents in the map.
	 */
	public HashMap<String, Continent> getContinents() {
		return this.d_continents;
	}

	/**
	 * Sets the d_continents HashMap to the given HashMap parameter.
	 * @param p_continents HashMap for d_continents
	 */
	public void setContinents(HashMap<String, Continent> p_continents) {
		this.d_continents = p_continents;
	}

	/**
	 * Return HashMap maintaining the list of countries in the map.
	 * @return return HashMap maintaining the list of countries in the map
	 */
	public HashMap<String, CountryDetails> getCountries() {
		return this.d_countries;
	}

	/**
	 * Sets the d_countries HashMap to the given HashMap parameter.
	 * @param p_countries HashMap for d_countries
	 */
	public void setCountries(HashMap<String, CountryDetails> p_countries) {
		this.d_countries = p_countries;
	}
	
}
