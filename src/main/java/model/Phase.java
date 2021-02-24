package model;
/**
 * It maintains the current phase of the game.
 * @author rahul
 *
 */
public enum Phase {

    /**
     * The game is yet to begin. Start of initial CLI for user.
     * Phase ends when 'editmap' or 'loadmap' command is encountered.
     */
    NULL,

    /**
     * An existing game map is being edited or a new game map is being created from the scratch.
     * Phase ends when 'loadmap' command is encountered.
     */
    EDITMAP,

    /**
     * Game is in start-up phase, i.e. players will be added/removed, countries will be distributed, and initial
     * armies will be assigned to the players.
     * Phase usually set after the loadmap command.
     * Phase ends when 'assigncountries' command is encountered, i.e countries are distributed and initial armies are
     * assigned.
     */
    STARTUP,

    /**
     * Player assigns initial armies to the countries owned by him/her.
     * Phase ends when all player's have assigned initial armies.
     */
    ASSIGN_REINFORCEMENTS,


    /**
     * Individual turn of player begins in round-robin fashion.
     * Player adds order to the list of current orders as in deploy command.
     * Phase ends all Players provide order based on individual turn.
     */
    ISSUE_ORDERS,

    /**
     * Executes orders for all the Players from the pool of order's list.
     * Phase ends when all the num armies are placed on CountryID as per order's list.
     */
    EXECUTE_ORDERS,

    /**
     * Marks the end of the current player and signals to let the player get the turn.
     * Phase ends when next player starts their move.
     */
    TURN,

    /**
     * CLoses the Game.
     */
    QUIT
}

