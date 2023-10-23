package itacademy.s5t2.diceGame.dto;

import itacademy.s5t2.diceGame.domain.Die;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiceGameDTO {
	
	private long idGame;
	private int dieResult1;
	private int dieResult2;
	private String gameResult;
	
	///DTO only fields & methods
	private static final int WIN_CONDITION = 7;
	private static final String RESULT_WIN = "Win";
	private static final String RESULT_LOSE = "Lose";
	
	public void playGame() {
		dieResult1 = Die.roll();
		dieResult2 = Die.roll();
		if ((dieResult1 + dieResult2) == WIN_CONDITION) {
			this.gameResult = RESULT_WIN;
		} else {
			this.gameResult = RESULT_LOSE;
		}
	}

}
