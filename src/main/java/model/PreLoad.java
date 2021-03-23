package model;

import java.util.ArrayList;

import controller.GameEngine;
//concreteState
public class PreLoad extends Edit{

    public PreLoad(GameEngine p_Ge)
    {
        d_Ge = p_Ge;
        d_PhaseName = "PreLoad";
    }
    
    /**
     * This method edits a continent
     *
     * @param p_data         input command string
     * @param p_continentId  continentId for the continent
     * @param p_controlValue controlValue for the continent
     */
    public void editContinent(String[] p_data, String p_continentId, int p_controlValue) {
        try {
            d_Ge.d_LogEntry.setMessage("Command given by user:"+p_data);
            for (int i = 1; i < p_data.length; i++) {
                if (p_data[i].equals("-add")) {
                    if (d_Ge.isAlphabetic(p_data[i + 1])) {
                        p_continentId = p_data[i + 1];
                    } else {
                        System.out.println("Invalid Continent ID");
                    }
                    p_controlValue = Integer.parseInt(p_data[i + 2]);

                    boolean l_check = d_Ge.d_RunG.addContinent(d_Ge.d_Map, p_continentId, p_controlValue);
                    if (l_check) {
                        System.out.println(p_continentId + " continent added to the map");
                        d_Ge.d_GamePhase = InternalPhase.EDITMAP;
                        d_Ge.setGamePhase(d_Ge.d_GamePhase); //TODO not needed
                    } else {
                        System.out.println("Continent already exists - Please add valid Continent ID");
                    }
                } else if (p_data[i].equals("-remove")) {
                    if (d_Ge.isAlphabetic(p_data[i + 1])) {
                        p_continentId = p_data[i + 1];
                    } else
                        System.out.println("Invalid Continent Id");

                    boolean l_check = d_Ge.d_RunG.removeContinent(d_Ge.d_Map, p_continentId);
                    if (l_check) {
                        System.out.println("Continent removed from Map");
                        d_Ge.d_GamePhase = InternalPhase.EDITMAP;
                        d_Ge.setGamePhase(d_Ge.d_GamePhase);
                    } else
                        System.out.println("Continent doesn't exist - Please enter valid Continent ID");
                }
            }
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("Invalid command - It should be of the form editcontinent -add continentID controlvalue -remove continentID");
        } catch (Exception e) {
            System.out.println("Invalid command - it should be of the form editcontinent -add continentID controlvalue -remove continentID");
        }
    }

