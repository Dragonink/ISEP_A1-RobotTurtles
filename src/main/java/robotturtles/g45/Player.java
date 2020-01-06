package robotturtles.g45;

import robotturtles.g45.tiles.Turtle;

import java.util.List;
import java.util.Stack;

public class Player {
    public Turtle turtle = new Turtle;
    private Card[] hand = new Card[];
    private int iceWalls;
    private int brickWalls;
    private Queue<Card> program = new Queue<Card>();
    private Stack<Card>availableCards = new Stack<Card>();
    private List<Card> ditchedCards = new List<Card>();

    public void addToProgram(Card){

    }

    public void executeProgram(){

    }

    public boolean buildWall(PlayerWall,int,int){

    }

    public void ditchCard(Card){

    }
}
