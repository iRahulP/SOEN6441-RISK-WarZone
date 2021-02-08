package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;







public class LoadMap {
public static int InMapIndex = 1;	


private GameMap map;	
private HashMap<Integer, CountryDetails> d_listOfCountries; //temporary HashMap to facilitate reading .map files

	public static void main(String[] args){
		
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the name of the map you want to play:");
		String l_mapName = in.nextLine();
		LoadMap loadedMap = new LoadMap();
		loadedMap.readMap(l_mapName);
		in.close();
		//loadedMap.printMap();
	}

	

	private GameMap readMap(String p_mapName) {
		map = new GameMap(p_mapName);
		d_listOfCountries = new HashMap<Integer, CountryDetails>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(p_mapName));
			String s;
			while ((s = reader.readLine()) != null) {
				if(s.equals("[continents]"))
					reader = readContinents(reader);
				if(s.equals("[countries]"))
					reader = readCountries(reader);
				if(s.equals("[borders]"))
					reader = readBorders(reader);
			}   
			reader.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("FileNotFoundException");
			System.out.println(e.getMessage());
		}
		catch(IOException e) {
			System.out.println("IOException");
			System.out.println(e.getMessage());
		}
		return map;
	}

	private BufferedReader readCountries(BufferedReader p_reader) {
		String l_s;
		try {
			while(!((l_s = p_reader.readLine()).equals(""))) {
				String[] l_countryString = l_s.split("\\s+");
				CountryDetails newCountry = new CountryDetails(l_countryString[0], l_countryString[1], l_countryString[2], l_countryString[3], l_countryString[4], map);
				try {
					if(newCountry.getInContinent()==null)
					{
						System.out.println("Error reading the file.");
						System.exit(-1);
					}
					d_listOfCountries.put(newCountry.getIndex(), newCountry);
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
	
	private BufferedReader readContinents(BufferedReader p_reader) {
		String l_s;
		try {
			while(!((l_s = p_reader.readLine()).equals(""))) {
				String[] l_continentString = l_s.split("\\s+");
				
				if(Integer.parseInt(l_continentString[1])>=0) {
					map.getContinents().put(l_continentString[0].toLowerCase(), new Continent(l_continentString[0], l_continentString[1], l_continentString[2]));
					InMapIndex++;
				} 
				else {
					System.out.println("Error reading the file.");
					System.exit(-1);
				}
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		InMapIndex = 1;
		return p_reader;
		
	}

	private BufferedReader readBorders(BufferedReader p_reader) {
		return null;
	}

}
