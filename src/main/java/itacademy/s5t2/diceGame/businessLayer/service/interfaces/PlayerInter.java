package itacademy.s5t2.diceGame.businessLayer.service.interfaces;

import java.util.List;

import itacademy.s5t2.diceGame.businessLayer.domain.Player;
import itacademy.s5t2.diceGame.businessLayer.dto.PlayerDTO;

public interface PlayerInter extends ServiceInterface {

	List<PlayerDTO> getAllPlayers();
	Player savePlayer(Player p);
	Player updatePlayer(long id, PlayerDTO p);
	PlayerDTO getById(long id);
	
	PlayerDTO getByName(String name);
	double getOneSuccessRateByName(String name);
	double getOneSuccessRateById(long id);
	
}
