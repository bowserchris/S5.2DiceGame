package itacademy.s5t2.diceGame.businessLayer.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Document(collection = "players")
public class Player {	//implements userdetails and relevant fields methods here
	
	//private static final long serialVersionUID = 1L; with implements Serializable on class as well as in dto class
	@NotBlank
	@Id
	private long idPlayer;
	
	@NotBlank
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime registrationDate;
	
	/*@Enumerated(EnumType.STRING)
    private Role role;
	 * 
	 */
	
	@NotBlank
	private String playerName;
	private List<DiceGame> playerGames;
	private double successRate;
	private HashMap<String, Integer> playerResultsWinLossMap = createPlayerMap();
	private static String WINS = "Win";
	private static String LOSSES = "Lose";
	
	private HashMap<String, Integer> createPlayerMap() {
		HashMap<String, Integer> map = new HashMap<>();
		map.put(WINS, 0);
		map.put(LOSSES, 0);
		return map;
	}
	
	public void addGameToList(DiceGame game) {
		playerGames.add(game);
		playerResultsWinLossMap.put(game.getGameResult(), (int)playerResultsWinLossMap.get(game.getGameResult()) + 1);
		successRate = calculateAverageSuccessRate();
	}
	
	public void deleteListOfGames() {
		successRate = 0.0;
		playerResultsWinLossMap.clear();
		playerGames.clear();
	}
		
	public double calculateAverageSuccessRate() {
		return Math.round(playerResultsWinLossMap.get(WINS) / playerGames.size() * 100);
	}
	
}
