package itacademy.s5t2.diceGame.businessLayer.domain;

import static itacademy.s5t2.diceGame.constants.CommonConstants.SIDES;

/**
 * The die for the game
 * 
 * @author bowser-chris
 */
public interface Die {
	public static int roll() {
		return (int) (Math.random() * SIDES + 1);
	}
}