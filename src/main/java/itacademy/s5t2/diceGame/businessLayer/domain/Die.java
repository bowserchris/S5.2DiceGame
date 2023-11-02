package itacademy.s5t2.diceGame.businessLayer.domain;

import itacademy.s5t2.diceGame.constants.CommonConstants;

public interface Die {
	
	public static int roll() {
		return (int)(Math.random()*CommonConstants.SIDES+1); //remove 1?
	}

}
