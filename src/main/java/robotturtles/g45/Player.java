package robotturtles.g45;

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
    public final void executeProgram() {
        //TODO
    }

    public final void ditchCards() {
        //TODO
    }
}
