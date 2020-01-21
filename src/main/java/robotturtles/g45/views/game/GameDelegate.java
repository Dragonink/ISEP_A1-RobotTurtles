package robotturtles.g45.views.game;

public interface GameDelegate {
    void onWallClick(int wallIdx);

    void onWallUnclick(int wallIdx);

    void onPlayerChange();

    void onBoardCellClick(int xPos, int yPos);
}
