package itacademy.s5t2.diceGame.controller;

import java.lang.invoke.CallSite;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T01N02Flor2.model.dto.FlowerDTO;
import itacademy.s5t2.diceGame.domain.Player;
import itacademy.s5t2.diceGame.dto.PlayerDTO;
import itacademy.s5t2.diceGame.service.DiceSoftwareServiceImpl;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/")
public class DiceSoftwareDTOController {
	
	@Autowired
	DiceSoftwareServiceImpl softwareService;
	
	public DiceSoftwareDTOController(DiceSoftwareServiceImpl service) {
		super();
		this.softwareService = service;
	}
	
	//Post: /players - creates a player
	@PostMapping("players")
	public ResponseEntity<Player> addPlayer(@RequestBody Player player) {
		try {
			Player newPlayer = (Player) softwareService.savePlayer(player);
			return new ResponseEntity<>(newPlayer , HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//Put: /players - updates player name
	@PutMapping("players")
	public ResponseEntity<PlayerDTO> updatePlayer(@RequestBody Player player) {
		PlayerDTO playerRequest = softwareService.getByName(player.getPlayerName());
		Player newPlayer = null;
		try {
			newPlayer = (Player) softwareService.updatePlayer(player.getIdPlayer(), playerRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (newPlayer != null) {
			PlayerDTO playerResponseDto = softwareService.getDTOMapper().apply(newPlayer);
			return ResponseEntity.ok().body(playerResponseDto);
		}
		return ResponseEntity.notFound().build();
	}
	
//Get: /players/ - returns all players with average success rate
	@GetMapping("players/")
	public ResponseEntity<List<PlayerDTO>> getAllPlayersAndSuccessRate(@RequestParam(required = false) String name) {
		List<PlayerDTO> list = softwareService.getAllPlayers()
				.stream()
				.map(player -> softwareService.savePlayer(player))		//modelMapper.map(flower,FlowerDTO.class)
				.collect(Collectors.toList());
		return ResponseEntity.notFound().build(); //ResponseEntity<>(list, HttpStatus.FOUND);
	}
	
//Get: /players/ranking - returns the average ranking of all players in the system. That is, the average percentage of successes
	@GetMapping("players/ranking")
	public ResponseEntity<Player> getTotalAverageSuccessRate() {
		List<PlayerDTO> list = softwareService.getAllPlayers();
		double totalWins = 0;
		double totalGames = 0;
		for (int i = 0; i < list.size(); i++) {
			totalWins += (list.get(i).getSuccessRate()/100) * list.get(i).getPlayerGames().size();
			totalGames += list.get(i).getPlayerGames().size();
		}
		Math.round((totalWins /totalGames) * 100);
		return ResponseEntity.notFound().build();
	}
	
//Get: /players/ranking/loser - return player with worst success rate
	@GetMapping("players/ranking/loser")
	public ResponseEntity<Player> getWorstPlayerSuccessRate() {
		List<PlayerDTO> list = softwareService.getAllPlayers();
		PlayerDTO worstPlayer;
		worstPlayer.setSuccessRate(101);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getSuccessRate() < worstPlayer.getSuccessRate()) {
				worstPlayer = list.get(i);
			}
		}
		return ResponseEntity.notFound().build();
	}
	
//Get: /players/ranking/winner - return player with best success rate
	@GetMapping("players/ranking/winner")
	public ResponseEntity<Player> getBestPlayerSuccessRate() {
		List<PlayerDTO> list = softwareService.getAllPlayers();
		PlayerDTO bestPlayer;
		bestPlayer.setSuccessRate(0);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getSuccessRate() > bestPlayer.getSuccessRate()) {
				bestPlayer = list.get(i);
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/players/{id}")
	public ResponseEntity<PlayerDTO> getOnePlayerById(@PathVariable long id) {
		PlayerDTO player = softwareService.getById(id);
		return ResponseEntity.ok().body(player);
	}
	
	/* belowe methods are for player controller
//Post: /players/{id}/games/ - specific player roles the dice
//Delete: /players/{id}/games - deletes all players rolls
//Get: /players/{id}/games/ - returns list of games for 1 player
	 */

}
