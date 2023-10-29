package itacademy.s5t2.diceGame.businessLayer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import itacademy.s5t2.diceGame.businessLayer.domain.Player;

import java.util.Date;

@Repository
public interface PlayerRepository extends MongoRepository<Player, Long>{
	
	Optional<Player> findByPlayerName(String name);
	List<Player> findByRegistrationDate(Date registrationDate);

}
