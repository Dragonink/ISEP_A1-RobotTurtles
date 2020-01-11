package robotturtles.g45.board;

import robotturtles.g45.BoardSprite;
import robotturtles.g45.Sprite;

import javax.swing.*;

public enum Turtle {
    BEEP("beepTile.jpg","blue", Sprite.SPRITE_PATH+"choixBeep.jpg"),
    PANGLE("pangleTile.jpg","yellow",Sprite.SPRITE_PATH+"choixPangle.jpg"),
    PI("piTile.jpg","red",Sprite.SPRITE_PATH+"choixPi.jpg"),
    DOT("dotTile.jpg","purple",Sprite.SPRITE_PATH+"choixDot.jpg");

    private final String color;
    private final ImageIcon image;
    private final BoardSprite sprite;
    private final ImageIcon playerChooseIcon;

    Turtle(String spriteName, String color, String playerChooseIconName ){
        this.color = color;
        this.sprite = new BoardSprite(spriteName);
        this.image = new ImageIcon(spriteName);
        this.playerChooseIcon = new ImageIcon(this.getClass().getResource(playerChooseIconName));
    }

    public String getColor() {
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
