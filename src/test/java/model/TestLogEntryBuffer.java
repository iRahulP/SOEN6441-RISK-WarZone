package model;

import controller.GameEngine;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
/**
 *Test if LogEntryBuffer(observable) updates the WriteLogEntry(observer)
 *  to write to log file log.txt*
 */
public class TestLogEntryBuffer {

    /**
     * d_Ge is reference for gameEngine
     */
    public GameEngine d_Ge;
    Player d_Player1;
    Player d_Player2;
    GameMap d_Map;
    ArrayList<Player> d_Players;
    String d_PlayerName="xyz";
    RunGameEngine d_Rge;
    String[] d_Data={"gameplayer", "-add", "xyz"};
    String[] d_Data1 = new String[]{"loadmap","world.map"};
    String d_MapName;

    /**
     * initial setup
     */
    @Before
    public void before(){
        d_Ge = new GameEngine();
        d_Rge = new RunGameEngine();
        d_Players = new ArrayList<Player>();
        d_Map = new GameMap("dummy.map");
        d_Player1 = new Player("Aarthi");
        d_Player2 = new Player("Shravya");
        d_Players.add(d_Player1);
        d_Players.add(d_Player2);
    }

    /**
     *Test if LogEntryBuffer(observable) updates the WriteLogEntry(observer)
     *  to write to log file log.txt
     */
    @Test
    public void testLogEntryBuffer(){

        //Testing preload phase logs are stored in log file or not
        d_Ge.setPhase(new PreLoad(d_Ge));
        d_MapName= "world.map";
        d_Ge.d_LogEntry.setGamePhase(d_Ge.d_Phase);
        d_Ge.d_Phase.loadMap(d_Data1,d_MapName);

        //Testing if invalid
        System.out.println("Testing invalid command(gameplayer) in phase: "+ d_Ge.d_Phase.getD_PhaseName());
        d_Ge.d_Phase.gamePlayer(d_Data,d_Players,d_Player1.getPlayerName());
        assertEquals("Invalid command in PostLoad state","Invalid command in PostLoad state");

        d_Map = d_Rge.editMap(d_MapName);
        d_Ge.d_Phase.showMap(d_Map);

        //Testing startup phase are stored in log file or not
        d_Ge.setPhase(new StartUp(d_Ge));
        d_Ge.d_LogEntry.setGamePhase(d_Ge.d_Phase);
        d_Ge.d_Phase.gamePlayer(d_Data,d_Players,d_Player1.getPlayerName());
        d_Ge.d_Phase.gamePlayer(d_Data,d_Players,d_Player2.getPlayerName());
        d_Ge.d_Phase.assignCountries(d_Map,d_Players );

    }


}
