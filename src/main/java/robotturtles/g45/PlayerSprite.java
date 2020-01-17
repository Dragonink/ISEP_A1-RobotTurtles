package robotturtles.g45;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PlayerSprite implements Drawable {
    private String sprite;
    private int posX;
    private int posY;

    public PlayerSprite(String sprite) {
        this.sprite = sprite;
    }

    public Image getSprite() throws IOException {
        return ImageIO.read(getClass().getResource(Sprite.SPRITE_PATH + sprite));
    }

    public void draw() {
        //TODO
    }
}
