package robotturtles.g45;

import robotturtles.g45.board.Turtle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class ChoosePlayerView {

    private static final List<Turtle> turtles = Arrays.asList(Turtle.BEEP, Turtle.PANGLE, Turtle.DOT, Turtle.PI);
    private List<Turtle> chosenTurtles = new ArrayList<>();
    private final BackgroundImagePanel backgroundImagePanel = createBackgroundImagePanel();
    private final JPanel choosePlayerPanel = createChoosePlayerPanel();
    private final JPanel numPlayerPanel = createNumPlayerPanel();
    private JLabel numPlayerLabel = createNumPlayerLabel();
    private final JPanel cardsPanel = createCardsPanel();
    private final JPanel buttonsPanel = createButtonsPanel();
    private final JPanel backButtonPanel = createBackButtonPanel();
    private final JPanel playButtonPanel = createPlayButtonPanel();
    private JButton backButton = createBackButton();
    private JButton playButton = createPlayButton();

    ChoosePlayerView() {
        drawChoosePlayerView();
        fillCardsPanel(createCardButtons());
    }

    BackgroundImagePanel getBackgroundImagePanel() {
        return backgroundImagePanel;
    }

    private boolean isReadyToPlay() {
        return chosenTurtles != null && chosenTurtles.size() > 1;
    }

    private boolean isCancelEnabled() {
        return chosenTurtles != null && chosenTurtles.size() > 0;
    }

    private void drawChoosePlayerView() {
        backgroundImagePanel.add(choosePlayerPanel);
        choosePlayerPanel.add(cardsPanel, BorderLayout.CENTER);
        choosePlayerPanel.add(buttonsPanel, BorderLayout.SOUTH);
        choosePlayerPanel.add(numPlayerPanel, BorderLayout.NORTH);
        buttonsPanel.add(backButtonPanel, BorderLayout.LINE_START);
        buttonsPanel.add(playButtonPanel, BorderLayout.LINE_END);
        numPlayerPanel.add(numPlayerLabel);
        backButtonPanel.add(backButton);
        playButtonPanel.add(playButton);
    }

    private BackgroundImagePanel createBackgroundImagePanel() {
        BackgroundImagePanel panel = new BackgroundImagePanel(new GridBagLayout());
        panel.setOpaque(true);
        return panel;
    }

    private JPanel createChoosePlayerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(2, 2));
        panel.setBackground(Color.WHITE);
        return panel;
    }

    private JPanel createNumPlayerPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        return panel;
    }

    private JLabel createNumPlayerLabel() {
        JLabel label = new JLabel("Joueur 1", JLabel.CENTER);
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

    private JPanel createBackButtonPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        return panel;
    }

    private JPanel createPlayButtonPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        return panel;
    }

    private JButton createBackButton() {
        JButton button = new JButton();
        button.setIcon(new ImageIcon(this.getClass().getResource("/images/back.jpg")));
        button.setPreferredSize(new Dimension(50, 50));
        button.setEnabled(isCancelEnabled());
        button.addActionListener(new OnCancelActionListener());
        return button;
    }

    private JButton createPlayButton() {
        JButton button = new JButton();
        button.setEnabled(isReadyToPlay());
        button.setIcon(new ImageIcon(this.getClass().getResource("/images/play.jpg")));
        button.setPreferredSize(new Dimension(50, 50));
        button.addActionListener(new OnPlayActionListener());
        return button;
    }

    private List<JButton> createCardButtons() {
        return turtles.stream().map(turtle -> {
            JButton card = new JButton(String.valueOf(turtle));
            card.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            card.setIcon(turtle.getPlayerChooseIcon());
            card.setVerticalTextPosition(SwingConstants.BOTTOM);
            card.setHorizontalTextPosition(SwingConstants.CENTER);
            card.setFont(new Font("Serif", Font.BOLD, 30));
            card.setForeground(turtle.getColor());
            card.addActionListener(new OnCardActionListener());
            return card;
        }).collect(Collectors.toList());
    }

    private void fillCardsPanel(List<JButton> cards) {
        cardsPanel.removeAll();
        cards.forEach(cardsPanel::add);
    }

    private class OnCardActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            final JButton buttonClicked = ((JButton) actionEvent.getSource());
            buttonClicked.setEnabled(false);
            chosenTurtles.add(Turtle.valueOf(buttonClicked.getText()));
            playButton.setEnabled(isReadyToPlay());
            backButton.setEnabled(isCancelEnabled());
            if (chosenTurtles.size() < 4) {
                numPlayerLabel.setText("Joueur " + (chosenTurtles.size() + 1));
            } else {
                numPlayerLabel.setText("C'est parti!!!");
                numPlayerLabel.setForeground(Color.RED);
            }
        }
    }

    private class OnCancelActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            chosenTurtles.remove(chosenTurtles.size() - 1);
            backButton.setEnabled(isCancelEnabled());
            playButton.setEnabled(isReadyToPlay());
            numPlayerLabel.setText("Joueur " + (chosenTurtles.size() + 1));
            numPlayerLabel.setForeground(Color.BLACK);
            List<JButton> newCards = createCardButtons();
            newCards.forEach(button -> button.setEnabled(chosenTurtles.stream().noneMatch(turtle -> button.getText().equals(turtle.name()))));
            fillCardsPanel(newCards);
        }
    }

    private static class OnPlayActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

        }
    }

}
