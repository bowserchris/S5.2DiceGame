package itacademy.s5t2.diceGame.businessLayer.service.interfaces;

import java.util.List;

import itacademy.s5t2.diceGame.businessLayer.domain.DiceGame;
import itacademy.s5t2.diceGame.businessLayer.dto.DiceGameDTO;

public interface DiceGameServiceInter extends ServiceInterface {
	
	List<DiceGameDTO> getAllDiceGames();
	DiceGame saveDiceGame(DiceGame dg);
	DiceGame updateDiceGame(long id, DiceGameDTO dg);
	DiceGameDTO getById(long id);
	
	int getDieValue1(long id);
	int getDieValue2(long id);
	String getResult(long id);
	
}
