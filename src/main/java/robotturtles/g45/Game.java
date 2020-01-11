package robotturtles.g45;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import javax.swing.*;

/** Core class of the game. */
public final class Game {
    /** Game board. */
    public static final Board board;

    /** Control panel for a player. */
    public static final PlayerPanel panel;

    /** List of the players, in play order. */
    private static final List<Player> players = new ArrayList<Player>(4);
    /** Gets an array of the players, in play order.
     * 
     * @return Array of the players, in play order.
     */
    public static final Player[] getPlayers() {
        return players.toArray(new Player[players.size()]);
    }

    /** List of the winning players. */
    private static final List<Player> winners = new ArrayList<Player>(3);
    /** Gets an array of the winning players.
     * 
     * @return Array of the winning players.
     */
    public static final Player[] getWinners() {
        return winners.toArray(new Player[winners.size()]);
    }


    /** Draws the main menu window. */
    private static final void drawNewWindow() {
    }

    /** Draws the game window. */
    private static final void drawGameWindow() {}

    /** Entrypoint of the program.
     * 
     * @param args
     */
    public static void main(String[] args) {
        //TODO
    }
}
