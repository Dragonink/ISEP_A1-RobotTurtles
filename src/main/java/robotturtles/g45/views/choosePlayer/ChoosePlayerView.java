package robotturtles.g45.views.choosePlayer;

import robotturtles.g45.BackgroundImagePanel;
import robotturtles.g45.board.Turtle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ChoosePlayerView {

    private static final List<Turtle> turtles = Arrays.asList(Turtle.BEEP, Turtle.PANGLE, Turtle.DOT, Turtle.PI);
    private List<Turtle> chosenTurtles = new ArrayList<>();
    private final BackgroundImagePanel rootPanel = createRootPanel();
    private final JPanel choosePlayerPanel = createChoosePlayerPanel();
    private final JPanel numPlayerPanel = createNumPlayerPanel();
    private JLabel numPlayerLabel = createNumPlayerLabel();
    private final JPanel cardsPanel = createCardsPanel();
    private final JPanel buttonsPanel = createButtonsPanel();
    private final JPanel backButtonPanel = createBackButtonPanel();
    private final JPanel playButtonPanel = createPlayButtonPanel();
    private JButton backButton = createBackButton();
    private JButton playButton = createPlayButton();
    private ChoosePlayerDelegate delegate;

    public ChoosePlayerView(final ChoosePlayerDelegate delegate) {
        this.delegate = delegate;
        drawChoosePlayerView();
        fillCardsPanel(createCardButtons());
    }

    public BackgroundImagePanel getRootPanel() {
        return rootPanel;
    }

    private String numPlayer() {
        return String.format("Joueur %s : choisis ton personnage", (chosenTurtles.size() + 1));
    }

    private String numPlayerPlaceholder() {
        return String.format("Joueur %s", (chosenTurtles.size() + 1));
    }

    private boolean isReadyToPlay() {
        return chosenTurtles != null && chosenTurtles.size() > 1;
    }

    private boolean isCancelEnabled() {
        return chosenTurtles != null && chosenTurtles.size() > 0;
    }

    private void drawChoosePlayerView() {
        rootPanel.add(choosePlayerPanel);
        choosePlayerPanel.add(cardsPanel, BorderLayout.CENTER);
        choosePlayerPanel.add(buttonsPanel, BorderLayout.SOUTH);
        choosePlayerPanel.add(numPlayerPanel, BorderLayout.NORTH);
        buttonsPanel.add(backButtonPanel, BorderLayout.LINE_START);
        buttonsPanel.add(playButtonPanel, BorderLayout.LINE_END);
        numPlayerPanel.add(numPlayerLabel);
        backButtonPanel.add(backButton);
        playButtonPanel.add(playButton);
    }

    private BackgroundImagePanel createRootPanel() {
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
        JLabel label = new JLabel(numPlayer(), JLabel.CENTER);
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
            card.setLayout(new GridBagLayout());
            card.setPreferredSize(new Dimension(card.getIcon().getIconWidth(), card.getIcon().getIconHeight() + 70));
            return card;
        }).collect(Collectors.toList());
    }

    private void fillCardsPanel(List<JButton> cards) {
        cardsPanel.removeAll();
        cards.forEach(cardsPanel::add);
    }

    private void addPlayerPlaceholderOn(JButton button, String text) {
        button.removeAll();
        JLabel label = new JLabel(text);
        label.setFont(new Font("Serif", Font.BOLD, 20));
        label.setForeground(Color.WHITE);
        button.add(label);
    }

    private class OnCardActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            final JButton buttonClicked = ((JButton) actionEvent.getSource());
            buttonClicked.setEnabled(false);
            addPlayerPlaceholderOn(buttonClicked, numPlayerPlaceholder());
            chosenTurtles.add(Turtle.valueOf(buttonClicked.getText()));
            playButton.setEnabled(isReadyToPlay());
            backButton.setEnabled(isCancelEnabled());
            if (chosenTurtles.size() < 4) {
                numPlayerLabel.setText(numPlayer());
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
            numPlayerLabel.setText(numPlayer());
            numPlayerLabel.setForeground(Color.BLACK);
            List<JButton> newCards = createCardButtons();
            newCards.forEach(button -> {
                button.setEnabled(chosenTurtles.stream().noneMatch(turtle -> button.getText().equals(turtle.name())));
                if (chosenTurtles.stream().anyMatch(turtle -> button.getText().equals(turtle.name()))) {
                    addPlayerPlaceholderOn(button, String.format("Joueur %s", (chosenTurtles.indexOf(Turtle.valueOf(button.getText())) + 1)));
                }
            });
            fillCardsPanel(newCards);
        }
    }

    private class OnPlayActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            delegate.onPlayClicked(chosenTurtles);
        }
    }
}
