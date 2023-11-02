package itacademy.s5t2.diceGame.businessLayer.service.mapper;

import java.util.function.Function;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import itacademy.s5t2.diceGame.businessLayer.domain.DiceGame;
import itacademy.s5t2.diceGame.businessLayer.dto.DiceGameDTO;

@Service
@Component
public class DiceGameDTOMapper implements Function<DiceGame, DiceGameDTO> {

	@Override
	public DiceGameDTO apply(DiceGame game) {		
		DiceGameDTO dto = new DiceGameDTO();
		dto.setGameId(game.getGameId());
		dto.setDieResult1(game.getDieResult1());
		dto.setDieResult2(game.getDieResult2());
		dto.setGameResult(game.getGameResult());
		return dto;
	}
	
	public DiceGame applyToEntity(DiceGameDTO dto) {
		DiceGame game = new DiceGame();
		game.setGameId(dto.getGameId());
		game.setDieResult1(dto.getDieResult1());
		game.setDieResult2(dto.getDieResult2());
		game.setGameResult(dto.getGameResult());
		return game;
	}
}
