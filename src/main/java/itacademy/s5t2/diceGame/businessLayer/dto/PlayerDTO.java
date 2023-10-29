package itacademy.s5t2.diceGame.businessLayer.dto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import itacademy.s5t2.diceGame.businessLayer.domain.DiceGame;
import itacademy.s5t2.diceGame.constants.CommonConstants;
import itacademy.s5t2.diceGame.securityLayer.dto.RegisterUserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDTO {
	
	private long idPlayer;
	private LocalDateTime registrationDate;
	private String playerName;
	private double successRate;
	private List<DiceGame> playerGames;
	private HashMap<String, Integer> playerResultsWinLossMap = createPlayerMap();	
	
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
		return Math.round(playerResultsWinLossMap.get(CommonConstants.WINS) / playerGames.size() * 100);
	}
	
	private HashMap<String, Integer> createPlayerMap() {
		HashMap<String, Integer> map = new HashMap<>();
		map.put(CommonConstants.WINS, 0);
		map.put(CommonConstants.LOSSES, 0);
		return map;
	}
	
}
