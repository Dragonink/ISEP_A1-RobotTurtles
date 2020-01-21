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
    private final GameDelegate delegate;


    public PlayerBoard(Player player, GameDelegate delegate, int numPlayer) {

        this.delegate = delegate;
        this.playButton = createPlayButton(Game.getPlayers()[numPlayer]);
        this.ditchButton = createDitchButton(Game.getPlayers()[numPlayer]);

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
            panel[2][i] = createHandButton(new ImageIcon(player.getHand()[i].getSprite().getSprite()));
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

    private JButton createHandButton(ImageIcon imageIcon) {
        JButton button = new JButton(imageIcon);
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
        JToggleButton toggleButton = new JToggleButton(null, new ImageIcon(hasEnoughIceWalls ? PlayerWall.ICE.getSprite().getSprite() : PlayerWall.BRICK.getSprite().getSprite()));
        toggleButton.addItemListener(new OnWallActionListener(columnIndex));
        toggleButton.setOpaque(false);
        return toggleButton;
    }

    private JButton createPlayButton(Player player) {
        JButton button = new JButton(new ImageIcon(this.getClass().getResource(Sprite.SPRITE_PATH + "play2.jpg")));
        button.setOpaque(false);
        button.addActionListener(e -> player.executeProgram());
        return button;
    }

    private JButton createDitchButton(Player player) {
        JButton button = new JButton(new ImageIcon(this.getClass().getResource(Sprite.SPRITE_PATH + "defausse.jpg")));
        button.setEnabled(false);
        button.setOpaque(false);
        button.addActionListener(e -> player.ditchCards());
        return button;
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

        private void togglePlayerPanel(boolean enabled) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 5; j++) {
                    panel[i][j].setEnabled(enabled);
                }
            }
        }
    }

    private class OnDeckActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            delegate.onPlayerChange();
        }
    }

    private class OnPlayActionListener implements ItemListener {

        private int index;

        public OnPlayActionListener(int index) {
            this.index = index;
        }

        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                togglePlayerPanel(true);
                delegate.onWallUnclick(index);
            } else {
                togglePlayerPanel(true);
                delegate.onWallUnclick(index);
            }
        }

        private void togglePlayerPanel(boolean enabled) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 5; j++) {
                    panel[i][j].setEnabled(enabled);
                }
            }
        }
    }
}
