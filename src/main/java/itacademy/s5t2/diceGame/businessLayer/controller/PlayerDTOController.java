package itacademy.s5t2.diceGame.businessLayer.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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

import itacademy.s5t2.diceGame.businessLayer.domain.Player;
import itacademy.s5t2.diceGame.businessLayer.dto.PlayerDTO;
import itacademy.s5t2.diceGame.businessLayer.service.PlayerServiceImpl;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/")
@Validated
public class PlayerDTOController {
	
	//private static Logger log = LoggerFactory.getLogger(PlayerController.class);
	
			@Autowired
			PlayerServiceImpl playerService;
			
			public PlayerDTOController(PlayerServiceImpl service) {
				super();
				this.playerService = service;
			}
			
			
			/*
			 * @PostMapping(value="/add")
		    @Operation(summary= "" +
		            "Adds a new Player", description = "Creates a new player and saves it in the database")
		    @ApiResponse(responseCode = "200", description = "Player created correctly", content = {@Content(mediaType = "application/json",
		            schema = @Schema(implementation = PlayerToSave.class))})
		    @ApiResponse(responseCode = "403", description = "The player already exists", content = @Content)
		    public ResponseEntity<?> createPlayer(@RequestBody PlayerToSave playerToSave){
		        log.info("create player: " + playerToSave);
		        try {
		            playerServiceMongo.create(playerToSave);
		            return ResponseEntity.ok(playerToSave);
		        }catch (ResponseStatusException e){
		            return new ResponseEntity<Map<String,Object>>(this.message(e), HttpStatus.FORBIDDEN);
		        }
		    }
			 */
			//Post: /players - creates a player
			@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
			@PostMapping(value = "players", headers = "Accept=application/json")
			public ResponseEntity<Player> addPlayer(@RequestBody Player player, UriComponentsBuilder ucBuilder) {
				try {
					if (!playerService.checkIfPlayerNameExists(player)) {
						Player newPlayer = playerService.savePlayer(player);
						/*URI location = ServletUriComponentsBuilder
						.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(savedPlayer.getId())
						.toUri();
						return ResponseEntity.created(location).build();*/
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
			
			/* @Operation(summary= "Update Player", description = "Updates the name of an existing player")
			    @ApiResponse(responseCode = "201", description = "Player updated correctly", content = {@Content(mediaType = "application/json",
			            schema = @Schema(implementation = PlayerToSave.class))})
			    @ApiResponse(responseCode = "404", description = "Player not found", content = @Content)
			    @PutMapping(value="/update/")
			    public ResponseEntity<?> updatePlayer(@RequestBody PlayerToSave playerToSave){
			        log.info("update player: " + playerToSave);
			        try {

			            playerServiceMongo.update(playerToSave);
			            return ResponseEntity.ok(playerToSave);
			        }catch (ResponseStatusException e){
			            return new ResponseEntity<Map<String,Object>>(this.message(e), HttpStatus.NOT_FOUND);
			        }
			    }*/
			
		//Put: /players - updates player name
			@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
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

			/*
			 *  @Operation(summary= "List all dice rolls for a player", description = "Returns the complete list of each player and the result of their dice rolls.")
		    @ApiResponse(responseCode = "200", description = "List of rolls", content = {@Content(mediaType = "application/json",
		            schema = @Schema(implementation = Playerdto.class))})
		    @ApiResponse(responseCode = "404", description = "Player not found", content = @Content)
		    @GetMapping("/{id}/games/")
		    public ResponseEntity<?> findAllGames(@PathVariable String id) {
		        try {
		            Playerdto playerdto = playerServiceMongo.findById(id);
		            return ResponseEntity.ok(playerdto);
		        } catch (ResponseStatusException e) {
		            return new ResponseEntity<Map<String,Object>>(this.message(e), HttpStatus.NOT_FOUND);
		        }
		    }
			 */
		//Get: /players/ - returns all players with average success rate
			@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
			@GetMapping(value = "players/", headers = "Accept=application/json")
			public ResponseEntity<List<PlayerDTO>> getAllPlayersAndSuccessRate(@RequestParam(required = false) String name) {
				List<PlayerDTO> list = playerService.getAllPlayers();
				list
				.stream()
				.map(player -> playerService.savePlayer(playerService.getDTOMapper().applyToEntity(player)))		//modelMapper.map(flower,FlowerDTO.class)
				.collect(Collectors.toList());
				return ResponseEntity.notFound().build(); //ResponseEntity<>(list, HttpStatus.FOUND);
			}
			/*
			 *  @Operation(summary= "List of results of all players", description = "returns the list of all the players in the system\n" +
		            "  with its average success rate.")
		    @ApiResponse(responseCode = "200", description = "List of results of all players", content = {@Content(mediaType = "application/json",
		            schema = @Schema(implementation = Ranking.class))})
		    @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
		    @GetMapping("/players/")
		    public ResponseEntity<?> findAllRanking() {
		        return ResponseEntity.ok(playerServiceMongo.listAllRanking());
		    }
		    
		     @Operation(summary= "Average of players rankings", description = "Returns the average success rate of all players")
		    @ApiResponse(responseCode = "200", description = "Average", content = @Content)
		    @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
		    @GetMapping("/players/ranking")
		    public ResponseEntity<Integer> rankgingAvg(){
		        return ResponseEntity.ok(playerServiceMongo.rankingAvg());
		    }
			 */
		//Get: /players/ranking - returns the average ranking of all players in the system. That is, the average percentage of successes
			@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
			@GetMapping("players/ranking")
			public ResponseEntity<Player> getTotalAverageSuccessRate() {
				double totalRate = playerService.calculateAverageSuccessRate();
				return ResponseEntity.notFound().build();
			}
			/*
			 * 
			 * @Operation(summary= "Player with the worst ranking", description = "Returns the player with the lowest success rate")
		    @ApiResponse(responseCode = "200", description = "Player id and games results", content = {@Content(mediaType = "application/json",
		            schema = @Schema(implementation = Ranking.class))})
		    @ApiResponse(responseCode = "204", description = "No content. There are no games saved in the database", content = @Content)
		    @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
		    @GetMapping("/ranking/looser")
		    public ResponseEntity<?> worstPlayer(){
		        Ranking worstPlayer;
		        try {
		            worstPlayer = playerServiceMongo.worstPlayer();
		        } catch (ResponseStatusException e) {
		            return new ResponseEntity<Map<String,Object>>(this.message(e), HttpStatus.NO_CONTENT);
		        }
		        return ResponseEntity.ok(worstPlayer);
		    }
			 */
		//Get: /players/ranking/loser - return player with worst success rate
			@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
			@GetMapping("players/ranking/loser")
			public ResponseEntity<Player> getWorstPlayerSuccessRate() {
				Player player = playerService.getWorstSuccessRate();
				return ResponseEntity.notFound().build();
			}
			/*
			 * 
			 *  @Operation(summary= "Player with the best ranking", description = "Returns the player with the highest success rate")
		    @ApiResponse(responseCode = "200", description = "Player id and games results", content = {@Content(mediaType = "application/json",
		            schema = @Schema(implementation = Ranking.class))})
		    @ApiResponse(responseCode = "204", description = "No content. There are no games saved in the database", content = @Content)
		    @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
		    @GetMapping("ranking/winner")
		    public ResponseEntity<?> bestPlayer(){
		        Ranking bestPlayer;
		        try {
		            bestPlayer = playerServiceMongo.bestPlayer();
		        } catch (ResponseStatusException e) {
		            return new ResponseEntity<Map<String,Object>>(this.message(e), HttpStatus.NO_CONTENT);
		        }
		        return ResponseEntity.ok(bestPlayer);
		    }
			 */
		//Get: /players/ranking/winner - return player with best success rate
			@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
			@GetMapping("players/ranking/winner")
			public ResponseEntity<Player> getBestPlayerSuccessRate() {
				Player player = playerService.getBestSuccessRate();
				return ResponseEntity.notFound().build();
			}
			
			@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
			@GetMapping(value = "/players/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
			public ResponseEntity<PlayerDTO> getOnePlayerById(@PathVariable("id") long id) {
				PlayerDTO player = playerService.getById(id);
						//.orElseThrow(() -> new EmployeeNotFoundException("Employee with ID :" + id)); custom exception made
				if (player == null) {
					return new ResponseEntity<PlayerDTO> (HttpStatus.NOT_FOUND);
				}
				return new ResponseEntity<PlayerDTO> (player, HttpStatus.OK);
				//return ResponseEntity.ok().body(player);
			}
			
			/* @DeleteMapping(value = "/{id}", headers = "Accept=application/json")
		 public ResponseEntity<PlayerDTO> deletePlayer(@PathVariable("id") long id) {
		  Optional<PlayerDTO> optPlayer = playerService.findById(id);
		  PlayerDTO player = optPlayer.get();
		  if (player == null) {
		   return new ResponseEntity<PlayerDTO> (HttpStatus.NOT_FOUND);
		  }
		  playerService.deletePlayerById(id);
		  return new ResponseEntity<PlayerDTO> (HttpStatus.NO_CONTENT);
		 }
			 */
			
			/* belowe methods are for dice controller
		//Post: /players/{id}/games/ - specific player roles the dice
		//Delete: /players/{id}/games - deletes all players rolls
		//Get: /players/{id}/games/ - returns list of games for 1 player
		 * 
		 * @Operation(summary= "Delete selected games", description = "deletes all games of selected player.")
		    @ApiResponse(responseCode = "200", description = "Games deleted", content = @Content)
		    @ApiResponse(responseCode = "404", description = "Player not found", content = @Content)
		    @DeleteMapping("/{id}/games/")
		    public ResponseEntity<?> deleteGamesByPlayerId(@PathVariable String id){
		        try {
		            playerServiceMongo.deleteGamesByPlayerId(id);
		            return ResponseEntity.ok(HttpStatus.OK);
		        }catch(ResponseStatusException e){
		            return new ResponseEntity<Map<String,Object>>(this.message(e), HttpStatus.NOT_FOUND);
		        }
		    }
		    
		    @Operation(summary= "Roll dices", description = "If dice 1 + dice 2 = 7, then the player wins. The result is saved in the database")
		    @ApiResponse(responseCode = "200", description = "Game added to the database", content = {@Content(mediaType = "application/json",
		            schema = @Schema(implementation = Playerdto.class))})
		    @ApiResponse(responseCode = "404", description = "Player not found", content = @Content)
		    @PostMapping("/{id}/games/")
		    public ResponseEntity<?> rollDice(@PathVariable String id){

		        try {
		            Playerdto playerdto = playerServiceMongo.playGame(id);
		            return ResponseEntity.ok(playerdto);
		        } catch (ResponseStatusException e){
		            return new ResponseEntity<Map<String,Object>>(this.message(e), HttpStatus.NOT_FOUND);
		        }
		    }
		    private Map<String, Object> message(ResponseStatusException e) {
		        Map<String, Object> error = new HashMap<>();
		        error.put("Message", e.getMessage());
		        error.put("Reason", e.getReason());
		        return error;
		    }
			 */
	
}
