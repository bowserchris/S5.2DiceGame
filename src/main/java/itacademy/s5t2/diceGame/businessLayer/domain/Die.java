package itacademy.s5t2.diceGame.businessLayer.domain;

import static itacademy.s5t2.diceGame.constants.CommonConstants.SIDES;

public interface Die {
	public static int roll() {
		return (int) (Math.random() * SIDES + 1); // remove 1?
	}
}