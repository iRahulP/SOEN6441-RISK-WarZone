package model;

/**
 * Validation of map takes place in this class
 *
 */
public class MapValidator {

	/**
	 * Check if the same continent already exist
	 * @param p_map GameMap object holding record of all the existing continents and countries
	 * @param p_continentName name of the continent to be checked
	 * @return true if continent already exists, else false
	 */
	public static boolean doesContinentExist(GameMap p_map, String p_continentName) {
		if(p_map.getContinents().containsKey(p_continentName.toLowerCase()))
			return true;
		else
			return false;
	}

	/**
	 * Check if the same country already exist
	 * @param p_map GameMap object holding record of all the existing continents and countries
	 * @param p_countryName name of the country to be checked
	 * @return true if country already exists, else false
	 */
	public static boolean doesCountryExist(GameMap p_map, String p_countryName) {
		if(p_map.getCountries().containsKey(p_countryName.toLowerCase()))
			return true;
		else
			return false;
	}

}
