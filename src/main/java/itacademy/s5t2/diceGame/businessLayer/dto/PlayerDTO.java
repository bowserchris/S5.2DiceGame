package itacademy.s5t2.diceGame.businessLayer.dto;

import static itacademy.s5t2.diceGame.constants.CommonConstants.PLAYER_NAME_EMPTY;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.DESCRIPTION_PLAYER_GAMES;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.DESCRIPTION_PLAYER_ID;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.DESCRIPTION_PLAYER_NAME;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.DESCRIPTION_PLAYER_RATIO;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.DESCRIPTION_PLAYER_REGISTRATION;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.DESCRIPTION_PLAYER_SUCCESS;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.EXAMPLE_PLAYER_GAMES;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.EXAMPLE_PLAYER_ID;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.EXAMPLE_PLAYER_NAME;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.EXAMPLE_PLAYER_RATIO;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.NAME_PLAYER_GAMES;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.NAME_PLAYER_ID;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.NAME_PLAYER_NAME;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.NAME_PLAYER_RATIO;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.NAME_PLAYER_REGISTRATION;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.NAME_PLAYER_SUCCESS;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.index.Indexed;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import itacademy.s5t2.diceGame.businessLayer.domain.DiceGame;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object of a Player
 * 
 * @author bowser-chris
 */
public class PlayerDTO {

	@Schema(description = DESCRIPTION_PLAYER_ID, name = NAME_PLAYER_ID, example = EXAMPLE_PLAYER_ID)
	@Indexed(unique = true)
	private long idPlayer;

	@Schema(description = DESCRIPTION_PLAYER_REGISTRATION, name = NAME_PLAYER_REGISTRATION)
	@Indexed
	private LocalDateTime registrationDate;

	@Schema(description = DESCRIPTION_PLAYER_NAME, name = NAME_PLAYER_NAME, example = EXAMPLE_PLAYER_NAME)
	@NotNull(message = PLAYER_NAME_EMPTY)
	@Indexed(unique = true)
	private String playerName;

	@Schema(description = DESCRIPTION_PLAYER_SUCCESS, name = NAME_PLAYER_SUCCESS)
	@Indexed
	private double successRate;

	@Schema(description = DESCRIPTION_PLAYER_RATIO, name = NAME_PLAYER_RATIO, example = EXAMPLE_PLAYER_RATIO)
	@Indexed
	private Map<String, Integer> playerResultsWinLossMap = createPlayerMap;

	@Schema(description = DESCRIPTION_PLAYER_GAMES, name = NAME_PLAYER_GAMES, example = EXAMPLE_PLAYER_GAMES)
	@Indexed
	private List<DiceGame> playerGames;

	@Autowired		//function bean from app configuration to inject the hashmap on creation of the player object.
	@Hidden
	private static Map<String, Integer> createPlayerMap;

	/**
	 * @param idPlayer
	 * @param registrationDate
	 * @param playerName
	 * @param successRate
	 * @param playerResultsWinLossMap
	 * @param playerGames
	 */
	public PlayerDTO(long idPlayer, LocalDateTime registrationDate,
			@NotNull(message = PLAYER_NAME_EMPTY) String playerName, double successRate,
			Map<String, Integer> playerResultsWinLossMap, List<DiceGame> playerGames) {
		this.idPlayer = idPlayer;
		this.registrationDate = registrationDate;
		this.playerName = playerName;
		this.successRate = successRate;
		this.playerResultsWinLossMap = playerResultsWinLossMap;
		this.playerGames = playerGames;
	}

	public PlayerDTO() {
	}

	public long getIdPlayer() {
		return this.idPlayer;
	}

	public void setIdPlayer(long idPlayer) {
		this.idPlayer = idPlayer;
	}

	public LocalDateTime getRegistrationDate() {
		return this.registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getPlayerName() {
		return this.playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public double getSuccessRate() {
		return this.successRate;
	}

	public void setSuccessRate(double successRate) {
		this.successRate = successRate;
	}

	public Map<String, Integer> getPlayerResultsWinLossMap() {
		return this.playerResultsWinLossMap;
	}

	public void setPlayerResultsWinLossMap(Map<String, Integer> playerResultsWinLossMap) {
		this.playerResultsWinLossMap = playerResultsWinLossMap;
	}

	public List<DiceGame> getPlayerGames() {
		return this.playerGames;
	}

	public void setPlayerGames(List<DiceGame> playerGames) {
		this.playerGames = playerGames;
	}

	public static Map<String, Integer> getCreatePlayerMap() {
		return createPlayerMap;
	}

	public static void setCreatePlayerMap(Map<String, Integer> createPlayerMap) {
		PlayerDTO.createPlayerMap = createPlayerMap;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Long.valueOf(this.idPlayer), this.playerGames, this.playerName,
				this.playerResultsWinLossMap, this.registrationDate, Double.valueOf(this.successRate));
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
		PlayerDTO other = (PlayerDTO) obj;
		return this.idPlayer == other.idPlayer && Objects.equals(this.playerGames, other.playerGames)
				&& Objects.equals(this.playerName, other.playerName)
				&& Objects.equals(this.playerResultsWinLossMap, other.playerResultsWinLossMap)
				&& Objects.equals(this.registrationDate, other.registrationDate)
				&& Double.doubleToLongBits(this.successRate) == Double.doubleToLongBits(other.successRate);
	}
}