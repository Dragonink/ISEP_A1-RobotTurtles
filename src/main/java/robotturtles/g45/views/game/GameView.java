package robotturtles.g45.views.game;

import javax.swing.*;
import java.awt.*;

public class GameView {

    private JPanel rootPanel = createRootPanel();

    public GameView() {
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    private JPanel createRootPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        return panel;
    }
}
