package robotturtles.g45;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BoardSprite implements Drawable {
    private String sprite;
    private int posX;
    private int posY;
    private int rotation;

    public BoardSprite(String sprite) {
        this.sprite = sprite;
    }

    public Image getSprite() throws IOException {
        return ImageIO.read(getClass().getResource("/images/" + sprite));
    }

    public void draw() {
        //TODO
    }
}
