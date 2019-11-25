package robotturtles.g45;

import javax.swing.*;

public final class App {
    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
    	 Runnable r = new Runnable() {
             @Override
             public void run() {
                 Board board = new Board(2);

                 JFrame frame = new JFrame("Robot Turtles");
                 frame.add(board.getGameScene());
                 frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                 frame.setLocationByPlatform(true);
                 frame.pack();
                 frame.setMinimumSize(frame.getSize());
                 frame.setMaximumSize(frame.getSize());
                 frame.setVisible(true);
             }
         };
         SwingUtilities.invokeLater(r);
    }
}
