package itacademy.s5t2.diceGame.exceptionLayer.business.request;

/**
 * Exception for when player is not found in database
 * 
 * @author bowser-chris
 */
public class PlayerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public PlayerNotFoundException(String message) {
		super(message);
	}
}