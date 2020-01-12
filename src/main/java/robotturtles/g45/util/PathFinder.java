package robotturtles.g45.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Utility class to compute paths in {@link robotturtles.g45.util.TraversableBoard TraversableBoard}. */
public final class PathFinder {
    /** Board to traverse. */
    private final TraversableBoard board;

    /** Coordinates of the starting square. */
    private final int[] from;

    /** Coordinates of the goal square. */
    private final int[] to;

    /** List of coordinates already crossed when traversing. */
    private List<Integer[]> crossed;


    /** Constructs a new {@code Path}.
     * 
     * @param board Board to traverse.
     * @param from Coordinates of the starting square.
     * @param to Coordinates of the goal square.
     * @throws IllegalArgumentException if {@code from} or {@code to} are invalid.
     */
    PathFinder(final TraversableBoard board, final int[] from, final int[] to) throws IllegalArgumentException {
        this.board = board;
        if (board.checkSquare(from)) this.from = from;
        else throw new IllegalArgumentException("'from' is invalid.");
        if (board.checkSquare(to)) this.to = to;
        else throw new IllegalArgumentException("'to' is invalid.");
    }


    /** Traverses the {@link #board PathFinder#board}.
     * 
     * @param goal Coordinates of the square to reach.
     * @param current Current coordinates.
     * @return {@code true} if {@code goal} is reached; {@code false} otherwise.
     */
    private boolean traverse(final int[] goal, int[] current) {
        if (Arrays.equals(goal, current)) return true;
        else {
            crossed.add(new Integer[] {current[0], current[1]});
            boolean reached = false;
            for (Integer[] neighbor : board.getNeighbors(current)) {
                if (reached) break;
                else if (!crossed.contains(neighbor)) reached = reached || traverse(goal, new int[] {neighbor[0], neighbor[1]});
            }
            return reached;
        }
    }

    /** Checks if the path exists.
     * 
     * @return {@code true} if the path exists; {@code false} otherwise.
     */
    boolean exists() {
        crossed = new ArrayList<Integer[]>();
        return traverse(to, from);
    }
}
