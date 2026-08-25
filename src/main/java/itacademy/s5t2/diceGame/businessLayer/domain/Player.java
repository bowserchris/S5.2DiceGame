package itacademy.s5t2.diceGame.businessLayer.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;
import itacademy.s5t2.diceGame.constants.CommonConstants;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Details of Player object")
@Document(collection = "players")
public class Player {	//implements userdetails and relevant fields methods here

	//private static final long serialVersionUID = 1L; with implements Serializable on class as well as in dto class
	//@MongoId
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Schema(description = "Unique id of the Player", name="idPlayer", example = "1")
	@Indexed(unique = true)
	private long idPlayer;

	@Transient
	public static final String SEQUENCE_NAME = "players_sequence";


	//@Value("${spring.jackson.date-format}")
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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

	//@Builder.Default
	@Schema(description = "Player win/loss ratio", name="Win/Loss Ratio", example = "{}")
	@Indexed
	private Map<String, Integer> playerResultsWinLossMap;

	@Schema(description = "List of games a player has played", name="Game List", example = "[]")
	//@Hidden
	@Indexed
	private List<DiceGame> playerGames;


	public Player() {
	}

	public Player(long idPlayer, LocalDateTime registrationDate,
			@NotNull(message = "Player name cannot be empty") String playerName, double successRate,
			Map<String, Integer> playerResultsWinLossMap, List<DiceGame> playerGames) {
		this.idPlayer = idPlayer;
		this.registrationDate = registrationDate;
		this.playerName = playerName;
		this.successRate = successRate;
		this.playerResultsWinLossMap = playerResultsWinLossMap;
		this.playerGames = playerGames;
	}
	public void addGameToList(DiceGame game) {
		this.playerGames.add(game);
		this.playerResultsWinLossMap.put(game.getGameResult(), this.playerResultsWinLossMap.getOrDefault(game.getGameResult(), 0) + 1); ///here is where sucess rate might not be gettting correctly
		//if (playerResultsWinLossMap.get(game.getGameResult()) != 0) {
		this.successRate = CommonConstants.calculateAverageSuccessRate(this.playerResultsWinLossMap.get(CommonConstants.WINS),this.playerGames.size());
		//}
	}
	public void deleteListOfGames() {
		this.successRate = 0.0;
		this.playerResultsWinLossMap.put(CommonConstants.WINS, 0);
		this.playerResultsWinLossMap.put(CommonConstants.LOSSES, 0);
		this.playerGames.clear();
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
		Player other = (Player) obj;
		return this.idPlayer == other.idPlayer && Objects.equals(this.playerGames, other.playerGames)
				&& Objects.equals(this.playerName, other.playerName)
				&& Objects.equals(this.playerResultsWinLossMap, other.playerResultsWinLossMap)
				&& Objects.equals(this.registrationDate, other.registrationDate)
				&& Double.doubleToLongBits(this.successRate) == Double.doubleToLongBits(other.successRate);
	}

}
