package itacademy.s5t2.diceGame.businessLayer.domain;

import static itacademy.s5t2.diceGame.constants.CommonConstants.DIE_VALUE_EMPTY;
import static itacademy.s5t2.diceGame.constants.CommonConstants.GAME_RESULT_EMPTY;
import static itacademy.s5t2.diceGame.constants.CommonConstants.LOSSES;
import static itacademy.s5t2.diceGame.constants.CommonConstants.WINS;
import static itacademy.s5t2.diceGame.constants.CommonConstants.WIN_CONDITION;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.DESCRIPTION_CLASS_DICEGAME;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.DESCRIPTION_GAME_ID;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.DESCRIPTION_RESULT_FIRST;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.DESCRIPTION_RESULT_GAME;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.DESCRIPTION_RESULT_SECOND;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.EXAMPLE_GAME_ID;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.NAME_RESULT_FIRST;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.NAME_RESULT_SECOND;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.NAME_REUSLT_GAME;

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

/**
 * Entity that represents an individual dice game
 * 
 * @author bowser-chris
 */
@Schema(description = DESCRIPTION_CLASS_DICEGAME)
@Entity(name = "DiceGame")
@Table(name = "games")
public class DiceGame {

	@Schema(description = DESCRIPTION_GAME_ID, example = EXAMPLE_GAME_ID)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "game_id")
	private long gameId;

	@Transient //for mongodb
	public static final String SEQUENCE_NAME = "dice_sequence";

	@Schema(description = DESCRIPTION_RESULT_FIRST, name = NAME_RESULT_FIRST)
	@NotNull(message = DIE_VALUE_EMPTY)
	@Column(name = "die_1_value", nullable = false)
	private int dieResult1;

	@Schema(description = DESCRIPTION_RESULT_SECOND, name = NAME_RESULT_SECOND)
	@NotNull(message = DIE_VALUE_EMPTY)
	@Column(name = "die_2_value", nullable = false)
	private int dieResult2;

	@Schema(description = DESCRIPTION_RESULT_GAME, name = NAME_REUSLT_GAME)
	@NotNull(message = GAME_RESULT_EMPTY)
	@Column(name = "game_result", nullable = false)
	private String gameResult;

	public DiceGame() {
	}

	/**
	 * @param gameId
	 * @param dieResult1
	 * @param dieResult2
	 * @param gameResult
	 */
	public DiceGame(long gameId, @NotNull(message = DIE_VALUE_EMPTY) int dieResult1,
			@NotNull(message = DIE_VALUE_EMPTY) int dieResult2,
			@NotNull(message = GAME_RESULT_EMPTY) String gameResult) {
		this.gameId = gameId;
		this.dieResult1 = dieResult1;
		this.dieResult2 = dieResult2;
		this.gameResult = gameResult;
	}

	/**
	 * Play the Game!
	 */
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
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		DiceGame other = (DiceGame) obj;
		return this.dieResult1 == other.dieResult1 && this.dieResult2 == other.dieResult2 && this.gameId == other.gameId
				&& Objects.equals(this.gameResult, other.gameResult);
	}
}