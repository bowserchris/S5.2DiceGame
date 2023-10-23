package itacademy.s5t2.diceGame.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.util.UriComponentsBuilder;

import itacademy.s5t2.diceGame.domain.Player;
import itacademy.s5t2.diceGame.dto.PlayerDTO;
import itacademy.s5t2.diceGame.service.PlayerServiceImpl;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/")
public class PlayerDTOController {
	
	//private static Logger log = LoggerFactory.getLogger(PlayerController.class);
	
	@Autowired
	PlayerServiceImpl playerService;
	
	public PlayerDTOController(PlayerServiceImpl service) {
		super();
		this.playerService = service;
	}
	
	//Post: /players - creates a player
	@PostMapping(value = "players", headers = "Accept=application/json")
	public ResponseEntity<Player> addPlayer(@RequestBody Player player, UriComponentsBuilder ucBuilder) {
		try {
			if (!playerService.checkIfPlayerNameExists(player)) {
				Player newPlayer = playerService.savePlayer(player);
				HttpHeaders headers = new HttpHeaders();
				headers.setLocation(ucBuilder.path("/players/{id}").buildAndExpand(newPlayer.getIdPlayer()).toUri());
				return new ResponseEntity<>(headers , HttpStatus.CREATED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;
				//new ResponseEntity<Void> (null, HttpStatus.CREATED);
		//return new ResponseEntity<>(null , HttpStatus.CREATED);
	}
	
	//Put: /players - updates player name
	@PutMapping(value = "players", headers = "Accept=application/json")
	public ResponseEntity<PlayerDTO> updatePlayer(@RequestBody Player player) {
		PlayerDTO playerRequest = playerService.getByName(player.getPlayerName());
		Player newPlayer = null;
		try {
			newPlayer = playerService.updatePlayer(player.getIdPlayer(), playerRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (newPlayer != null) {
			PlayerDTO playerResponseDto = playerService.getDTOMapper().apply(newPlayer);
			return ResponseEntity.ok().body(playerResponseDto);
		}
		return ResponseEntity.notFound().build();
	}

	//Get: /players/ - returns all players with average success rate
	@GetMapping(value = "players/", headers = "Accept=application/json")
	public ResponseEntity<List<PlayerDTO>> getAllPlayersAndSuccessRate(@RequestParam(required = false) String name) {
		List<PlayerDTO> list = playerService.getAllPlayers();
		list
		.stream()
		.map(player -> playerService.savePlayer(playerService.getDTOMapper().applyToEntity(player)))		//modelMapper.map(flower,FlowerDTO.class)
		.collect(Collectors.toList());
		return ResponseEntity.notFound().build(); //ResponseEntity<>(list, HttpStatus.FOUND);
	}

	//Get: /players/ranking - returns the average ranking of all players in the system. That is, the average percentage of successes
	@GetMapping("players/ranking")
	public ResponseEntity<Player> getTotalAverageSuccessRate() {
		double totalRate = playerService.calculateAverageSuccessRate();
		return ResponseEntity.notFound().build();
	}

	//Get: /players/ranking/loser - return player with worst success rate
	@GetMapping("players/ranking/loser")
	public ResponseEntity<Player> getWorstPlayerSuccessRate() {
		Player player = playerService.getWorstSuccessRate();
		return ResponseEntity.notFound().build();
	}

	//Get: /players/ranking/winner - return player with best success rate
	@GetMapping("players/ranking/winner")
	public ResponseEntity<Player> getBestPlayerSuccessRate() {
		Player player = playerService.getBestSuccessRate();
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(value = "/players/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PlayerDTO> getOnePlayerById(@PathVariable("id") long id) {
		PlayerDTO player = playerService.getById(id);
		if (player == null) {
			return new ResponseEntity<PlayerDTO> (HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<PlayerDTO> (player, HttpStatus.OK);
		//return ResponseEntity.ok().body(player);
	}
	
}
