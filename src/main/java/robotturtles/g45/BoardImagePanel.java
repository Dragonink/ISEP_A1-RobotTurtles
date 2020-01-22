package robotturtles.g45;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BoardImagePanel extends JPanel {
    private static final long serialVersionUID = -5423539471360799507L;

	public BoardImagePanel(GridLayout gridLayout) {
        super(gridLayout);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            Image img = ImageIO.read(getClass().getResource(Sprite.SPRITE_PATH + "board.jpg"));
            g.drawImage(img, 0 ,0 , this.getWidth(), this.getHeight(),this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
