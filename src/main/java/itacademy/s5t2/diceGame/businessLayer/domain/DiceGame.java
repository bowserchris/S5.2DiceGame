package itacademy.s5t2.diceGame.businessLayer.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import itacademy.s5t2.diceGame.businessLayer.dto.PlayerDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Details of a Dice Game object")
@Entity
/*@JsonIgnoreProperties({
"hibernateLazyInitializer",
"handler"
})
*/
@Table(name = "games")
public class DiceGame {
	
	@Schema(description = "Unique id of the DiceGame")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long gameId;
	
	@Schema(description = "Value of 1st Die",name="dieResult1")
	@NotNull(message = "Die value cannot be empty")
	@Column(name = "die_1_value")
	private int dieResult1;
	
	@Schema(description = "Value of 2nd Die",name="dieResult2")
	@NotNull(message = "Die value cannot be empty")
	@Column(name = "die_2_value")
	private int dieResult2;
	
	@Schema(description = "Result of the Game",name="gameResult")
	@NotNull(message = "Game Result cannot be empty")
	@Column(name = "game_result")
	private String gameResult;
	
	/*//linking multiple tables, only on this object and not on player
	 * @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private User user; */

}
