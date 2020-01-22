package robotturtles.g45;

import robotturtles.g45.board.BoardWall;
import robotturtles.g45.board.Turtle;
import robotturtles.g45.player.Card;

import java.util.*;

/**
 * Class for players.
 */
public final class Player {
    /**
     * Turtle of the player.
     */
    public final Turtle turtle;

    /**
     * Cards in the player's hand.
     */
    private final Card[] hand = new Card[5];

    /**
     * Player's left ice walls.
     */
    private int iceWalls = 2;

    /**
     * Player's left brick walls.
     */
    private int brickWalls = 3;

    /**
     * Player's program.
     */
    private Queue<Card> program = new ArrayDeque<Card>();

    /**
     * Player's available cards.
     */
    private Stack<Card> availableCards = new Stack<Card>();

    /**
     * Player's ditched cards.
     */
    private List<Card> ditchedCards = new ArrayList<Card>();


    /**
     * Constructs a new {@code Player}.
     *
     * @param turtle Turtle of the player.
     */
    Player(Turtle turtle) {
        this.turtle = turtle;

        int[] cardsCount = {18, 8, 8, 3};
        final Random rnd = new Random();
        int nextCard = rnd.nextInt(4);
        while (availableCards.size() < 37) {
            if (cardsCount[nextCard] > 0) {
                if (nextCard == 0) availableCards.push(Card.FRONT_FORWARD);
                else if (nextCard == 1) availableCards.push(Card.FRONT_ROTATE_LEFT);
                else if (nextCard == 2) availableCards.push(Card.FRONT_ROTATE_RIGHT);
                else availableCards.push(Card.FRONT_LASER);
                cardsCount[nextCard]--;
            }
            nextCard = rnd.nextInt(4);
        }
        for (int card = 0; card < 5; card++) {
            hand[card] = availableCards.pop();
        }
    }

    public Card[] getHand() {
        return hand;
    }

    public int getIceWalls() {
        return iceWalls;
    }

    public int getBrickWalls() {
        return brickWalls;
    }

    public Queue<Card> getProgram() {
        return program;
    }

    public Stack<Card> getAvailableCards() {
        return availableCards;
    }

    public List<Card> getDitchedCards() {
        return ditchedCards;
    }

    /**
     * Adds a card to the player's program.
     *
     * @param cardIdx Index of the card to add.
     */
    public final void addToProgram(final int cardIdx) {
        program.add(hand[cardIdx]);
        hand[cardIdx] = null;
    }

    /**
     * Executes the player's program.
     */
    public final void executeProgram(Card card) {
            if (card.equals(Card.FRONT_ROTATE_LEFT)) turtle.setRotation(turtle.getRotation() - 1);
            else if (card.equals(Card.FRONT_ROTATE_RIGHT)) turtle.setRotation(turtle.getRotation() + 1);
            else if (card.equals(Card.FRONT_FORWARD)) {
                Integer[] pos = turtle.getPos();
                switch (turtle.getRotation()) {
                    case 0:
                        pos[0]--;
                        break;
                    case 1:
                        pos[1]++;
                        break;
                    case 2:
                        pos[0]++;
                        break;
                    case 3:
                        pos[1]--;
                        break;
                }
                if (pos[0] >= 0 && pos[0] < 8 && pos[1] >= 0 && pos[1] < 8) {
                    if (Game.board.getSquare(pos[0], pos[1]) == null || Game.board.getSquare(pos[0], pos[1]).isEmpty()) {// Move if square is empty
                        Game.board.setSquare(pos[0], pos[1], turtle.getSprite());
                        Game.board.resetSquare(pos[0], pos[1]);
                        turtle.setPos(pos[0], pos[1]);
                    } else if (Game.board.getSquare(pos[0], pos[1]).equals(BoardWall.BRICK.getSprite()) || Game.board.getSquare(pos[0], pos[1]).equals(BoardWall.ICE.getSprite())) turtle.setRotation(turtle.getRotation() + 2);// Rotate if square is wall
                    else {
                        for (Integer[] turtle : Game.board.getTurtles()) if (pos[0].equals(turtle[0]) && pos[1].equals(turtle[1])) {// Reset positions of both turtles
                            for (Player player: Game.getPlayers()) if (player.turtle.getPos()[0].equals(pos[0]) && player.turtle.getPos()[1].equals(pos[1])) {
                                player.turtle.reset();
                                break;
                            }
                            this.turtle.reset();
                            break;
                        }
                        for (Integer[] jewel : Game.board.getJewels()) if (pos[0].equals(jewel[0]) && pos[1].equals(jewel[1])) {// Win if square is jewel
                            Game.playerWins(this);
                            Game.board.resetSquare(turtle.getPos()[0], turtle.getPos()[1]);
                            return;
                        }
                    }
                } else turtle.setRotation(turtle.getRotation() + 2);// Rotate if board border
            } else if (card.equals(Card.FRONT_LASER)) {
                Integer[] pos = turtle.getPos();
                int direction = 1;
                do {
                    switch (turtle.getRotation()) {
                        case 0:
                            pos[0] -= direction;
                            break;
                        case 1:
                            pos[1] += direction;
                            break;
                        case 2:
                            pos[0] += direction;
                            break;
                        case 3:
                            pos[1] -= direction;
                            break;
                    }
                    if (Game.board.getSquare(pos[0], pos[1]) == null || Game.board.getSquare(pos[0], pos[1]).isEmpty())
                        continue;
                    else if (Game.board.getSquare(pos[0], pos[1]).equals(BoardWall.ICE.getSprite())) Game.board.resetSquare(pos[0], pos[1]);
                    else {
                        for (Player player : Game.getPlayers()) if (player.turtle.getPos()[0].equals(pos[0]) && player.turtle.getPos()[1].equals(pos[1])) {
                            if (Game.getPlayers().length > 2) player.turtle.reset();
                            else player.turtle.setRotation(player.turtle.getRotation() + 2);
                            break;
                        }
                        for (Integer[] jewel : Game.board.getJewels()) if (pos[0].equals(jewel[0]) && pos[1].equals(jewel[1])) {
                            direction = -direction;
                            break;
                        }
                    }
                } while (pos[0] > 0 && pos[0] < 7 && pos[1] > 0 && pos[1] < 7 && !Game.board.getSquare(pos[0], pos[1]).equals(BoardWall.BRICK.getSprite()));
            }
        }

    public final void ditchCard(final int cardIdx) {
        ditchedCards.add(hand[cardIdx]);
        hand[cardIdx] = null;
    }

    public final void pickCards() {
        for (int c = 0; c < hand.length; c++) {
            if (hand[c] == null) {
                if (availableCards.empty()) {
                    availableCards.addAll(ditchedCards);
                    ditchedCards.clear();
                }
                hand[c] = availableCards.pop();
            }
        }
    }
}
