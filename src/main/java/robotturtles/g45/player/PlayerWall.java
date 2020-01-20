package robotturtles.g45.player;

import robotturtles.g45.PlayerSprite;

public enum PlayerWall {
    BRICK("stoneWall2.jpg"),
    ICE("iceWall2.jpg");

    private final PlayerSprite sprite;
    public final PlayerSprite getSprite(){
        return sprite;
    }

    private PlayerWall(String spriteName ){
        sprite = new PlayerSprite(spriteName);
    }
}
