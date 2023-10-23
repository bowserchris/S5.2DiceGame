package itacademy.s5t2.diceGame.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itacademy.s5t2.diceGame.domain.Player;
import itacademy.s5t2.diceGame.dto.PlayerDTO;
import itacademy.s5t2.diceGame.repository.PlayerRepository;
import itacademy.s5t2.diceGame.service.interfaces.SoftwarePlayerInter;
import itacademy.s5t2.diceGame.service.mapper.PlayerDTOMapper;

@Service
public class DiceSoftwareServiceImpl implements SoftwarePlayerInter {
	
	@Autowired
	private final PlayerRepository playerRepo;
	private final PlayerDTOMapper dtoMapper;
	
	public DiceSoftwareServiceImpl(PlayerRepository repo, PlayerDTOMapper map) {
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

	@Override	// update player
	public Player updatePlayer(long id, PlayerDTO dtoRequest) {
		Optional<Player> playerInDB = checkOptional(id);		//need to fix exception if not found
		Player playerUpdated = playerInDB.get();
		playerUpdated = dtoMapper.applyToEntity(dtoRequest);
		return playerUpdated;
	}

	@Override	//get player by id
	public PlayerDTO getById(long id) {
		Optional<Player> optional = checkOptional(id);
		PlayerDTO player = null;
		if (optional.isPresent()) {
			player = dtoMapper.apply(optional.get());
		} else {
			throw new RuntimeException ("Player not found for id: " + id);
		}
		return player;
	}

	@Override	//delete player by id
	public void deleteById(long id) {
		playerRepo.deleteById(id);
	}

	@Override	//get player by name
	public PlayerDTO getByName(String name) {
		Optional<Player> optional = playerRepo.findByName(name);
		PlayerDTO player = null;
		if (optional.isPresent()) {
			player = dtoMapper.apply(optional.get());
		} else {
			throw new RuntimeException ("Player not found with name: " + name);
		}
		return player;
	}
	
	public Optional<Player> checkOptional(long id) {
		return playerRepo.findById(id);
	}

	@Override
	public double getOneSuccessRateById(long id) {
		return playerRepo.findById(id).get().getSuccessRate();
	}

	@Override
	public double getOneSuccessRateByName(String name) {
		return playerRepo.findByName(name).get().getSuccessRate();
	}
	
	//public <T> Product createProduct(T value) {

}
