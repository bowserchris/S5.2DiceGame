package itacademy.s5t2.diceGame.businessLayer.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import itacademy.s5t2.diceGame.businessLayer.domain.Player;
import itacademy.s5t2.diceGame.businessLayer.dto.PlayerDTO;
import itacademy.s5t2.diceGame.businessLayer.repository.PlayerRepository;
import itacademy.s5t2.diceGame.businessLayer.service.interfaces.PlayerInter;
import itacademy.s5t2.diceGame.businessLayer.service.mapper.PlayerDTOMapper;

@Service
//@Transactional
public class PlayerServiceImpl implements PlayerInter {
	
	@Autowired
	private final PlayerRepository playerRepo;
	
	//@Bean needed here?
	private final PlayerDTOMapper dtoMapper;
	
	// curious to the logger class private static final Logger log = LoggerFactory.getLogger(PlayerServiceImpl.class);
	
	public PlayerServiceImpl(PlayerRepository repo, PlayerDTOMapper map) {
		super();
		this.playerRepo = repo;
		this.dtoMapper = map;
	}
	
	public PlayerDTOMapper getDTOMapper() {
		return dtoMapper;
	}

	@Override	//get list of players
	public List<PlayerDTO> getAllPlayers() {
		return playerRepo.findAll()
				.stream()
				.map(p -> dtoMapper.apply(p))
				.collect(Collectors.toList());
	}

	@Override	//save player with casting
	public Player savePlayer(Player p) {
		return playerRepo.save(p);
	}
	
	public boolean checkIfPlayerNameExists(Player p) {
		boolean exists = true;
		if (!p.getPlayerName().equals("ANONYMOUS")) {
			if (checkForUniqueName(p)) {
				exists = false;
			} else {
				System.out.println("Player already exists in Database.");
               // need to implement exception here throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Te player with name " + playerToSave.getName() + " exists.");
			}
		} else {
			exists = false;
		}
		return exists;
	}

	public boolean checkForUniqueName(Player p) {
		boolean uniqueName = true;
		Player player = dtoMapper.applyToEntity(getByName(p.getPlayerName()));
		if (player.getPlayerName().equalsIgnoreCase(p.getPlayerName())) {
			uniqueName = false;
		}
		return uniqueName;
	}

	@Override	// update player
	public Player updatePlayer(long id, PlayerDTO dtoRequest) {
		//log.info("update player: " + dtoRequest);
		PlayerDTO playerInDB = getById(id);		//need to fix exception if not found
		Player playerUpdated = null;
		if (playerInDB != null) {
			playerUpdated = dtoMapper.applyToEntity(dtoRequest);
		}
		return playerUpdated;
	}

	@Override	//get player by id
	public PlayerDTO getById(long id) {
		//log.info("Find by Id: " + id);
		Optional<Player> optional = checkOptional(id);
		PlayerDTO player = dtoMapper.apply(optional.get());
		return player;
	}

	@Override	//delete player by id
	public void deleteById(long id) {
		playerRepo.deleteById(id);
	}

	@Override	//get player by name
	public PlayerDTO getByName(String name) {
		//log.info("Find by Id: " + id);
		Optional<Player> optional = playerRepo.findByName(name);
		PlayerDTO player = null;
		if (optional.isPresent()) {
			player = dtoMapper.apply(optional.get());
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Player name " + name + " does not exists.");
		}
		return player;
	}
	
	public Optional<Player> checkOptional(long id) {
		Optional<Player> optional = playerRepo.findById(id);
		if (!optional.isPresent()) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Player id " + id + " does not exists.");
		}
		return optional;
	}

	@Override
	public double getOneSuccessRateById(long id) {
		return playerRepo.findById(id).get().getSuccessRate();
	}

	@Override
	public double getOneSuccessRateByName(String name) {
		return playerRepo.findByName(name).get().getSuccessRate();
	}
	
	public double calculateAverageSuccessRate() {
		List<Player> list = playerRepo.findAll();
		double totalWins = 0;
		double totalGames = 0;
		for (int i = 0; i < list.size(); i++) {
			totalWins += (list.get(i).getSuccessRate()/100) * list.get(i).getPlayerGames().size();
			totalGames += list.get(i).getPlayerGames().size();
		}
		return Math.round((totalWins /totalGames) * 100);
		//above code is a reverse engineerd formula to get the new total value, below should call directly from repo
		//return Math.round(software.getTotalResultsWinLossMap().get("Win") / playerRepo.findAll().size() * 100);
	}
	
	public Player getBestSuccessRate() {
		Player bestPlayer = new Player();
		bestPlayer.setSuccessRate(0);
		for (int i = 0; i < playerRepo.findAll().size(); i++) {
			if (playerRepo.findAll().get(i).getSuccessRate() > bestPlayer.getSuccessRate()) {
				bestPlayer = playerRepo.findAll().get(i);
			}
		}
		return bestPlayer;
	}
	
	public Player getWorstSuccessRate() {
		Player worstPlayer = new Player();
		worstPlayer.setSuccessRate(101);
		for (int i = 0; i < playerRepo.findAll().size(); i++) {
			if (playerRepo.findAll().get(i).getSuccessRate() < worstPlayer.getSuccessRate()) {
				worstPlayer = playerRepo.findAll().get(i);
			}
		}
		return worstPlayer;
	}
	
	//public <T> Product createProduct(T value) {

}
