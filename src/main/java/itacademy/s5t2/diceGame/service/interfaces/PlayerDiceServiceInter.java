package itacademy.s5t2.diceGame.service.interfaces;

import java.util.List;

import itacademy.s5t2.diceGame.domain.DiceGame;
import itacademy.s5t2.diceGame.dto.DiceGameDTO;

public interface PlayerDiceServiceInter extends ServiceInterface {
	
	List<DiceGameDTO> getAllDiceGames();
	DiceGame saveDiceGame(DiceGame dg);
	DiceGame updateDiceGame(long id, DiceGameDTO dg);
	DiceGameDTO getById(long id);
	
	void deleteAllRolls();
	int getDieValue1(long id);
	int getDieValue2(long id);
	String getResult(long id);
	
}
