package itacademy.s5t2.diceGame.businessLayer.dto;

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
import lombok.Builder;

public class PlayerDTO {

	@Schema(description = "Unique id of the Player", name="idPlayer", example = "1")
	@Indexed(unique = true)
	private long idPlayer;

	@Schema(description = "Player's registration date", name="registrationDate")
	@Indexed
	private LocalDateTime registrationDate;

	@Schema(description = "Player name", name="playerName", example = "ANONYMOUS")
	@NotNull(message = "Player name cannot be empty")
	@Indexed(unique = true)
	private String playerName;

	@Schema(description = "Player success rate", name="successRate")
	@Indexed
	private double successRate;

	@Builder.Default
	@Schema(description = "Player win/loss ratio", name="Win/Loss Ratio", example = "{}")
	@Indexed
	private Map<String, Integer> playerResultsWinLossMap = createPlayerMap;

	@Schema(description = "List of games a player has played", name="Game List", example = "[]")
	//@Hidden
	@Indexed
	private List<DiceGame> playerGames;

	@Autowired		//function bean from app configuration to inject the hashmap on creation of the player object.
	@Hidden
	private static Map<String, Integer> createPlayerMap;

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

	public PlayerDTO(long idPlayer, LocalDateTime registrationDate,
			@NotNull(message = "Player name cannot be empty") String playerName, double successRate,
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

	@Override
	public int hashCode() {
		return Objects.hash(Long.valueOf(this.idPlayer), this.playerGames, this.playerName,
				this.playerResultsWinLossMap, this.registrationDate, Double.valueOf(this.successRate));
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
		PlayerDTO other = (PlayerDTO) obj;
		return this.idPlayer == other.idPlayer && Objects.equals(this.playerGames, other.playerGames)
				&& Objects.equals(this.playerName, other.playerName)
				&& Objects.equals(this.playerResultsWinLossMap, other.playerResultsWinLossMap)
				&& Objects.equals(this.registrationDate, other.registrationDate)
				&& Double.doubleToLongBits(this.successRate) == Double.doubleToLongBits(other.successRate);
	}

}
