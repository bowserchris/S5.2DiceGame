package itacademy.s5t2.diceGame.businessLayer.service.mapper;

import java.util.function.Function;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import itacademy.s5t2.diceGame.businessLayer.domain.DiceGame;
import itacademy.s5t2.diceGame.businessLayer.dto.DiceGameDTO;

/**
 * Mapper class for Dice Games to DiceGameDTOs
 * 
 * @author bowser-chris
 */
@Service
@Component
public class DiceGameDTOMapper implements Function<DiceGame, DiceGameDTO> {

	/**
	 * Maps a Dice Game to a DiceGameDTO
	 * 
	 * @param game a dice game
	 * @return dto the dice game as a DTO
	 */
	@Override
	public DiceGameDTO apply(DiceGame game) {
		DiceGameDTO dto = new DiceGameDTO();
		dto.setGameId(game.getGameId());
		dto.setDieResult1(game.getDieResult1());
		dto.setDieResult2(game.getDieResult2());
		dto.setGameResult(game.getGameResult());
		return dto;
	}

	/**
	 * Maps a DiceGameDTO to a Dice Game
	 * 
	 * @param dto the dto to map to a dice game
	 * @return game the game from the DTO
	 */
	public DiceGame applyToEntity(DiceGameDTO dto) {
		DiceGame game = new DiceGame();
		game.setGameId(dto.getGameId());
		game.setDieResult1(dto.getDieResult1());
		game.setDieResult2(dto.getDieResult2());
		game.setGameResult(dto.getGameResult());
		return game;
	}
}