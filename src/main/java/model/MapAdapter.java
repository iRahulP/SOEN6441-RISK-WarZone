package model;

/**
 * Class provide the bridge between DominationMap and ConquestMap
 * @author Rucha
 *
 */
public class MapAdapter extends DominationMap{

    ConquestMap d_ConquestMap;

    /**
     * Constructor of MapAdapter
     * @param p_conquestMap reference of conquest map
     */
    public MapAdapter(ConquestMap p_conquestMap){
        this.d_ConquestMap = p_conquestMap;
    }

   /**
    * Read the conquest map
    * @return return map to read
    */
    public GameMap readMap(String p_mapName) {
        return d_ConquestMap.readMap(p_mapName);
    }

    /**
     * Save the conquest map
     * @param p_map map to save
     * @param p_fileName the name of map saved
     * @return return true if map saved succesfully, else false
     */
    public boolean saveMap(GameMap p_map, String p_fileName) {
        return d_ConquestMap.saveMap(p_map, p_fileName);
    }
}
