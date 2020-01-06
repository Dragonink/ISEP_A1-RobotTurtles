package robotturtles.g45.board;

import robotturtles.g45.BoardSprite;


public enum Turtle {
    BEEP('beepTile.jpg','blue'),
    PANGLE('pangleTile.jpg','yellow'),
    PI('piTile.jpg','red'),
    DOT('dotTile.jpg','purple');

    public final String color;

    private final BoardSprite sprite;
    public final BoardSprite toSprite(){
        return sprite;
    }

    private Turtle(Image spriteName, String color ){
        sprite = new BoardSprite(spriteName);
        this.color = color;
    }
}
