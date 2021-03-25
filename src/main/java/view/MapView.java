package view;

import java.util.ArrayList;

import controller.GameEngine;
import model.Continent;
import model.CountryDetails;
import model.GameMap;
import model.Player;

/**
 *Class to show map. 
 * @author yashs
 *
 */
public class MapView {

	
	/**
	 * Constructor to initialize object.
	 */
	public MapView(){
	}
	
	 /**
		 * print the continents, countries and each country's neighbor in the map
		 * @param p_map the map to be shown.
		 */
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
	 
		/**
		 * Shows map with along with Owner and Army units.
		 * @param p_players List of players in the game
		 * @param p_map Game map
		 */
		public void showMap(ArrayList<Player> p_players, GameMap p_map) {

			if(p_map==null)
				return;
			if(p_players.size()==0 || p_players.get(0).getOwnedCountries().size()==0) {
				showMap(p_map);
				return;
			}
			System.out.format("%25s%25s%35s%25s%10s\n", "Owner", "Country", "Neighbors", "Continent", "#Armies");
			System.out.format("%85s\n", "---------------------------------------------------------------------------------------------------------------------------");
			boolean l_printPlayerName = true;
			boolean l_printContinentId = true;
			boolean l_printCountryId = true;
			boolean l_printNumberOfArmies = true;

			for(int i=0; i<p_players.size(); i++){
				Player l_p = p_players.get(i);
				for(CountryDetails l_country : l_p.getOwnedCountries().values()) {
					for(CountryDetails l_neighbor : l_country.getNeighbours().values()) {
						if(l_printPlayerName && l_printContinentId && l_printCountryId) {
							System.out.format("\n%25s%25s%35s%25s%10d\n", l_p.getPlayerName(), l_country.getCountryId(), l_neighbor.getCountryId(), l_country.getInContinent(), l_country.getNumberOfArmies());
							l_printPlayerName = false;
							l_printContinentId = false;
							l_printCountryId = false;
							l_printNumberOfArmies = false;
						}
						else if(l_printContinentId && l_printCountryId && l_printNumberOfArmies) {
							System.out.format("\n%25s%25s%35s%25s%10d\n", "", l_country.getCountryId(), l_neighbor.getCountryId(), l_country.getInContinent(), l_country.getNumberOfArmies());
							l_printPlayerName = false;
							l_printCountryId = false;
							l_printNumberOfArmies = false;
						}
						else {
							System.out.format("\n%25s%25s%35s%25s%10s\n", "", "", l_neighbor.getCountryId(), "", "");
						}
					}
					l_printContinentId = true;
					l_printCountryId = true;
					l_printNumberOfArmies = true;
				}
				l_printPlayerName = true;
				l_printContinentId = true;
				l_printCountryId = true;
				l_printNumberOfArmies = true;
			}
		}
}
