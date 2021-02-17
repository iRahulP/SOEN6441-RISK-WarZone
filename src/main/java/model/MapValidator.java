package model;

import org.jgrapht.*;
import org.jgrapht.graph.DefaultEdge;


/**
 * Validation of map takes place in this class
 */
public class MapValidator {

    private Graph<CountryDetails, DefaultEdge> l_mapGraph; //JGraphT type Graph representing the game map

    /**
     * Creates a graph(using jgrapht library) by taking countries as vertices and adds edges between country and its neighbors
     *
     * @param p_map Game map representing countries ,continents and borders.
     * @return returns graph representing the map
     */
    public Graph<CountryDetails, DefaultEdge> createGraph(GameMap p_map) {

        //add CountryDetails to the Graph
        for (CountryDetails l_countryDetails : p_map.getCountries().values()) {
            l_mapGraph.addVertex(l_countryDetails);
        }

        //add Edges between country and its neighbours
        for (CountryDetails l_countryDetails : p_map.getCountries().values()) {
            for (CountryDetails l_neighbor : p_map.getCountries().values()) {
                l_mapGraph.addEdge(l_countryDetails, l_neighbor);
            }
        }
        return l_mapGraph;
    }

    /**
     * Check if the same continent already exist
     *
     * @param p_map           GameMap object holding record of all the existing continents and countries
     * @param p_continentName name of the continent to be checked
     * @return true if continent already exists, else false
     */
    public static boolean doesContinentExist(GameMap p_map, String p_continentName) {
        if (p_map.getContinents().containsKey(p_continentName.toLowerCase()))
            return true;
        else
            return false;
    }

    /**
     * Check if the same country already exist
     *
     * @param p_map         GameMap object holding record of all the existing continents and countries
     * @param p_countryName name of the country to be checked
     * @return true if country already exists, else false
     */
    public static boolean doesCountryExist(GameMap p_map, String p_countryName) {
        if (p_map.getCountries().containsKey(p_countryName.toLowerCase()))
            return true;
        else
            return false;
    }

}
