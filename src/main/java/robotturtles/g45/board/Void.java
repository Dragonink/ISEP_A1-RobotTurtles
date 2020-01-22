package robotturtles.g45.board;

import robotturtles.g45.BoardSprite;
import robotturtles.g45.Sprite;

public enum Void implements Sprite {
    VOID;

    private int posI;
    private int posJ;

    @Override
    public Integer[] getPos() {
        return new Integer[]{this.posI, this.posJ};
    }

    public void setPos(int posI, int posJ) {
        this.posI = posI;
        this.posJ = posJ;
    }

    @Override
    public BoardSprite getSprite() {
        return new BoardSprite(null);
    }
}
