package robotturtles.g45;

import robotturtles.g45.board.BoardWall;
import robotturtles.g45.board.Laser;
import robotturtles.g45.board.Turtle;
import robotturtles.g45.board.Void;
import robotturtles.g45.player.Card;
import robotturtles.g45.views.game.GameDelegate;

import javax.swing.*;
import java.util.*;
import java.util.concurrent.ExecutionException;

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

    public final void executeProgram(GameDelegate gameDelegate) {
        final SwingWorker<Boolean, Sprite> swingWorker = new SwingWorker<Boolean, Sprite>() {

            @Override
            protected Boolean doInBackground() throws Exception {
                for (Card card = program.poll(); card != null; card = program.poll()) {
                    if (card.equals(Card.FRONT_ROTATE_LEFT)) {
                        turtle.setRotation(turtle.getRotation() - 1);
                        this.publish(turtle);
                        Thread.sleep(2000);
                    } else if (card.equals(Card.FRONT_ROTATE_RIGHT)) {
                        turtle.setRotation(turtle.getRotation() + 1);
                        this.publish(turtle);
                        Thread.sleep(2000);
                    } else if (card.equals(Card.FRONT_FORWARD)) {
                        Integer[] pos = turtle.getPos();
                        Game.board.getBoard()[pos[0]][pos[1]] = new BoardSprite(null);
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
                                this.publish(turtle);
                                Thread.sleep(2000);
                            } else if (Game.board.getSquare(pos[0], pos[1]).equals(BoardWall.BRICK.getSprite()) || Game.board.getSquare(pos[0], pos[1]).equals(BoardWall.ICE.getSprite())) {
                                turtle.setRotation(turtle.getRotation() + 2);// Rotate if square is wall
                                this.publish(turtle);
                                Thread.sleep(2000);
                            } else {
                                for (Integer[] turtleTest : Game.board.getTurtles())
                                    if (pos[0].equals(turtleTest[0]) && pos[1].equals(turtleTest[1])) {// Reset positions of both turtles
                                        for (Player player : Game.getPlayers())
                                            if (player.turtle.getPos()[0].equals(pos[0]) && player.turtle.getPos()[1].equals(pos[1])) {
                                                player.turtle.reset();
                                                this.publish(turtle);
                                                Thread.sleep(2000);
                                                break;
                                            }
                                        turtle.reset();
                                        this.publish(turtle);
                                        Thread.sleep(2000);
                                        break;
                                    }
                                for (Integer[] jewel : Game.board.getJewels())
                                    if (pos[0].equals(jewel[0]) && pos[1].equals(jewel[1])) {// Win if square is jewel
                                        Game.board.resetSquare(turtle.getPos()[0], turtle.getPos()[1]);
                                        this.publish(turtle);
                                        Thread.sleep(2000);
                                        return true;
                                    }
                            }
                        } else {
                            turtle.setRotation(turtle.getRotation() + 2);// Rotate if board border
                            this.publish(turtle);
                            Thread.sleep(2000);
                        }
                    } else if (card.equals(Card.FRONT_LASER)) {
                        Laser laser = Laser.LASER;
                        Void empty = Void.VOID;
                        Integer[] pos = turtle.getPos();
                        int direction = 1;
                        do {
                            Game.board.getBoard()[laser.getPos()[0]][laser.getPos()[1]] = new BoardSprite(null);
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
                            if (Game.board.getSquare(pos[0], pos[1]) == null || Game.board.getSquare(pos[0], pos[1]).isEmpty()) {
                                laser.setPos(pos[0], pos[1]);
                                this.publish(laser);
                                Thread.sleep(2000);
                            } else if (Game.board.getSquare(pos[0], pos[1]).equals(BoardWall.ICE.getSprite())) {
                                Game.board.resetSquare(pos[0], pos[1]);
                                laser.setPos(pos[0], pos[1]);
                                this.publish(laser);
                                Thread.sleep(2000);
                                empty.setPos(pos[0], pos[1]);
                                this.publish(empty);
                                Thread.sleep(2000);
                                return false;
                            } else {
                                for (Player player : Game.getPlayers())
                                    if (player.turtle.getPos()[0].equals(pos[0]) && player.turtle.getPos()[1].equals(pos[1])) {
                                        if (Game.getPlayers().length > 2) {
                                            player.turtle.reset();
                                            laser.setPos(pos[0], pos[1]);
                                            this.publish(laser);
                                            Thread.sleep(2000);
                                        } else {
                                            player.turtle.setRotation(player.turtle.getRotation() + 2);
                                            this.publish(turtle);
                                            Thread.sleep(2000);
                                        }
                                        break;
                                    }
                                for (Integer[] jewel : Game.board.getJewels())
                                    if (pos[0].equals(jewel[0]) && pos[1].equals(jewel[1])) {
                                        direction = -direction;
                                        break;
                                    }
                            }
                        } while (pos[0] >= 0 && pos[0] < 7 && pos[1] >= 0 && pos[1] < 7 && !Game.board.getSquare(pos[0], pos[1]).equals(BoardWall.BRICK.getSprite()));
                        empty.setPos(laser.getPos()[0], laser.getPos()[1]);
                        this.publish(empty);
                        Thread.sleep(2000);
                    }
                }
                return false;
            }

            @Override
            protected void process(List<Sprite> chunks) {
                super.process(chunks);
                for (Sprite sprite : chunks) {
                    gameDelegate.onActionDone(sprite);
                }
            }

            @Override
            protected void done() {
                super.done();

                try {
                    if (get()) {
                        gameDelegate.onPlayerSuccess();
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        swingWorker.execute();
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
