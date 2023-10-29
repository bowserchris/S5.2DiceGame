package itacademy.s5t2.diceGame.businessLayer.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import itacademy.s5t2.diceGame.businessLayer.dto.PlayerDTO;
import jakarta.validation.constraints.NotBlank;
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
	@Schema(description = "Unique id of the Player", name="idPlayer")
	@NotBlank
	@Id
	private long idPlayer;
	
	@NotBlank
	@Schema(description = "Player's registration date", name="registrationDate")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime registrationDate;
	
	@Schema(description = "Player name", name="playerName")
	@NotBlank
	@Indexed(unique = true)
	private String playerName;
	
	@Schema(description = "List of games a player has played", name="playerGames")
	private List<DiceGame> playerGames;

	@Schema(description = "Player success rate", name="successRate")
	private double successRate;
	
	@Schema(description = "Player win/loss ratio", name="playerResultsWinLossMap")
	private HashMap<String, Integer> playerResultsWinLossMap ;
	
}
