package robotturtles.g45.board;

import robotturtles.g45.BoardSprite;

public enum BoardWall {
    BRICK('stoneWall.jpeg'),
    ICE('iceWall.jpeg'),
    VOID(null);

    private final BoardSprite sprite;
    public final BoardSprite toSprite(){
        return sprite;
    }

    private BoardWall(Image spriteName ){
        sprite = (spriteName != null) ? new BoardSprite(spriteName): null ;
    }
}
