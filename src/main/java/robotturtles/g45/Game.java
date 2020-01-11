package robotturtles.g45;

import robotturtles.g45.board.Turtle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

/** Core class of the game. */
public final class Game {
    /** Game board. */
    public static Board board;

    /** Control panel for a player. */
    public static PlayerPanel panel;

    private static final List<Turtle> chooseTurtle = Arrays.asList(Turtle.BEEP,Turtle.PANGLE,Turtle.DOT,Turtle.PI);

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

    private static JButton buildCard(Turtle turtle){
        JButton card = new JButton();
        card.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        card.setIcon(turtle.getPlayerChooseIcon());
        return card;
    }

    /** Draws the main menu window. */
    private static final void drawNewWindow() {
        JFrame fenetre = new JFrame("Bienvenue sur ROBOT TURTLE'S");
        fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fenetre.setLocationByPlatform(true);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        fenetre.setSize(screensize);
        fenetre.setLocation(0,0);
        MainPanel panel = new MainPanel(new GridBagLayout());
        panel.setOpaque(true);
        JPanel principale = new JPanel();
        principale.setLayout(new BorderLayout(2,2));
        principale.setBackground(Color.WHITE);
        JPanel numPlayer = new JPanel();
        numPlayer.setOpaque(true);
        numPlayer.setBackground(Color.WHITE);
        JLabel numPlayerLabel = new JLabel("Joueur", JLabel.CENTER);
        numPlayerLabel.setForeground(Color.BLACK);
        numPlayerLabel.setFont(new Font("Serif",Font.PLAIN,40));
        numPlayer.add(numPlayerLabel);
        principale.add(numPlayer,BorderLayout.NORTH);
        JPanel choose = new JPanel();
        choose.setOpaque(false);
        for (Turtle turtle : chooseTurtle){
            choose.add(buildCard(turtle));
        }
        principale.add(choose,BorderLayout.CENTER);
        JPanel buttons = new JPanel();
        buttons.setLayout(new BorderLayout(2,2));
        buttons.setOpaque(false);
        JPanel backButton = new JPanel();
        backButton.setOpaque(false);
        JButton backButtonLabel = new JButton();
        backButtonLabel.setIcon(new ImageIcon(new Game().getClass().getResource("/images/back.jpg")));
        backButtonLabel.setPreferredSize(new Dimension(50,50));
        backButton.add(backButtonLabel);
        JPanel playButton = new JPanel();
        backButton.setOpaque(false);
        buttons.add(backButton,BorderLayout.LINE_START);
        JButton playButtonLabel = new JButton();
        playButton.setOpaque(false);
        playButtonLabel.setIcon(new ImageIcon(new Game().getClass().getResource("/images/play.jpg")));
        playButtonLabel.setPreferredSize(new Dimension(50,50));
        playButton.add(playButtonLabel);
        buttons.add(playButton,BorderLayout.LINE_END);
        principale.add(buttons,BorderLayout.SOUTH);
        panel.add(principale);
        fenetre.add(panel);
        fenetre.setResizable(false);
        fenetre.setVisible(true);
    }

    /** Draws the game window. */
    private static final void drawGameWindow() {}

    /** Entrypoint of the program.
     * 
     * @param args
     */
    public static void main(String[] args) {
        drawNewWindow();
    }
}
