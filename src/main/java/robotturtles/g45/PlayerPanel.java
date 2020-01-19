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
    public JButton[][] getPanel() {
        return panel;
    }

    /** Matrix containing the sprites. */
    private final JButton[][] panel = new JButton[3][5];
    private final JButton play = createPlay();
    private final JButton defausse = createDefausse();

    public PlayerPanel(Player player) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                panel[i][j] = new JButton(new ImageIcon());
            }
        }
        for (int i = 0 ; i < player.getIceWalls()+player.getBrickWalls(); i++){
            panel[0][i] = new JButton(new ImageIcon((i < player.getIceWalls()) ? PlayerWall.ICE.getSprite().getSprite() : PlayerWall.BRICK.getSprite().getSprite()));
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

    private JButton createPlay(){
        JButton bouton = new JButton(new ImageIcon(this.getClass().getResource(Sprite.SPRITE_PATH+"play.jpg")));
        bouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("bravo");
            }
        });
        return bouton;
    }

    private JButton createDefausse(){
        JButton bouton = new JButton(new ImageIcon(this.getClass().getResource(Sprite.SPRITE_PATH+"defausse.jpg")));
        bouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("bravo");
            }
        });
        return bouton;
    }
}
