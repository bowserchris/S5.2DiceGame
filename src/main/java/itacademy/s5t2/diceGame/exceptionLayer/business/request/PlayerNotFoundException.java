package itacademy.s5t2.diceGame.exceptionLayer.business.request;

public class PlayerNotFoundException extends RuntimeException {
	
    private static final long serialVersionUID = 1L;

    public PlayerNotFoundException(String message) {
        super(message);
    }
    
}
