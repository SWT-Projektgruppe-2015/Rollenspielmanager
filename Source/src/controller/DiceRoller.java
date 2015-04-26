package controller;

import java.util.Random;

/**
 * Zuständig für das Simulieren von Würfelwürfen.
 * @author Britta Heymann
 *
 */
public class DiceRoller {

	public static int RollW4() {
		return rollDice(4);
	}

	
	
	public static int RollW6() {
		return rollDice(6);
	}
	
	
	
	public static int RollW8() {
		return rollDice(8);
	}


	
	public static int RollW10() {
		return rollDice(10);
	}
	
	

	public static int RollW12() {
		return rollDice(12);
	}
	
	
	
	public static int RollW20() {
		return rollDice(20);
	}

	
	
	public static int RollW30() {
		return rollDice(30);
	}

	
	
	private static int rollDice(int faces) {
		Random random = new Random();
		return random.nextInt(faces) + 1;
	}
}