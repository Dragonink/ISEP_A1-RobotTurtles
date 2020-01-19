package robotturtles.g45.views.game;

import robotturtles.g45.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameView {

    private JPanel rootPanel = createRootPanel();
    private JPanel infoPlayerPanel = createInfoPlayerPanel();
    private JLabel infoPlayerLabel = createInfoPlayerLabel();
    private JPanel gamePanel = createGamePanel();
    private JPanel boardPanel = createBoardPanel();
    private JPanel playerPanel = createPlayerPanel();
    private Board board = new Board();

    public GameView() {
        drawGameView();
        fillBoard();
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    private String numPlayer() {
        return String.format("Joueur %s : %s", (1), (Game.getPlayers()[0].turtle.name()));
    }

    private void drawGameView() {
        rootPanel.add(infoPlayerPanel, BorderLayout.NORTH);
        infoPlayerPanel.add(infoPlayerLabel);
        rootPanel.add(gamePanel, BorderLayout.CENTER);
        gamePanel.add(boardPanel);
        gamePanel.add(playerPanel);
    }

    private JPanel createRootPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(20, 20));
        panel.setBackground(Game.getPlayers()[0].turtle.getColor());
        return panel;
    }

    private JPanel createInfoPlayerPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        return panel;
    }

    private JLabel createInfoPlayerLabel() {
        JLabel label = new JLabel();
        label.setText(numPlayer());
        label.setBorder(BorderFactory.createEmptyBorder(20, 5, 5, 5));
        label.setFont(new Font("Serif", Font.PLAIN, 40));
        label.setBackground(Game.getPlayers()[0].turtle.getColor());
        label.setForeground(Color.WHITE);
        return label;
    }

    private JPanel createGamePanel() {
        JPanel panel = new JPanel(new GridLayout(1,2, 30, 30));
        panel.setOpaque(false);
        return panel;
    }

    private JPanel createBoardPanel() {
        BoardImagePanel panel = new BoardImagePanel(new GridLayout(8, 8));
        return panel;
    }

    private void fillBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getBoard()[i][j].getSprite() != null) {
                    JLabel label = new JLabel(new ImageIcon(board.getBoard()[i][j].getSprite()));
                    boardPanel.add(label);
                } else {
                    JLabel label = new JLabel(new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB)));
                    boardPanel.add(label);
                }
            }
        }
    }

    private JPanel createPlayerPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 5));
        PlayerPanel joueur = new PlayerPanel(Game.getPlayers()[0]);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                if (joueur.getPanel()[i][j] != null) {
                    panel.add(joueur.getPanel()[i][j]);
                } else {
                    JLabel label = new JLabel(new ImageIcon(new BufferedImage(170, 102, BufferedImage.TYPE_INT_ARGB)));
                    panel.add(label);
                }
            }
        }
        return panel;
    }
}
