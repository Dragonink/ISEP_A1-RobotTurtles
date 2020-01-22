package robotturtles.g45.views.game;

import robotturtles.g45.Sprite;

public interface GameDelegate {
    void onWallClick(int wallIdx);

    void onWallUnclick(int wallIdx);

    void onPlayerChange();

    void onBoardCellClick(int xPos, int yPos);

    void onActionDone(Sprite sprite);

    void onPlayerSuccess();
}
