package itacademy.s5t2.diceGame.businessLayer.domain;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import itacademy.s5t2.diceGame.constants.CommonConstants;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
	@MongoId
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Unique id of the Player", name="idPlayer")
	@Indexed(unique = true)
	private long idPlayer;
	
	/*
	//@Value("${spring.jackson.date-format}")
	
	private LocalDateTime registrationDate;*/
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Schema(description = "Player's registration date", name="registrationDate")
    private LocalDateTime registrationDate;
	
	@Schema(description = "Player name", name="playerName", example = "ANONYMOUS")
	@NotNull(message = "Player name cannot be empty")
	@Indexed(unique = true)
	private String playerName;
	
	@Schema(description = "Player success rate", name="successRate")
	@Indexed
	private double successRate;
	
	@Schema(description = "List of games a player has played", name="playerGames", example = "[]")
	//@Hidden
	@Indexed
	private List<DiceGame> playerGames;

	//@Builder.Default
	@Schema(description = "Player win/loss ratio", name="playerResultsWinLossMap", example = "{}")
	@Indexed
	private Map<String, Integer> playerResultsWinLossMap;
	
	/*@Autowired
	@Hidden
	private static Map<String, Integer> createPlayerMap;*/
	
	public void addGameToList(DiceGame game) {
		playerGames.add(game);
		playerResultsWinLossMap.put(game.getGameResult(), playerResultsWinLossMap.getOrDefault(game.getGameResult(), 0) + 1); ///here is where sucess rate might not be gettting correctly
		//if (playerResultsWinLossMap.get(game.getGameResult()) != 0) {
			successRate = calculateAverageSuccessRate();
		//}
	}
	/*
	 * words.computeIfPresent("hello", (k, v) -> v + 1);
System.out.println(words.get("hello"));

Alternatevely, you could use merge method, where 1 is the default value and function increments existing value by 1:

words.merge("hello", 1, Integer::sum);

In addition, there is a bunch of other useful methods, such as putIfAbsent, getOrDefault, forEach, etc.

 .merge is my solution with Integer::sum
 
   // update the value of Second
    // Using computeIfPresent()
    numbers.computeIfPresent("Second", (key, oldValue) -> oldValue * 2);
    System.out.println("HashMap with updated value: " + numbers);
    HashMap: {Second=2, First=1}
HashMap with updated value: {Second=4, First=1}
 // update the value of First
    // Using the merge() method
    numbers.merge("First", 4, (oldValue, newValue) -> oldValue + newValue);
    System.out.println("HashMap with updated value: " + numbers);
	 */
	
	public void deleteListOfGames() {
		successRate = 0.0;
		playerResultsWinLossMap.put(CommonConstants.WINS, 0);
		playerResultsWinLossMap.put(CommonConstants.LOSSES, 0);
		playerGames.clear();
	}
		
	public double calculateAverageSuccessRate() {
		double result = 0.0;
		if (playerResultsWinLossMap.get(CommonConstants.WINS) != null) {
			Math.round(playerResultsWinLossMap.get(CommonConstants.WINS) / playerGames.size() * 100);
		}
		return result;
	}
}
