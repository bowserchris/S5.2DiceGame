package itacademy.s5t2.diceGame.businessLayer.domain;

public interface Die {

	static final int SIDES = 6;
	
	public static int roll() {
		return (int)(Math.random()*SIDES+1);
	}

}
