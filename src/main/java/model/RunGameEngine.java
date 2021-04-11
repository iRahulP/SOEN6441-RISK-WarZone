package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


import view.MapView;

/**
 * It contains logic for executing GameEngine
 * @author Rucha
 */
public class RunGameEngine {
	
	/**
	 * mv Reference for MapView.
	 */
	MapView mv;
	
	/**
	 * Load map for playing the game.
	 * It checks whether map file exist or not.
	 * @param p_mapName name of the map to be used for playing the game.
	 * @return l_gameMap represents the existing map to be used for playing the game.
	 */
	public GameMap loadMap(String p_mapName) {
		//check if file exists
		String l_filePath = "src/main/resources/maps/" + p_mapName;
		GameMap l_gameMap;
		String l_MapType;
		DominationMap l_domMap;
		File l_file = new File(l_filePath);
		if(l_file.exists())
		{
			LoadMap l_loadMap = new LoadMap();
			l_MapType = l_loadMap.readMap(l_filePath);
			System.out.println("MapType: "+ l_MapType);
			if(l_MapType.equals("domination")) {
				l_domMap= new DominationMap();
			}
			else {
				l_domMap= new MapAdapter(new ConquestMap());
			}
			l_gameMap= l_domMap.readMap(l_filePath);
			l_gameMap.setMapName(p_mapName);
			if(validateMap(l_gameMap)) {
				l_gameMap.setValid(true);
			}
			else {
				System.out.println("Map is not suitable for playing . Correct the map to continue with this map or load another map from the existing maps.");
				l_gameMap.setValid(false);
			}
		}else {
			System.out.println("Map " + p_mapName + " does not exist. Try to load again or use 'editMap' to create a map.");
			return null;
		}
		return l_gameMap;	
	}
	
