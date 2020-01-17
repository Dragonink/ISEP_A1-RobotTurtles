package robotturtles.g45.board;

import robotturtles.g45.BoardSprite;

public enum Jewel {
    BLUE("jewelTileBlue.jpg"),
    GREEN("jewelTileGreen.jpg"),
    MAGENTA("jewelTileMagenta.jpg");

    private final BoardSprite sprite;
    public final BoardSprite toSprite(){
        return sprite;
    }

    private Jewel(String spriteName ){
        sprite = new BoardSprite(spriteName);
    }

}
