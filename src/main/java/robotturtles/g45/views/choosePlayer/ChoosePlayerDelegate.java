package robotturtles.g45.views.choosePlayer;

import robotturtles.g45.board.Turtle;

import java.util.List;

public interface ChoosePlayerDelegate {
    void onPlayClicked(List<Turtle> chosenTurtles);
}
