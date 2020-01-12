package robotturtles.g45;

import robotturtles.g45.board.Turtle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public static Boolean getIsPlayEnablePlay() {
        return players.size()>1;
    }

    public static Boolean getIsPlayEnableBack() {
        return players.size()>0;
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
        JPanel principal = new JPanel();
        principal.setLayout(new BorderLayout(2,2));
        principal.setBackground(Color.WHITE);
        JPanel numPlayer = new JPanel();
        numPlayer.setOpaque(true);
        numPlayer.setBackground(Color.WHITE);
        JLabel numPlayerLabel = new JLabel("Joueur " + (players.size()+1), JLabel.CENTER);
        numPlayerLabel.setForeground(Color.BLACK);
        numPlayerLabel.setFont(new Font("Serif",Font.PLAIN,40));
        numPlayer.add(numPlayerLabel);
        principal.add(numPlayer,BorderLayout.NORTH);
        JPanel buttons = new JPanel();
        buttons.setLayout(new BorderLayout(2,2));
        buttons.setOpaque(false);
        JPanel backButton = new JPanel();
        backButton.setOpaque(false);
        JButton backButtonLabel = new JButton();
        backButtonLabel.setIcon(new ImageIcon(new Game().getClass().getResource("/images/back.jpg")));
        backButtonLabel.setPreferredSize(new Dimension(50,50));
        backButtonLabel.setEnabled(getIsPlayEnableBack());
        backButton.add(backButtonLabel);
        JPanel playButton = new JPanel();
        backButton.setOpaque(false);
        buttons.add(backButton,BorderLayout.LINE_START);
        JButton playButtonLabel = new JButton();
        playButton.setOpaque(false);
        playButtonLabel.setEnabled(getIsPlayEnablePlay());
        playButtonLabel.setIcon(new ImageIcon(new Game().getClass().getResource("/images/play.jpg")));
        playButtonLabel.setPreferredSize(new Dimension(50,50));
        playButtonLabel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawGameWindow();
            }
        });
        playButton.add(playButtonLabel);
        buttons.add(playButton,BorderLayout.LINE_END);
        JPanel choose = new JPanel();
        choose.setOpaque(false);
        List<JButton> cards = new ArrayList();
        for (Turtle turtle : chooseTurtle){
            JButton card = new JButton(String.valueOf(turtle));
            card.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            card.setIcon(turtle.getPlayerChooseIcon());
            card.setVerticalTextPosition(SwingConstants.BOTTOM);
            card.setHorizontalTextPosition(SwingConstants.CENTER);
            card.setFont(new Font("Serif",Font.BOLD,30));
            card.setForeground(turtle.getColor());

            card.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    card.setEnabled(false);
                    players.add(new Player(turtle));
                    playButtonLabel.setEnabled(getIsPlayEnablePlay());
                    backButtonLabel.setEnabled(getIsPlayEnableBack());
                    if (players.size() < 4) {
                        numPlayerLabel.setText("Joueur " + (players.size() + 1));
                    } else {
                        numPlayerLabel.setText("Go to play!!!");
                        numPlayerLabel.setForeground(Color.RED);
                    }
                }
            });
            choose.add(card);
            cards.add(card);
        }
        backButtonLabel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Player turtle = players.remove(players.size() - 1);
                for (JButton button : cards){
                    if (button.getText()==turtle.turtle.name()){
                        button.setEnabled(true);
                        backButtonLabel.setEnabled(getIsPlayEnableBack());
                        playButtonLabel.setEnabled(getIsPlayEnablePlay());
                        numPlayerLabel.setText("Joueur " + (players.size() + 1));
                    }
                }
            }
        });
        principal.add(choose,BorderLayout.CENTER);
        principal.add(buttons,BorderLayout.SOUTH);
        panel.add(principal);
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
