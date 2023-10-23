package itacademy.s5t2.diceGame.dto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import itacademy.s5t2.diceGame.domain.DiceGame;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Schema(description = "Player created to play the game.", example = "", required = true)
public class PlayerDTO {
	
	// for fields required?   @Schema(description = "This is the "fieldname" of the Player. If empty, the system will show Anonymous.", example = "Chris", required = false)
	private long idPlayer;
	private LocalDateTime registrationDate;
	private String playerName;
	private List<DiceGame> playerGames;
	private double successRate;
	private HashMap<String, Integer> playerResultsWinLossMap = createPlayerMap();
	private static String WINS = "Win";
	private static String LOSSES = "Lose";
	
	
	////are below meethods needed here in dto or in entity?
	
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
	
	// are below methods needed?///
	
	/*public int searchPlayerGamesList(int id) {
        int counter = 0;
        int index = -1;

        while (counter < playerGames.size()) {
            if (playerGames.get(counter).getIdGame() == id) {
                index = counter;
                counter = playerGames.size();
            }
            counter++;
        }
        return index;
    }
	
	public void printTotalResults() {
		resultsWinLossMap.forEach((key, value) -> System.out.println("The total amount of "
                + key + " are: " + value));
	}*/
	
	//playerList.forEach(System.out::println); in case method is needed

}
