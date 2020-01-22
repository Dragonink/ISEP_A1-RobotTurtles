package robotturtles.g45.views.game;

import robotturtles.g45.*;
import robotturtles.g45.board.BoardWall;
import robotturtles.g45.views.winner.WinnerDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
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
        public void onBoardCellClick(int xPos, int yPos) {
            int wallIdx = -1;
            for (int i = 0; i < 5; i++) {
                if (playerBoards.get(activePlayerIndex).getPanel()[0][i].isSelected()) {
                    wallIdx = i;
                }
            }
            if (wallIdx > -1) {
                Game.board.getBoard()[xPos][yPos] = wallIdx > 1 ? BoardWall.BRICK.getSprite() : BoardWall.ICE.getSprite();
                JButton boardCell = boardCells[xPos][yPos];
                boardCell.setIcon(new ImageIcon(wallIdx > 1 ? BoardWall.BRICK.getSprite().getSprite() : BoardWall.ICE.getSprite().getSprite()));
                boardCell.setDisabledIcon(new ImageIcon(wallIdx > 1 ? BoardWall.BRICK.getSprite().getSprite() : BoardWall.ICE.getSprite().getSprite()));
                boardCell.setEnabled(false);
                boardCell.setOpaque(false);
                playerBoards.get(activePlayerIndex).getPanel()[0][wallIdx].setSelected(false);
                playerBoards.get(activePlayerIndex).getPanel()[0][wallIdx].setIcon(null);
                playerBoards.get(activePlayerIndex).getPanel()[0][wallIdx].setDisabledIcon(null);
                playerBoards.get(activePlayerIndex).getPanel()[0][wallIdx].removeItemListener(playerBoards.get(activePlayerIndex).getPanel()[0][wallIdx].getItemListeners()[0]);
                playerBoards.get(activePlayerIndex).afterAction();
            }
        }

        @Override
        public void onPlayerChange() {
            changeActivePlayer();
        }

        @Override
        public void onActionDone(Sprite sprite) {
            Game.board.getBoard()[sprite.getPos()[0]][sprite.getPos()[1]] = sprite.getSprite();
            boardPanel.removeAll();
            fillBoard();
            boardPanel.revalidate();
            boardPanel.repaint();
        }

        @Override
        public void onPlayerSuccess() {
            Game.playerWins(Game.getPlayers()[activePlayerIndex]);
            if (Game.getWinners().length < Game.getPlayers().length - 1) {
                this.onPlayerChange();
            } else {
                for (Player player : Game.getPlayers()) {
                    if (Arrays.stream(Game.getWinners()).noneMatch(winner -> winner.turtle.name().equals(player.turtle.name()))) {
                        Game.playerWins(player);
                    }
                }
                winnerDelegate.onGameOver();
            }
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
    private WinnerDelegate winnerDelegate;

    public GameView(WinnerDelegate winnerDelegate) {
        drawGameView();
        setupUIForPlayer();
        fillBoard();
        fillPlayerPanel();
        this.winnerDelegate = winnerDelegate;
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    private String numPlayer() {
        return String.format("Joueur %s : %s", (activePlayerIndex + 1), (Game.getPlayers()[activePlayerIndex].turtle.name()));
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

    private JButton createBoardCell(ImageIcon imageIcon, int xPos, int yPos) {
        JButton button = new JButton(imageIcon);
        button.setDisabledIcon(imageIcon);
        button.setEnabled(false);
        button.setOpaque(false);
        button.addActionListener(new OnBoardCellActionListener(xPos, yPos));
        return button;
    }

    private void fillBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (Game.board.getSquare(i, j).getSprite() != null) {
                    JButton boardCell = createBoardCell(new ImageIcon(Game.board.getSquare(i, j).getSprite()), i, j);
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
        if (Arrays.stream(Game.getWinners()).noneMatch(player -> Game.getPlayers()[activePlayerIndex].turtle.name().equals(player.turtle.name()))) {
            rootPanel.setBackground(Game.getPlayers()[activePlayerIndex].turtle.getColor());
            infoPlayerLabel.setText(numPlayer());
            fillPlayerPanel();
        } else {
            changeActivePlayer();
        }
    }

    private class OnBoardCellActionListener implements ActionListener {

        private int xPos;
        private int yPos;

        OnBoardCellActionListener(int xPos, int yPos) {
            this.xPos = xPos;
            this.yPos = yPos;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            gameDelegate.onBoardCellClick(xPos, yPos);
        }
    }
}
