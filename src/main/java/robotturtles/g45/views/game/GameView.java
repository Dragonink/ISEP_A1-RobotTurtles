package robotturtles.g45.views.game;

import robotturtles.g45.BoardImagePanel;
import robotturtles.g45.Game;
import robotturtles.g45.PlayerBoard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameView {

    private final GameDelegate gameDelegate = new GameDelegate() {
        @Override
        public void onWallClick(int wallIdx) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    boolean isCellAvailable = Game.board.canBuildWall(wallIdx, i, j);
                    boardCells[i][j].setEnabled(isCellAvailable);
                    boardCells[i][j].setOpaque(isCellAvailable);
                }
            }
        }

        @Override
        public void onWallUnclick(int wallIdx) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    boardCells[i][j].setEnabled(false);
                    boardCells[i][j].setOpaque(false);
                }
            }
        }

        @Override
        public void onPlayerChange() {
            changeActivePlayer();
        }
    };
    private JPanel rootPanel = createRootPanel();
    private JPanel infoPlayerPanel = createInfoPlayerPanel();
    private JLabel infoPlayerLabel = createInfoPlayerLabel();
    private JPanel gamePanel = createGamePanel();
    private JPanel boardPanel = createBoardPanel();
    private JPanel playerPanel = createPlayerPanel();
    private List<PlayerBoard> playerBoards = createPlayerBoards();
    private JButton[][] boardCells = new JButton[8][8];
    private int activePlayerIndex = 0;

    public GameView() {
        drawGameView();
        setupUIForPlayer();
        fillBoard();
        fillPlayerPanel();
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    private String numPlayer() {
        return String.format("Joueur %s : %s", activePlayerIndex + 1, (Game.getPlayers()[activePlayerIndex].turtle.name()));
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
        return panel;
    }

    private JPanel createInfoPlayerPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        return panel;
    }

    private JLabel createInfoPlayerLabel() {
        JLabel label = new JLabel();
        label.setBorder(BorderFactory.createEmptyBorder(20, 5, 5, 5));
        label.setFont(new Font("Serif", Font.PLAIN, 40));
        label.setBackground(Game.getPlayers()[0].turtle.getColor());
        label.setForeground(Color.WHITE);
        return label;
    }

    private JPanel createGamePanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 30, 30));
        panel.setOpaque(false);
        return panel;
    }

    private JPanel createBoardPanel() {
        return new BoardImagePanel(new GridLayout(8, 8));
    }

    private JPanel createPlayerPanel() {
        return new JPanel(new GridLayout(3, 5));
    }

    private JButton createBoardCell(ImageIcon imageIcon) {
        JButton button = new JButton(imageIcon);
        button.setDisabledIcon(imageIcon);
        button.setEnabled(false);
        button.setOpaque(false);
        return button;
    }

    private void fillBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (Game.board.getSquare(i, j).getSprite() != null) {
                    JButton boardCell = createBoardCell(new ImageIcon(Game.board.getSquare(i, j).getSprite()));
                    boardCells[i][j] = boardCell;
                    boardPanel.add(boardCell);
                }
            }
        }
    }

    private List<PlayerBoard> createPlayerBoards() {
        List<PlayerBoard> playerBoards = new ArrayList<>();
        for (int i = 0; i < Game.getPlayers().length; i++) {
            playerBoards.add(new PlayerBoard(Game.getPlayers()[i], gameDelegate, i));
        }
        return playerBoards;
    }

    private void fillPlayerPanel() {
        playerPanel.removeAll();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                playerPanel.add(playerBoards.get(activePlayerIndex).getPanel()[i][j]);
            }
        }
    }

    private void changeActivePlayer() {
        if (activePlayerIndex == Game.getPlayers().length - 1) {
            activePlayerIndex = 0;
        } else {
            activePlayerIndex += 1;
        }

        setupUIForPlayer();
    }

    private void setupUIForPlayer() {
        rootPanel.setBackground(Game.getPlayers()[activePlayerIndex].turtle.getColor());
        infoPlayerLabel.setText(numPlayer());
        fillPlayerPanel();
    }
}