    /**
     * This method edits a country
     *
     * @param p_data        input command string
     * @param p_continentId continentId of the country to be added
     * @param p_countryId   countryId to be added
     */
    public void editCountry(String[] p_data, String p_continentId, String p_countryId) {
        try {
            d_Ge.d_LogEntry.setMessage("Command given by user:"+p_data);
            for (int i = 1; i < p_data.length; i++) {
                if (p_data[i].equals("-add")) {
                    if (d_Ge.isAlphabetic(p_data[i + 1]) || d_Ge.isAlphabetic(p_data[i + 2])) {
                        p_countryId = p_data[i + 1];
                        p_continentId = p_data[i + 2];
                    } else {
                        System.out.println("Invalid country name");
                    }
                    boolean l_check = d_Ge.d_RunG.addCountry(d_Ge.d_Map, p_countryId, p_continentId);
                    if (l_check) {
                        System.out.println("Country added to the map");
                        d_Ge.d_GamePhase = InternalPhase.EDITMAP;
                        d_Ge.setGamePhase(d_Ge.d_GamePhase);
                    } else {
                        System.out.println("Country already exists - Please add valid Country ID");
                    }
                } else if (p_data[i].equals("-remove")) {
                    if (d_Ge.isAlphabetic(p_data[i + 1])) {
                        p_countryId = p_data[i + 1];
                    } else {
                        System.out.println("Invalid country name");
                    }
                    boolean l_check = d_Ge.d_RunG.removeCountry(d_Ge.d_Map, p_countryId);
                    if (l_check) {
                        System.out.println("Country removed from the map");
                        d_Ge.d_GamePhase = InternalPhase.EDITMAP;
                        d_Ge.setGamePhase(d_Ge.d_GamePhase);
                    } else {
                        System.out.println("Country does not exist - Please enter valid country name");
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid command - it should be of the form editcountry -add countryId continentId -remove countryId");
        } catch (Exception e) {
            System.out.println("Invalid command - it should be of the form editcountry -add countryId continentId -remove countryId");
        }
    }

    /**
     * This method allows to add neighbors to a country
     *
     * @param p_data              input command string
     * @param p_countryId         countryId of the country
     * @param p_neighborCountryId neighbourCountryId of the country p_countryId
     */
    public void editNeighbour(String[] p_data, String p_countryId, String p_neighborCountryId) {
        try {
            d_Ge.d_LogEntry.setMessage("Command given by user:"+p_data);
            for (int i = 1; i < p_data.length; i++) {
                if (p_data[i].equals("-add")) {
                    if (d_Ge.isAlphabetic(p_data[i + 1]) || d_Ge.isAlphabetic(p_data[i + 2])) {
                        p_countryId = p_data[i + 1];
                        p_neighborCountryId = p_data[i + 2];
                    } else {
                        System.out.println("Invalid country ID");
                    }

                    boolean l_check = d_Ge.d_RunG.addNeighbor(d_Ge.d_Map, p_countryId, p_neighborCountryId);
                    if (l_check) {
                        d_Ge.d_GamePhase = InternalPhase.EDITMAP;
                        d_Ge.setGamePhase(d_Ge.d_GamePhase);
                    } else {
                        System.out.println("Country does not exist - Please enter valid countryID neighborcountryID");
                    }
                } else if (p_data[i].equals("-remove")) {
                    if (d_Ge.isAlphabetic(p_data[i + 1]) || d_Ge.isAlphabetic(p_data[i + 2])) {
                        p_countryId = p_data[i + 1];
                        p_neighborCountryId = p_data[i + 2];
                    } else {
                        System.out.println("Invalid country ID");
                    }

                    boolean l_check = d_Ge.d_RunG.removeNeighbor(d_Ge.d_Map, p_countryId, p_neighborCountryId);
                    if (l_check) {
                        d_Ge.d_GamePhase = InternalPhase.EDITMAP;
                        d_Ge.setGamePhase(d_Ge.d_GamePhase);
                    } else
                        System.out.println("Country does not exist - Please enter valid countryID neighborcountryID");
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid command - it should be of the form editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID");
        } catch (Exception e) {
            System.out.println("Invalid command - it should be of the form editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID");
        }
    }

    /**
     * This method saves the edited maps
     *
     * @param p_data    input command string
     * @param p_mapName name of the map
     */
    public void savemap(String[] p_data, String p_mapName) {
        try {
            d_Ge.d_LogEntry.setMessage("Command given by user:"+p_data);
            if (p_data[1] != "") {
                if (d_Ge.isMapNameValid(p_data[1])) {
                    p_mapName = p_data[1];
                    boolean l_check = d_Ge.d_RunG.saveMap(d_Ge.d_Map, p_mapName);
                    if (l_check) {
                        System.out.println("Map file saved successfully");
                        d_Ge.d_GamePhase = InternalPhase.EDITMAP;
                        d_Ge.setGamePhase(d_Ge.d_GamePhase);
                    } else
                        System.out.println("Error in saving - invalid map");
                } else
                    System.out.println("Map name not valid!");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid command - it should be of the form(without extension) savemap filename");
        } catch (Exception e) {
            System.out.println("Invalid command - it should be of the form(without extension) savemap filename");
        }
    }

    /**
     * This method calls the editMap functionalities
     *
     * @param p_data    input command String
     * @param p_mapName name of the map to be edited
     */
    public void editMap(String[] p_data, String p_mapName) {
        try {
            d_Ge.d_LogEntry.setMessage("Command given by user:"+p_data);
            if (p_data[1] != null) {
                if (d_Ge.isMapNameValid(p_data[1])) {
                    p_mapName = p_data[1];
                    d_Ge.d_Map = d_Ge.d_RunG.editMap(p_mapName);
                    System.out.println("Start editing " + p_mapName);
                    d_Ge.d_GamePhase = InternalPhase.EDITMAP;
                    d_Ge.setGamePhase(d_Ge.d_GamePhase);
                } else
                    System.out.println("Map name is invalid!");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid command - it should be of the form editmap sample.map");
        } catch (Exception e) {
            System.out.println("Invalid command - it should be of the form editmap sample.map");
        }
        d_Ge.setPhase(new PreLoad(d_Ge));

    }

    @Override
    public void loadMap(String[] p_data,String p_mapName) {
        try {
            d_Ge.d_LogEntry.setMessage("Command given by user: "+ p_data[0] + " " +p_data[1]);
            if (p_data[1] != null) {
                if (d_Ge.isMapNameValid(p_data[1])) {
                    p_mapName = p_data[1];
                    d_Ge.d_Map = d_Ge.d_RunG.loadMap(p_mapName);
                    if (d_Ge.d_Map != null) {
                        if (!d_Ge.d_Map.getValid()) {
                            System.out.println("Map is not valid");
                            d_Ge.d_LogEntry.setMessage("Map is not valid");
                            d_Ge.d_GamePhase = InternalPhase.NULL;
                            d_Ge.setGamePhase(d_Ge.d_GamePhase);
                        } else {
                            System.out.println("Map is valid. Please Add players -> ");
                            d_Ge.d_LogEntry.setMessage("Map is valid. Please Add players -> ");
                            d_Ge.d_GamePhase = InternalPhase.STARTUP;
                            d_Ge.setGamePhase(d_Ge.d_GamePhase);
                        }
                    } else {
                        d_Ge.d_GamePhase = InternalPhase.NULL;
                        d_Ge.setGamePhase(d_Ge.d_GamePhase);
                    }
                } else {
                    System.out.println("Map name not valid");
                    d_Ge.d_LogEntry.setMessage("Map name not valid");
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid command - try -> loadmap sample.map");
            d_Ge.d_LogEntry.setMessage("Invalid command - try -> loadmap sample.map");
        }
        d_Ge.setPhase(new PostLoad(d_Ge));
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
	public	void showMap(ArrayList<Player> p_players, GameMap p_map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reinforce() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gamePlayer(String[] p_data, ArrayList<Player> p_players, String p_playerName) {
		System.out.println("not allowed in preload state");
		
	}

	
}
