package robotturtles.g45;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PlayerSprite {
    private String sprite;
    private int posX;
    private int posY;

    public PlayerSprite(String sprite) {
        this.sprite = sprite;
    }

    public Image getSprite() {
        try{
            return sprite.isEmpty() ? new BufferedImage(170,102, BufferedImage.TYPE_INT_ARGB) : ImageIO.read(getClass().getResource(Sprite.SPRITE_PATH + sprite));
        } catch (IOException e) {
            return new BufferedImage(170,102, BufferedImage.TYPE_INT_ARGB);
        }
    }
}
