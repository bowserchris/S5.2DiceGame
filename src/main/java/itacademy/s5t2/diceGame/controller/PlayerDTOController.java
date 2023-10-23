package itacademy.s5t2.diceGame.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itacademy.s5t2.diceGame.domain.DiceGame;
import itacademy.s5t2.diceGame.service.PlayerDiceListServiceImpl;

@RestController
@RequestMapping("/players/{id}")
public class PlayerDTOController {
	
	@Autowired
	PlayerDiceListServiceImpl playerService;
	
	public PlayerDTOController(PlayerDiceListServiceImpl service) {
		super();
		this.playerService = service;
	}
	
	//@PathVariable long id		//are path variables needed?
	
//Post: /players/{id}/games/ - specific player roles the dice
	@PostMapping("/games/")
	public ResponseEntity<DiceGame> playGame() {
		playerService.playGame();
		return new ResponseEntity<DiceGame>(HttpStatus.OK);
	}

//Delete: /players/{id}/games - deletes all players rolls
	@DeleteMapping("/games")
	public ResponseEntity<DiceGame> deleteAllRoles() {
		playerService.deleteAllRolls();
		return new ResponseEntity<DiceGame>(HttpStatus.OK);
	}
	
	//Get: /players/{id}/games/ - returns list of games for 1 player
	@GetMapping("/games/")
	public ResponseEntity<List<DiceGame>> getAllGames(@PathVariable long id) {
		List<DiceGame> list = playerService.getAllDiceGames()
				.stream()
				.map(game -> playerService.(game))		//modelMapper.map(flower,FlowerDTO.class)
				.collect(Collectors.toList());
		return ResponseEntity.notFound().build(); //ResponseEntity<>(list, HttpStatus.FOUND);
	}
	/*
	 * 
	 */
	
}
