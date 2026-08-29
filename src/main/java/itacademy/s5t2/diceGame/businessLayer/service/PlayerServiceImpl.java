package itacademy.s5t2.diceGame.businessLayer.service;

import static itacademy.s5t2.diceGame.constants.CommonConstants.ANONYMOUS;
import static itacademy.s5t2.diceGame.constants.CommonConstants.PLAYER_EXISTS;
import static itacademy.s5t2.diceGame.constants.CommonConstants.WINS;
import static itacademy.s5t2.diceGame.constants.CommonConstants.returnPlayerIdDoesNotExistMSG;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import itacademy.s5t2.diceGame.businessLayer.domain.DiceGame;
import itacademy.s5t2.diceGame.businessLayer.domain.Player;
import itacademy.s5t2.diceGame.businessLayer.dto.PlayerDTO;
import itacademy.s5t2.diceGame.businessLayer.repository.PlayerRepository;
import itacademy.s5t2.diceGame.businessLayer.service.interfaces.PlayerInter;
import itacademy.s5t2.diceGame.businessLayer.service.mapper.PlayerDTOMapper;
import itacademy.s5t2.diceGame.constants.CommonConstants;

/**
 * Service layer implementation for the player entity
 * 
 * @author bowser-chris
 */
@Service
//@Transactional
public class PlayerServiceImpl implements PlayerInter {

	@Autowired
	private final PlayerRepository playerRepo;

	@Autowired
	private final PlayerDTOMapper dtoMapper;

	@Autowired
	private final SequenceGeneratorService sequenceService;

	public PlayerServiceImpl(PlayerRepository repo, PlayerDTOMapper map, SequenceGeneratorService sequence) {
		this.playerRepo = repo;
		this.dtoMapper = map;
		this.sequenceService = sequence;
	}

	@Override
	public List<PlayerDTO> getAllPlayers() {
		return this.playerRepo.findAll()
				.stream()
				.map(p -> this.mapToPlayerDto(p))
				.collect(Collectors.toList());
	}

	@Override
	public Player savePlayer(Player p) {
		p.setPlayerGames(new ArrayList<DiceGame>());
		p.setPlayerResultsWinLossMap(CommonConstants.createPlayerMap());
		p.setIdPlayer(this.sequenceService.generateSequence(Player.SEQUENCE_NAME));
		return this.playerRepo.save(p);
	}

	/**
	 * Checks if player name exists or not, ignoring ANONYMOUS x amount of those can
	 * exist
	 * 
	 * @param p the player
	 * @return boolean whether player exists or not
	 * @throws ResponseStatusException if player already exists
	 */
	public boolean checkIfPlayerNameExists(Player p) {
		boolean exists = true;
		if (!p.getPlayerName().equalsIgnoreCase(ANONYMOUS)) {
			if (this.checkForUniqueName(p)) {
				exists = false;
			} else {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, PLAYER_EXISTS);
			}
		} else {
			exists = false;
		}
		return exists;
	}

	/**
	 * Checks if the player name is truly unique
	 * 
	 * @param p the player
	 * @return boolean if truly unique or not
	 */
	private boolean checkForUniqueName(Player p) {
		boolean uniqueName = true;
		PlayerDTO player = this.getByName(p.getPlayerName());
		if (player == null) {
			return uniqueName;
		} else if (p.getPlayerName().equalsIgnoreCase(player.getPlayerName())) {
			uniqueName = false;
		}
		return uniqueName;
	}

	public Player mapToPlayer(PlayerDTO p) {
		return this.dtoMapper.applyToEntity(p);
	}

	public PlayerDTO mapToPlayerDto(Player p) {
		return this.dtoMapper.apply(p);
	}

	@Override
	public Player updatePlayer(long id, PlayerDTO dtoRequest) {
		PlayerDTO playerInDB = this.getById(id);		//need to fix exception if not found
		Player playerUpdated = null;
		if (playerInDB != null) {
			playerUpdated = this.mapToPlayer(playerInDB);
			playerUpdated.setPlayerName(dtoRequest.getPlayerName());
			this.playerRepo.save(playerUpdated);
		}
		return playerUpdated;
	}

	@Override
	public PlayerDTO getById(long id) {
		Optional<Player> optional = this.checkOptional(id);
		PlayerDTO player = this.mapToPlayerDto(optional.get());
		return player;
	}

	@Override
	public void deleteById(long id) {
		this.playerRepo.deleteById(id);
	}

	@Override
	public PlayerDTO getByName(String name) {
		Optional<Player> optional = this.playerRepo.findByPlayerName(name);
		PlayerDTO player = null;
		if (optional.isPresent()) {
			player = this.mapToPlayerDto(optional.get());
		}
		return player;
	}

	/**
	 * Checks whether the player is present or not in the DB
	 * 
	 * @param id id of the player
	 * @return optional the player
	 * @throws ResponseStatusException if player doesnt exist
	 */
	public Optional<Player> checkOptional(long id) {
		Optional<Player> optional = this.playerRepo.findById(id);
		if (!optional.isPresent()) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,
					returnPlayerIdDoesNotExistMSG(id));
		}
		return optional;
	}

	@Override
	public double getOneSuccessRateById(long id) {
		return this.playerRepo.findById(id).get().getSuccessRate();
	}

	@Override
	public double getOneSuccessRateByName(String name) {
		return this.playerRepo.findByPlayerName(name).get().getSuccessRate();
	}

	public double calculateAverageSuccessRate() {
		List<Player> list = this.playerRepo.findAll();
		int totalWins = 0;
		int totalGames = 0;
		for (int i = 0; i < list.size(); i++) {
			totalWins += list.get(i).getPlayerResultsWinLossMap().get(WINS);
			totalGames += list.get(i).getPlayerGames().size();
		}
		return Math.round(CommonConstants.calculateAverageSuccessRate(totalWins, totalGames));
	}

	public Player getBestSuccessRate() {
		Player bestPlayer = new Player();
		bestPlayer.setSuccessRate(0);
		for (int i = 0; i < this.playerRepo.findAll().size(); i++) {
			if (this.playerRepo.findAll().get(i).getSuccessRate() > bestPlayer.getSuccessRate()) {
				bestPlayer = this.playerRepo.findAll().get(i);
			}
		}
		return bestPlayer;
	}

	public Player getWorstSuccessRate() {
		Player worstPlayer = new Player();
		worstPlayer.setSuccessRate(101);
		for (int i = 0; i < this.playerRepo.findAll().size(); i++) {
			if (this.playerRepo.findAll().get(i).getSuccessRate() < worstPlayer.getSuccessRate()) {
				worstPlayer = this.playerRepo.findAll().get(i);
			}
		}
		return worstPlayer;
	}

	public boolean addGameToPlayerList(DiceGame dg, long playerId) {
		Player player = this.dtoMapper.applyToEntity(this.getById(playerId));
		if (player.getPlayerGames() == null) {
			player.setPlayerGames(new ArrayList<DiceGame>());
		}	//here i could just have an array list with id numbers and save to mongo within the player db
		//then from mysql i pull out a game id and then have the service call the dice repo and return the dice id from there
		dg.setGameId(this.sequenceService.generateSequence(DiceGame.SEQUENCE_NAME));
		player.addGameToList(dg);		//return true or false to check update correctly?
		this.playerRepo.save(player);
		return true;
	}

	public boolean deletePlayerGames(long playerId) {
		Player player = this.dtoMapper.applyToEntity(this.getById(playerId));
		player.deleteListOfGames();
		this.playerRepo.save(player);
		return true;
	}
}