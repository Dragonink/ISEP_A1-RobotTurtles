package robotturtles.g45.board;

import robotturtles.g45.BoardSprite;
import robotturtles.g45.Sprite;

import javax.swing.*;
import java.awt.*;

public enum Turtle {
    BEEP("beepTile.jpg",Color.BLUE, Sprite.SPRITE_PATH+"choixBeep.jpg"),
    PANGLE("pangleTile.jpg",Color.YELLOW,Sprite.SPRITE_PATH+"choixPangle.jpg"),
    PI("piTile.jpg",Color.RED,Sprite.SPRITE_PATH+"choixPi.jpg"),
    DOT("dotTile.jpg",Color.MAGENTA,Sprite.SPRITE_PATH+"choixDot.jpg");

    private final Color color;
    private final ImageIcon image;
    private final BoardSprite sprite;
    private final ImageIcon playerChooseIcon;

    Turtle(String spriteName, Color color, String playerChooseIconName ){
        this.color = color;
        this.sprite = new BoardSprite(spriteName);
        this.image = new ImageIcon(spriteName);
        this.playerChooseIcon = new ImageIcon(this.getClass().getResource(playerChooseIconName));
    }

    public Color getColor() {
        return color;
    }

    public BoardSprite getSprite() {
        return sprite;
    }

    public ImageIcon getImage() {
        return this.image;
    }

    public ImageIcon getPlayerChooseIcon() {
        return playerChooseIcon;
    }

    public final BoardSprite toSprite(){
        return sprite;
    }
}
