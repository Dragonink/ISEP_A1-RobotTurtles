package robotturtles.g45.board;

import robotturtles.g45.BoardSprite;
import robotturtles.g45.Game;
import robotturtles.g45.Player;
import robotturtles.g45.Sprite;

import javax.swing.*;
import java.awt.*;

public enum Turtle {
    BEEP("beepTileS.jpg","beepTileN.jpg","beepTileE.jpg","beepTileW.jpg",Color.BLUE, Sprite.SPRITE_PATH+"choixBeep.jpg"),
    PANGLE("pangleTileS.jpg","pangleTileN.jpg","pangleTileE.jpg","pangleTileW.jpg", Color.ORANGE, Sprite.SPRITE_PATH + "choixPangle.jpg"),
    PI("piTileS.jpg","pangleTileN.jpg","pangleTileE.jpg","pangleTileW.jpg",Color.RED,Sprite.SPRITE_PATH+"choixPi.jpg"),
    DOT("dotTileS.jpg","dotTileN.jpg","dotTileE.jpg","dotTileW.jpg",Color.MAGENTA,Sprite.SPRITE_PATH+"choixDot.jpg");

    private final Color color;
    private final BoardSprite spriteS;
    private final BoardSprite spriteN;
    private final BoardSprite spriteW;
    private final BoardSprite spriteE;
    private final ImageIcon playerChooseIcon;
    private int startPosI;
    private int startPosJ;
    public final Integer[] getStartPos() {
        return new Integer[] {startPosI, startPosJ};
    }
    public final void setStartPos(int i, int j) {
        startPosI = i;
        startPosJ = j;
    }
    private int posI;
    private int posJ;
    public final Integer[] getPos() {
        return new Integer[] {posI, posJ};
    }
    public final void setPos(int i, int j) {
        posI = i;
        posJ = j;
    }
    private int rotation = 2;
    public final int getRotation() {
        return rotation;
    }
    public final void setRotation(int rotation) {
        this.rotation = Math.floorMod(rotation, 4);
    }

    Turtle(String spriteNameS,String spriteNameN, String spriteNameE, String spriteNameW, Color color, String playerChooseIconName ){
        this.color = color;
        this.spriteS = new BoardSprite(spriteNameS);
        this.spriteN = new BoardSprite(spriteNameN);
        this.spriteW = new BoardSprite(spriteNameW);
        this.spriteE = new BoardSprite(spriteNameE);
        this.playerChooseIcon = new ImageIcon(this.getClass().getResource(playerChooseIconName));
    }

    public Color getColor() {
        return color;
    }

    public BoardSprite getSpriteS() { return spriteS; }
    public BoardSprite getSpriteN() { return spriteN; }
    public BoardSprite getSpriteW() { return spriteW; }
    public BoardSprite getSpriteE() { return spriteE; }
    public final BoardSprite getSprite() {
        if (rotation == 0) return spriteN;
        else if (rotation == 1) return spriteE;
        else if (rotation == 2) return spriteS;
        else return spriteW;
    }

    public ImageIcon getPlayerChooseIcon() {
        return playerChooseIcon;
    }

    public final void reset() {
        for (Player player : Game.getPlayers()) if (player.turtle.getStartPos()[0].equals(startPosI) && player.turtle.getStartPos()[1].equals(startPosJ)) {
            player.turtle.reset();
            break;
        }
        setRotation(2);
        Game.board.resetSquare(posI, posJ);
        Game.board.setSquare(startPosI, startPosJ, getSprite());
        setPos(startPosI, startPosJ);
    }
}
