package itacademy.s5t2.diceGame.businessLayer.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;
import itacademy.s5t2.diceGame.constants.CommonConstants;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

	public void addGameToList(DiceGame game) {
		playerGames.add(game);
		playerResultsWinLossMap.put(game.getGameResult(), playerResultsWinLossMap.getOrDefault(game.getGameResult(), 0) + 1); ///here is where sucess rate might not be gettting correctly
		//if (playerResultsWinLossMap.get(game.getGameResult()) != 0) {
		successRate = CommonConstants.calculateAverageSuccessRate(playerResultsWinLossMap.get(CommonConstants.WINS),playerGames.size());
		//}
	}
	public void deleteListOfGames() {
		successRate = 0.0;
		playerResultsWinLossMap.put(CommonConstants.WINS, 0);
		playerResultsWinLossMap.put(CommonConstants.LOSSES, 0);
		playerGames.clear();
	}
	
}
