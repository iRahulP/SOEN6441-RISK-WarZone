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
import java.util.Scanner;

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
	DominationMap l_domMap;
	
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
			l_gameMap= l_domMap.readDominationMap(l_filePath);
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
		String l_MapType;
		LoadMap l_loadMap = new LoadMap();
		File l_file = new File(l_filePath);
		if(l_file.exists()) {
			l_MapType = l_loadMap.readMap(l_filePath);
			System.out.println("MapType: "+ l_MapType);
			System.out.println(p_mapName+" exist and you can edit it.");
			if(l_MapType.equals("domination")) {
				l_domMap= new DominationMap();
			}
			else {
				l_domMap= new MapAdapter(new ConquestMap());
			}
			l_gameMap= l_domMap.readDominationMap(l_filePath);
			l_gameMap.setMapName(p_mapName);
		}
		else {
			l_loadMap.setMapType("domination");
			System.out.println("MapType: "+ l_loadMap.getMapType());
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
		System.out.println("Type \"domination\" or \"conquest\" to save the map in respective format");
		Scanner sc= new Scanner(System.in);
		String cmd= sc.nextLine();
		boolean result;
		if(cmd.equals("domination")) {
			l_domMap= new DominationMap();	
		}
		else {
			l_domMap= new MapAdapter(new ConquestMap());
		}
		result= l_domMap.saveMap(p_map, p_fileName);
		return result;
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
	/**
     * Saves risk game.
     * @param p_game Represents the state of the game.
     * @param p_fileName Name of the file saving the game.
     * @return true boolean value. 
     */
    public boolean saveGame(GameData  p_game, String p_fileName){
        GameDataBuilder l_gameDataBuilder = new GameDataBuilder();
        l_gameDataBuilder.setMap(p_game.getMap());
        l_gameDataBuilder.setMapType(p_game.getMapType());
        l_gameDataBuilder.setGamePhase(p_game.getGamePhase());
        l_gameDataBuilder.setPlayers(p_game.getPlayers());
        l_gameDataBuilder.setActivePlayer(p_game.getActivePlayer());
        l_gameDataBuilder.setDeck(p_game.getDeck());
        l_gameDataBuilder.setCardsDealt((p_game.getCardsDealt()));
        l_gameDataBuilder.setPhase(p_game.getD_Phase());
        l_gameDataBuilder.setPhaseName(p_game.getD_Phase().getD_PhaseName());
        
        System.out.println(l_gameDataBuilder);
        try{
           // FileOutputStream l_f = new FileOutputStream(new File("src/main/resources/game/" + p_fileName));
            ObjectOutputStream l_o = new ObjectOutputStream(new FileOutputStream(new File("src/main/resources/game/" + p_fileName)));
            l_o.writeObject(l_gameDataBuilder);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    /**
     * Loads risk game.
     * @param p_fileName Name of the file to be loaded.
     * @return GameDataBuilder object to build GameData object.
     */
    public GameDataBuilder loadGame(String p_fileName){
        GameDataBuilder l_gameDataBuilder=new GameDataBuilder();
        try{
        	 FileInputStream l_f = new FileInputStream((new File("src/main/resources/game/" + p_fileName)));
             ObjectInputStream l_o = new ObjectInputStream((l_f));
             l_gameDataBuilder = (GameDataBuilder) l_o.readObject();
             
        } catch(FileNotFoundException e){
            System.out.println("Entered file name doesnot exist.");
            return null;
        } catch(IOException e) {
            return null;
        } catch (ClassNotFoundException e){
            return null;
        }
        return l_gameDataBuilder;
    }	
}
