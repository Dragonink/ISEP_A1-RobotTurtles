package robotturtles.g45;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainPanel extends JPanel {
    public MainPanel(GridBagLayout gridBagLayout) {
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
