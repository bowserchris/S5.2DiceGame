package itacademy.s5t2.diceGame.businessLayer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import itacademy.s5t2.diceGame.businessLayer.domain.DiceGame;

public interface DiceGameRepository extends JpaRepository<DiceGame, Long> {
	
	List<DiceGame> findByGameResult(String result);
	
}
