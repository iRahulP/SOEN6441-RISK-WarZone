package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * Adaptee class of adapter pattern
 * Responsible for reading and saving conquest map
 * 
 * @author Rucha
 *
 */
public class ConquestMap {
	
	GameMap  d_ConquestMap;
	HashMap<Integer, CountryDetails> d_ListOfCountries;
	RunGameEngine d_RunGE;
    
	/**
	 * Reads the ".map" file and creates a GameMap object accordingly.
	 * Performs basic validation checks too.
	 * @param p_mapName Name of the map file to be read
	 * @return object representing the map just read
	 */
	public GameMap readConquestMap(String p_mapName){
        d_ConquestMap = new GameMap(p_mapName);
        d_ListOfCountries = new HashMap<Integer, CountryDetails>();
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(p_mapName));
            String s;
            while ((s = reader.readLine()) != null) {
                if(s.equals("[Continents]")) {
                	 reader = readContinents(reader, d_ConquestMap);
                }
                   // reader = readContinents(reader, d_Map);
                if(s.equals("[Territories]")) {
                    reader = readTerritories(reader, d_ConquestMap);
                }
            }
            reader.close();
            //make sure valid countries exists as neighbors
            for(CountryDetails country : d_ConquestMap.getCountries().values()){
                for(String neighbor : country.getNeighbours().keySet()){
                    if(country.getNeighbours().get(neighbor.toLowerCase())==null){
                        if(d_ConquestMap.getCountries().get(neighbor.toLowerCase())==null){
                            return null;
                        }
                        country.getNeighbours().put(neighbor.toLowerCase(), d_ConquestMap.getCountries().get(neighbor.toLowerCase()));
                    }
                }
            }
        }
        catch(FileNotFoundException e) {
            System.out.println("FileNotFoundException");
            System.out.println(e.getMessage());
        }
        catch(IOException e) {
            System.out.println("IOException");
            System.out.println(e.getMessage());
        }
        return d_ConquestMap;
    }

	/**
	 * Reads the continents from the ".map" files.
	 * Exits the program if error of duplicate continents is found.
	 * @param p_reader Stream starting from continents section of ".map" file
	 * @param p_map Represents the game map
	 * @return BufferedReader stream at the point where it has finished reading continents
	 */
	public BufferedReader readContinents(BufferedReader p_reader, GameMap p_map){
        String s;
        //boolean continentExists = false;
        try {
            while(!((s = p_reader.readLine()).equals(""))) {
                String[] continentString = s.split("=");

                //Check if continent already exists in the map
                if(Integer.parseInt(continentString[1])>=0) {
                    p_map.getContinents().put(continentString[0].toLowerCase(), new Continent(continentString[0], Integer.parseInt(continentString[1])));
                    //d_InMapIndex++;
                }
                else {
                    //terminate the program if continent already exists
                    System.out.println("Error reading the file.");
                    System.out.println("Negative control value exists.");
                    System.exit(-1);
                }
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return p_reader;
    }
	
	/**
	 * Reads the countries from the ".map" files.
     * Exits the program if duplicate country or in non-existent country.
	 * @param p_reader Stream starting from countries section of ".map" file
	 * @param p_map Represents the game map
	 * @return BufferedReader stream at the point where it has finished reading countries
	 */
	public BufferedReader readTerritories(BufferedReader p_reader, GameMap p_map){
        String s;
        try {
            while((s = p_reader.readLine()) != null) {
                if(s.equals("")){
                    continue;
                }
                String[] countryString = s.split(",");
                CountryDetails newCountry = new CountryDetails(countryString[0], countryString[1], countryString[2], countryString[3]);
              //  CountryDetails newCountry = new CountryDetails(countryString[0], countryString[1], countryString[2], countryString[3], countryString[4], map);
                try {
                    if(newCountry.getInContinent()==null)
                    {
                        System.out.println("Error reading the file.");
                        System.out.println("This continent does not exist.");
                        System.exit(-1);
                    }
                    addToContinentMap(newCountry, p_map);	//Add country to the appropriate continent in the map. Terminate if duplicate entry.

                    //add neighbors
                    for(int i=4; i<countryString.length; i++){
                        if(p_map.getCountries().containsKey(countryString[i].toLowerCase())){
                            newCountry.getNeighbours().put(countryString[i].toLowerCase(), p_map.getCountries().get(countryString[i].toLowerCase()));
                            p_map.getCountries().get(countryString[i].toLowerCase()).getNeighbours().put(newCountry.getCountryId().toLowerCase(), newCountry);
                        } else {
                            newCountry.getNeighbours().put(countryString[i].toLowerCase(), null);
                        }
                    }
                }
                catch(NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return p_reader;
    }

	/**
     * Registers this new country as part of its continent.
     * If duplicate country, exits the program throwing error.
     * @param p_newCountry Country to be registered with the corresponding continent
     * @param p_map Represents the map of the game
     */
	public void addToContinentMap(CountryDetails p_newCountry, GameMap p_map){
        if(!MapValidator.doesCountryExist(p_map, p_newCountry.getCountryId())) {
            Continent argumentContinent = p_map.getContinents().get(p_newCountry.getInContinent().toLowerCase());
            argumentContinent.getCountries().put(p_newCountry.getCountryId().toLowerCase(), p_newCountry);
            p_map.getCountries().put(p_newCountry.getCountryId().toLowerCase(), p_newCountry);
        }
        else {
            //terminate the program if same name country exists in the continent
            System.out.println("Error reading the file.");
            System.out.println("Two countries of same name exists in the same continent.");
            System.exit(-1);
        }
    }

	/**
     * Saves map in Conquest map file format
     * @param p_map GameMap object representing the map to be saved
     * @param p_fileName Name with which map file is to be saved
     * @return true if successful in saving the map, else false
     */
	public boolean saveMap(GameMap p_map, String p_fileName) {
		if(d_RunGE.validateMap(p_map)) {
            try{
                BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/othermaps/" + p_fileName + ".map"));

                //write preliminary information
                writer.write("[Map]");
                writer.newLine();
                writer.newLine();
                writer.flush();

                //write information about all the continents
                writer.write("[Continents]");
                writer.newLine();
                for (Continent continent : p_map.getContinents().values()) {
                    writer.write(continent.getContinentId() + "=" + continent.getControlValue());
                    writer.newLine();
                    writer.flush();
                }
                writer.newLine();

                //write information about countries and its neighbors
                writer.write("[Territories]");
                writer.newLine();
                String s;
                for(CountryDetails country : p_map.getCountries().values()){
                    s = country.getCountryId() + "," + country.getxCoOrdinate() + "," + country.getyCoOrdinate() + "," + country.getInContinent();
                    for(CountryDetails  neighbor : country.getNeighbours().values()){
                        s += "," + neighbor.getCountryId();
                    }
                    writer.write(s);
                    writer.newLine();
                    writer.flush();
                }

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;

        } else {
            return false;
        }
	}

}
