package robotturtles.g45;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BoardSprite {
    private String sprite;
    private Color color;

    public BoardSprite(String sprite) {
        this(sprite, null);
    }

    public BoardSprite(String sprite, Color color) {
        this.sprite = sprite;
        this.color = color;
    }

    public Image getSprite() {
        if (isEmpty()) {
            return new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        } else if (sprite != null && color == null) {
            try {
                return ImageIO.read(getClass().getResource(Sprite.SPRITE_PATH + sprite));
            } catch (IOException e) {
                return new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
            }
        } else {
            BufferedImage bufferedImage = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
            g.setColor(color);
            g.fillRect(0, 0, 64, 64);

            g.setComposite(AlphaComposite.Dst);
            g.drawImage(bufferedImage, 0, 0, 64, 64, 0, 0, 64, 64, null);
            return bufferedImage;
        }
    }

    public boolean isEmpty() {
        return sprite == null && color == null;
    }
}
