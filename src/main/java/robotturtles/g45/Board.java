package robotturtles.g45;

import robotturtles.g45.board.BoardWall;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

/** Class of the game board. */
public final class Board implements Drawable {
    /** Matrix containing the sprites. */
    private final BoardSprite[][] board = new BoardSprite[8][8];

    /** Gets the sprite image of the board.
     * 
     * @return Sprite image of the board.
     */
    public final Image getSprite() throws IOException {
        return ImageIO.read(getClass().getResource(Sprite.SPRITE_PATH + "board.jpg"));
    }


    public void draw() {
        //TODO
    }

    /** Gets the sprite contained in a square.
     * 
     * @param line Line index.
     * @param column Column index.
     * @return Sprite contained in the square.
     */
    public final BoardSprite getSquare(final int line, final int column) {
        return board[line][column];
    }

    /** Sets a sprite inside a square.
     * 
     * @param line Line index.
     * @param column Column index.
     * @param sprite Sprite to be contained in the square.
     * @return <code>true</code> if the sprite has been set; <code>false</code> otherwise.
     */
    public final boolean setSquare(final int line, final int column, final BoardSprite sprite) {
        if (getSquare(line, column) != null) return false;
        else board[line][column] = sprite;
        return true;
    }

    /** Gets the neighboring sprites of a square.
     * 
     * @param line Line index.
     * @param column Column index.
     * @return Array <code>[N,E,S,W]</code> of the neighboring sprites.
     */
    public final BoardSprite[] getNeighbors(final int line, final int column) {
        final BoardSprite[] neighbors = new BoardSprite[4];
        neighbors[0] = (line - 1 >= 0) ? getSquare(line - 1, column) : BoardWall.VOID.getSprite();
        neighbors[1] = (column + 1 < 8) ? getSquare(line, column + 1) : BoardWall.VOID.getSprite();
        neighbors[2] = (line + 1 < 8) ? getSquare(line + 1, column) : BoardWall.VOID.getSprite();
        neighbors[3] = (line - 1 >= 0) ? getSquare(line, column - 1) : BoardWall.VOID.getSprite();
        return neighbors;
    }
}
