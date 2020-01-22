package robotturtles.g45.board;

import robotturtles.g45.BoardSprite;

public enum Jewel {
    BLUE("jewelTileBlue.jpg"),
    GREEN("jewelTileGreen.jpg"),
    MAGENTA("jewelTilePurple.jpg");

    private final BoardSprite sprite;
    public final BoardSprite getSprite(){
        return sprite;
    }

    private Jewel(String spriteName){
        sprite = new BoardSprite(spriteName);
    }

}
