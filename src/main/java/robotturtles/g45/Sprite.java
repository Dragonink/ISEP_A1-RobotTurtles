package robotturtles.g45;

import java.awt.Image;
import java.io.IOException;

public interface Sprite extends Drawable {
    public static final String SPRITE_PATH = "/images/";

    
    public Image getSprite() throws IOException;

    public void draw();
}
