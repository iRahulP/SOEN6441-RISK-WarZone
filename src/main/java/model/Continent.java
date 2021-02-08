package model;

import com.risk.Risk.LoadMap;

/**
 * Continent Class has the information about continents like the continent name,continent control value,
 * continent color and index values of Continents
 * <p>
 * All the countries that belong to a continent are stored in HashMap,where the key is the Country String
 * and its value is corresponding Country Object.
 *
 * @author Aarthi
 */
public class Continent {

    private String d_ContinentName;
    private int d_ControlValue;
    private String d_ContinentColor;
    private int d_MapIndex;

    /**
     * Creates a Continent object with the arguments passed
     * This constructor will be used when loading .map files
     *
     * @param p_ContinentName  Name of the continent
     * @param p_ControlValue   Control value for this continent
     * @param p_ContinentColor Color value of the continent
     */
    Continent(String p_ContinentName, String p_ContinentColor, String p_ControlValue) {
        d_ContinentName = p_ContinentName;
        d_ControlValue = Integer.parseInt(p_ControlValue);
        d_ContinentColor = p_ContinentColor;
        d_MapIndex = LoadMap.inMapIndex;
        // Countries TO_DO
    }


    /**
     * Returns the name of the continent.
     *
     * @return returns the name of the continent
     */
    public String getContinentName() {
        return this.d_ContinentName;
    }

    /**
     * Setter method to set control value of the continent
     *
     * @param p_ControlValue set control value of the continent to this value
     */
    public void setControlVale(int p_ControlValue) {
        d_ControlValue = p_ControlValue;
    }

    /**
     * Returns the Control Value of the continent.
     *
     * @return returns the Control Value of the continent
     */
    public int getControlVale() {
        return this.d_ControlValue;
    }

    /**
     * Setter method to set color of the continent
     *
     * @param p_ContinentColor set color of the continent to this value
     */
    public void setContinentColor(String p_ContinentColor) {
        d_ContinentColor = p_ContinentColor;
    }

    /**
     * Returns the color of the continent.
     *
     * @return returns the color of the continent
     */
    public String getContinentColor() {
        return this.d_ContinentColor;
    }

}


