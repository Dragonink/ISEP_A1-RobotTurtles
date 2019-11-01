package robotturtles.g45.tiles;
import robotturtles.g45.Board;

/** Enum of the turtles a player can control.
 * 
 * <b>Main commands</b>:<ul>
 * <li>Blue cards: {@link #moveForward() Turtle#moveForward()}</li>
 * <li>Yellow cards: {@link #rotate(int) Turtle#rotate(int)} with <code>int < 0</code></li>
 * <li>Purple cards: {@link #rotate(int) Turtle#rotate(int)} with <code>int > 0</code></li>
 * TODO <li>Laser cards</li>
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
        if (startPosition[0] == -1 && startPosition[1] == -1) throw new IllegalStateException("Cannot be invoked after the beginning of the game.");
        else if (i < 0 || i > 7 || j < 0 || j > 7) throw new ArrayIndexOutOfBoundsException("Invalid position.");
        startPosition[0] = i;
        startPosition[1] = j;
        position[0] = i;
        position[1] = j;
    }
    /** Resets the position of the turtle.
     * 
     * @return <code>this</code> for chaining.
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
    /** Tries to make the turtle go forward. */
    public final void moveForward() {
        final Object beforeSquare = getNeighbors()[direction];
        if (beforeSquare == null || beforeSquare instanceof Turtle) {
            switch (direction) {
                case 0:
                    try {
                        board.moveTurtle(this, position[0] - 1, position[1]);
                        position[0]--;
                    } catch (IllegalStateException exception) {}
                    break;
                case 1:
                    try {
                        board.moveTurtle(this, position[0], position[1] + 1);
                        position[1]++;
                    } catch (IllegalStateException exception) {}
                    break;
                case 2:
                    try {
                        board.moveTurtle(this, position[0] + 1, position[1]);
                        position[0]++;
                    } catch (IllegalStateException exception) {}
                    break;
                case 3:
                    try {
                        board.moveTurtle(this, position[0], position[1] - 1);
                        position[1]--;
                    } catch (IllegalStateException exception) {}
                    break;
            }
        } else if (beforeSquare instanceof Wall) turnBack();
        else if (beforeSquare instanceof Jewel) {
            //TODO: Player finishes
        }
    }


    /** Returns the neighborhood of the turtle.
     * 
     * Out-of-board squares are represented by {@link Wall#STONE Wall.STONE}.
     * Calls {@link Board#getNeighbors(int, int)}.
     * 
     * @return Array containing the neighbors of the <code>(i,j)</code> square.
     * <ol start="0"><li>Northern neighbor</li><li>Eastern neighbor</li><li>Southern neighbor</li><li>Western neighbor</li></ol>
     */
    public final Object[] getNeighbors() {
        return board.getNeighbors(position[0], position[1]);
    }
}
