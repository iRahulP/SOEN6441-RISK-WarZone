package model;



import java.util.ArrayList;

import controller.GameEngine;

public class PostLoad extends Edit {

	public PostLoad(GameEngine p_Ge) {
		 d_Ge = p_Ge;
		 d_PhaseName = "PostLoad";
	}

	@Override
	public void loadMap(String[] p_data, String p_mapName) {
		// TODO Auto-generated method stub

	}

	@Override
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
			for(CountryDetails l_country : l_continent.getCountries().values()) {
				if(l_country.getNeighbours().size()==0) {
					if(l_PrintContinentName && l_PrintCountryName) {
						System.out.printf("\n%25s%25s%25s\n", l_continent.getContinentId(), l_country.getCountryId(), "");
						l_PrintContinentName = false;
						l_PrintCountryName = false;
					}
					else if(l_PrintCountryName) {
						System.out.printf("\n%25s%25s%25s\n", "", l_country.getCountryId(), "");
						l_PrintCountryName =  false;
					}
				}
				for(CountryDetails l_neighbor : l_country.getNeighbours().values()) {
					if(l_PrintContinentName && l_PrintCountryName) {
						System.out.printf("\n%25s%25s%25s\n", l_continent.getContinentId(), l_country.getCountryId(), l_neighbor.getCountryId());
						l_PrintContinentName = false;
						l_PrintCountryName = false;
					}
					else if(l_PrintCountryName) {
						System.out.printf("\n%25s%25s%25s\n", "", l_country.getCountryId(), l_neighbor.getCountryId());
						l_PrintCountryName = false;
					}
					else {
						System.out.printf("%25s%25s%25s\n", "", "", l_neighbor.getCountryId());
					}
				}
				l_PrintCountryName = true;
			}
			l_PrintContinentName = true;
			l_PrintCountryName = true;
		}

	}

//	@Override
//	public boolean addPlayer(ArrayList<Player> p_players, String p_playerName) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean removePlayer(ArrayList<Player> p_players, String p_playerName) {
//		// TODO Auto-generated method stub
//		return false;
//	}

	@Override
	public boolean assignCountries(GameMap p_map, ArrayList<Player> p_players) {
		// TODO Auto-generated method stub
		return false;
	}

	

	@Override
	public void showMap(ArrayList<Player> p_players, GameMap p_map) {
		printInvalidCommandMessage();
	}

	@Override
	public void reinforce() {
	
		
	}

	@Override
	public void editContinent(String[] p_data, String p_continentId, int p_controlValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editCountry(String[] p_data, String p_continentId, String p_countryId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editNeighbour(String[] p_data, String p_countryId, String p_neighborCountryId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void savemap(String[] p_data, String p_mapName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editMap(String[] p_data, String p_mapName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gamePlayer(String[] p_data, ArrayList<Player> p_players, String p_playerName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validatemap(){
		// TODO Auto-generated method stub
	}

}
