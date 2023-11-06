package itacademy.s5t2.diceGame.businessLayer.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import itacademy.s5t2.diceGame.businessLayer.domain.DiceGame;
import itacademy.s5t2.diceGame.businessLayer.domain.Player;
import itacademy.s5t2.diceGame.businessLayer.dto.PlayerDTO;
import itacademy.s5t2.diceGame.businessLayer.service.DiceGameServiceImpl;
import itacademy.s5t2.diceGame.businessLayer.service.PlayerServiceImpl;
import itacademy.s5t2.diceGame.constants.CommonConstants;
import itacademy.s5t2.diceGame.securityLayer.service.AuthenticationService;

//@CrossOrigin(origins = CommonConstants.ORIGIN, allowCredentials = "true")
//@Validated
@RestController
@CrossOrigin(origins ="http://localhost:27017")
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping(CommonConstants.INDEX)
@Tag(name = "Player controller options", 
			description = "This controller contains the methods to play the game")
public class PlayerDTOController {
	
	//private static Logger log = LoggerFactory.getLogger(PlayerController.class);
	
	@Autowired
	PlayerServiceImpl playerService;

	@Autowired
	DiceGameServiceImpl diceService;

	@Autowired
	AuthenticationService authService;
			
	public PlayerDTOController(PlayerServiceImpl ps, DiceGameServiceImpl dgs,AuthenticationService authService) {
		super();
		this.playerService = ps;
		this.diceService = dgs;
		this.authService = authService;
	}

	
			//Post: /players - creates a player
	@Operation(summary= "Add a new Player", 
			description = "Checks if name isn´t already taken, then creates new player in the database")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = CommonConstants.CODE_200, description = CommonConstants.PLAYER_CREATED, content = { 
					@Content(mediaType = CommonConstants.MEDIA_TYPE_JSON, schema = @Schema(implementation = Player.class))
			}),
			@ApiResponse(responseCode = CommonConstants.CODE_403, description = CommonConstants.PLAYER_EXISTS, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_404, description = CommonConstants.PLAYER_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_500, description = CommonConstants.INTERNAL_SERVER_ERR, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_1001, description = CommonConstants.APPLICATION_ERROR, content = @Content)
	})
	@PostMapping(value = CommonConstants.SAVE_PLAYER, headers = CommonConstants.HEADER_TYPE_OBJECT) 		//
	public ResponseEntity<?> addPlayer(		//is generic response entity safe?
			@Parameter(description = "Player details needed to create Player object", required = true)
			@RequestBody Player player, UriComponentsBuilder ucBuilder) 
	{
		Player newPlayer = null;
		try {
			if (!playerService.checkIfPlayerNameExists(player)) {
				newPlayer = playerService.savePlayer(player);
				HttpHeaders headers = new HttpHeaders();
				headers.setLocation(ucBuilder.path(CommonConstants.PLAYER_ID_PATH).buildAndExpand(newPlayer.getIdPlayer()).toUri());
			} else {
				return new ResponseEntity<>(CommonConstants.PLAYER_EXISTS, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (ResponseStatusException rse) {
			return new ResponseEntity<>(CommonConstants.APPLICATION_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		} //ResponseStatusException is implemented here with a return of return new ResponseEntity<Map<String,Object>>(error, HttpStatus.NOT_FOUND);
		return ResponseEntity.ok(newPlayer);
	}
			
			/* this method was used within creating a new player. 
			 * URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedPlayer.getId())
			.toUri();
			return ResponseEntity.created(location).build();*/
			
			
	@Operation(summary= "Returns player by id", 
			description = "Finds and returns player by their id")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = CommonConstants.CODE_200, description = CommonConstants.SUCCESSFUL, content = { 
					@Content(mediaType = CommonConstants.MEDIA_TYPE_JSON, schema = @Schema(implementation = PlayerDTO.class))
			}),//implementation might be a double here
			@ApiResponse(responseCode = CommonConstants.CODE_404, description = CommonConstants.PLAYER_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_500, description = CommonConstants.INTERNAL_SERVER_ERR, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_1001, description = CommonConstants.APPLICATION_ERROR, content = @Content)
	})
	@GetMapping(value = CommonConstants.PLAYER_ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getOnePlayerById(@Parameter(description = "Player id needed to return player object", required = true)
	@PathVariable("id") long id) {
		PlayerDTO player = playerService.getById(id);
		//.orElseThrow(() -> new PlayerNotFoundException(Player with ID :" + id)); custom exception made
		if (player == null) {
			return new ResponseEntity<> (CommonConstants.PLAYER_NOT_FOUND,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<PlayerDTO> (player, HttpStatus.OK);
	} //ResponseStatusException is implemented here with a return of return new ResponseEntity<Map<String,Object>>(error, HttpStatus.NOT_FOUND);
			
			
	//Post: /players/{id}/games/ - specific player roles the dice
	@Operation(summary= "ROLL OR DIE!", 
			description = "Selected player rolls a die, then results are saved in DB")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = CommonConstants.CODE_200, description = CommonConstants.GAME_CREATED, content = { 
					@Content(mediaType = CommonConstants.MEDIA_TYPE_JSON, schema = @Schema(implementation = DiceGame.class))
			}),//implementation might be a double here
			@ApiResponse(responseCode = CommonConstants.CODE_403, description = CommonConstants.USER_UNAUTHENTICATED, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_404, description = CommonConstants.PLAYER_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_500, description = CommonConstants.INTERNAL_SERVER_ERR, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_1001, description = CommonConstants.APPLICATION_ERROR, content = @Content)
	})
	@PostMapping(value = CommonConstants.GAMES_ALL_OR_PLAY, headers = CommonConstants.HEADER_TYPE_OBJECT)
	public ResponseEntity<?> playGame(@Parameter(description = "Id of Player needed in order to play the game", 
			required = true)
			@PathVariable("id") long playerId) {
		DiceGame game = diceService.playGame();
		if ((game.getDieResult1() + game.getDieResult2()) == 2) {
			System.out.println(CommonConstants.SNAKE_EYES);
		}
		playerService.addGameToPlayerList(game, playerId);
		diceService.saveDiceGame(game);
		return ResponseEntity.ok(game);
	} //ResponseStatusException is implemented here with a return of return new ResponseEntity<Map<String,Object>>(error, HttpStatus.NOT_FOUND);
			
		
	//Delete: /players/{id}/games - deletes all players rolls
	@Operation(summary= "Delete all game rolls", 
			description = "Selected player deletes their game history")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = CommonConstants.CODE_200, description = CommonConstants.GAME_DELETED, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_403, description = CommonConstants.USER_UNAUTHENTICATED, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_404, description = CommonConstants.GAME_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_500, description = CommonConstants.INTERNAL_SERVER_ERR, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_1001, description = CommonConstants.APPLICATION_ERROR, content = @Content)
	})
	@DeleteMapping(value = CommonConstants.GAMES_DELETE, headers = CommonConstants.HEADER_TYPE_OBJECT)
	public ResponseEntity<?> deleteAllRolls(@PathVariable long id) {
		String resultString = CommonConstants.NO_GAME_DELETE;
		if (playerService.deletePlayerGames(id)) {
			resultString = CommonConstants.GAME_DELETED;
		}
		return ResponseEntity.ok(resultString);
	} //ResponseStatusException is implemented here with a return of return new ResponseEntity<Map<String,Object>>(error, HttpStatus.NOT_FOUND);
			
		
	//Get: /players/{id}/games/ - returns list of games for 1 player
	@Operation(summary= "Returns list of games", 
			description = "Returns the game history of the selected player")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = CommonConstants.CODE_200, description = CommonConstants.LIST_RETURNED, content = { 
					@Content(mediaType = CommonConstants.MEDIA_TYPE_JSON, schema = @Schema(implementation = DiceGame.class))
			}),//implementation might be a double here
			@ApiResponse(responseCode = CommonConstants.CODE_403, description = CommonConstants.USER_UNAUTHENTICATED, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_404, description = CommonConstants.PLAYER_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_500, description = CommonConstants.INTERNAL_SERVER_ERR, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_1001, description = CommonConstants.APPLICATION_ERROR, content = @Content)
	})
	@GetMapping(value = CommonConstants.GAMES_ALL_OR_PLAY, headers = CommonConstants.HEADER_TYPE_OBJECT)
	public ResponseEntity<?> getAllGames(@Parameter(description = "Id of Player needed in order to retrieve their games", 
	required = true)
	@PathVariable long id) {
		List<DiceGame> list = playerService.getById(id).getPlayerGames();
		list
		.stream()
		.map(game -> diceService.mapToDiceGameDTO(game))
		.collect(Collectors.toList());
		if (list.size() == 0) {
			return ResponseEntity.ok(CommonConstants.LIST_IS_EMPTY);
		}
		return ResponseEntity.ok(list); 
	} //ResponseStatusException is implemented here with a return of return new ResponseEntity<Map<String,Object>>(error, HttpStatus.NOT_FOUND);
			
			
	//Get: /players/ - returns all players with average success rate
	@Operation(summary= "Gets a list of players", 
			description = "Gets a list of all players and their respective success rate")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = CommonConstants.CODE_200, description = CommonConstants.LIST_RETURNED, 
					content = { @Content(mediaType = CommonConstants.MEDIA_TYPE_JSON, 
					schema = @Schema(implementation = PlayerDTO.class))}),
			@ApiResponse(responseCode = CommonConstants.CODE_404, description = CommonConstants.PLAYER_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_500, description = CommonConstants.INTERNAL_SERVER_ERR, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_1001, description = CommonConstants.APPLICATION_ERROR, content = @Content)
	})
	@GetMapping(value = CommonConstants.GET_ALL_PLAYERS) //headers = CommonConstants.HEADER_TYPE_OBJECT
	public ResponseEntity<?> getAllPlayersAndSuccessRate(@Parameter(description = "Player name as an option, "
			+ "in case need to search specific player", 
			required = false)
	@RequestParam(required = false) String name) {
		List<PlayerDTO> list = playerService.getAllPlayers();
		//Map<String, Double> map;
		if (!list.isEmpty()) {
			//map = new HashMap<>();
			//list.forEach(k -> map.put("Player id: ", String.valueOf(k.getIdPlayer())), map.put(name, name), map.put(name, name));
			//list.forEach(k -> map.put(k.getPlayerName(), k.getSuccessRate()));
		} else {
			//map = new HashMap<>();
			//map.put("The amount of current players is at: ", 0.0);
			List<String> emptyList = new ArrayList<>();
			emptyList.add(CommonConstants.EMPTY_PLAYER_DB);
			return ResponseEntity.ok(emptyList);
			
		}
		return ResponseEntity.ok(list); 
	}
			
			
	//Get: /players/ranking - returns the average ranking of all players in the system. That is, the average percentage of successes
	@Operation(summary= "Returns average ranking of all players", 
			description = "Returns an average success rate of all players in the DB")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = CommonConstants.CODE_200, description = CommonConstants.SUCCESSFUL, content = { 
					@Content(mediaType = CommonConstants.MEDIA_TYPE_JSON, schema = @Schema(implementation = Double.class))
			}),//implementation might be a double here
			@ApiResponse(responseCode = CommonConstants.CODE_404, description = CommonConstants.PLAYER_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_500, description = CommonConstants.INTERNAL_SERVER_ERR, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_1001, description = CommonConstants.APPLICATION_ERROR, content = @Content)
	})
	@GetMapping(CommonConstants.RANKINGS)
	public ResponseEntity<?> getTotalAverageSuccessRate() {
		if (playerService.getAllPlayers().size() == 0) {
			return ResponseEntity.ok(CommonConstants.EMPTY_PLAYER_DB);
		} else {
			return ResponseEntity.ok(playerService.calculateAverageSuccessRate());
		}
	}
			
			
	//Get: /players/ranking/loser - return player with worst success rate
	@Operation(summary= "Returns worst ranking", 
			description = "Returns the player with the worst sucess rate")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = CommonConstants.CODE_200, description = CommonConstants.SUCCESSFUL, content = { 
					@Content(mediaType = CommonConstants.MEDIA_TYPE_JSON, schema = @Schema(implementation = Player.class))
			}),//implementation might be a double here
			@ApiResponse(responseCode = CommonConstants.CODE_204, description = CommonConstants.LIST_IS_EMPTY, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_404, description = CommonConstants.PLAYER_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_500, description = CommonConstants.INTERNAL_SERVER_ERR, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_1001, description = CommonConstants.APPLICATION_ERROR, content = @Content)
	})
	@GetMapping(CommonConstants.RANKINGS_LOSER)
	public ResponseEntity<?> getWorstPlayerSuccessRate() {
		if (playerService.getAllPlayers().size() == 0) {
			return ResponseEntity.ok(CommonConstants.EMPTY_PLAYER_DB);
		} else {
			return ResponseEntity.ok(playerService.getWorstSuccessRate());
		}
	}		//has try catch around search with responsestatusexception
			
			
	//Get: /players/ranking/winner - return player with best success rate
	@Operation(summary= "Returns best ranking", 
			description = "Returns the player with the best sucess rate")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = CommonConstants.CODE_200, description = CommonConstants.SUCCESSFUL, content = { 
					@Content(mediaType = CommonConstants.MEDIA_TYPE_JSON, schema = @Schema(implementation = Player.class))
			}),//implementation might be a double here
			@ApiResponse(responseCode = CommonConstants.CODE_204, description = CommonConstants.LIST_IS_EMPTY, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_404, description = CommonConstants.PLAYER_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_500, description = CommonConstants.INTERNAL_SERVER_ERR, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_1001, description = CommonConstants.APPLICATION_ERROR, content = @Content)
	})
	@GetMapping(CommonConstants.RANKINGS_WINNER)
	public ResponseEntity<?> getBestPlayerSuccessRate() {
		if (playerService.getAllPlayers().size() == 0) {
			return ResponseEntity.ok(CommonConstants.EMPTY_PLAYER_DB);
		} else {
			return ResponseEntity.ok(playerService.getBestSuccessRate());
		}
	}		//has try catch around search with responsestatusexception
			
			
	//Put: /players - updates player name
	@Operation(summary= "Update the Player", 
			description = "Finds player by name and updates them in the DB")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = CommonConstants.CODE_200, description = CommonConstants.PLAYER_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_201, description = CommonConstants.PLAYER_UPDATED, content = { 
					@Content(mediaType = CommonConstants.MEDIA_TYPE_JSON, schema = @Schema(implementation = PlayerDTO.class))
			}),
			@ApiResponse(responseCode = CommonConstants.CODE_403, description = CommonConstants.UNAUTHORIZED, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_404, description = CommonConstants.PLAYER_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_500, description = CommonConstants.INTERNAL_SERVER_ERR, content = @Content),
			@ApiResponse(responseCode = CommonConstants.CODE_1001, description = CommonConstants.APPLICATION_ERROR, content = @Content)
	})
	@PutMapping(value = CommonConstants.SAVE_PLAYER, headers = CommonConstants.HEADER_TYPE_OBJECT)
	public ResponseEntity<?> updatePlayer(@Parameter(description = "Player details needed to update Player name only", required = true)
	@RequestBody PlayerDTO player) {	//
		Player newP = null;
		try {
			newP = playerService.updatePlayer(player.getIdPlayer(), player);
		} catch (ResponseStatusException rse) {
			rse.printStackTrace();
		}
		return ResponseEntity.ok(newP);
	} //ResponseStatusException is implemented here with a return of return new ResponseEntity<Map<String,Object>>(error, HttpStatus.NOT_FOUND);
			
}
