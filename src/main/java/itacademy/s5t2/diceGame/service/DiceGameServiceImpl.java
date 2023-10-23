package itacademy.s5t2.diceGame.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import itacademy.s5t2.diceGame.domain.DiceGame;
import itacademy.s5t2.diceGame.dto.DiceGameDTO;
import itacademy.s5t2.diceGame.repository.DiceGameRepository;
import itacademy.s5t2.diceGame.service.interfaces.DiceGameServiceInter;
import itacademy.s5t2.diceGame.service.mapper.DiceGameDTOMapper;

@Service
//@Transactional
public class DiceGameServiceImpl implements DiceGameServiceInter {
	
	@Autowired
	private final DiceGameRepository diceRepo;
	private final DiceGameDTOMapper dtoMapper;
	
	// curious to the logger class private static final Logger log = LoggerFactory.getLogger(PlayerServiceImpl.class);
	
	public DiceGameServiceImpl(DiceGameRepository repo, DiceGameDTOMapper map) {
		super();
		this.diceRepo = repo;
		this.dtoMapper = map;
	}
	
	public DiceGameDTOMapper getDTOMapper() {
		return dtoMapper;
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
		//log.info("update player: " + dtoRequest);
		DiceGameDTO gameInDB = getById(id);		
		DiceGame gameUpdated = null;
		if (gameInDB != null) {
			gameUpdated = dtoMapper.applyToEntity(dtoRequest);
		}
		return gameUpdated;
	}

	@Override	//get game by id
	public DiceGameDTO getById(long id) {
		//log.info("Find by Id: " + id);
		Optional<DiceGame> optional = checkOptional(id);
		DiceGameDTO game = dtoMapper.apply(optional.get());
		return game;
	}

	public Optional<DiceGame> checkOptional(long id) {
		Optional<DiceGame> optional = diceRepo.findById(id);
		if (!optional.isPresent()) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Dice game id " + id + " does not exists.");
		}
		return optional;
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

	public void playGame() {
		DiceGameDTO game = new DiceGameDTO();
		game.playGame();
		saveDiceGame(dtoMapper.applyToEntity(game));
	}

}
