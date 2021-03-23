package model;

public class Diplomacy implements Order{

    private Player d_currentPlayer, d_targetPlayer;

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
