package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

class DominationMap {

	 public static int d_InMapIndex = 1;
	 private GameMap d_DominationMap;
	 private HashMap<Integer, CountryDetails> d_ListOfCountries;
	
	
	 /**
	     * getter for Map
	     * @return d_Map
	     */
	    public GameMap getMap() {
	        return this.d_DominationMap;
	    }
	    
	    /**
	     *  setter method for map
	     * @param p_map is the reference to GameMap class   
	     */
	    public void setMap(GameMap p_map) {
	        this.d_DominationMap = p_map;
	    }
	    
	    /**
	     * Reads the ".map" file and creates a GameMap object accordingly.
	     * Performs basic validation checks too.
	     * @param p_mapName Name of the map file to be read
	     * @return object representing the map just read
	     */
	    public GameMap readDominationMap(String p_mapName) {
	        d_DominationMap = new GameMap(p_mapName);
	        d_ListOfCountries = new HashMap<Integer, CountryDetails>();

	        try {
	            BufferedReader l_reader = new BufferedReader(new FileReader(p_mapName));
	            String l_s;
	            while ((l_s = l_reader.readLine()) != null) {
	                if (l_s.equals("[continents]"))
	                    l_reader = readContinents(l_reader);
	                if (l_s.equals("[countries]"))
	                    l_reader = readCountries(l_reader);
	                if (l_s.equals("[borders]"))
	                    l_reader = readBorders(l_reader);
	            }
	            l_reader.close();
	        } catch (FileNotFoundException e) {
	            System.out.println("FileNotFoundException");
	            System.out.println(e.getMessage());
	        } catch (IOException e) {
	            System.out.println("IOException");
	            System.out.println(e.getMessage());
	        }
	        return d_DominationMap;
	    }
	    
	    /**
	     * Reads the countries from the ".map" files.
	     * Exits the program if duplicate country or in non-existent country.
	     * @param p_reader Stream starting from countries section of ".map" file
	     * @return  BufferedReader stream at the point where it has finished reading countries
	     */
	    private BufferedReader readCountries(BufferedReader p_reader) {
	        String l_s;
	        try {
	            while (!((l_s = p_reader.readLine()).equals(""))) {
	                String[] l_countryString = l_s.split("\\s+");
	                CountryDetails l_newCountry = new CountryDetails(l_countryString[0], l_countryString[1], l_countryString[2], l_countryString[3], l_countryString[4], d_DominationMap);
	                try {
	                    if (l_newCountry.getInContinent() == null) {
	                        System.out.println("Error reading the file.");
	                        System.exit(-1);
	                    }
	                    addToContinentMap(l_newCountry);    //Add country to the appropriate continent in the map. Terminate if duplicate entry.
	                    d_ListOfCountries.put(l_newCountry.getIndex(), l_newCountry);
	                } catch (NullPointerException e) {
	                    e.printStackTrace();
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return p_reader;
	    }
	    
	    /**
	     *  Reads the continents from the ".map" files.
	     * Exits the program if error of duplicate continents is found.
	     * @param p_reader Stream starting from continents section of ".map" file
	     * @return BufferedReader stream at the point where it has finished reading continents
	     */
	    private BufferedReader readContinents(BufferedReader p_reader) {
	        String l_s;
	        try {
	            while (!((l_s = p_reader.readLine()).equals(""))) {
	                String[] l_continentString = l_s.split("\\s+");

	                if (Integer.parseInt(l_continentString[1]) >= 0) {
	                    d_DominationMap.getContinents().put(l_continentString[0].toLowerCase(), new Continent(l_continentString[0], l_continentString[1], l_continentString[2]));
	                    d_InMapIndex++;
	                } else {
	                    System.out.println("Error reading the file.");
	                    System.exit(-1);
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        d_InMapIndex = 1;
	        return p_reader;

	    }
	    
	    /**
	     * Reads the borders from the ".map" files.
	     * Exits the programming error if attempted to add invalid neighbor or to an invalid country.
	     * @param p_reader buffer data from file to be parsed
	     * @return BufferedReader stream at the point where it has finished reading borders
	     */
	    private BufferedReader readBorders(BufferedReader p_reader) {
	        String l_s;
	        try {
	            while ((l_s = p_reader.readLine()) != null) {
	                if (!l_s.equals("")) {
	                    String[] l_borderString = l_s.split("\\s+");
	                    CountryDetails l_argumentCountry = new CountryDetails();
	                    l_argumentCountry = d_ListOfCountries.get(Integer.parseInt(l_borderString[0]));
	                    for (int l_neighborCount = 1; l_neighborCount < l_borderString.length; l_neighborCount++) {
	                        addNeighbour(l_argumentCountry, l_borderString[l_neighborCount]);

	                    }

	                }

	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return p_reader;
	    }
	    
	    /**
	     * Registers the country at argument 'stringIndex' with the argumentCountry.
	     * Exits the programming throwing error if invalid neighbor is found
	     * @param p_argumentCountry Country to which neighbor is to be registered.
	     * @param p_stringIndex Index of the country to be added as a neighbor to the argument country
	     */
	    private void addNeighbour(CountryDetails p_argumentCountry, String p_stringIndex) {
	        int l_borderIndex = Integer.parseInt(p_stringIndex);
	        CountryDetails l_neighbourCountry = new CountryDetails();
	        try {
	            l_neighbourCountry = d_ListOfCountries.get(l_borderIndex);
	        } catch (IndexOutOfBoundsException e) {
	            System.out.println("Found error reading the .map file");
	            System.out.println("The neighbour " + l_borderIndex + " does not exist.");
	            System.exit(-1);
	        }
	        if (!p_argumentCountry.getNeighbours().containsKey(l_neighbourCountry.getCountryId().toLowerCase()))
	            p_argumentCountry.getNeighbours().put(l_neighbourCountry.getCountryId().toLowerCase(), l_neighbourCountry);
	    }
	    
	    /**
	     * Registers this new country as part of its continent.
	     * If duplicate country, exits the program throwing error.
	     * @param p_newCountry new Country
	     */
	    private void addToContinentMap(CountryDetails p_newCountry) {

	        if (!MapValidator.doesCountryExist(d_DominationMap, p_newCountry.getCountryId())) {
	            //newCountry.printCountry();
	            Continent l_argumentContinent = d_DominationMap.getContinents().get(p_newCountry.getInContinent().toLowerCase());
	            //System.out.println("Fetched continent: " + argumentContinent.getContinentName());
	            l_argumentContinent.getCountries().put(p_newCountry.getCountryId().toLowerCase(), p_newCountry);
	            d_DominationMap.getCountries().put(p_newCountry.getCountryId().toLowerCase(), p_newCountry);
	        } else {
	            //terminate the program if same name country exists in the continent
	            System.out.println("Error reading the file.");
	            System.out.println("Two countries of same name exists in the same continent.");
	            System.exit(-1);
	        }
	    }
}
