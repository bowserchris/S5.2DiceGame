package itacademy.s5t2.diceGame.service.interfaces;

import java.util.List;

import itacademy.s5t2.diceGame.domain.Player;
import itacademy.s5t2.diceGame.dto.PlayerDTO;

public interface SoftwarePlayerInter extends ServiceInterface {

	List<PlayerDTO> getAllPlayers();
	Player savePlayer(Player p);
	Player updatePlayer(long id, PlayerDTO p);
	PlayerDTO getById(long id);
	
	PlayerDTO getByName(String name);
	double getOneSuccessRateByName(String name);
	double getOneSuccessRateById(long id);
	
}
