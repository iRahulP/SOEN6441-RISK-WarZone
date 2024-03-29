package model;

import org.jgrapht.*;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import java.util.HashMap;


/**
 * Validation of map takes place in this class
 */
public class MapValidator {

    private Graph<CountryDetails, DefaultEdge> d_MapGraph; //JGraphT type Graph representing the game map

    /**
     * This constructor instantiates the Directed Graph using JGraphT .
     */
    MapValidator() {
        d_MapGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
    }

    /**
     * Creates a graph(using jgrapht library) by taking countries as vertices and adds edges between country and its neighbors
     *
     * @param p_map Game map representing countries ,continents and borders.
     * @return returns graph representing the map
     */
    public Graph<CountryDetails, DefaultEdge> createGraph(GameMap p_map) {

        //add CountryDetails to the Graph
        for (CountryDetails l_countryDetails : p_map.getCountries().values()) {
            d_MapGraph.addVertex(l_countryDetails);
        }

        //add Edges between country and its neighbours
        for (CountryDetails l_countryDetails : p_map.getCountries().values()) {
            for (CountryDetails l_neighbor : l_countryDetails.getNeighbours().values()) {
                d_MapGraph.addEdge(l_countryDetails, l_neighbor);
            }
        }
        return d_MapGraph;
    }

    /**
     *This function creates a subgraph for a continent
     * @param p_subGraph subgraph for a continent
     * @param p_countries countries of a continent
     * @return p_subGraph
     */
    public Graph<CountryDetails, DefaultEdge> createSubGraph(Graph<CountryDetails, DefaultEdge> p_subGraph, HashMap<String, CountryDetails> p_countries) {

        for (CountryDetails l_country : p_countries.values()) {
            p_subGraph.addVertex(l_country);
        }

        for (CountryDetails l_country : p_countries.values()) {
            for (CountryDetails l_neighbour : l_country.getNeighbours().values()) {
                if (p_countries.containsKey(l_neighbour.getCountryId().toLowerCase())) {
                    p_subGraph.addEdge(l_country, l_neighbour);
                }
            }
        }
        return p_subGraph;
    }

    /**
     * This function checks if the graph is a connected graph.
     * @param p_graph The graph whose connectivity is checked
     * @return returns true is graph is connected
     */
    public boolean isGraphConnected(Graph<CountryDetails, DefaultEdge> p_graph) {
        ConnectivityInspector<CountryDetails, DefaultEdge> l_cInspector = new ConnectivityInspector<>(p_graph);
        if (l_cInspector.isConnected())
            return true;
        else
            return false;
    }
    
    /**
	 * Checks if all continents are connected sub-graphs or not
	 * @param p_map GameMap object representing the game map
	 * @return true if all continents are connected sub-graph, else false indicating incorrect map
	 */
	public boolean continentConnectivityCheck(GameMap p_map) {
		for(Continent l_continent : p_map.getContinents().values()) {
			Graph<CountryDetails, DefaultEdge> subGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
			subGraph = createSubGraph(subGraph, l_continent.getCountries());
			if(!isGraphConnected(subGraph)) {
				return false;
			}
		}
		return true;
	}
	

    /**
     * Check if the same continent already exist
     *
     * @param p_map GameMap object holding record of all the existing continents and countries
     * @param p_continentId name of the continent to be checked
     * @return true if continent already exists, else false
     */
    public static boolean doesContinentExist(GameMap p_map, String p_continentId) {
        if (p_map.getContinents().containsKey(p_continentId.toLowerCase()))
            return true;
        else
            return false;
    }

    /**
     * Check if the same country already exist
     *
     * @param p_map GameMap object holding record of all the existing continents and countries
     * @param p_countryId name of the country to be checked
     * @return true if country already exists, else false
     */
    public static boolean doesCountryExist(GameMap p_map, String p_countryId) {
        if (p_map.getCountries().containsKey(p_countryId.toLowerCase()))
            return true;
        else
            return false;
    }
    
	/**
	 * Check if any continent is empty and does not hold any country.
	 * @param p_map GameMap object representing the game map
	 * @return true if no continent is empty, else false indicating empty continent
	 */
	public boolean notEmptyContinent(GameMap p_map) {
		for(Continent l_continent : p_map.getContinents().values()) {
			if(l_continent.getCountries().size()==0)
				return false;
		}
		return true;
	}


}
