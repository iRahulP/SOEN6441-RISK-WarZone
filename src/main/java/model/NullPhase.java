package model;

import controller.GameEngine;
//concreteState
public class NullPhase extends Phase {

    public NullPhase(GameEngine p_Ge)
    {
        d_Ge = p_Ge;
    }

    @Override
    public void loadMap(String[] p_data,String p_mapName) {
        try {
            if (p_data[1] != null) {
                if (d_Ge.isMapNameValid(p_data[1])) {
                    p_mapName = p_data[1];
                    d_Ge.d_Map = d_Ge.d_RunG.loadMap(p_mapName);
                    if (d_Ge.d_Map != null) {
                        if (!d_Ge.d_Map.getValid()) {
                            System.out.println("Map is not valid");
                            //d_Ge.d_LogObject.setMessage("Map is not valid");
                            d_Ge.d_GamePhase = InternalPhase.NULL;
                            d_Ge.setGamePhase(d_Ge.d_GamePhase);
                        } else {
                            System.out.println("Map is valid. Please Add players -> ");
                            //d_LogObject.setMessage("Map is valid. Please Add players -> ");
                            d_Ge.d_GamePhase = InternalPhase.STARTUP;
                            d_Ge.setGamePhase(d_Ge.d_GamePhase);
                        }
                    } else {
                        d_Ge.d_GamePhase = InternalPhase.NULL;
                        d_Ge.setGamePhase(d_Ge.d_GamePhase);
                    }
                } else {
                    System.out.println("Map name not valid");
                    //d_LogObject.setMessage("Map name not valid");
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid command - try -> loadmap sample.map");
        }
    }
}
