package model;

/**
 * Class implements functionality of Diplomacy card.
 */
public class Diplomacy implements Order{

    private Player d_currentPlayer, d_targetPlayer;
    /**
     * Constructor that initializes class variable.
     * @param p_currentPlayer sorce player giving order
     * @param p_targetPlayer target player 
     */
    public Diplomacy(Player p_currentPlayer,Player p_targetPlayer){
        this.d_currentPlayer = p_currentPlayer;
        this.d_targetPlayer = p_targetPlayer;
    }

    @Override
    public boolean execute() {
        this.d_currentPlayer.addPlayerNegList(d_targetPlayer);
        this.d_targetPlayer.addPlayerNegList(d_currentPlayer);

        return true;
    }
}
