package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class ConquestMap {
	
	GameMap  d_Map;
	HashMap<Integer, CountryDetails> d_ListOfCountries;
	RunGameEngine d_RunGE;
    
	public GameMap readMap(String mapName){
        d_Map = new GameMap(mapName);
        d_ListOfCountries = new HashMap<Integer, CountryDetails>();
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(mapName));
            String s;
            while ((s = reader.readLine()) != null) {
                if(s.equals("[Continents]")) {
                	 reader = readContinents(reader, d_Map);
                }
                   // reader = readContinents(reader, d_Map);
                if(s.equals("[Territories]")) {
                    reader = readTerritories(reader, d_Map);
                }
            }
            reader.close();
            //make sure valid countries exists as neighbors
            for(CountryDetails country : d_Map.getCountries().values()){
                for(String neighbor : country.getNeighbours().keySet()){
                    if(country.getNeighbours().get(neighbor.toLowerCase())==null){
                        if(d_Map.getCountries().get(neighbor.toLowerCase())==null){
                            return null;
                        }
                        country.getNeighbours().put(neighbor.toLowerCase(), d_Map.getCountries().get(neighbor.toLowerCase()));
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
        return d_Map;
    }

	public BufferedReader readContinents(BufferedReader reader, GameMap map){
        String s;
        //boolean continentExists = false;
        try {
            while(!((s = reader.readLine()).equals(""))) {
                String[] continentString = s.split("=");

                //Check if continent already exists in the map
                if(Integer.parseInt(continentString[1])>=0) {
                    map.getContinents().put(continentString[0].toLowerCase(), new Continent(continentString[0], Integer.parseInt(continentString[1])));
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
        return reader;
    }
	
	public BufferedReader readTerritories(BufferedReader reader, GameMap map){
        String s;
        try {
            while((s = reader.readLine()) != null) {
                if(s.equals("")){
                    continue;
                }
                String[] countryString = s.split(",");
               // CountryDetails newCountry = new CountryDetails(countryString[0], countryString[1], countryString[2], countryString[3]);
                CountryDetails newCountry = new CountryDetails(countryString[0], countryString[1], countryString[2], countryString[3], countryString[4], map);
                try {
                    if(newCountry.getInContinent()==null)
                    {
                        System.out.println("Error reading the file.");
                        System.out.println("This continent does not exist.");
                        System.exit(-1);
                    }
                    addToContinentMap(newCountry, map);	//Add country to the appropriate continent in the map. Terminate if duplicate entry.

                    //add neighbors
                    for(int i=4; i<countryString.length; i++){
                        if(map.getCountries().containsKey(countryString[i].toLowerCase())){
                            newCountry.getNeighbours().put(countryString[i].toLowerCase(), map.getCountries().get(countryString[i].toLowerCase()));
                            map.getCountries().get(countryString[i].toLowerCase()).getNeighbours().put(newCountry.getCountryId().toLowerCase(), newCountry);
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
        return reader;
    }

	public void addToContinentMap(CountryDetails newCountry, GameMap map){
        if(!MapValidator.doesCountryExist(map, newCountry.getCountryId())) {
            Continent argumentContinent = map.getContinents().get(newCountry.getInContinent().toLowerCase());
            argumentContinent.getCountries().put(newCountry.getCountryId().toLowerCase(), newCountry);
            map.getCountries().put(newCountry.getCountryId().toLowerCase(), newCountry);
        }
        else {
            //terminate the program if same name country exists in the continent
            System.out.println("Error reading the file.");
            System.out.println("Two countries of same name exists in the same continent.");
            System.exit(-1);
        }
    }

	public boolean saveMap(GameMap map, String fileName) {
		if(d_RunGE.validateMap(map)) {
            try{
                BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/othermaps/" + fileName + ".map"));

                //write preliminary information
                writer.write("[Map]");
                writer.newLine();
                writer.newLine();
                writer.flush();

                //write information about all the continents
                writer.write("[Continents]");
                writer.newLine();
                for (Continent continent : map.getContinents().values()) {
                    writer.write(continent.getContinentId() + "=" + continent.getControlValue());
                    writer.newLine();
                    writer.flush();
                }
                writer.newLine();

                //write information about countries and its neighbors
                writer.write("[Territories]");
                writer.newLine();
                String s;
                for(CountryDetails country : map.getCountries().values()){
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
