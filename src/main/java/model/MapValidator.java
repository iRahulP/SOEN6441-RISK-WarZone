package model;

/**
 * Validation of map takes place in this class
 *
 */
public class MapValidator {

	public static boolean doesContinentExist(GameMap map, String continentName) {
		if(map.getContinents().containsKey(continentName.toLowerCase()))
			return true;
		else
			return false;
	}
}
