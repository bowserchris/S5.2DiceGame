package itacademy.s5t2.diceGame.service.mapper;

import com.mongodb.Function;

import itacademy.s5t2.diceGame.domain.Player;
import itacademy.s5t2.diceGame.dto.PlayerDTO;

public class PlayerDTOMapper implements Function<Player, PlayerDTO>{

	@Override
	public PlayerDTO apply(Player player) {
		PlayerDTO dto = new PlayerDTO();
		dto.setIdPlayer(player.getIdPlayer());
		dto.setRegistrationDate(player.getRegistrationDate());
		dto.setPlayerName(player.getPlayerName());
		dto.setPlayerGames(player.getPlayerGames());
		dto.setSuccessRate(player.getSuccessRate());
		dto.setPlayerResultsWinLossMap(player.getPlayerResultsWinLossMap());
		return dto;
	}
	
	public Player applyToEntity(PlayerDTO dto) {
		Player player = new Player();
		player.setIdPlayer(dto.getIdPlayer());
		player.setRegistrationDate(dto.getRegistrationDate());
		player.setPlayerName(dto.getPlayerName());
		player.setPlayerGames(dto.getPlayerGames());
		player.setSuccessRate(dto.getSuccessRate());
		player.setPlayerResultsWinLossMap(dto.getPlayerResultsWinLossMap());
		return null;
	}

}
