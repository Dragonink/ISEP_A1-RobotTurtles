package robotturtles.g45;
import robotturtles.g45.board.BoardWall;
import robotturtles.g45.board.Jewel;

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


    /** Constructs a new {@code Board}. */
    public Board() {
        switch (Game.getPlayers().length) {
            case 2:
                board[0][1] = Game.getPlayers()[0].turtle.getSprite();
                board[0][5] = Game.getPlayers()[1].turtle.getSprite();
                board[7][3] = Jewel.GREEN.getSprite();
                for (int l = 0; l < 8; l++) board[l][7] = BoardWall.BRICK.getSprite();
                break;
            case 3:
                board[0][0] = Game.getPlayers()[0].turtle.getSprite();
                board[0][3] = Game.getPlayers()[1].turtle.getSprite();
                board[0][6] = Game.getPlayers()[2].turtle.getSprite();
                board[7][0] = Jewel.MAGENTA.getSprite();
                board[7][3] = Jewel.GREEN.getSprite();
                board[7][6] = Jewel.BLUE.getSprite();
                for (int l = 0; l < 8; l++) board[l][7] = BoardWall.BRICK.getSprite();
                break;
            case 4:
                board[0][0] = Game.getPlayers()[0].turtle.getSprite();
                board[0][2] = Game.getPlayers()[1].turtle.getSprite();
                board[0][5] = Game.getPlayers()[2].turtle.getSprite();
                board[0][7] = Game.getPlayers()[3].turtle.getSprite();
                board[7][1] = Jewel.MAGENTA.getSprite();
                board[7][6] = Jewel.BLUE.getSprite();
                break;
        }
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
     * @return {@code true} if the sprite has been set; {@code false} otherwise.
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
}
