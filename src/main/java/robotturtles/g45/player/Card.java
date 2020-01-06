package robotturtles.g45.player;

import robotturtles.g45.PlayerSprite;

public enum Card {
    FRONT_FORWARD('moveCard.jpg'),
    FRONT_ROTATE_LEFT('turnLeftCard.jpg'),
    FRONT_ROTATE_RIGHT('turnRightCard.jpg'),
    FRONT_LASER('laserCard.png'),
    BACK_BEEP('beepCard.jpg'),
    BACK_PANGLE('pangleCard.jpg'),
    BACK_PI('piCard.jpg'),
    BACK_DOT('dotCard.jpg');

    private final PlayerSprite sprite;
    public final PlayerSprite toSprite(){
        return sprite;
    }

    private Card(Image spriteName ){
        sprite = new PlayerSprite(spriteName);
    }
}
