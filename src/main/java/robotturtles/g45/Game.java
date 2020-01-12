package robotturtles.g45;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/** Core class of the game. */
public final class Game {
    /** Game board. */
    public static Board board;

    /** Control panel for a player. */
    public static PlayerPanel panel;

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
    public static Player[] getWinners() {
        return winners.toArray(new Player[winners.size()]);
    }

    public static Boolean getIsPlayEnablePlay() {
        return players.size()>1;
    }

    public static Boolean getIsPlayEnableBack() {
        return players.size()>0;
    }

    /** Draws the main menu window. */
    private static void drawNewWindow() {
        JFrame window = new JFrame("Bienvenue sur ROBOT TURTLE'S");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setLocationByPlatform(true);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        ChoosePlayerView choosePlayerView = new ChoosePlayerView();
        window.setSize(screensize);
        window.setLocation(0, 0);
        window.add(choosePlayerView.getBackgroundImagePanel());
        window.setResizable(false);
        window.setVisible(true);
    }

    /** Draws the game window. */
    private static void drawGameWindow() {
    }

    /** Entrypoint of the program.
     * 
     * @param args
     */
    public static void main(String[] args) {
        drawNewWindow();
    }
}
