package robotturtles.g45.util;

import java.util.Arrays;
import java.util.Random;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** Test class for {@link PathFinder}. */
public final class TestPathFinder {
    /** Global {@code Random} object. */
    private static final Random RND = new Random();


    /** Test meant to have an existing path. */
    @Test
    public final void testPass() {
        // Test case definition
        Integer[][] board = new Integer[8][8];
        for (int l = 0; l < 8; l++) for (int c = 0; c < 8; c++) board[l][c] = 0;
        int emptyLine = RND.nextInt(8);
        for (int l = 0; l < 8; l++) if (l != emptyLine) board[l][1 + RND.nextInt(6)] = 1;
        final TraversableBoard<Integer> testBoard = new TraversableBoard<Integer>(board, Arrays.asList(1));
        // Test execution
        for (int l = 0; l < 8; l++) {
            for (int c = 0; c < 8; c++) System.out.printf("%2d", board[l][c]);
            System.out.println();
        }
        System.out.println();
        assertTrue(new PathFinder<Integer>(testBoard, new int[] {0,0}, new int[] {7,7}).exists());
    }

    /** Test meant to have no existing path. */
    @Test
    public final void testNotPass() {
        // Test case definition
        Integer[][] board = new Integer[8][8];
        for (int l = 0; l < 8; l++) for (int c = 0; c < 8; c++) board[l][c] = 0;
        int fullColumn = 1 + RND.nextInt(6);
        for (int l = 0; l < 8; l++) board[l][fullColumn] = 1;
        final TraversableBoard<Integer> testBoard = new TraversableBoard<Integer>(board, Arrays.asList(1));
        // Test execution
        for (int l = 0; l < 8; l++) {
            for (int c = 0; c < 8; c++) System.out.printf("%2d", board[l][c]);
            System.out.println();
        }
        System.out.println();
        assertFalse(new PathFinder<Integer>(testBoard, new int[] {0,0}, new int[] {7,7}).exists());
    }
}
