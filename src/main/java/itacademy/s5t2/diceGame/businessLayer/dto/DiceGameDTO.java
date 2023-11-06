package itacademy.s5t2.diceGame.businessLayer.dto;

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
	
	/*for mysql only tables
	 * @Schema(description = "The id of the player")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    //@JoinColumn(name="playerId")
    private Userdto userdto;
	 */

}
