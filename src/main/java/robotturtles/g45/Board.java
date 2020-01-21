package robotturtles.g45;

import robotturtles.g45.board.BoardWall;
import robotturtles.g45.board.Jewel;
import robotturtles.g45.util.PathFinder;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class of the game board.
 */
public final class Board {
    /**
     * Matrix containing the sprites.
     */
    private final BoardSprite[][] board = new BoardSprite[8][8];

    public final List<Integer[]> getTurtles() {
        List<Integer[]> turtles = new ArrayList<Integer[]>();
        for (Player player : Game.getPlayers()) turtles.add(player.turtle.getPos());
        return turtles;
    }

    /**
     * List containing the starting locations of the turtles.
     */
    private final List<Integer[]> startPoints = new ArrayList<Integer[]>(4);

    private final List<Integer[]> getBlockingPos() {
        List<Integer[]> blockingPos = new ArrayList<Integer[]>(startPoints);
        for (Player player : Game.getPlayers()) blockingPos.add(player.turtle.getPos());
        return blockingPos;
    }

    /**
     * List containing the locations of the jewels.
     */
    private final List<Integer[]> jewels = new ArrayList<Integer[]>(3);

    public final List<Integer[]> getJewels() {
        return jewels;
    }

    /**
     * Constructs a new {@code Board}.
     */
    public Board() {
        for (int i = 0; i < 8; i++) for (int j = 0; j < 8; j++) board[i][j] = new BoardSprite("");
        switch (Game.getPlayers().length) {
            case 2:
                board[0][1] = Game.getPlayers()[0].turtle.getSpriteS();
                Game.getPlayers()[0].turtle.setStartPos(0, 1);
                board[0][5] = Game.getPlayers()[1].turtle.getSpriteS();
                Game.getPlayers()[1].turtle.setStartPos(0, 5);
                board[7][3] = Jewel.GREEN.getSprite();
                jewels.add(new Integer[]{7, 3});
                for (int l = 0; l < 8; l++) board[l][7] = BoardWall.BRICK.getSprite();
                break;
            case 3:
                board[0][0] = Game.getPlayers()[0].turtle.getSpriteS();
                Game.getPlayers()[0].turtle.setStartPos(0, 0);
                board[0][3] = Game.getPlayers()[1].turtle.getSpriteS();
                Game.getPlayers()[1].turtle.setStartPos(0, 3);
                board[0][6] = Game.getPlayers()[2].turtle.getSpriteS();
                Game.getPlayers()[2].turtle.setStartPos(0, 6);
                board[7][0] = Jewel.MAGENTA.getSprite();
                jewels.add(new Integer[]{7, 0});
                board[7][3] = Jewel.GREEN.getSprite();
                jewels.add(new Integer[]{7, 3});
                board[7][6] = Jewel.BLUE.getSprite();
                jewels.add(new Integer[]{7, 6});
                for (int l = 0; l < 8; l++) board[l][7] = BoardWall.BRICK.getSprite();
                break;
            case 4:
                board[0][0] = Game.getPlayers()[0].turtle.getSpriteS();
                Game.getPlayers()[0].turtle.setStartPos(0, 0);
                board[0][2] = Game.getPlayers()[1].turtle.getSpriteS();
                Game.getPlayers()[1].turtle.setStartPos(0, 2);
                board[0][5] = Game.getPlayers()[2].turtle.getSpriteS();
                Game.getPlayers()[2].turtle.setStartPos(0, 5);
                board[0][7] = Game.getPlayers()[3].turtle.getSpriteS();
                Game.getPlayers()[3].turtle.setStartPos(0, 7);
                board[7][1] = Jewel.MAGENTA.getSprite();
                jewels.add(new Integer[]{7, 1});
                board[7][6] = Jewel.BLUE.getSprite();
                jewels.add(new Integer[]{7, 6});
                break;
        }
    }

    /**
     * Gets the sprite contained in a square.
     *
     * @param line   Line index.
     * @param column Column index.
     * @return Sprite contained in the square.
     */
    public final BoardSprite getSquare(final int line, final int column) {
        return board[line][column];
    }

    /**
     * Sets a sprite inside a square.
     *
     * @param line   Line index.
     * @param column Column index.
     * @param sprite Sprite to be contained in the square.
     * @return {@code true} if the sprite has been set; {@code false} otherwise.
     */
    public final boolean setSquare(final int line, final int column, final BoardSprite sprite) {
        if (!(getSquare(line, column).getSprite() instanceof BufferedImage)) return false;
        else board[line][column] = sprite;
        return true;
    }

    /**
     * Sets a sprite inside a square to {@code null}.
     *
     * @param line   Line index.
     * @param column Column index.
     */
    public final void resetSquare(final int line, final int column) {
        board[line][column] = null;
    }

    /**
     * Gets the neighboring sprites of a square.
     *
     * @param line   Line index.
     * @param column Column index.
     * @return Array {@code [N,E,S,W]} of the neighboring sprites.
     */
    public final BoardSprite[] getNeighbors(final int line, final int column) {
        final BoardSprite[] neighbors = new BoardSprite[4];
        neighbors[0] = (line - 1 >= 0) ? getSquare(line - 1, column) : BoardWall.VOID.getSprite();
        neighbors[1] = (column + 1 < 8) ? getSquare(line, column + 1) : BoardWall.VOID.getSprite();
        neighbors[2] = (line + 1 < 8) ? getSquare(line + 1, column) : BoardWall.VOID.getSprite();
        neighbors[3] = (line - 1 >= 0) ? getSquare(line, column - 1) : BoardWall.VOID.getSprite();
        return neighbors;
    }

    /**
     * Checks if a path exists.
     *
     * @param from Coordinates of the starting square.
     * @param to   Coordinates of the goal square.
     * @return {@code true} if the path exists; {@code false} otherwise.
     * @throws IllegalArgumentException if {@code from} or {@code to} are invalid.
     */
    public final boolean existsPath(final int[] from, final int[] to) throws IllegalArgumentException {
        return new PathFinder(board, Arrays.asList(new Object[]{BoardWall.BRICK, BoardWall.VOID}), from, to).exists();
    }

    /**
     * Checks if a path exists.
     *
     * @param from Coordinates of the starting square.
     * @param to   Coordinates of the goal square.
     * @return {@code true} if the path exists; {@code false} otherwise.
     * @throws IllegalArgumentException if {@code from} or {@code to} are invalid.
     */
    public final boolean existsPath(final Integer[] from, final Integer[] to) throws IllegalArgumentException {
        return existsPath(new int[]{from[0], from[1]}, new int[]{to[0], to[1]});
    }

    public BoardSprite[][] getBoard() {
        return board;
    }

    /**
     * Checks if a wall can be build on a square.
     *
     * @param wallIdx Index of the wall.
     * @param line    Line index of the square.
     * @param column  Column index of the square.
     * @return {@code true} if the wall can be build; {@code false} otherwise.
     */
    public boolean canBuildWall(final int wallIdx, final int line, final int column) {
        return getSquare(line, column).isEmpty();
       /* if (!getSquare(line, column).equals(null) && !getSquare(line, column).isEmpty()) return false;
        else if (wallIdx >= 2) {
            setSquare(line, column, BoardWall.BRICK.getSprite());
            boolean blocked = false;
            for (Integer[] turtle : getBlockingPos()) for (Integer[] jewel : jewels) {
                if (blocked) break;
                else blocked = !existsPath(turtle, jewel);
            }
            resetSquare(line, column);
            return !blocked;
        } else return true;*/
    }
}
