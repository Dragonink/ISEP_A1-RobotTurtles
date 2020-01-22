package robotturtles.g45.board;

import robotturtles.g45.BoardSprite;

public enum BoardWall {
    BRICK("stoneWall.jpg"),
    ICE("iceWall.jpg");

    private final BoardSprite sprite;

    BoardWall(String spriteName) {
        sprite = (spriteName != null) ? new BoardSprite(spriteName): null ;
    }

    public BoardSprite getSprite() {
        return sprite;
    }
}
