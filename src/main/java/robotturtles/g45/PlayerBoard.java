package robotturtles.g45;

import robotturtles.g45.player.Card;
import robotturtles.g45.player.PlayerWall;
import robotturtles.g45.views.game.GameDelegate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Class of the player panel.
 */
public final class PlayerBoard {
    /**
     * Matrix containing the sprites.
     */
    private final AbstractButton[][] panel = new AbstractButton[3][5];
    private final JButton playButton;
    private final JButton ditchButton;
    private final int numPlayer;
    private final GameDelegate delegate;
    private boolean hasDoneAction = false;


    public PlayerBoard(Player player, GameDelegate delegate, int numPlayer) {

        this.delegate = delegate;
        this.numPlayer = numPlayer;
        this.playButton = createPlayButton();
        this.ditchButton = createDitchButton();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                panel[i][j] = createEmptyButton();
            }
        }
        for (int i = 0; i < player.getIceWalls() + player.getBrickWalls(); i++) {
            panel[0][i] = createWallButton(i < player.getIceWalls(), i);
        }
        panel[1][0] = playButton;
        panel[1][2] = ditchButton;
        switch (player.turtle) {
            case BEEP:
                panel[1][4] = createDeckButton(new ImageIcon(Card.BACK_BEEP.getSprite().getSprite()));
                break;
            case PANGLE:
                panel[1][4] = createDeckButton(new ImageIcon(Card.BACK_PANGLE.getSprite().getSprite()));
                break;
            case DOT:
                panel[1][4] = createDeckButton(new ImageIcon(Card.BACK_DOT.getSprite().getSprite()));
                break;
            case PI:
                panel[1][4] = createDeckButton(new ImageIcon(Card.BACK_PI.getSprite().getSprite()));
                break;
        }

        for (int i = 0; i < 5; i++) {
            panel[2][i] = createHandButton(new ImageIcon(player.getHand()[i].getSprite().getSprite()), i);
        }
    }

    public AbstractButton[][] getPanel() {
        return panel;
    }

    private JButton createEmptyButton() {
        JButton button = new JButton();
        button.setOpaque(false);
        return button;
    }

    private JToggleButton createHandButton(ImageIcon imageIcon, int index) {
        JToggleButton button = new JToggleButton(imageIcon);
        button.addItemListener(new OnHandActionListener(index));
        button.setOpaque(false);
        return button;
    }

    private JButton createDeckButton(ImageIcon imageIcon) {
        JButton button = new JButton(imageIcon);
        button.addActionListener(new OnDeckActionListener());
        button.setEnabled(false);
        button.setOpaque(false);
        return button;
    }

    private JToggleButton createWallButton(boolean hasEnoughIceWalls, int columnIndex) {
        JToggleButton toggleButton = new JToggleButton(new ImageIcon(hasEnoughIceWalls ? PlayerWall.ICE.getSprite().getSprite() : PlayerWall.BRICK.getSprite().getSprite()));
        toggleButton.addItemListener(new OnWallActionListener(columnIndex));
        toggleButton.setOpaque(false);
        return toggleButton;
    }

    private JButton createPlayButton() {
        JButton button = new JButton(new ImageIcon(this.getClass().getResource(Sprite.SPRITE_PATH + "play2.jpg")));
        button.setOpaque(false);
        button.addActionListener(new OnPlayActionListener());
        return button;
    }

    private JButton createDitchButton() {
        JButton button = new JButton(new ImageIcon(this.getClass().getResource(Sprite.SPRITE_PATH + "defausse.jpg")));
        button.setEnabled(false);
        button.setOpaque(false);
        button.addActionListener(actionEvent -> {
            int index = findSelectedCardIndex();
            Game.getPlayers()[numPlayer].ditchCard(index);
            panel[2][index].setIcon(null);
            panel[2][index].setSelected(false);
            panel[2][index].removeItemListener(panel[2][index].getItemListeners()[0]);
            panel[2][index] = null;
            toggleHandCards(true);
        });
        return button;
    }

    private int findSelectedCardIndex() {
        int cardIndex = -1;
        for (int i = 0; i < 5; i++) {
            if (getPanel()[2][i] != null && getPanel()[2][i].isSelected()) {
                cardIndex = i;
            }
        }
        return cardIndex;
    }

    public void afterAction() {
        for (int i = 0; i < 5; i++) {
            panel[0][i].setEnabled(false);
        }
        panel[1][0].setEnabled(false);
        panel[1][4].setEnabled(true);
        hasDoneAction = true;
    }

    private void togglePlayerPanel(boolean enabled) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                if (panel[i][j] != null) {
                    panel[i][j].setEnabled(enabled);
                }
            }
        }
        panel[1][2].setEnabled(false);
        panel[1][4].setEnabled(false);
    }

    private void toggleHandCards(boolean enabled) {
        for (int i = 0; i < 5; i++) {
            if (panel[2][i] != null) {
                panel[2][i].setEnabled(enabled);
            }
        }
        panel[1][0].setEnabled(!enabled);
        panel[1][2].setEnabled(!enabled);
        panel[1][4].setEnabled(enabled);
    }

    private class OnPlayActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            int index = findSelectedCardIndex();

            if (index == -1) {
                Game.getPlayers()[numPlayer].executeProgram();
                hasDoneAction = true;
            } else {
                Game.getPlayers()[numPlayer].addToProgram(index);
                panel[2][index].setIcon(null);
                panel[2][index].setSelected(false);
                panel[2][index].removeItemListener(panel[2][index].getItemListeners()[0]);
                panel[2][index] = null;
                toggleHandCards(true);
            }
        }
    }

    private class OnWallActionListener implements ItemListener {

        private int index;

        public OnWallActionListener(int index) {
            this.index = index;
        }

        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                togglePlayerPanel(false);
                panel[0][index].setEnabled(true);
                delegate.onWallClick(index);
            } else {
                togglePlayerPanel(true);
                panel[1][2].setEnabled(false);
                panel[1][4].setEnabled(false);
                delegate.onWallUnclick(index);
            }
        }
    }

    private class OnDeckActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Game.getPlayers()[numPlayer].pickCards();
            for (int i = 0; i < 5; i++) {
                panel[2][i] = createHandButton(new ImageIcon(Game.getPlayers()[numPlayer].getHand()[i].getSprite().getSprite()), i);
            }
            togglePlayerPanel(true);
            hasDoneAction = false;
            delegate.onPlayerChange();
        }
    }

    private class OnHandActionListener implements ItemListener {

        private int index;

        public OnHandActionListener(int index) {
            this.index = index;
        }

        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                togglePlayerPanel(false);
                panel[2][index].setEnabled(true);
                panel[1][0].setEnabled(!hasDoneAction);
                panel[1][2].setEnabled(true);
            } else {
                togglePlayerPanel(true);
                panel[1][2].setEnabled(false);
                panel[1][4].setEnabled(false);
                for (int i = 0; i < 5; i++) {
                    if (Game.getPlayers()[numPlayer].getHand()[i] == null) {
                        togglePlayerPanel(false);
                        panel[1][4].setEnabled(true);
                        for (int j = 0; j < 5; j++) {
                            if (Game.getPlayers()[numPlayer].getHand()[j] != null) {
                                panel[2][j].setEnabled(true);
                            }
                        }
                        i = 5;
                    }
                }
            }
        }
    }
}
