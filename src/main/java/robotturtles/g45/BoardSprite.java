package robotturtles.g45;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BoardSprite {
    private String sprite;

    public BoardSprite(String sprite) {
        this.sprite = sprite;
    }

    public Image getSprite() {
        try{
            return sprite.isEmpty() ? new BufferedImage(64,64, BufferedImage.TYPE_INT_ARGB) : ImageIO.read(getClass().getResource(Sprite.SPRITE_PATH + sprite));
        } catch (IOException e) {
            return new BufferedImage(64,64, BufferedImage.TYPE_INT_ARGB);
        }
    }

    public boolean isEmpty() {
        return sprite.equals(null) || sprite.equals("");
    }
}
