package com.risk.Risk;

import java.util.HashMap;
import java.util.Scanner;



public class LoadMap {
public static int inMapIndex = 1;	

	
	public static void main(String[] args){
		
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the name of the map you want to play:");
		String mapName = in.nextLine();
		LoadMap loadedMap = new LoadMap();
		loadedMap.readMap(mapName);
		in.close();
		//loadedMap.printMap();
	}


	private void readMap(String mapName) {
		// TODO Auto-generated method stub
		System.out.println("map loading");
	}
}