	/**
	 * Load map for editing if it exist.
	 * If the map file does not exist, creates a new GameMap object to add information.
	 * @param p_mapName Name of the map to be searched/created
	 * @return l_gameMap represents the existing map or null value if new one is to be created
	 */
	public GameMap editMap(String p_mapName) {
		//check if file exists
		String l_filePath = "src/main/resources/maps/" + p_mapName;
		GameMap l_gameMap;
		DominationMap dm = new DominationMap();
		File l_file = new File(l_filePath);
		if(l_file.exists()) {
			System.out.println(p_mapName+" exist and you can edit it.");
			LoadMap l_loadMap = new LoadMap();
			l_gameMap = dm.readMap(l_filePath);
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
	 * Add continent with given name and control value if it is valid and does not exist.
	 * Invalid if continent already exists.
	 * @param p_map the map to which continent is to be added
	 * @param p_continentID ID of the continent to be added
	 * @param p_continentValue Continent value of the continent to be added
	 * @return true if successful, else false indicating invalid command
	 */
	public boolean addContinent(GameMap p_map, String p_continentID, int p_continentValue) {
		//check if continent already exists
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
	 * Remove the continent and all the countries present in that continent.
	 * Remove neighboring node for all the countries that are being removed.
	 * @param p_map represent the map being edited
	 * @param p_continentID ID of the continent to be removed
	 * @return true if successful, else false indicating invalid command
	 */
	public boolean removeContinent(GameMap p_map, String p_continentID) {
		//check if continent exists or not
		if(p_map.getContinents().containsKey(p_continentID.toLowerCase())) {
			Continent l_continent = p_map.getContinents().get(p_continentID.toLowerCase());
			
			//remove each country of the continent
			ArrayList<CountryDetails> l_tempList = new ArrayList<CountryDetails>();
			for(CountryDetails l_cd : l_continent.getCountries().values()) {
				l_tempList.add(l_cd);
			}
			Iterator<CountryDetails> l_itr = l_tempList.listIterator();
			while(l_itr.hasNext()) {
				CountryDetails l_c = l_itr.next();
				if(!removeCountry(p_map, l_c.getCountryId()))
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
	 * Add country with given name  to the corresponding given continent in the map if it is valid.
	 * Invalid if country already exists.
	 * @param p_map map to which country is to be added
	 * @param p_countryID ID of the country which is to be added
	 * @param p_continentID ID of the continent to which new country is added.
	 * @return true if successful, else false indicating invalid command
	 */
	public boolean addCountry(GameMap p_map, String p_countryID, String p_continentID) {
		//check if country exists or not
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
	 * Remove the country and remove all its neighbors.
	 * @param p_map represent the map being edited
	 * @param p_countryID ID of the country to be removed
	 * @return true if successful, else false indicating invalid command
	 */
	public boolean removeCountry(GameMap p_map, String p_countryID) {
		//check if country exists or not
		if(p_map.getCountries().containsKey(p_countryID.toLowerCase())) {
			CountryDetails l_country = p_map.getCountries().get(p_countryID.toLowerCase());
			ArrayList<CountryDetails> l_tempList = new ArrayList<CountryDetails>();
			
			//remove each neighbor this country
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
	
	/**
	 * Adds link between the two argument countries and made the neighbor.
	 * @param p_map represent map being edited
	 * @param p_countryID ID of one argument country
	 * @param p_neighborCountryID ID of other argument country
	 * @return true if successful, else false indicating invalid command
	 */
	public boolean addNeighbor(GameMap p_map, String p_countryID, String p_neighborCountryID) {
		//Check if both the country exists
		if(p_map.getCountries().containsKey(p_countryID.toLowerCase()) && p_map.getCountries().containsKey(p_neighborCountryID.toLowerCase())) {
			CountryDetails l_c1 = p_map.getCountries().get(p_countryID.toLowerCase());
			CountryDetails l_c2 = p_map.getCountries().get(p_neighborCountryID.toLowerCase());
			//check if both countries are neighbor to each other or not
			if(!l_c1.getNeighbours().containsKey(l_c2.getCountryId().toLowerCase())) {
				l_c1.getNeighbours().put(p_neighborCountryID.toLowerCase(), l_c2);
				System.out.println(p_countryID+" added as neighbor to "+p_neighborCountryID);
			}
			else 
				System.out.println("already neighbor");
			if(!l_c2.getNeighbours().containsKey(l_c1.getCountryId().toLowerCase())) {
				l_c2.getNeighbours().put(p_countryID.toLowerCase(), l_c1);
				System.out.println(p_neighborCountryID+" added as neighbor to "+p_countryID);
			}
			else 
				System.out.println("already neighbor");
			return true;
		}
		else {
			if(!p_map.getCountries().containsKey(p_countryID.toLowerCase()) && !p_map.getCountries().containsKey(p_neighborCountryID.toLowerCase()))
				System.out.println(p_countryID + " and " + p_neighborCountryID + "  does not exist. Create country first and then set their neighbors.");
			else if(!p_map.getCountries().containsKey(p_countryID.toLowerCase()))
				System.out.println(p_countryID + " does not exist. Create country first and then set its neighbors.");
			else
				System.out.println(p_neighborCountryID + " does not exist. Create country first and then set its neighbors.");
			return false;
		}
	}
	
	/**
	 * Removes the link between the two neighboring Country
	 * @param p_map represent map being edited
	 * @param p_countryID ID of one argument country
	 * @param p_neighborCountryID ID of other argument country
	 * @return true if successful, else false indicating invalid command
	 */
	public boolean removeNeighbor(GameMap p_map, String p_countryID, String p_neighborCountryID) {
		//Check if both the country exists
		if(p_map.getCountries().containsKey(p_countryID.toLowerCase()) && p_map.getCountries().containsKey(p_neighborCountryID.toLowerCase())) {
			CountryDetails l_c1 = p_map.getCountries().get(p_countryID.toLowerCase());
			CountryDetails l_c2 = p_map.getCountries().get(p_neighborCountryID.toLowerCase());
			//check if both countries are neighbor to each other
			if(l_c1.getNeighbours().containsKey(l_c2.getCountryId().toLowerCase())) {
				l_c1.getNeighbours().remove(p_neighborCountryID.toLowerCase());
				System.out.println(p_countryID+" remove as neighbor to "+p_neighborCountryID);
			}
			if(l_c2.getNeighbours().containsKey(l_c1.getCountryId().toLowerCase())) {
				l_c2.getNeighbours().remove(p_countryID.toLowerCase());
				System.out.println(p_neighborCountryID+" remove as neighbor to "+p_countryID);
			}
			return true;
		}
		else {
			if(!p_map.getCountries().containsKey(p_countryID.toLowerCase()) && !p_map.getCountries().containsKey(p_neighborCountryID.toLowerCase()))
				System.out.println(p_countryID + " and " + p_neighborCountryID + "  does not exist.");
			else if(!p_map.getCountries().containsKey(p_countryID.toLowerCase()))
				System.out.println(p_countryID + " does not exist.");
			else
				System.out.println(p_neighborCountryID + " does not exist.");
			return false;
		}
	}
	
	/**
	 * print the continents, countries and each country's neighbor in the map
	 * @param p_map the map to be shown.
	 */
	public void showMap(GameMap p_map) {
		mv.showMap(p_map);
		mv=new MapView();
	}
	
	/**
	 * save GameMap as ".map" file using Domination game format.
	 * @param p_map The map to be saved
	 * @param p_fileName name of the file 
	 * @return true if successful, else false indicating invalid command
	 */
	public boolean saveMap(GameMap p_map, String p_fileName) {
		//Check if map is valid or not 
		if(validateMap(p_map)) {
			try {
				BufferedWriter l_writer = new BufferedWriter(new FileWriter("src/main/resources/maps/"+p_fileName+ ".map"));
				int l_continentIndex = 1;	 //to track continent index in "map" file
				int l_countryIndex = 1;		 //to track country index in "map" file
				HashMap<Integer, String> l_indexToCountry = new HashMap<Integer, String>(); //get country name corresponding to map index to be in compliance with Domination format
				HashMap<String, Integer> l_countryToIndex = new HashMap<String, Integer>(); //get map index to be in compliance with Domination format
				
				//write basic information
				l_writer.write("name " + p_fileName + " Map");
				l_writer.newLine();
				l_writer.newLine();
				l_writer.write("[files]");
				l_writer.newLine();
				l_writer.newLine();
				l_writer.flush();
				
				//write information about all the continents
				l_writer.write("[continents]");
				l_writer.newLine();
				for(Continent l_continent : p_map.getContinents().values()) {
					l_writer.write(l_continent.getContinentId() + " " + Integer.toString(l_continent.getControlValue())+ " " + l_continent.getContinentColor());
					l_writer.newLine();
					l_writer.flush();
					l_continent.setInMapIndex(l_continentIndex);
					l_continentIndex++;
				}
				l_writer.newLine();
				
				//write information about all the countries
				l_writer.write("[countries]");
				l_writer.newLine();
				for(CountryDetails l_country : p_map.getCountries().values()) {
					l_writer.write(Integer.toString(l_countryIndex) + " " + l_country.getCountryId() + " " + Integer.toString(p_map.getContinents().get(l_country.getInContinent().toLowerCase()).getInMapIndex()) + " " + l_country.getxCoOrdinate() + " " + l_country.getyCoOrdinate());
					l_writer.newLine();
					l_writer.flush();
					l_indexToCountry.put(l_countryIndex, l_country.getCountryId().toLowerCase());
					l_countryToIndex.put(l_country.getCountryId().toLowerCase(), l_countryIndex);
					l_countryIndex++;
				}
				l_writer.newLine();
				
				//write information about all the borders
				l_writer.write("[borders]");
				l_writer.newLine();
				l_writer.flush();
				for(int i=1;i<l_countryIndex;i++) {
					String l_countryID = l_indexToCountry.get(i);
					CountryDetails l_cd = p_map.getCountries().get(l_countryID.toLowerCase());
					l_writer.write(Integer.toString(i) + " ");
					for(CountryDetails l_neighbor : l_cd.getNeighbours().values()) {
						l_writer.write(Integer.toString(l_countryToIndex.get(l_neighbor.getCountryId().toLowerCase())) + " ");
						l_writer.flush();
					}
					l_writer.newLine();
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
	
	/**
     * Saves risk game.
     * @param p_game Represents the state of the game.
     * @param p_fileName Name of the file saving the game.
     */
    public void saveGame(GameData  p_game, String p_fileName){
        GameDataBuilder l_gameDataBuilder = new GameDataBuilder();
        l_gameDataBuilder.setMap(p_game.getMap());
        l_gameDataBuilder.setMapType(p_game.getMapType());
        l_gameDataBuilder.setGamePhase(p_game.getGamePhase());
        l_gameDataBuilder.setPlayers(p_game.getPlayers());
        l_gameDataBuilder.setActivePlayer(p_game.getActivePlayer());
        l_gameDataBuilder.setDeck(p_game.getDeck());
        l_gameDataBuilder.setCardsDealt((p_game.getCardsDealt()));

        try{
            FileOutputStream l_f = new FileOutputStream(new File("src/main/resources/game/" + p_fileName));
            ObjectOutputStream l_o = new ObjectOutputStream((l_f));
            l_o.writeObject(l_gameDataBuilder);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Loads risk game.
     * @param p_fileName Name of the file to be loaded.
     * @return GameDataBuilder object to build GameData object.
     */
    public GameDataBuilder loadGame(String p_fileName){
        GameDataBuilder l_gameDataBuilder;
        try{
            FileInputStream l_f = new FileInputStream((new File("src/main/resources/game/" + p_fileName)));
            ObjectInputStream l_o = new ObjectInputStream((l_f));
            l_gameDataBuilder = (GameDataBuilder) l_o.readObject();
        } catch(FileNotFoundException e){
            return null;
        } catch(IOException e) {
            return null;
        } catch (ClassNotFoundException e){
            return null;
        }
        return l_gameDataBuilder;
    }
	
	/**
	 * It consist of various validity checks to ensure that map is suitable for playing the game.
	 * Checks:
	 * 	1) any empty continent is present or not, i.e. continent without any country
	 * 	2) map for the game is connected graph or not
	 * 	3) each continent in map is a connected sub-graph or not
	 * @param p_map GameMap to be be checked.
	 * @return return true if map is valid, else false indicate invalid map
	 */
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
