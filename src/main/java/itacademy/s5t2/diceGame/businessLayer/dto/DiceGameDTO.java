package itacademy.s5t2.diceGame.businessLayer.dto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import itacademy.s5t2.diceGame.businessLayer.domain.DiceGame;
import itacademy.s5t2.diceGame.businessLayer.domain.Die;
import itacademy.s5t2.diceGame.constants.CommonConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiceGameDTO {
	
	private long gameId;
	private int dieResult1;
	private int dieResult2;
	private String gameResult;
	
	public void playGame() {
		dieResult1 = Die.roll();
		dieResult2 = Die.roll();
		if ((dieResult1 + dieResult2) == CommonConstants.WIN_CONDITION) {
			this.gameResult = CommonConstants.WINS;
		} else {
			this.gameResult = CommonConstants.LOSSES;
		}
	}
	
	/*for mysql only tables
	 * @Schema(description = "The id of the player")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    //@JoinColumn(name="playerId")
    private Userdto userdto;
	 */

}
