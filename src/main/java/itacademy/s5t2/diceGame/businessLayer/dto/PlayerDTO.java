package itacademy.s5t2.diceGame.businessLayer.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.index.Indexed;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import itacademy.s5t2.diceGame.businessLayer.domain.DiceGame;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
	
}
