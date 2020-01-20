package robotturtles.g45;

import robotturtles.g45.board.BoardWall;
import robotturtles.g45.board.Jewel;
import robotturtles.g45.player.Card;
import robotturtles.g45.player.PlayerWall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** Class of the player panel. */
public final class PlayerPanel {
    public AbstractButton[][] getPanel() {
        return panel;
    }

    /** Matrix containing the sprites. */
    private final AbstractButton[][] panel = new AbstractButton[3][5];
    private final JButton play = createPlay(Game.getPlayers()[0]);
    private final JButton defausse = createDefausse(Game.getPlayers()[0]);

    public PlayerPanel(Player player) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                panel[i][j] = new JButton(new ImageIcon());
            }
        }
        for (int i = 0 ; i < player.getIceWalls()+player.getBrickWalls(); i++){
            panel[0][i] = new JToggleButton("", new ImageIcon((i < player.getIceWalls()) ? PlayerWall.ICE.getSprite().getSprite() : PlayerWall.BRICK.getSprite().getSprite()));
        }
        panel[1][0] = play;
        panel[1][2] = defausse;
        switch (player.turtle){
            case BEEP: panel[1][4] = new JButton(new ImageIcon(Card.BACK_BEEP.getSprite().getSprite()));
            case PANGLE: panel[1][4] = new JButton(new ImageIcon(Card.BACK_PANGLE.getSprite().getSprite()));
            case DOT: panel[1][4] = new JButton(new ImageIcon(Card.BACK_DOT.getSprite().getSprite()));
            case PI: panel[1][4] = new JButton(new ImageIcon(Card.BACK_PI.getSprite().getSprite()));
        }

        for(int i = 0; i < 5; i++) {
            panel[2][i] = new JButton(new ImageIcon(player.getHand()[i].getSprite().getSprite()));
        }
    }

    private JButton createPlay(Player player){
        JButton button = new JButton(new ImageIcon(this.getClass().getResource(Sprite.SPRITE_PATH+"play2.jpg")));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.executeProgram();
            }
        });
        return button;
    }

    private JButton createDefausse(Player player){
        JButton button = new JButton(new ImageIcon(this.getClass().getResource(Sprite.SPRITE_PATH+"defausse.jpg")));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.ditchCards();
            }
        });
        return button;
    }
}
