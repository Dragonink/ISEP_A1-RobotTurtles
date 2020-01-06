package robotturtles.g45.player;

import robotturtles.g45.PlayerSprite;

public enum PlayerWall {
    BRICK('stoneWall.jpeg'),
    ICE('iceWall.jpeg');

    private final PlayerSprite sprite;
    public final PlayerSprite toSprite(){
        return sprite;
    }

    private PlayerWall(Image spriteName ){
        sprite = new PlayerSprite(spriteName);
    }
}
