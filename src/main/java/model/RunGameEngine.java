package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Contain logic for executing GameEngine
 * @author Rucha
 *
 */
public class RunGameEngine {
	
	/**
	 * 
	 * @param p_mapName name of the map to be used for loading
	 * @return the existing map to be used for game play
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
			if(validateMap(l_gameMap)) {
				l_gameMap.setValid(true);
			}
			else {
				System.out.println("Map not suitable for game play. Correct the map to continue with this map or load another map from the existing maps.");
				l_gameMap.setValid(false);
			}
		}else {
			System.out.println("Map " + p_mapName + " does not exist. Try to load again or use 'editMap' to create a map.");
			return null;
		}
		return l_gameMap;
			
	}
	
	/**
	 * 
	 * @param p_mapName Name of the map to be searched/created
	 * @return object representing the existing map or null value if new one is to be created
	 */
	public GameMap editMap(String p_mapName) {
		String l_filePath = "src/main/resources/maps/" + p_mapName;
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
	 * @param p_map GameMap representing the map to which continent is to be added
	 * @param p_continentID ID of the continent to be added
	 * @param p_continentValue Continent value of the continent to be added
	 * @return true if valid entry, else false indicating invalid command
	 */
	public boolean addContinent(GameMap p_map, String p_continentID, int p_continentValue) {
		if(!(MapValidator.doesContinentExist(p_map,p_continentID))) {
			if(p_continentValue<0)
				return false;
			Continent l_continent = new Continent(p_continentID, p_continentValue);
			p_map.getContinents().put(p_continentID.toLowerCase(), l_continent);
			return true;
		}
		else {
			return false;
		}
		
	}
	
	/**
	 * 
	 * @param p_map GameMap object representing the map being edited
	 * @param p_continentID ID of the continent to be removed
	 * @return true if successful, else false indicating invalid command
	 */
	public boolean removeContinent(GameMap p_map, String p_continentID) {
		if(p_map.getContinents().containsKey(p_continentID.toLowerCase())) {
			Continent l_continent = p_map.getContinents().get(p_continentID.toLowerCase());
			
			//remove each country of the continent
			ArrayList<CountryDetails> l_tempList = new ArrayList<CountryDetails>();
			for(CountryDetails c : l_continent.getCountries().values()) {
				l_tempList.add(c);
			}
			Iterator<CountryDetails> l_itr = l_tempList.listIterator();
			while(l_itr.hasNext()) {
				CountryDetails c = l_itr.next();
				if(!removeCountry(p_map, c.getCountryId()))
					return false;
			}
			p_map.getContinents().remove(p_continentID.toLowerCase());
			return true;
		}
		else {
			System.out.println(p_continentID + " does not exist.");
			return false;
		}
	}
	
	/**
	 * 
	 * @param p_map GameMap representing the map to which country is to be added
	 * @param p_countryID ID of the country which is to be added
	 * @param p_continentID ID of the continent to which this new country belongs
	 * @return true if valid entry, else false indicating invalid command
	 */
	public boolean addCountry(GameMap p_map, String p_countryID, String p_continentID) {
		if(!MapValidator.doesCountryExist(p_map, p_countryID)) {
			if(!p_map.getContinents().containsKey(p_continentID.toLowerCase())) {
				System.out.println(p_continentID + " does not exist.");
				return false;
			}
			CountryDetails l_country = new CountryDetails(p_countryID, p_continentID);
			Continent l_continent = p_map.getContinents().get(p_continentID.toLowerCase());
			l_continent.getCountries().put(p_countryID.toLowerCase(), l_country);
			p_map.getCountries().put(p_countryID.toLowerCase(), l_country);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param p_map GameMap object representing the map being edited
	 * @param p_countryID Name of the country to be removed
	 * @return true if successful, else false indicating invalid command
	 */
	public boolean removeCountry(GameMap p_map, String p_countryID) {
		if(p_map.getCountries().containsKey(p_countryID.toLowerCase())) {
			CountryDetails l_country = p_map.getCountries().get(p_countryID.toLowerCase());
			ArrayList<CountryDetails> l_tempList = new ArrayList<CountryDetails>();
			
			for(CountryDetails l_neighbor : l_country.getNeighbours().values()) {
				l_tempList.add(l_neighbor);
			}
			Iterator<CountryDetails> l_itr = l_tempList.listIterator();
			while(l_itr.hasNext()) {
				CountryDetails l_neighbor = l_itr.next();
				if(!removeNeighbor(p_map, l_country.getCountryId(), l_neighbor.getCountryId()))
					return false;
			}
			p_map.getCountries().remove(p_countryID.toLowerCase());
			p_map.getContinents().get(l_country.getInContinent().toLowerCase()).getCountries().remove(p_countryID.toLowerCase());
			return true;
		}
		else {
			System.out.println(p_countryID + " does not exist.");
			return false;
		}
	}
	
	public boolean addNeighbor(GameMap map, String countryID, String neighborCountryID) {
		//Check if both the country exists
		if(map.getCountries().containsKey(countryID.toLowerCase()) && map.getCountries().containsKey(neighborCountryID.toLowerCase())) {
			CountryDetails c1 = map.getCountries().get(countryID.toLowerCase());
			CountryDetails c2 = map.getCountries().get(neighborCountryID.toLowerCase());
			if(!c1.getNeighbours().containsKey(c2.getCountryId().toLowerCase()))
				c1.getNeighbours().put(neighborCountryID.toLowerCase(), c2);
			if(!c2.getNeighbours().containsKey(c1.getCountryId().toLowerCase()))
				c2.getNeighbours().put(countryID.toLowerCase(), c1);
			return true;
		}
		else {
			if(!map.getCountries().containsKey(countryID.toLowerCase()) && !map.getCountries().containsKey(neighborCountryID.toLowerCase()))
				System.out.println(countryID + " and " + neighborCountryID + "  does not exist. Create country first and then set their neighbors.");
			else if(!map.getCountries().containsKey(countryID.toLowerCase()))
				System.out.println(countryID + " does not exist. Create country first and then set its neighbors.");
			else
				System.out.println(neighborCountryID + " does not exist. Create country first and then set its neighbors.");
			return false;
		}
	}
	
	public boolean removeNeighbor(GameMap map, String countryID, String neighborCountryID) {
		//Check if both the country exists
		if(map.getCountries().containsKey(countryID.toLowerCase()) && map.getCountries().containsKey(neighborCountryID.toLowerCase())) {
			CountryDetails c1 = map.getCountries().get(countryID.toLowerCase());
			CountryDetails c2 = map.getCountries().get(neighborCountryID.toLowerCase());
			c1.getNeighbours().remove(neighborCountryID.toLowerCase());
			c2.getNeighbours().remove(countryID.toLowerCase());
			return true;
		}
		else {
			if(!map.getCountries().containsKey(countryID.toLowerCase()) && !map.getCountries().containsKey(neighborCountryID.toLowerCase()))
				System.out.println(countryID + " and " + neighborCountryID + "  does not exist.");
			else if(!map.getCountries().containsKey(countryID.toLowerCase()))
				System.out.println(countryID + " does not exist.");
			else
				System.out.println(neighborCountryID + " does not exist.");
			return false;
		}
	}
	
	/**
	 * 
	 * @param p_map GameMap object representing the map to be shown.
	 */
	public void showMap(GameMap p_map) {
		if(p_map==null)
			return;
		System.out.printf("%85s\n", "-------------------------------------------------------------------------------------------");
		System.out.printf("%25s%25s%35s\n", "Continents", "Country", "Country's neighbors");
		System.out.printf("%85s\n", "-------------------------------------------------------------------------------------------");
		boolean l_PrintContinentName = true;
		boolean l_PrintCountryName = true;
		for(Continent l_continent : p_map.getContinents().values()) {
			if(l_continent.getCountries().size()==0) {
				System.out.printf("\n%25s%25s%25s\n", l_continent.getContinentId(), "", "");
			}
			for(CountryDetails country : l_continent.getCountries().values()) {
				if(country.getNeighbours().size()==0) {
					if(l_PrintContinentName && l_PrintCountryName) {
						System.out.printf("\n%25s%25s%25s\n", l_continent.getContinentId(), country.getCountryId(), "");
						l_PrintContinentName = false;
						l_PrintCountryName = false;
					}
					else if(l_PrintCountryName) {
						System.out.printf("\n%25s%25s%25s\n", "", country.getCountryId(), "");
						l_PrintCountryName =  false;
					}
				}
				for(CountryDetails neighbor : country.getNeighbours().values()) {
					if(l_PrintContinentName && l_PrintCountryName) {
						System.out.printf("\n%25s%25s%25s\n", l_continent.getContinentId(), country.getCountryId(), neighbor.getCountryId());
						l_PrintContinentName = false;
						l_PrintCountryName = false;
					}
					else if(l_PrintCountryName) {
						System.out.printf("\n%25s%25s%25s\n", "", country.getCountryId(), neighbor.getCountryId());
						l_PrintCountryName = false;
					}
					else {
						System.out.printf("%25s%25s%25s\n", "", "", neighbor.getCountryId());
					}
				}
				l_PrintCountryName = true;
			}
			l_PrintContinentName = true;
			l_PrintCountryName = true;
		}
	}
	
	public boolean saveMap(GameMap p_map, String p_fileName) {
		//Check if map is valid or not 
		if(validateMap(p_map)) {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/maps/"+p_fileName+ ".map"));
				int l_continentIndex = 1;	//to track continent index in "map" file
				int l_countryIndex = 1; //to track country index in "map" file
				HashMap<Integer, String> indexToCountry = new HashMap<Integer, String>(); //to get in country name corresponding to in map index to be in compliance with Domination format
				HashMap<String, Integer> countryToIndex = new HashMap<String, Integer>(); //to get in map index to be in compliance with Domination format
				
				//write preliminary basic information
				writer.write("name " + p_fileName + " Map");
				writer.newLine();
				writer.newLine();
				writer.write("[files]");
				writer.newLine();
				writer.newLine();
				//writer.newLine();
				writer.flush();
				
				//write information about all the continents
				writer.write("[continents]");
				writer.newLine();
				for(Continent l_continent : p_map.getContinents().values()) {
					writer.write(l_continent.getContinentId() + " " + Integer.toString(l_continent.getControlValue())+ " " + l_continent.getContinentColor());
					writer.newLine();
					writer.flush();
					l_continent.setInMapIndex(l_continentIndex);
					l_continentIndex++;
				}
				writer.newLine();
				
				//write information about all the countries
				writer.write("[countries]");
				writer.newLine();
				for(CountryDetails l_country : p_map.getCountries().values()) {
					writer.write(Integer.toString(l_countryIndex) + " " + l_country.getCountryId() + " " + Integer.toString(p_map.getContinents().get(l_country.getInContinent().toLowerCase()).getInMapIndex()) + " " + l_country.getxCoOrdinate() + " " + l_country.getyCoOrdinate());
					writer.newLine();
					writer.flush();
					indexToCountry.put(l_countryIndex, l_country.getCountryId().toLowerCase());
					countryToIndex.put(l_country.getCountryId().toLowerCase(), l_countryIndex);
					l_countryIndex++;
				}
				writer.newLine();
				//countryIndex = 1;
				
				//write information about all the borders
				writer.write("[borders]");
				writer.newLine();
				//writer.newLine();
				writer.flush();
				for(int i=1;i<l_countryIndex;i++) {
					String l_countryID = indexToCountry.get(i);
					CountryDetails c = p_map.getCountries().get(l_countryID.toLowerCase());
					writer.write(Integer.toString(i) + " ");
					for(CountryDetails l_neighbor : c.getNeighbours().values()) {
						writer.write(Integer.toString(countryToIndex.get(l_neighbor.getCountryId().toLowerCase())) + " ");
						writer.flush();
					}
					writer.newLine();
				}
			}
			catch(IOException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
		else
		{
			System.out.println("Map not suitable for game play. Correct the map to continue with the new map or load a map from the existing maps.");
			return false;
		}
	}
	
	public boolean validateMap(GameMap p_map) {
		MapValidator l_mv = new MapValidator();
		if(!l_mv.notEmptyContinent(p_map)) {
			System.out.println("Invalid map - emtpy continent present.");
			return false;
		}
		else if(!l_mv.isGraphConnected(l_mv.createGraph(p_map))) {
			System.out.println("Invalid map - not a connected graph");
			return false;
		}
		else if(!l_mv.continentConnectivityCheck(p_map)) {
			System.out.println("Invalid map - one of the continent is not a connected sub-graph");
			return false;
		}
		
		return true;
	}
	
}
