package itacademy.s5t2.diceGame.exceptionLayer.business.request;

/**
 * Exception for when dice game is not found in database
 * 
 * @author bowser-chris
 */
public class DiceGameNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public DiceGameNotFoundException(String message) {
		super(message);
	}
}