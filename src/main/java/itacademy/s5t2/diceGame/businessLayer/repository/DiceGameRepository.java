package itacademy.s5t2.diceGame.businessLayer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import itacademy.s5t2.diceGame.businessLayer.domain.DiceGame;

@Repository
public interface DiceGameRepository extends JpaRepository<DiceGame, Long> {
	
	List<DiceGame> findByGameResult(String result);
	
}
