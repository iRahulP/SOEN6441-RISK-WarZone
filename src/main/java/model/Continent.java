package model;


import java.util.HashMap;

/**
 * Continent Class has the information about continents like the continent name,continent control value,
 * continent color and index values of Continents
 * All the countries that belong to a continent are stored in HashMap,where the key is the Country String
 * and its value is corresponding Country Object.
 *
 * @author Aarthi
 */
public class Continent {

    private String d_continentName;
    private int d_controlValue;
    private String d_continentColor;
    private int d_continentId;
    private HashMap<String, CountryDetails> d_countries;

    /**
     * Creates a Continent object with the arguments passed
     * This constructor will be used when loading .map files
     *
     * @param p_continentName  Name of the continent
     * @param p_controlValue   Control value for this continent
     * @param p_continentColor Color value of the continent
     */
    Continent(String p_continentName, String p_continentColor, String p_controlValue) {
        d_continentName = p_continentName;
        d_controlValue = Integer.parseInt(p_controlValue);
        d_continentColor = p_continentColor;
        d_continentId = LoadMap.InMapIndex;
        d_countries = new HashMap<>();
    }


    /**
     * Creates a Continent based on continentId and controlValue(used in editMap)
     * Will be used when user defined continents are to be created.
     *
     * @param p_continentId  Id of the continent
     * @param p_controlValue Control value for this continent
     */
    Continent(String p_continentId, int p_controlValue) {
        // continent name
        d_continentId = Integer.parseInt(p_continentId);
        d_controlValue = p_controlValue;
        d_continentColor = "000";
        d_countries = new HashMap<>();
    }

    /**
     * Returns the name of the continent.
     *
     * @return returns the name of the continent
     */
    public String getContinentName() {
        return this.d_continentName;
    }

    /**
     * Returns the Control Value of the continent.
     *
     * @return returns the Control Value of the continent
     */
    public int getControlVale() {
        return this.d_controlValue;
    }

    /**
     * Setter method to set control value of the continent
     *
     * @param p_controlValue set control value of the continent to this value
     */
    public void setControlVale(int p_controlValue) {
        d_controlValue = p_controlValue;
    }

    /**
     * Returns the color of the continent.
     *
     * @return returns the color of the continent
     */
    public String getContinentColor() {
        return this.d_continentColor;
    }

    /**
     * Setter method to set color of the continent
     *
     * @param p_continentColor set color of the continent to this value
     */
    public void setContinentColor(String p_continentColor) {
        d_continentColor = p_continentColor;
    }

    /**
     * Returns the countries as HashMap that belong to this Continent
     *
     * @return returns the HashMap containing countries for a continent
     */
    public HashMap<String, CountryDetails> getCountries() {
        return d_countries;
    }

    /**
     * Returns the index for this continent when saved  in ".map" file following domination's rules
     *
     * @return returns ID value of the continent
     */
    public int getContinentId() {
        return this.d_continentId;
    }

    /**
     * Sets the index of this continent
     *
     * @param p_continentId ID value of the continent
     */
    public void setInMapIndex(int p_continentId) {
        d_continentId = p_continentId;
    }
}

