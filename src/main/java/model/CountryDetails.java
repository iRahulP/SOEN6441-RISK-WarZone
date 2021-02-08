package model;

import java.util.HashMap;

import main.java.GameMap;

/**
 * CountryDetails class have all the details of countries related to the selected .map file.
 * It also maintains a list of it's neighboring Countries.
 *
 */
public class CountryDetails {

	private int d_index;
	private String d_countryName;
	private String d_inContinent;
	private HashMap<String, CountryDetails> d_neighbours;
	private int d_xCoOrdinate;
	private int d_yCoOrdinate;
	
	public CountryDetails(){}
	
	public CountryDetails(String p_countryName, String p_inContinent){
		this.d_countryName = p_countryName;
		this.d_inContinent = p_inContinent;
		this.d_neighbours = new HashMap<String, CountryDetails>();
	}
	
	public CountryDetails(String p_index, String p_countryName, String p_continentIndex, String p_xCoOrdinate, String p_yCoOrdinate, GameMap map){
		this.d_index = Integer.parseInt(p_index);
		this.d_countryName = p_countryName;
		for(Continent c : map.getContinents().values()) {
			if(c.getInMapIndex()==Integer.parseInt(p_continentIndex)) {
				this.d_inContinent = c.getContinentName();
				//break;
			}	
		}	
		this.d_neighbours = new HashMap<String, CountryDetails>();
		this.d_xCoOrdinate = Integer.parseInt(p_xCoOrdinate);
		this.d_yCoOrdinate = Integer.parseInt(p_yCoOrdinate);
	}

	public int getIndex() {
		return d_index;
	}
	
	public String getInContinent() {
		return this.d_inContinent;
	}
	
	public String getCountryName() {
		return d_countryName;
	}
	
	public HashMap<String, CountryDetails> getNeighbours(){
		return this.d_neighbours;
	}

	public int getxCoOrdinate() {
		return this.d_xCoOrdinate;
	}
	
	public void setxCoOrdinate(int p_xCoOrdinate) {
		this.d_xCoOrdinate = p_xCoOrdinate;
	}
	
	public int getyCoOrdinate() {
		return this.d_yCoOrdinate;
	}

	public void setyCoOrdinate(int p_yCoOrdinate) {
		this.d_yCoOrdinate = p_yCoOrdinate;
	}
	
	@Override
	public String toString() {
		return "Country [countryName=" + d_countryName + ", xCoOrdinate=" + d_xCoOrdinate + ", yCoOrdinate=" + d_yCoOrdinate
				+ "]";
	}
	
}
