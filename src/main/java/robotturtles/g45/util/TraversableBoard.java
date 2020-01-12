package robotturtles.g45.util;

import java.util.ArrayList;
import java.util.List;

/** Utility class for prepare a {@link robotturtles.g45.Board Board} for {@link PathFinder}. */
public final class TraversableBoard {
    /** Board used to compute a path. */
    private final Object[][] board;

    /** List of objects that cannot be crossed on the board. */
    private final List<Object> obstacles;
    /** Gets a list of obstacles on the board.
     * 
     * @return List of objects that cannot be crossed on the board.
     */
    public final List<Object> getObstacles() {
        return obstacles;
    }


    /** Constructs a new {@code TraversableBoard}.
     * 
     * @param board Board used to compute a path.
     * @param obstacles List of objects that cannot be crossed on the board.
     */
    public TraversableBoard(final Object[][] board, final List<Object> obstacles) {
        this.board = board;
        this.obstacles = obstacles;
    }


    /** Checks if a square exists in {@link #board TraversableBoard#board}.
     * 
     * @param square Coordinates of the square in {@link #board TraversableBoard#board}.
     * @return {@code true} if the square exists; {@code false} otherwise.
     */
    boolean checkSquare(final int[] square) {
        return square[0] >= 0 && square[0] < board.length && square[1] >= 0 && square[1] < board[0].length;
    }
    /** Checks if a square exists in {@link #board TraversableBoard#board}.
     * 
     * @param square Coordinates of the square in {@link #board TraversableBoard#board}.
     * @return {@code true} if the square exists; {@code false} otherwise.
     */
    boolean checkSquare(final Integer[] square) {
        return checkSquare(new int[] {square[0], square[1]});
    }

    /** Gets the object contained at a given square in {@link #board TraversableBoard#board}.
     * 
     * @param square Coordinates of the square in {@link #board TraversableBoard#board}.
     * @return Object at the given square.
     * @throws IllegalArgumentException if {@code square} is invalid.
     */
    Object getSquare(final int[] square) throws IllegalArgumentException {
        if (!checkSquare(square)) throw new IllegalArgumentException("'square' is invalid.");
        else return board[square[0]][square[1]];
    }
    /** Gets the object contained at a given square in {@link #board TraversableBoard#board}.
     * 
     * @param square Coordinates of the square in {@link #board TraversableBoard#board}.
     * @return Object at the given square.
     * @throws IllegalArgumentException if {@code square} is invalid.
     */
    Object getSquare(final Integer[] square) throws IllegalArgumentException {
        return getSquare(new int[] {square[0], square[1]});
    }

    /** Gets the valid neighbors of a square.
     * 
     * @param square Coordinates of the square in {@link #board TraversableBoard#board}.
     * @return List of the coordinates of valid neighbors of {@code square}.
     * @throws IllegalArgumentException if {@code square} is invalid.
     */
    List<Integer[]> getNeighbors(final int[] square) throws IllegalArgumentException {
        if (!checkSquare(square)) throw new IllegalArgumentException("'square' is invalid.");
        else {
            List<Integer[]> neighbors = new ArrayList<Integer[]>();
            final Integer[] east = {square[0], square[1] + 1};
            final Integer[] south = {square[0] + 1, square[1]};
            final Integer[] west = {square[0], square[1] - 1};
            final Integer[] north = {square[0] - 1, square[1]};
            for (Integer[] neighbor : new Integer[][] {east, south, west, north}) {
                if (checkSquare(neighbor) && !obstacles.contains(getSquare(neighbor))) neighbors.add(neighbor);
            }
            return neighbors;
        }
    }
}
