package robotturtles.g45;

import robotturtles.g45.board.BoardWall;

import javax.swing.*;

/** Class of the game board. */
public final class Board implements Drawable {
    /** Matrix containing the sprites. */
    private final BoardSprite[][] board = new BoardSprite[8][8];

    /** Sprite image of the board. */
    private final ImageIcon sprite;
    /** Gets the sprite image of the board.
     * 
     * @return Sprite image of the board.
     */
    public final ImageIcon getSprite() {
        return sprite;
    }


    /** Constructs a new <code>Board</code>.
     * 
     * @param spriteName Filename of the image sprite.
     */
    Board(String spriteName) {
        //TODO: sprite
        this.sprite = new ImageIcon(spriteName);
    }

    // draw board
    public void draw(String image) {

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
