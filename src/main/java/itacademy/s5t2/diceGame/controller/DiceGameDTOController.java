package itacademy.s5t2.diceGame.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itacademy.s5t2.diceGame.domain.DiceGame;
import itacademy.s5t2.diceGame.dto.DiceGameDTO;
import itacademy.s5t2.diceGame.service.DiceGameServiceImpl;

@RestController
@RequestMapping("/players/{id}")
@Validated
public class DiceGameDTOController {
	
	@Autowired
	DiceGameServiceImpl diceService;
	
	public DiceGameDTOController(DiceGameServiceImpl service) {
		super();
		this.diceService = service;
	}
	
	//@PathVariable long id		//are path variables needed?
	
//Post: /players/{id}/games/ - specific player roles the dice
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@PostMapping(value = "/games/", headers = "Accept=application/json")
	public ResponseEntity<DiceGame> playGame() {
		diceService.playGame();
		return new ResponseEntity<DiceGame>(HttpStatus.OK);
	}

//Delete: /players/{id}/games - deletes all players rolls
	@PreAuthorize("hasAuthority('ROLE_MANAGER')")
	@DeleteMapping(value = "/games", headers = "Accept=application/json")
	//ResponseEntity<String> deleteAllRolls(@PathVariable("id") @Min(1) int id)
	// repo.findbyid(id).orElseThrow(() -> new EmployeeNotFoundException("Employee Not Found with ID :" + id));
	// return ResponseEntity.ok().body("Employee deleted with success!");
	public ResponseEntity<DiceGame> deleteAllRoles() {
		diceService.deleteAllRolls();
		return new ResponseEntity<DiceGame> (HttpStatus.NO_CONTENT);
		//return new ResponseEntity<DiceGame>(HttpStatus.OK);
	}
	
	//Get: /players/{id}/games/ - returns list of games for 1 player
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@GetMapping(value = "/games/", headers = "Accept=application/json")
	public ResponseEntity<List<DiceGameDTO>> getAllGames(@PathVariable long id) {
		List<DiceGameDTO> list = diceService.getAllDiceGames();
		list
		.stream()
		.map(game -> diceService.saveDiceGame(diceService.getDTOMapper().applyToEntity(game)))		//modelMapper.map(flower,FlowerDTO.class)
		.collect(Collectors.toList());
		return ResponseEntity.notFound().build(); //ResponseEntity<>(list, HttpStatus.FOUND);
	}

	/*
	 * 
	 */
	
}
