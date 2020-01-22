package robotturtles.g45.views.winner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import robotturtles.g45.BackgroundImagePanel;

public class WinnerView {
    private final BackgroundImagePanel rootPanel = createRootPanel();
    private final JPanel winnerPanel = createWinnerPanel();
    private final JPanel messagePanel = createMessagePanel();
    private JLabel messageLabel = createMessageLabel();
    private final JPanel cardsPanel = createCardsPanel();
    //private final List<JLabel> cardsLabel = createCardsLabel();
    private final JPanel buttonsPanel = createButtonsPanel();
    private JButton playButton = createPlayButton();

    private void drawWinnerView() {
        rootPanel.add(winnerPanel);
        winnerPanel.add(messagePanel, BorderLayout.CENTER);
        messagePanel.add(messageLabel);
        winnerPanel.add(cardsPanel, BorderLayout.CENTER);
        //cardsPanel.add(cardsLabel);
        winnerPanel.add(buttonsPanel, BorderLayout.SOUTH);
        buttonsPanel.add(playButton);
    }

    public WinnerView(){
        drawWinnerView();
    }

    private BackgroundImagePanel createRootPanel() {
        BackgroundImagePanel panel = new BackgroundImagePanel(new GridBagLayout());
        panel.setOpaque(true);
        return panel;
    }

    public BackgroundImagePanel getRootPanel() { return rootPanel;}

    private JPanel createWinnerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(2, 2));
        panel.setBackground(Color.WHITE);
        return panel;
    }

    private JPanel createMessagePanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        return panel;
    }

    private JLabel createMessageLabel() {
        JLabel label = new JLabel(message(), JLabel.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(20, 5, 5, 5));
        label.setFont(new Font("Serif", Font.PLAIN, 40));
        label.setBackground(Color.WHITE);
        label.setForeground(Color.BLACK);
        return label;
    }

    private String message() {
        return String.format("Voici le classement final !!!");
    }

    private JPanel createCardsPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        return panel;
    }

    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(2, 2));
        panel.setOpaque(false);
        return panel;
    }

    private JButton createPlayButton() {
        JButton button = new JButton();
        button.setEnabled(true);
        button.setIcon(new ImageIcon(this.getClass().getResource("/images/play.jpg")));
        button.setPreferredSize(new Dimension(50, 50));
        //button.addActionListener(new ChoosePlayerView.OnPlayActionListener());
        return button;
    }

}
