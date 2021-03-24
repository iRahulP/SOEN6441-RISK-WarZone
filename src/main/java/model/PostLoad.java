package model;

import java.util.ArrayList;

import controller.GameEngine;
/**
 * Implementation of PostLoad phase of the game.
 */
public class PostLoad extends Edit {
	/**
	 * it is constructor to initialize values
	 * @param p_ge is the reference of gameEngine class 
	 */
	public PostLoad(GameEngine p_ge) {
		 d_Ge = p_ge;
		 d_PhaseName = "PostLoad";
	}
	
	/**
	 * print the continents, countries and each country's neighbor in the map
	 * @param p_map the map to be shown.
	 */
	@Override
	public void showMap(GameMap p_map) {
		d_Ge.d_LogEntry.setMessage("Command given by user: showmap");
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
	@Override
	public void loadMap(String[] p_data, String p_mapName) {
		printInvalidCommandMessage();
		d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
	}

	@Override
	public void editContinent(String[] p_data, String p_continentId, int p_controlValue) {
		printInvalidCommandMessage();
		d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
	}

	@Override
	public void editCountry(String[] p_data, String p_continentId, String p_countryId) {
		printInvalidCommandMessage();
		d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
	}

	@Override
	public void editNeighbour(String[] p_data, String p_countryId, String p_neighborCountryId) {
		printInvalidCommandMessage();
		d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
	}
	
	@Override
	public void savemap(String[] p_data, String p_mapName) {
		printInvalidCommandMessage();
		d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
	}

	@Override
	public void editMap(String[] p_data, String p_mapName) {
		printInvalidCommandMessage();
		d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
	}

	@Override
	public void validatemap(){
		printInvalidCommandMessage();
		d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
	}

}
