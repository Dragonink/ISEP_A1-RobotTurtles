package robotturtles.g45;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

import robotturtles.g45.tiles.*;
import robotturtles.g45.lib.*;
import robotturtles.g45.lib.RotatedIcon.Rotate;

/**
 * Class that controls the game board.
 * 
 * @author Tanguy Berthoud
 */
public final class Board {
	/** Matrix representing the game board. */
	private final Object[][] board = new Object[8][8];

	/** JPanel representing the game scene(board...) **/
	private final JPanel gameScene = new JPanel(new BorderLayout(3, 3));
	/** TODO **/
	private JLabel boardContainer;
	private JPanel cardContainer = new JPanel();
	/** TODO **/
	private JLabel[][] tiles = new JLabel[8][8];

	public JComponent getBoardContainer() {
		return boardContainer;
	}

	public JComponent getGameScene() {
		return gameScene;
	}

	/**
	 * Gets the matrix representing the game board.
	 * 
	 * @return {@link #board Board#board}
	 */
	public final Object[][] getBoard() {
		return board;
	}

	/** Number of players on the board. */
	private int players;

	/**
	 * Gets the number of players on the board.
	 * 
	 * @return {@link #players Board#players}
	 */
	public final int getPlayers() {
		return players;
	}

	/**
	 * Constructs a new <code>Board</code>. First fills {@link #board Board#board}
	 * with <code>null</code>. Then adds {@link Turtle turtles}, {@link Jewel
	 * jewels} and {@link Wall#STONE Wall.STONE} depending on <code>players</code>.
	 * 
	 * @param players Number of players.
	 * @throws IllegalArgumentException if <code>players < 2</code> or
	 *                                  <code>players > 4</code>.
	 */
	public Board(int players) throws IllegalArgumentException {
		this.players = players;
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				board[i][j] = null;
		switch (players) {
		case 2:
			board[0][1] = Turtle.BEEP;
			Turtle.BEEP.setBoard(this);
			Turtle.BEEP.initPosition(0, 1);
			board[0][5] = Turtle.PI;
			Turtle.PI.setBoard(this);
			Turtle.PI.initPosition(0, 5);
			board[7][3] = Jewel.BLUE;
			for (int i = 0; i < 8; i++)
				board[i][7] = Wall.STONE;
			drawBoard();
			break;
		case 3:
			board[0][0] = Turtle.BEEP;
			Turtle.BEEP.setBoard(this);
			Turtle.BEEP.initPosition(0, 0);
			board[0][3] = Turtle.PI;
			Turtle.PI.setBoard(this);
			Turtle.PI.initPosition(0, 3);
			board[0][6] = Turtle.PANGLE;
			Turtle.PANGLE.setBoard(this);
			Turtle.PANGLE.initPosition(0, 6);
			board[7][0] = Jewel.BLUE;
			board[7][3] = Jewel.RED;
			board[7][6] = Jewel.GREEN;
			for (int i = 0; i < 8; i++)
				board[i][7] = Wall.STONE;
			drawBoard();
			break;
		case 4:
			board[0][0] = Turtle.BEEP;
			Turtle.BEEP.setBoard(this);
			Turtle.BEEP.initPosition(0, 0);
			board[0][2] = Turtle.PI;
			Turtle.PI.setBoard(this);
			Turtle.PI.initPosition(0, 2);
			board[0][5] = Turtle.PANGLE;
			Turtle.PANGLE.setBoard(this);
			Turtle.PANGLE.initPosition(0, 5);
			board[0][7] = Turtle.DOT;
			Turtle.DOT.setBoard(this);
			Turtle.DOT.initPosition(0, 7);
			board[7][1] = Jewel.BLUE;
			board[7][6] = Jewel.RED;
			drawBoard();
			break;
		default:
			throw new IllegalArgumentException("Invalid number of players.");
		}
	}
	public static void affichage (int nbJoueur) {
		Runnable r = new Runnable() {
	        @Override
	        public void run() {
	            Board board = new Board(nbJoueur);
	
	            JFrame frame = new JFrame("Robot Turtles");
	            frame.add(board.getGameScene());
	            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	            frame.setLocationByPlatform(true);
	            frame.pack();
	            //frame.setMinimumSize(frame.getSize());
	            //frame.setMaximumSize(frame.getSize());
	            frame.setVisible(true);
	        }
	    };
	    SwingUtilities.invokeLater(r);
	}

