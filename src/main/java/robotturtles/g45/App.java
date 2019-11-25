package robotturtles.g45;

import java.util.Scanner;

/**
 * Hello world!
 */
public final class App {
    private static Scanner scanner;

	/**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
    	System.out.println("Vous étes combien à jouer?");
    	scanner = new Scanner(System.in);
    	int nbJoueur = scanner.nextInt();
    	Board.affichage (nbJoueur);
    }
}
