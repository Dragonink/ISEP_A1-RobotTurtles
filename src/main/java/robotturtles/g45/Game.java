package robotturtles.g45;

import robotturtles.g45.views.choosePlayer.ChoosePlayerView;
import robotturtles.g45.views.game.GameView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Core class of the game.
 */
public final class Game {

    enum GameState {
        CHOOSEPLAYER,
        PLAYING
    }

    private static JFrame window = createMainWindow();

    /**
     * Game board.
     */
    public static Board board;

    /**
     * Control panel for a player.
     */
    public static PlayerBoard panel;

    /**
     * List of the players, in play order.
     */
    private static List<Player> players = new ArrayList<>();

    /**
     * Gets an array of the players, in play order.
     *
     * @return Array of the players, in play order.
     */
    public static final Player[] getPlayers() {
        return players.toArray(new Player[players.size()]);
    }

    /**
     * List of the winning players.
     */
    private static final List<Player> winners = new ArrayList<Player>(3);

    /**
     * Gets an array of the winning players.
     *
     * @return Array of the winning players.
     */
    public static Player[] getWinners() {
        return winners.toArray(new Player[winners.size()]);
    }

    private static JFrame createMainWindow() {
        JFrame window = new JFrame("Bienvenue sur ROBOT TURTLES");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setLocationByPlatform(true);
        window.setSize(screenSize);
        window.setLocation(0, 0);
        window.setResizable(false);
        return window;
    }

    /**
     * Draws the choose player window.
     */
    private static void drawChoosePlayerView() {
        ChoosePlayerView choosePlayerView = new ChoosePlayerView(chosenTurtles -> {
            players = chosenTurtles.stream().map(Player::new).collect(Collectors.toList());
            draw(GameState.PLAYING);
        });
        window.add(choosePlayerView.getRootPanel());
    }

    /**
     * Draws the game window.
     */
    private static void drawGameView() {
        GameView gameView = new GameView();
        window.add(gameView.getRootPanel());
    }

    private static void draw(final GameState state) {
        window.getContentPane().removeAll();
        switch (state) {
            case CHOOSEPLAYER:
                drawChoosePlayerView();
                break;
            case PLAYING:
                drawGameView();
                break;
        }
        window.setVisible(true);
    }

    /**
     * Entrypoint of the program.
     *
     * @param args
     */
    public static void main(String[] args) {
        draw(GameState.CHOOSEPLAYER);
    }
}
