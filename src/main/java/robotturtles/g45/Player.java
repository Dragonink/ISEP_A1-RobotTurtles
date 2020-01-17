package robotturtles.g45;
import robotturtles.g45.board.BoardWall;
import robotturtles.g45.board.Turtle;
import robotturtles.g45.player.Card;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

/** Class for players. */
public final class Player {
    /** Turtle of the player. */
    public final Turtle turtle;

    /** Cards in the player's hand. */
    private final Card[] hand = new Card[5];

    /** Player's left ice walls. */
    private int iceWalls = 2;

    /** Player's left brick walls. */
    private int brickWalls = 3;

    /** Player's program. */
    private Queue<Card> program = new ArrayDeque<Card>();

    /** Player's available cards. */
    private Stack<Card> availableCards = new Stack<Card>();

    /** Player's ditched cards. */
    private List<Card> ditchedCards = new ArrayList<Card>();


    /** Constructs a new {@code Player}.
     * 
     * @param turtle Turtle of the player.
     */
    Player(Turtle turtle) {
        this.turtle = turtle;

        int[] cardsCount = {18, 8, 8, 13};
        final Random rnd = new Random();
        int nextCard = rnd.nextInt(4);
        while (availableCards.size() < 37 && cardsCount[nextCard] > 0) {
            if (nextCard == 0) availableCards.push(Card.FRONT_FORWARD);
            else if (nextCard == 1) availableCards.push(Card.FRONT_ROTATE_LEFT);
            else if (nextCard == 2) availableCards.push(Card.FRONT_ROTATE_RIGHT);
            else availableCards.push(Card.FRONT_LASER);
            cardsCount[nextCard]--;
        }
        for (int card = 0; card < 5; card++) {
            hand[card] = availableCards.pop();
        }
    }


    /** Adds a card to the player's program.
     * 
     * @param cardIdx Index of the card to add.
     */
    public final void addToProgram(final int cardIdx) {
        program.add(hand[cardIdx]);
        hand[cardIdx] = null;
    }

    /** Executes the player's program. */
    public final void executeProgram() {
        //TODO
    }

    /** Builds a wall on the game board.
     * 
     * @param wallIdx Index of the wall to build.
     * @param line Line index.
     * @param column Column index.
     * @return {@code true} if the wall can be built; {@code false} otherwise.
     * @throws IllegalStateException if {@code line} or {@code column} are invalid.
     */
    public final boolean buildWall(final int wallIdx, final int line, final int column) throws IllegalStateException {
        if (Game.board.getSquare(line, column) != null) throw new IllegalStateException("Location occupied.");
        else if (wallIdx >= 2) {
            Game.board.setSquare(line, column, BoardWall.BRICK.getSprite());
            boolean blocked = false;
            for (Integer[] turtle : Game.board.getTurtles()) for (Integer[] jewel : Game.board.getJewels()) {
                if (blocked) break;
                else blocked = !Game.board.existsPath(turtle, jewel);
            }
            if (blocked) Game.board.resetSquare(line, column);
            return !blocked;
        } else return Game.board.setSquare(line, column, BoardWall.ICE.getSprite());
    }
}
