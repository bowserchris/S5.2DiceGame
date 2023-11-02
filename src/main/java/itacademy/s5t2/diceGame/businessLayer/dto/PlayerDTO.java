package itacademy.s5t2.diceGame.businessLayer.dto;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
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
	//private HashMap<String, Integer> playerResultsWinLossMap;
	
	@Builder.Default
	@Schema(description = "Player win/loss ratio", name="playerResultsWinLossMap")
	private Map<String, Integer> playerResultsWinLossMap = createPlayerMap;
	
	@Autowired
	@Hidden
	private static Map<String, Integer> createPlayerMap;
	
}
