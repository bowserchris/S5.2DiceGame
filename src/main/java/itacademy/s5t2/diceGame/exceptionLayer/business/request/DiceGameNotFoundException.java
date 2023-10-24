package itacademy.s5t2.diceGame.exceptionLayer.business.request;

public class DiceGameNotFoundException extends RuntimeException {
	
    private static final long serialVersionUID = 1L;

    public DiceGameNotFoundException(String message) {
        super(message);
    }
    
}