	private final void drawBoard() {
		gameScene.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		gameScene.setBackground(Color.blue);
		gameScene.setSize(1000, 2000);
		GridLayout gridlayoutBoard = new GridLayout(8, 8, 0, 0);
		ImageIcon backgroungBoardImg = new ImageIcon(this.getClass().getResource("/resources/images/board.jpg"));
		boardContainer = new JLabel(backgroungBoardImg);
		
		boardContainer.setLayout(gridlayoutBoard);
		gameScene.add(boardContainer, BorderLayout.LINE_START);
		
		GridLayout gridlayoutCard = new GridLayout(3, 5, 3, 8);
		//ImageIcon card = new ImageIcon(this.getClass().getResource("/resources/images/beepCard.jpg"));
		cardContainer.setBackground(Color.white);
		cardContainer.setSize(100,200);
		cardContainer.setLayout(gridlayoutCard);
		gameScene.add(cardContainer, BorderLayout.LINE_END);

		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles.length; j++) {
				JLabel label = new JLabel();
				
				label.setBorder(BorderFactory.createLineBorder(Color.black));
				if (board[i][j] == null) {
					ImageIcon imageIcon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
					label.setIcon(imageIcon);
				}else if (board[i][j] == Turtle.BEEP){
					ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/resources/images/beepTile.jpg"));
					RotatedIcon rotateImageIcon = new RotatedIcon(imageIcon, Rotate.UPSIDE_DOWN);
					label.setIcon(rotateImageIcon);
				}else if (board[i][j] == Turtle.DOT){
					ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/resources/images/dotTile.jpg"));
					RotatedIcon rotateImageIcon = new RotatedIcon(imageIcon, Rotate.UPSIDE_DOWN);
					label.setIcon(rotateImageIcon);
				}else if (board[i][j] == Turtle.PANGLE){
					ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/resources/images/pangleTile.jpg"));
					RotatedIcon rotateImageIcon = new RotatedIcon(imageIcon, Rotate.UPSIDE_DOWN);
					label.setIcon(rotateImageIcon);
				}else if (board[i][j] == Turtle.PI){
					ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/resources/images/piTile.jpg"));
					RotatedIcon rotateImageIcon = new RotatedIcon(imageIcon, Rotate.UPSIDE_DOWN);
					label.setIcon(rotateImageIcon);
				}else if (board[i][j] == Jewel.BLUE){
					ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/resources/images/jewelTileBlue.jpg"));
					label.setIcon(imageIcon);
				}else if (board[i][j] == Jewel.GREEN){
					ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/resources/images/jewelTileGreen.jpg"));
					label.setIcon(imageIcon);
				}else if (board[i][j] == Jewel.RED){
					ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/resources/images/jewelTilePurple.jpg"));
					label.setIcon(imageIcon);
				}else if (board[i][j] == Wall.STONE){
					ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/resources/images/stoneWall.jpeg"));
					label.setIcon(imageIcon);
				}
				tiles[i][j] = label;
				boardContainer.add(label);
			}
		} 
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 5; j++) {
				JLabel label = new JLabel();
				
				label.setBorder(BorderFactory.createLineBorder(Color.black));
				if (i==2) {
					ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/resources/images/laserCard.png"));
					label.setIcon(imageIcon);
				}
				else if (i==0 && j==1) {
					ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/resources/images/iceWall.jpeg"));
					label.setIcon(imageIcon);
				}
				else if (i==1 && j==0) {
					ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/resources/images/beepCard.jpg"));
					label.setIcon(imageIcon);
				}
				else if (i==1 && j==4) {
					ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/resources/images/beepCard.jpg"));
					label.setIcon(imageIcon);
				}
				else {
					ImageIcon imageIcon = new ImageIcon(new BufferedImage(90, 64, BufferedImage.TYPE_INT_ARGB));
					label.setIcon(imageIcon);
				}
				tiles[i][j] = label;
				cardContainer.add(label);
			}
		}
	}

	/**
	 * Returns the neighborhood of the <code>(i,j)</code> square.
	 * 
	 * Out-of-board squares are represented by {@link Wall#STONE Wall.STONE}.
	 * 
	 * @param i Line index of the square.
	 * @param j Column index of the square.
	 * @return Array containing the neighbors of the <code>(i,j)</code> square.
	 *         <ol start="0">
	 *         <li>Northern neighbor</li>
	 *         <li>Eastern neighbor</li>
	 *         <li>Southern neighbor</li>
	 *         <li>Western neighbor</li>
	 *         </ol>
	 */
	public final Object[] getNeighbors(int i, int j) {
		final Object[] neighbors = { (i > 0) ? board[i - 1][j] : Wall.STONE, (j < 7) ? board[i][j + 1] : Wall.STONE,
				(i < 7) ? board[i + 1][j] : Wall.STONE, (j > 0) ? board[i][j - 1] : Wall.STONE };
		return neighbors;
	}

	/**
	 * Moves a turtle on the board. If the destination contains another turtle, both
	 * are sent back to their starting positions.
	 * 
	 * @param turtle Turtle to move.
	 * @param i      Destination line index.
	 * @param j      Destination column index.
	 * @throws IllegalStateException if the destination square is not empty.
	 */
	public final void moveTurtle(Turtle turtle, int i, int j) throws IllegalStateException {
		if (board[i][j] instanceof Turtle) {
			((Turtle) board[i][j]).resetPosition().resetDirection();
			turtle.resetPosition().resetDirection();
		}
		if (board[i][j] != null)
			throw new IllegalStateException("Destination is not empty.");
		final int[] position = turtle.getPosition();
		board[i][j] = turtle;
		board[position[0]][position[1]] = null;
	}

	/**
	 * Tries to break a wall. Only {@link Wall#ICE Wall.ICE} is destructible.
	 * 
	 * @param turtle Turtle who tries to break a wall.
	 */
	public final void breakWall(Turtle turtle) {
		final int[] wallPosition = turtle.getBeforeSquare();
		if (board[wallPosition[0]][wallPosition[1]].equals(Wall.ICE))
			board[wallPosition[0]][wallPosition[1]] = null;
	}

	/**
	 * Tries to build a wall.
	 * 
	 * @param turtle Turtle who tries to build a wall.
	 */
	public final void buildWall(Turtle turtle) {
		final int[] wallPosition = turtle.getBeforeSquare();
		if (board[wallPosition[0]][wallPosition[1]].equals(null))
			board[wallPosition[0]][wallPosition[1]] = Wall.ICE;
	}
}
