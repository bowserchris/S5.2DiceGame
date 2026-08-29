package itacademy.s5t2.diceGame.businessLayer.controller;

import static itacademy.s5t2.diceGame.constants.CommonConstants.*;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.*;

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
import itacademy.s5t2.diceGame.securityLayer.service.AuthenticationService;

//@CrossOrigin(origins = CommonConstants.ORIGIN, allowCredentials = "true")
/**
 * Controller Class for the player and main interaction with the Dice Game
 * 
 * @author bowser-chris
 */
@RestController
@CrossOrigin(origins = CROSS_ORIGINS_URL)
@SecurityRequirement(name = SECURITY_NAME_BEARER)
@RequestMapping(INDEX)
@Tag(name = TAG_NAME_PLAYER_CONTROLLER, description = DESCRIPTION_PLAYER_CONTROLLER)
public class PlayerDTOController {

	//probably best to implement a parent service that will autowire both playerservice and diceservice, then the controller here would call it
	//this would reduce the clutter in this controller and leave it as a modular space between this controller and the 2 separate services

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

	/**
	 * Adds player to the DB thru endpoint Post .../players
	 * 
	 * @param player    details to be created
	 * @param ucBuilder uri component builder
	 * @return responseEntity player
	 */
	@Operation(summary = SUMMARY_ADDPLAYER, description = DESCRIPTION_ADDPLAYER)
	@ApiResponses(value = {
			@ApiResponse(responseCode = CODE_200, description = PLAYER_CREATED, content = {
					@Content(mediaType = MEDIA_TYPE_JSON, schema = @Schema(implementation = Player.class))
			}),
			@ApiResponse(responseCode = CODE_403, description = PLAYER_EXISTS, content = @Content),
			@ApiResponse(responseCode = CODE_404, description = PLAYER_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = CODE_500, description = INTERNAL_SERVER_ERR, content = @Content),
			@ApiResponse(responseCode = CODE_1001, description = APPLICATION_ERROR, content = @Content)
	})
	@PostMapping(value = SAVE_PLAYER, headers = HEADER_TYPE_OBJECT)
	public ResponseEntity<?> addPlayer(
			@Parameter(description = PARAMETER_PLAYER, required = true)
			@RequestBody Player player, UriComponentsBuilder ucBuilder)
	{
		Player newPlayer = null;
		try {
			if (!this.playerService.checkIfPlayerNameExists(player)) {
				newPlayer = this.playerService.savePlayer(player);
				HttpHeaders headers = new HttpHeaders();
				headers.setLocation(ucBuilder.path(PLAYER_ID_PATH).buildAndExpand(newPlayer.getIdPlayer()).toUri());
			} else {
				return new ResponseEntity<>(PLAYER_EXISTS, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (ResponseStatusException rse) {
			return new ResponseEntity<>(PLAYER_EXISTS, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(newPlayer);
	}

	/**
	 * Returns one player thru endpoint GET .../players/{id}
	 *
	 * @param id of the player
	 * @return responseEntity playerDTO
	 */
	@Operation(summary = SUMMARY_GET_1_PLAYER, description = DESCRIPTION_GET_1_PLAYER)
	@ApiResponses(value = {
			@ApiResponse(responseCode = CODE_200, description = SUCCESSFUL, content = {
					@Content(mediaType = MEDIA_TYPE_JSON, schema = @Schema(implementation = PlayerDTO.class))
			}),
			@ApiResponse(responseCode = CODE_404, description = PLAYER_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = CODE_500, description = INTERNAL_SERVER_ERR, content = @Content),
			@ApiResponse(responseCode = CODE_1001, description = APPLICATION_ERROR, content = @Content)
	})
	@GetMapping(value = PLAYER_ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getOnePlayerById(@Parameter(description = PARAMETER_PLAYER_ID, required = true)
	@PathVariable("id") long id) {
		PlayerDTO player = this.playerService.getById(id);
		if (player == null) {
			return new ResponseEntity<>(PLAYER_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<PlayerDTO> (player, HttpStatus.OK);
	}

	/**
	 * Plays a game with the specific id of the player thru endpoint Post:
	 * .../players/{id}/games/
	 *
	 * @param playerId
	 * @return responseEntity the game
	 */
	@Operation(summary = SUMMARY_PLAY_GAME, description = DESCRIPTION_PLAY_GAME)
	@ApiResponses(value = {
			@ApiResponse(responseCode = CODE_200, description = GAME_CREATED, content = {
					@Content(mediaType = MEDIA_TYPE_JSON, schema = @Schema(implementation = DiceGame.class))
			}),
			@ApiResponse(responseCode = CODE_403, description = USER_UNAUTHENTICATED, content = @Content),
			@ApiResponse(responseCode = CODE_404, description = PLAYER_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = CODE_500, description = INTERNAL_SERVER_ERR, content = @Content),
			@ApiResponse(responseCode = CODE_1001, description = APPLICATION_ERROR, content = @Content)
	})
	@PostMapping(value = GAMES_ALL_OR_PLAY, headers = HEADER_TYPE_OBJECT)
	public ResponseEntity<?> playGame(@Parameter(description = PARAMETER_PLAY_GAME,
	required = true)
	@PathVariable("id") long playerId) {
		DiceGame game = this.diceService.playGame();
		if ((game.getDieResult1() + game.getDieResult2()) == 2) {
			System.out.println(SNAKE_EYES);
		}
		this.playerService.addGameToPlayerList(game, playerId);
		this.diceService.saveDiceGame(game);
		return ResponseEntity.ok(game);
	}

	/**
	 * Delete all games for a player thru endpoint Delete: .../players/{id}/games
	 * 
	 * @param id of player to delete their games
	 * @return responseEntity string for confirmation
	 */
	@Operation(summary = SUMMARY_DELETE_ROLLS, description = DESCRIPTION_DELETE_ROLLS)
	@ApiResponses(value = {
			@ApiResponse(responseCode = CODE_200, description = GAME_DELETED, content = @Content),
			@ApiResponse(responseCode = CODE_403, description = USER_UNAUTHENTICATED, content = @Content),
			@ApiResponse(responseCode = CODE_404, description = GAME_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = CODE_500, description = INTERNAL_SERVER_ERR, content = @Content),
			@ApiResponse(responseCode = CODE_1001, description = APPLICATION_ERROR, content = @Content)
	})
	@DeleteMapping(value = GAMES_DELETE, headers = HEADER_TYPE_OBJECT)
	public ResponseEntity<?> deleteAllRolls(@PathVariable long id) {
		String resultString = NO_GAME_DELETE;
		if (this.playerService.deletePlayerGames(id)) {
			resultString = GAME_DELETED;
		}
		return ResponseEntity.ok(resultString);
	}

	/**
	 * Get all games of a player thru endpoint Get: .../players/{id}/games/
	 * 
	 * @param id of the player
	 * @return list of the players games
	 */
	@Operation(summary = SUMMARY_GET_ALL_GAMES, description = DESCRIPTION_GET_ALL_GAMES)
	@ApiResponses(value = {
			@ApiResponse(responseCode = CODE_200, description = LIST_RETURNED, content = {
					@Content(mediaType = MEDIA_TYPE_JSON, schema = @Schema(implementation = DiceGame.class))
			}),//implementation might be a double here
			@ApiResponse(responseCode = CODE_403, description = USER_UNAUTHENTICATED, content = @Content),
			@ApiResponse(responseCode = CODE_404, description = PLAYER_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = CODE_500, description = INTERNAL_SERVER_ERR, content = @Content),
			@ApiResponse(responseCode = CODE_1001, description = APPLICATION_ERROR, content = @Content)
	})
	@GetMapping(value = GAMES_ALL_OR_PLAY, headers = HEADER_TYPE_OBJECT)
	public ResponseEntity<?> getAllGames(@Parameter(description = PARAMETER_PLAYER_ID_ALL_GAMES,
	required = true)
	@PathVariable long id) {
		List<DiceGame> list = this.playerService.getById(id).getPlayerGames();
		list
		.stream()
		.map(game -> this.diceService.mapToDiceGameDTO(game))
		.collect(Collectors.toList());
		if (list.size() == 0) {
			return ResponseEntity.ok(LIST_IS_EMPTY);
		}
		return ResponseEntity.ok(list);
	}

	/**
	 * Get all players and the average success rate thru endpoint Get: .../players/
	 * 
	 * @param name for specific player if needed
	 * @return list of all games
	 */
	@Operation(summary = SUMMARY_GET_ALL_RATIO, description = DESCRIPTION_GET_ALL_RATIO)
	@ApiResponses(value = {
			@ApiResponse(responseCode = CODE_200, description = LIST_RETURNED, content = {
					@Content(mediaType = MEDIA_TYPE_JSON,
							schema = @Schema(implementation = PlayerDTO.class))}),
			@ApiResponse(responseCode = CODE_404, description = PLAYER_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = CODE_500, description = INTERNAL_SERVER_ERR, content = @Content),
			@ApiResponse(responseCode = CODE_1001, description = APPLICATION_ERROR, content = @Content)
	})
	@GetMapping(value = GET_ALL_PLAYERS)
	public ResponseEntity<?> getAllPlayersAndSuccessRate(@Parameter(description = PARAMETER_PLAYER_NAME_SUCCESS_RATIO,
	required = false)
	@RequestParam(required = false) String name) {
		List<PlayerDTO> list = this.playerService.getAllPlayers();
		//Map<String, Double> map;
		if (!list.isEmpty()) {
			//map = new HashMap<>();
			//list.forEach(k -> map.put("Player id: ", String.valueOf(k.getIdPlayer())), map.put(name, name), map.put(name, name));
			//list.forEach(k -> map.put(k.getPlayerName(), k.getSuccessRate()));
		} else {
			//map = new HashMap<>();
			//map.put("The amount of current players is at: ", 0.0);
			List<String> emptyList = new ArrayList<>();
			emptyList.add(EMPTY_PLAYER_DB);
			return ResponseEntity.ok(emptyList);
		}
		return ResponseEntity.ok(list);
	}

	//
	/**
	 * Returns the average ranking of all players in the system thru endpoint Get:
	 * .../players/ranking
	 * 
	 * @return successRate
	 */
	@Operation(summary = SUMMARY_TOTAL_AVERAGE, description = DESCRIPTION_TOTAL_AVERAGE)
	@ApiResponses(value = {
			@ApiResponse(responseCode = CODE_200, description = SUCCESSFUL, content = {
					@Content(mediaType = MEDIA_TYPE_JSON, schema = @Schema(implementation = Double.class))
			}),
			@ApiResponse(responseCode = CODE_404, description = PLAYER_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = CODE_500, description = INTERNAL_SERVER_ERR, content = @Content),
			@ApiResponse(responseCode = CODE_1001, description = APPLICATION_ERROR, content = @Content)
	})
	@GetMapping(RANKINGS)
	public ResponseEntity<?> getTotalAverageSuccessRate() {
		if (this.playerService.getAllPlayers().size() == 0) {
			return ResponseEntity.ok(EMPTY_PLAYER_DB);
		} else {
			return ResponseEntity.ok(this.playerService.calculateAverageSuccessRate());
		}
	}

	/**
	 * Gets the worst players success rate thru endpoint Get:
	 * .../players/ranking/loser
	 *
	 * @return worse successRate
	 */
	@Operation(summary = SUMMARY_WORSE_SUCCESS, description = DESCRIPTION_WORSE_SUCCESS)
	@ApiResponses(value = {
			@ApiResponse(responseCode = CODE_200, description = SUCCESSFUL, content = {
					@Content(mediaType = MEDIA_TYPE_JSON, schema = @Schema(implementation = Player.class))
			}),
			@ApiResponse(responseCode = CODE_204, description = LIST_IS_EMPTY, content = @Content),
			@ApiResponse(responseCode = CODE_404, description = PLAYER_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = CODE_500, description = INTERNAL_SERVER_ERR, content = @Content),
			@ApiResponse(responseCode = CODE_1001, description = APPLICATION_ERROR, content = @Content)
	})
	@GetMapping(RANKINGS_LOSER)
	public ResponseEntity<?> getWorstPlayerSuccessRate() {
		if (this.playerService.getAllPlayers().size() == 0) {
			return ResponseEntity.ok(EMPTY_PLAYER_DB);
		} else {
			return ResponseEntity.ok(this.playerService.getWorstSuccessRate());
		}
	}

	/**
	 * Returnn player with best success rate thru endpoint Get:
	 * .../players/ranking/winner
	 * 
	 * @return best success rate
	 */
	@Operation(summary = SUMMARY_BEST_SUCCESS, description = DESCRIPTION_BEST_SUCCESS)
	@ApiResponses(value = {
			@ApiResponse(responseCode = CODE_200, description = SUCCESSFUL, content = {
					@Content(mediaType = MEDIA_TYPE_JSON, schema = @Schema(implementation = Player.class))
			}),
			@ApiResponse(responseCode = CODE_204, description = LIST_IS_EMPTY, content = @Content),
			@ApiResponse(responseCode = CODE_404, description = PLAYER_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = CODE_500, description = INTERNAL_SERVER_ERR, content = @Content),
			@ApiResponse(responseCode = CODE_1001, description = APPLICATION_ERROR, content = @Content)
	})
	@GetMapping(RANKINGS_WINNER)
	public ResponseEntity<?> getBestPlayerSuccessRate() {
		if (this.playerService.getAllPlayers().size() == 0) {
			return ResponseEntity.ok(EMPTY_PLAYER_DB);
		} else {
			return ResponseEntity.ok(this.playerService.getBestSuccessRate());
		}
	}

	/**
	 * Updates players data thru endpoint Put: .../players
	 *
	 * @param player details to be udpated
	 * @return newP the new player details updated
	 */
	@Operation(summary = SUMMARY_UPDATE_PLAYER, description = DESCRIPTION_UPDATE_PLAYER)
	@ApiResponses(value = {
			@ApiResponse(responseCode = CODE_200, description = PLAYER_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = CODE_201, description = PLAYER_UPDATED, content = {
					@Content(mediaType = MEDIA_TYPE_JSON, schema = @Schema(implementation = PlayerDTO.class))
			}),
			@ApiResponse(responseCode = CODE_403, description = UNAUTHORIZED, content = @Content),
			@ApiResponse(responseCode = CODE_404, description = PLAYER_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = CODE_500, description = INTERNAL_SERVER_ERR, content = @Content),
			@ApiResponse(responseCode = CODE_1001, description = APPLICATION_ERROR, content = @Content)
	})
	@PutMapping(value = SAVE_PLAYER, headers = HEADER_TYPE_OBJECT)
	public ResponseEntity<?> updatePlayer(
			@Parameter(description = PARAMETER_UPDATE_PLAYER, required = true)
			@RequestBody PlayerDTO player) {
		Player newP = null;
		try {
			newP = this.playerService.updatePlayer(player.getIdPlayer(), player);
		} catch (ResponseStatusException rse) {
			rse.printStackTrace();
		}
		return ResponseEntity.ok(newP);
	}
}