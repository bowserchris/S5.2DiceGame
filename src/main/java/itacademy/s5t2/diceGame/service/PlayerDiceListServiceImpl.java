package itacademy.s5t2.diceGame.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itacademy.s5t2.diceGame.domain.DiceGame;
import itacademy.s5t2.diceGame.dto.DiceGameDTO;
import itacademy.s5t2.diceGame.repository.DiceGameRepository;
import itacademy.s5t2.diceGame.service.interfaces.PlayerDiceServiceInter;
import itacademy.s5t2.diceGame.service.mapper.DiceGameDTOMapper;

@Service
public class PlayerDiceListServiceImpl implements PlayerDiceServiceInter {
	
	@Autowired
	private final DiceGameRepository diceRepo;
	private final DiceGameDTOMapper dtoMapper;
	
	public PlayerDiceListServiceImpl(DiceGameRepository repo, DiceGameDTOMapper map) {
		super();
		this.diceRepo = repo;
		this.dtoMapper = map;
	}

	@Override	//get all games
	public List<DiceGameDTO> getAllDiceGames() {
		return diceRepo.findAll()
				.stream()
				.map(d -> dtoMapper.apply(d))
				.collect(Collectors.toList());
	}

	@Override	//save dicegame with casting
	public DiceGame saveDiceGame(DiceGame dg) {
		return diceRepo.save(dg);
	}

	@Override	//update dicegame
	public DiceGame updateDiceGame(long id, DiceGameDTO dtoRequest) {
		Optional<DiceGame> gameInDB = checkOptional(id);		//need to fix exception if not found
		DiceGame gameUpdated = gameInDB.get();
		gameUpdated = dtoMapper.applyToEntity(dtoRequest);
		return gameUpdated;
	}

	@Override	//get game by id
	public DiceGameDTO getById(long id) {
		Optional<DiceGame> optional = checkOptional(id);
		DiceGameDTO game = null;
		if (optional.isPresent()) {
			game = dtoMapper.apply(optional.get());
		} else {
			throw new RuntimeException ("Game not found with id: " + id);
		}
		return game;
	}

	@Override	//delete game by id
	public void deleteById(long id) {
		diceRepo.deleteById(id);
	}

	@Override	//delete all rolls within 
	public void deleteAllRolls() {
		diceRepo.deleteAll();
	}
	
	@Override
	public int getDieValue1(long id) {
		return diceRepo.findById(id).get().getDieResult1();
	}
	
	@Override
	public int getDieValue2(long id) {
		return diceRepo.findById(id).get().getDieResult2();
	}

	@Override
	public String getResult(long id) {
		return diceRepo.findById(id).get().getGameResult();
	}

	public Optional<DiceGame> checkOptional(long id) {
		return diceRepo.findById(id);
	}
	
	public void playGame() {
		DiceGameDTO game = new DiceGameDTO();
		game.playGame();
		saveDiceGame(dtoMapper.applyToEntity(game));
	}

}
