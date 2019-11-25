package robotturtles.g45.tiles;
import javax.swing.*;
import robotturtles.g45.Board;

/** Enum of the turtles a player can control.
 * 
 * <b>Main commands</b>:<ul>
 * <li>Blue cards: {@link #moveForward() Turtle#moveForward()}</li>
 * <li>Yellow cards: {@link #rotate(int) Turtle#rotate(int)} with <code>int < 0</code></li>
 * <li>Purple cards: {@link #rotate(int) Turtle#rotate(int)} with <code>int > 0</code></li>
 * <li>Laser cards: {@link #fire() Turtle#fire()}</li>
 * <li>Wall cards: {@link #build() Turtle#build()}</li>
 * </ul>
 * 
 * @author Tanguy Berthoud
 */
public enum Turtle {
    /** Blue turtle. */
    BEEP,
    /** Red turtle. */
    PI,
    /** Green turtle. */
    PANGLE,
    /** Pink turtle. */
    DOT;


    /** Board the turtle is on. */
    private Board board = null;
    /** Sets the board the turtle is on.
     * 
     * @param board Board of the game.
     * @throws IllegalStateException if invoked after the beginning of the game.
     */
    public final void setBoard(Board board) throws IllegalStateException {
        if (this.board == null) this.board = board;
        else throw new IllegalStateException("Cannot be invoked after the beginning of the game.");
    }

    /** Direction of the turtle.
     * 
     * <ol start="0"><li>North</li><li>East</li><li>South</li><li>West</li></ol>
     */
    private int direction = 2;
    /** Gets the direction of the turtle.
     * 
     * @return {@link #direction Turtle#direction}
     */
    public final int getDirection() {
        return direction;
    }
    /** Resets the direction of the turtle.
     * 
     * @return <code>this</code> for chaining.
     */
    public final Turtle resetDirection() {
        direction = 2;
        return this;
    }
    /** Rotates the turtle by 90° in <code>direction</code>.
     * If <code>direction < 0</code>, the turtle will rotate counter clockwise.
     * If <code>direction > 0</code>, the turtle will rotate clockwise.
     * 
     * @param direction Direction of the rotation.
     */
    public final void rotate(int direction) {
        this.direction = (this.direction + direction / Math.abs(direction) + 4) % 4;
    }
    /** Rotates the turtle by 180°. */
    public final void turnBack() {
        this.direction = (this.direction + 2) % 4;
    }

    /** Position of the turtle at the beginning of the game. */
    private final int[] startPosition = { -1, -1 };
    /** Position of the turtle. */
    private int[] position = { -1, -1 };
    /** Gets the position of the turtle.
     * 
     * @return {@link #position Turtle#position}
     */
    public final int[] getPosition() {
        return position;
    }
    /** Initializes the position of the turtle.
     * 
     * Sets {@link #position Turtle#position} and {@link #startPosition Turtle#startPosition}.
     * 
     * @param i Line index of the square.
     * @param j Column index of the square.
     * @throws IllegalStateException if invoked after the beginning of the game.
     * @throws ArrayIndexOutOfBoundsException if <code>i</code> or <code>j</code> are not in <code>[0,7]</code>.
     */
    public final void initPosition(int i, int j) throws IllegalStateException, ArrayIndexOutOfBoundsException {
        if (startPosition[0] != -1 && startPosition[1] != -1) throw new IllegalStateException("Cannot be invoked after the beginning of the game.");
        else if (i < 0 || i > 7 || j < 0 || j > 7) throw new ArrayIndexOutOfBoundsException("Invalid position.");
        startPosition[0] = i;
        startPosition[1] = j;
        position[0] = i;
        position[1] = j;
    }
    /** Resets the position of the turtle.
     * 
     * @return <code>this</code> for chaining.
     * @see Board#moveTurtle(Turtle, int, int) Board#moveTurtle(Turtle, int, int)
     */
    public final Turtle resetPosition() {
        try {
            board.moveTurtle(this, startPosition[0], startPosition[1]);
        } catch (IllegalStateException exception) {} finally {
            position[0] = startPosition[0];
            position[1] = startPosition[1];
        }
        return this;
    }
    /** Tries to make the turtle go forward.
     * 
     * @see Board#moveTurtle(Turtle, int, int) Board#moveTurtle(Turtle, int, int)
     * @see #turnBack() Turtle#turnBack()
     */
    public final void moveForward() {
        final Object beforeSquare = getNeighbors()[direction];
        if (beforeSquare == null || beforeSquare instanceof Turtle) {
            try {
                switch (direction) {
                    case 0:
                        board.moveTurtle(this, position[0] - 1, position[1]);
                        position[0]--;
                        break;
                    case 1:
                        board.moveTurtle(this, position[0], position[1] + 1);
                        position[1]++;
                        break;
                    case 2:
                        board.moveTurtle(this, position[0] + 1, position[1]);
                        position[0]++;
                        break;
                    case 3:
                        board.moveTurtle(this, position[0], position[1] - 1);
                        position[1]--;
                        break;
                }
            } catch (IllegalStateException exception) {}
        } else if (beforeSquare instanceof Wall) turnBack();
        else if (beforeSquare instanceof Jewel) {
            //TODO: Player finishes
        }
    }


    /** Makes the turtle fire a laser.
     * 
     * @see Board#breakWall(Turtle) Board#breakWall(Turtle)
     * @see #turnBack() Turtle#turnBack()
     * @see #resetPosition() Turtle#resetPosition()
     * @see #resetDirection() Turtle#resetDirection()
     */
    public final void fire() {
        final Object beforeSquare = getNeighbors()[direction];
        if (beforeSquare instanceof Wall) board.breakWall(this);
        else if (beforeSquare instanceof Turtle || beforeSquare instanceof Jewel) {
            final Turtle target = (beforeSquare instanceof Jewel) ? this : (Turtle) beforeSquare;
            if (board.getPlayers() <= 2) target.turnBack();
            else target.resetPosition().resetDirection();
        }
    }

    /** Makes the turtle build a wall.
     * 
     * @see Board#buildWall(Turtle) Board#buildWall(Turtle)
     */
    public final void build() {
        board.buildWall(this);
    }

    /** Returns the neighborhood of the turtle.
     * 
     * Out-of-board squares are represented by {@link Wall#STONE Wall.STONE}.
     * 
     * @return Array containing the neighbors of the square where the turtle is located.
     * <ol start="0"><li>Northern neighbor</li><li>Eastern neighbor</li><li>Southern neighbor</li><li>Western neighbor</li></ol>
     * @see Board#getNeighbors(int, int) Board#getNeighbors(int, int)
     */
    public final Object[] getNeighbors() {
        return board.getNeighbors(position[0], position[1]);
    }

    /** Returns the coordinates of the square before the turtle.
     * 
     * @return Coordinates of the square before the turtle.
     */
    public final int[] getBeforeSquare() {
        final int[] before = getPosition();
        switch (getDirection()) {
            case 0:
                before[0]--;
                break;
            case 1:
                before[1]++;
                break;
            case 2:
                before[0]++;
                break;
            case 3:
                before[1]--;
                break;
        }
        return before;
    }
}
