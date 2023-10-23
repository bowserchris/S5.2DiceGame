package itacademy.s5t2.diceGame.service.mapper;

import java.util.function.Function;

import itacademy.s5t2.diceGame.domain.DiceGame;
import itacademy.s5t2.diceGame.dto.DiceGameDTO;

public class DiceGameDTOMapper implements Function<DiceGame, DiceGameDTO> {

	@Override
	public DiceGameDTO apply(DiceGame game) {		
		DiceGameDTO dto = new DiceGameDTO();
		dto.setIdGame(game.getIdGame());
		dto.setDieResult1(game.getDieResult1());
		dto.setDieResult2(game.getDieResult2());
		dto.setGameResult(game.getGameResult());
		return dto;
	}
	
	public DiceGame applyToEntity(DiceGameDTO dto) {
		DiceGame game = new DiceGame();
		game.setIdGame(dto.getIdGame());
		game.setDieResult1(dto.getDieResult1());
		game.setDieResult2(dto.getDieResult2());
		game.setGameResult(dto.getGameResult());
		return game;
	}
}
