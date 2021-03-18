package model;

import controller.GameEngine;

/**
 * Abstract class Phase consists of all the states and related abstract methods
 */
abstract public class Phase{

    GameEngine d_Ge;

    abstract public void loadMap(String[] p_data, String p_mapName);


}