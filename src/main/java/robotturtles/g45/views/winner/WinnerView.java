package robotturtles.g45.views.winner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import robotturtles.g45.BackgroundImagePanel;
import robotturtles.g45.Player;

public class WinnerView {
    private final BackgroundImagePanel rootPanel = createRootPanel();
    private final JPanel winnerPanel = createWinnerPanel();
    private final JPanel messagePanel = createMessagePanel();
    private JLabel messageLabel = createMessageLabel();
    private final JPanel cardsPanel = createCardsPanel();
    private final JPanel buttonsPanel = createButtonsPanel();
    private JButton playButton = createPlayButton();
    private ReplayDelegate replayDelegate;

    private void drawWinnerView() {
        rootPanel.add(winnerPanel);
        winnerPanel.add(messagePanel, BorderLayout.NORTH);
        messagePanel.add(messageLabel);
        winnerPanel.add(cardsPanel, BorderLayout.CENTER);
        winnerPanel.add(buttonsPanel, BorderLayout.SOUTH);
        buttonsPanel.add(playButton);
    }

    public WinnerView(List<Player> winners, ReplayDelegate replayDelegate) {
        drawWinnerView();
        fillWinners(createCardButtons(winners));
        this.replayDelegate = replayDelegate;
    }

    private BackgroundImagePanel createRootPanel() {
        BackgroundImagePanel panel = new BackgroundImagePanel(new GridBagLayout());
        panel.setOpaque(true);
        return panel;
    }

    public BackgroundImagePanel getRootPanel() {
        return rootPanel;
    }

    private JPanel createWinnerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(2, 2));
        panel.setBackground(Color.WHITE);
        return panel;
    }

    private JPanel createMessagePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.RED);
        panel.setOpaque(false);
        return panel;
    }

    private JLabel createMessageLabel() {
        JLabel label = new JLabel("Voici le classement final !!!", JLabel.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(20, 5, 5, 5));
        label.setFont(new Font("Serif", Font.PLAIN, 40));
        label.setBackground(Color.WHITE);
        label.setForeground(Color.BLACK);
        return label;
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
        button.addActionListener(new OnReplayActionListener());
        return button;
    }

    private void fillWinners(List<JButton> cards) {
        cardsPanel.removeAll();
        cards.forEach(cardsPanel::add);
    }

    private List<JButton> createCardButtons(List<Player> winners) {
        List<JButton> buttons = winners.stream().map(player -> {
            JButton card = new JButton(player.turtle.name());
            card.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            card.setIcon(player.turtle.getPlayerChooseIcon());
            card.setDisabledIcon(player.turtle.getPlayerChooseIcon());
            card.setVerticalTextPosition(SwingConstants.BOTTOM);
            card.setHorizontalTextPosition(SwingConstants.CENTER);
            card.setFont(new Font("Serif", Font.BOLD, 25));
            card.setForeground(player.turtle.getColor());
            card.setEnabled(false);
            card.setLayout(new GridBagLayout());
            card.setPreferredSize(new Dimension(card.getIcon().getIconWidth(), card.getIcon().getIconHeight() + 70));
            return card;
        }).collect(Collectors.toList());

        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setText(buttons.get(i).getText() + String.format(" -> %d", i + 1));
        }

        return buttons;
    }

    private class OnReplayActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            replayDelegate.onReplay();
        }
    }
}
