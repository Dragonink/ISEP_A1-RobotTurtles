package robotturtles.g45.board;

import robotturtles.g45.BoardSprite;
import robotturtles.g45.Sprite;

import javax.swing.*;
import java.awt.*;

public enum Turtle {
    BEEP("beepTileS.jpg","beepTileN.jpg","beepTileE.jpg","beepTileW.jpg",Color.BLUE, Sprite.SPRITE_PATH+"choixBeep.jpg"),
    PANGLE("pangleTileS.jpg","pangleTileN.jpg","pangleTileE.jpg","pangleTileW.jpg", Color.ORANGE, Sprite.SPRITE_PATH + "choixPangle.jpg"),
    PI("piTileS.jpg","pangleTileN.jpg","pangleTileE.jpg","pangleTileW.jpg",Color.RED,Sprite.SPRITE_PATH+"choixPi.jpg"),
    DOT("dotTileS.jpg","dotTileN.jpg","dotTileE.jpg","dotTileW.jpg",Color.MAGENTA,Sprite.SPRITE_PATH+"choixDot.jpg");

    private final Color color;
    private final BoardSprite spriteS;
    private final BoardSprite spriteN;
    private final BoardSprite spriteW;
    private final BoardSprite spriteE;
    private final ImageIcon playerChooseIcon;

    Turtle(String spriteNameS,String spriteNameN, String spriteNameE, String spriteNameW, Color color, String playerChooseIconName ){
        this.color = color;
        this.spriteS = new BoardSprite(spriteNameS);
        this.spriteN = new BoardSprite(spriteNameN);
        this.spriteW = new BoardSprite(spriteNameW);
        this.spriteE = new BoardSprite(spriteNameE);
        this.playerChooseIcon = new ImageIcon(this.getClass().getResource(playerChooseIconName));
    }

    public Color getColor() {
        return color;
    }

    public BoardSprite getSpriteS() { return spriteS; }
    public BoardSprite getSpriteN() { return spriteN; }
    public BoardSprite getSpriteW() { return spriteW; }
    public BoardSprite getSpriteE() { return spriteE; }

    public ImageIcon getPlayerChooseIcon() {
        return playerChooseIcon;
    }
}
