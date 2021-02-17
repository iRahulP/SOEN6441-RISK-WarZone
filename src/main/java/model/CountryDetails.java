package model;

import java.util.HashMap;
/**
 * CountryDetails class have all the details of countries related to the selected .map file.
 * It also maintains a list of it's neighboring Countries.
 * 
 * @author Shravya
 *
 */
public class CountryDetails {

	private int d_index;
	private String d_countryName;
	private String d_inContinent;
	private HashMap<String, CountryDetails> d_neighbours;
	private int d_xCoOrdinate;
	private int d_yCoOrdinate;
	private int d_numberOfArmies;

	/**
	 * Set CountryDetails object with default values.
	 */
	public CountryDetails(){}
	
	/**
	 * Create CountryDetails object with values in argument parameters and set defaults for the rest.
	 * @param p_countryName Name of the country
	 * @param p_inContinent Name of the continent in which this country belongs
	 */
	public CountryDetails(String p_countryName, String p_inContinent){
		this.d_countryName = p_countryName;
		this.d_inContinent = p_inContinent;
		this.d_neighbours = new HashMap<String, CountryDetails>();
		this.d_numberOfArmies = 0;
	}
	
	/**
	 * Creates CountryDetails object as per the argument parameters.
	 * This constructor used while reading from ".map" files.
	 * @param p_index index in the ".map" file as per Domination's conventions
	 * @param p_countryName name of the country
	 * @param p_continentIndex index of the continent this country belongs to
	 * @param p_xCoOrdinate x-coordinate on GUI map
	 * @param p_yCoOrdinate y-coordinate on GUI map
	 * @param p_map GameMap in which this country resides
	 */
	public CountryDetails(String p_index, String p_countryName, String p_continentIndex, String p_xCoOrdinate, String p_yCoOrdinate, GameMap p_map){
		this.d_index = Integer.parseInt(p_index);
		this.d_countryName = p_countryName;
		for(Continent c : p_map.getContinents().values()) {
			if(c.getContinentId()==Integer.parseInt(p_continentIndex)) {
				this.d_inContinent = c.getContinentName();
				//break;
			}	
		}	
		this.d_neighbours = new HashMap<String, CountryDetails>();
		this.d_xCoOrdinate = Integer.parseInt(p_xCoOrdinate);
		this.d_yCoOrdinate = Integer.parseInt(p_yCoOrdinate);
		this.d_numberOfArmies = 0;
	}

	/**
	 * Returns the index of this country in the ".map" file
	 * @return returns d_index of this country in the ".map" file
	 */
	public int getIndex() {
		return d_index;
	}
	
	/**
	 * Returns the name of the Continent in which this country belongs
	 * @return returns d_inContinent in which this country belongs
	 */

	public String getInContinent() {
		return this.d_inContinent;
	}
	
	/**
	 * Returns the name of the country
	 * @return returns d_countryName
	 */
	public String getCountryName() {
		return d_countryName;
	}
	
	/**
	 * Returns the HashMap holding all the neighbors with their names in lower case as keys and their 
	 * CountryDetails object references as values.
	 * @return returns d_neighbors of this country
	 */
	public HashMap<String, CountryDetails> getNeighbours(){
		return this.d_neighbours;
	}

	/**
	 * Getter method to fetch x-coordinate value
	 * @return returns d_xCoOrdinate
	 */
	public int getxCoOrdinate() {
		return this.d_xCoOrdinate;
	}
	
	/**
	 * Setter method to fetch x-coordinate
	 * @param p_xCoOrdinate argument d_xCoOrdinate value to be set
	 */
	public void setxCoOrdinate(int p_xCoOrdinate) {
		this.d_xCoOrdinate = p_xCoOrdinate;
	}
	
	/**
	 * Getter method to fetch y-coordinate value
	 * @return returns d_yCoOrdinate
	 */
	public int getyCoOrdinate() {
		return this.d_yCoOrdinate;
	}

	/**
	 * Setter method to fetch y-coordinate
	 * @param p_yCoOrdinate argument d_yCoOrdinate value to be set
	 */
	public void setyCoOrdinate(int p_yCoOrdinate) {
		this.d_yCoOrdinate = p_yCoOrdinate;
	}
	
	/**
	 * Getter method to get number of armies in the country.
	 * @return returns d_numberOfArmies
	 */
	public int getNumberOfArmies() {
		return this.d_numberOfArmies;
	}

	/**
	 * Set number of armies in the country
	 * @param p_numberOfArmies number of armies to be set in the country
	 */
	public void setNumberOfArmies(int p_numberOfArmies) {
		this.d_numberOfArmies = p_numberOfArmies;
	}

	
	@Override
	public String toString() {
		return "Country [countryName=" + d_countryName + ", xCoOrdinate=" + d_xCoOrdinate + ", yCoOrdinate=" + d_yCoOrdinate
				+ "]";
	}
	
}
