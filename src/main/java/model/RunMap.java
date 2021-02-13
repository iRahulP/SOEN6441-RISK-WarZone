package model;

import java.io.File;

public class RunMap {
	public GameMap loadMap(String p_mapName) {
		String l_filePath = "maps/" + p_mapName;
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

	public void showMap(GameMap map) {
		if(map==null)
			return;
		System.out.printf("%25s%25s%35s\n", "Continents", "Country", "Country's neighbors");
		System.out.printf("%85s\n", "-------------------------------------------------------------------------------------------");
		boolean l_PrintContinentName = true;
		boolean l_PrintCountryName = true;
		for(Continent continent : map.getContinents().values()) {
			if(continent.getCountries().size()==0) {
				System.out.printf("\n%25s%25s%25s\n", continent.getContinentName(), "", "");
			}
			for(CountryDetails country : continent.getCountries().values()) {
				if(country.getNeighbours().size()==0) {
					if(l_PrintContinentName && l_PrintCountryName) {
						System.out.printf("\n%25s%25s%25s\n", continent.getContinentName(), country.getCountryName(), "");
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
						System.out.printf("\n%25s%25s%25s\n", continent.getContinentName(), country.getCountryName(), neighbor.getCountryName());
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
