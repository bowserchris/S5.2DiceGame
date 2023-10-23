package itacademy.s5t2.diceGame.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import itacademy.s5t2.diceGame.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
        
	Optional<User> findByUsername(String username);
	
}
