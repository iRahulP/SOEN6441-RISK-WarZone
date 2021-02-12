    package model;
/**
 * It maintains the current phase of the game.
 * @author rahul
 *
 */
public enum Phase {

    /**
     * The game is yet to begin. First command has yet not been encountered.
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
     * Phase ends when 'assigncountries' command is encountered, i.e countries are distributed and initial armies are
     * assigned.
     */
    STARTUP,

    /**
     * Player assigns initial armies to the countries owned by him/her.
     * Phase ends when all player's have assigned initial armies.
     */
    ARMYALLOCATION,

    /**
     * Individual turn of player begins in round-robin fashion.
     * Player assigns reinforced armies amongst owned countries.
     * Phase ends when reinforcement armies are distributed amongst the owned countries.
     */
    DEPLOYMENT,

    /**
     * Marks the end of the current player and signals to let the player get the turn.
     * Phase ends when next player starts their move.
     */
    TURNEND,

    /**
     * Indicates desire to completely close the game.
     */
    QUIT
}

