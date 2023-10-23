package itacademy.s5t2.diceGame.request;

public class PlayerNotFoundException extends RuntimeException {
	
    private static final long serialVersionUID = 1L;

    public PlayerNotFoundException(String message) {
        super(message);
    }
    
}
