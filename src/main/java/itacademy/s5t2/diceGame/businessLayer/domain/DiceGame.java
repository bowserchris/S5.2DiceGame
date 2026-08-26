package itacademy.s5t2.diceGame.businessLayer.domain;

import static itacademy.s5t2.diceGame.constants.CommonConstants.LOSSES;
import static itacademy.s5t2.diceGame.constants.CommonConstants.WINS;
import static itacademy.s5t2.diceGame.constants.CommonConstants.WIN_CONDITION;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Details of a Dice Game object")
@Entity(name = "DiceGame")
/*@JsonIgnoreProperties({
"hibernateLazyInitializer",
"handler"
})
 */
@Table(name = "games")
public class DiceGame {

	@Schema(description = "Unique id of the DiceGame", example = "1")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "game_id")
	private long gameId;

	@Transient //for mongodb
	public static final String SEQUENCE_NAME = "dice_sequence";

	@Schema(description = "Value of 1st Die",name="dieResult1")
	@NotNull(message = "Die value cannot be empty")
	@Column(name = "die_1_value", nullable = false)
	private int dieResult1;

	@Schema(description = "Value of 2nd Die",name="dieResult2")
	@NotNull(message = "Die value cannot be empty")
	@Column(name = "die_2_value", nullable = false)
	private int dieResult2;

	@Schema(description = "Result of the Game",name="gameResult")
	@NotNull(message = "Game Result cannot be empty")
	@Column(name = "game_result", nullable = false)
	private String gameResult;

	public DiceGame() {
	}

	public DiceGame(long gameId, @NotNull(message = "Die value cannot be empty") int dieResult1,
			@NotNull(message = "Die value cannot be empty") int dieResult2,
			@NotNull(message = "Game Result cannot be empty") String gameResult) {
		this.gameId = gameId;
		this.dieResult1 = dieResult1;
		this.dieResult2 = dieResult2;
		this.gameResult = gameResult;
	}

	public void playGame() {
		this.dieResult1 = Die.roll();
		this.dieResult2 = Die.roll();
		if ((this.dieResult1 + this.dieResult2) == WIN_CONDITION) {
			this.gameResult = WINS;
		} else {
			this.gameResult = LOSSES;
		}
	}

	public long getGameId() {
		return this.gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public int getDieResult1() {
		return this.dieResult1;
	}

	public void setDieResult1(int dieResult1) {
		this.dieResult1 = dieResult1;
	}

	public int getDieResult2() {
		return this.dieResult2;
	}

	public void setDieResult2(int dieResult2) {
		this.dieResult2 = dieResult2;
	}

	public String getGameResult() {
		return this.gameResult;
	}

	public void setGameResult(String gameResult) {
		this.gameResult = gameResult;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(this.dieResult1), Integer.valueOf(this.dieResult2),
				Long.valueOf(this.gameId), this.gameResult);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		DiceGame other = (DiceGame) obj;
		return this.dieResult1 == other.dieResult1 && this.dieResult2 == other.dieResult2 && this.gameId == other.gameId
				&& Objects.equals(this.gameResult, other.gameResult);
	}

	/*//linking multiple tables, only on this object and not on player
	 * @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private User user; */


}
