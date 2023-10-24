package itacademy.s5t2.diceGame.businessLayer.domain;

import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
/*@JsonIgnoreProperties({
"hibernateLazyInitializer",
"handler"
})
*/
@Table(name = "games")
public class DiceGame {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idGame;
	
	@ApiModelProperty(notes = "Value of 1st Die",name="dieResult1",required=true,value="test die1")
	@NotNull(message = "Die value cannot be empty")
	@Column(name = "die_1_value")
	private int dieResult1;
	
	@ApiModelProperty(notes = "Value of 2nd Die",name="dieResult2",required=true,value="test die2")
	@NotNull(message = "Die value cannot be empty")
	@Column(name = "die_2_value")
	private int dieResult2;
	
	@ApiModelProperty(notes = "Result of the Game",name="gameResult",required=true,value="test result")
	@NotNull(message = "Game Result cannot be empty")
	@Column(name = "game_result")
	private String gameResult;
	
	/*//linking multiple tables, only on this object and not on player
	 * @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private User user; */

}
