package robotturtles.g45;

import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BackgroundImagePanel extends JPanel {
    private static final long serialVersionUID = -6268416178703905130L;

	public BackgroundImagePanel(GridBagLayout gridBagLayout) {
        super(gridBagLayout);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            Image img = ImageIO.read(this.getClass().getResource("/images/accueil.jpg"));
            g.drawImage(img, 0 ,0 , this.getWidth(), this.getHeight(),this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
