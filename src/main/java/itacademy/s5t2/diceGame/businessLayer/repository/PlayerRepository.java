package itacademy.s5t2.diceGame.businessLayer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import itacademy.s5t2.diceGame.businessLayer.domain.Player;

import java.util.Date;

public interface PlayerRepository extends MongoRepository<Player, Long>{
	
	Optional<Player> findByName(String name);
	List<Player> findByRegistrationDate(Date registrationDate);

}
