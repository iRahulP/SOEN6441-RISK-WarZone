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



	private BufferedReader readBorders(BufferedReader p_reader) {
		// TODO Auto-generated method stub
		return null;
	}



	private BufferedReader readCountries(BufferedReader p_reader) {
		// TODO Auto-generated method stub
		return null;
	}



	private BufferedReader readContinents(BufferedReader p_reader) {
		// TODO Auto-generated method stub
		return null;
	}
}

