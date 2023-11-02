package itacademy.s5t2.diceGame.businessLayer.service.mapper;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.mongodb.Function;

import itacademy.s5t2.diceGame.businessLayer.domain.Player;
import itacademy.s5t2.diceGame.businessLayer.dto.PlayerDTO;
import itacademy.s5t2.diceGame.constants.CommonConstants;

@Service
@Component
public class PlayerDTOMapper implements Function<Player, PlayerDTO>{

	@Override
	public PlayerDTO apply(Player player) {
		PlayerDTO dto = new PlayerDTO();
		dto.setIdPlayer(player.getIdPlayer());
		dto.setRegistrationDate(player.getRegistrationDate());
		dto.setPlayerName(player.getPlayerName());
		dto.setSuccessRate(player.getSuccessRate());
		dto.setPlayerGames(player.getPlayerGames());
		dto.setPlayerResultsWinLossMap(CommonConstants.createPlayerMap());
		dto.setPlayerResultsWinLossMap(player.getPlayerResultsWinLossMap());
		return dto;
	}
	
	public Player applyToEntity(PlayerDTO dto) {
		Player player = new Player();
		player.setIdPlayer(dto.getIdPlayer());
		player.setRegistrationDate(dto.getRegistrationDate());
		player.setPlayerName(dto.getPlayerName());
		player.setSuccessRate(dto.getSuccessRate());
		player.setPlayerGames(dto.getPlayerGames());
		player.setPlayerResultsWinLossMap(CommonConstants.createPlayerMap());
		player.setPlayerResultsWinLossMap(dto.getPlayerResultsWinLossMap());
		return player;
	}

}
