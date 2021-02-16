package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Contain logic for executing GameEngine
 * @author Rucha
 *
 */
public class RunGameEngine {
	
	/**
	 * 
	 * @param p_mapName
	 * @return
	 */
	public GameMap loadMap(String p_mapName) {
		String l_filePath = "src/main/resources/maps/" + p_mapName;
		GameMap l_gameMap;
		File l_file = new File(l_filePath);
		if(l_file.exists())
		{
			LoadMap l_loadMap = new LoadMap();
			l_gameMap = l_loadMap.readMap(l_filePath);
			l_gameMap.setMapName(p_mapName);
		}else {
			System.out.println("Map " + p_mapName + " does not exist. Try to load again or use 'editMap' to create a map.");
			return null;
		}
		return l_gameMap;
			
	}
	
	/**
	 * 
	 * @param p_mapName
	 * @return
	 */
	public GameMap editMap(String p_mapName) {
		String l_filePath = "maps/" + p_mapName;
		GameMap l_gameMap;
		File l_file = new File(l_filePath);
		if(l_file.exists()) {
			LoadMap l_loadMap = new LoadMap();
			l_gameMap = l_loadMap.readMap(l_filePath);
			l_gameMap.setMapName(p_mapName);
		}
		else {
			System.out.println(p_mapName + " does not exist.");
			System.out.println("Creating a new Map named " + p_mapName);
			l_gameMap = new GameMap(p_mapName);
		}
		return l_gameMap;
	}

	/**
	 * 
	 * @param p_map
	 * @param p_continentName
	 * @param p_controlValue
	 * @return
	 */
	public boolean addContinent(GameMap p_map, String p_continentName, int p_controlValue) {
		if(!(MapValidator.doesContinentExist(p_map, p_continentName))) {
			if(p_controlValue<0)
				return false;
			Continent l_continent = new Continent(p_continentName, p_controlValue);
			p_map.getContinents().put(p_continentName.toLowerCase(), l_continent);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param p_map
	 * @param p_continentName
	 * @return
	 */
	public boolean removeContinent(GameMap p_map, String p_continentName) {
		if(p_map.getContinents().containsKey(p_continentName.toLowerCase())) {
			Continent l_continent = p_map.getContinents().get(p_continentName.toLowerCase());
			
			//remove each country of the continent
			ArrayList<CountryDetails> l_tempList = new ArrayList<CountryDetails>();
			for(CountryDetails c : l_continent.getCountries().values()) {
				l_tempList.add(c);
			}
			Iterator<CountryDetails> itr = l_tempList.listIterator();
			while(itr.hasNext()) {
				CountryDetails c = itr.next();
				if(!removeCountry(p_map, c.getCountryName()))
					return false;
			}
			map.getContinents().remove(p_continentName.toLowerCase());
			return true;
		}
		else {
			System.out.println(p_continentName + " does not exist.");
			return false;
		}
	}
	
	/**
	 * 
	 * @param p_map
	 * @param p_countryName
	 * @param p_continentName
	 * @return
	 */
	public boolean addCountry(GameMap p_map, String p_countryName, String p_continentName) {
		if(!MapValidator.doesCountryExist(p_map, p_countryName)) {
			if(!p_map.getContinents().containsKey(p_continentName.toLowerCase())) {
				System.out.println(p_continentName + " does not exist.");
				return false;
			}
			CountryDetails l_country = new CountryDetails(p_countryName, p_continentName);
			Continent l_continent = p_map.getContinents().get(p_continentName.toLowerCase());
			l_continent.getCountries().put(p_countryName.toLowerCase(), l_country);
			p_map.getCountries().put(p_countryName.toLowerCase(), l_country);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param p_map
	 * @param p_countryName
	 * @return
	 */
	public boolean removeCountry(GameMap p_map, String p_countryName) {
		if(p_map.getCountries().containsKey(p_countryName.toLowerCase())) {
			CountryDetails l_country = p_map.getCountries().get(p_countryName.toLowerCase());
			ArrayList<CountryDetails> l_tempList = new ArrayList<CountryDetails>();
			
			for(CountryDetails l_neighbor : l_country.getNeighbours().values()) {
				l_tempList.add(l_neighbor);
			}
			Iterator<CountryDetails> l_itr = l_tempList.listIterator();
			while(l_itr.hasNext()) {
				CountryDetails l_neighbor = l_itr.next();
				if(!removeNeighbor(p_map, l_country.getCountryName(), l_neighbor.getCountryName()))
					return false;
			}
			p_map.getCountries().remove(p_countryName.toLowerCase());
			p_map.getContinents().get(l_country.getInContinent().toLowerCase()).getCountries().remove(p_countryName.toLowerCase());
			return true;
		}
		else {
			System.out.println(p_countryName + " does not exist.");
			return false;
		}
	}
	
	public void showMap(GameMap p_map) {
		if(p_map==null)
			return;
		System.out.printf("%25s%25s%35s\n", "Continents", "Country", "Country's neighbors");
		System.out.printf("%85s\n", "-------------------------------------------------------------------------------------------");
		boolean l_PrintContinentName = true;
		boolean l_PrintCountryName = true;
		for(Continent l_continent : map.getContinents().values()) {
			if(l_continent.getCountries().size()==0) {
				System.out.printf("\n%25s%25s%25s\n", l_continent.getContinentName(), "", "");
			}
			for(CountryDetails country : l_continent.getCountries().values()) {
				if(country.getNeighbours().size()==0) {
					if(l_PrintContinentName && l_PrintCountryName) {
						System.out.printf("\n%25s%25s%25s\n", l_continent.getContinentName(), country.getCountryName(), "");
						l_PrintContinentName = false;
						l_PrintCountryName = false;
					}
					else if(l_PrintCountryName) {
						System.out.printf("\n%25s%25s%25s\n", "", country.getCountryName(), "");
						l_PrintCountryName =  false;
					}
				}
				for(CountryDetails neighbor : country.getNeighbours().values()) {
					if(l_PrintContinentName && l_PrintCountryName) {
						System.out.printf("\n%25s%25s%25s\n", l_continent.getContinentName(), country.getCountryName(), neighbor.getCountryName());
						l_PrintContinentName = false;
						l_PrintCountryName = false;
					}
					else if(l_PrintCountryName) {
						System.out.printf("\n%25s%25s%25s\n", "", country.getCountryName(), neighbor.getCountryName());
						l_PrintCountryName = false;
					}
					else {
						System.out.printf("%25s%25s%25s\n", "", "", neighbor.getCountryName());
					}
				}
				l_PrintCountryName = true;
			}
			l_PrintContinentName = true;
			l_PrintCountryName = true;
		}
	}
	
}
