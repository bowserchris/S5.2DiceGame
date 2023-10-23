package itacademy.s5t2.diceGame.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import itacademy.s5t2.diceGame.domain.DiceGame;

public interface DiceGameRepository extends JpaRepository<DiceGame, Long> {
	
	List<DiceGame> findByGameResult(String result);
	
}
