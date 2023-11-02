package itacademy.s5t2.diceGame.businessLayer.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import itacademy.s5t2.diceGame.businessLayer.domain.DiceGame;
import itacademy.s5t2.diceGame.businessLayer.dto.DiceGameDTO;
import itacademy.s5t2.diceGame.businessLayer.repository.DiceGameRepository;
import itacademy.s5t2.diceGame.businessLayer.service.interfaces.DiceGameServiceInter;
import itacademy.s5t2.diceGame.businessLayer.service.mapper.DiceGameDTOMapper;
import itacademy.s5t2.diceGame.constants.CommonConstants;

@Service
public class DiceGameServiceImpl implements DiceGameServiceInter {
	
	@Autowired
	private final DiceGameRepository diceRepo;
	@Autowired
	private final DiceGameDTOMapper dtoMapper;
	
	// curious to the logger class private static final Logger log = LoggerFactory.getLogger(PlayerServiceImpl.class);
	
	public DiceGameServiceImpl(DiceGameRepository repo, DiceGameDTOMapper map) {
		super();
		this.diceRepo = repo;
		this.dtoMapper = map;
	}
	
	@Override	//get all games
	public List<DiceGameDTO> getAllDiceGames() {
		return diceRepo.findAll()
				.stream()
				.map(d -> mapToDiceGameDTO(d))
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
			gameUpdated = mapToDiceGame(dtoRequest);
		}
		return gameUpdated;
	}
	
	public DiceGameDTO mapToDiceGameDTO(DiceGame dg) {
		return dtoMapper.apply(dg);
	}
	
	public DiceGame mapToDiceGame(DiceGameDTO dg) {
		return dtoMapper.applyToEntity(dg);
	}

	@Override	//get game by id
	public DiceGameDTO getById(long id) {
		//log.info("Find by Id: " + id);
		Optional<DiceGame> optional = checkOptional(id);
		DiceGameDTO game = mapToDiceGameDTO(optional.get());
		return game;
	}

	public Optional<DiceGame> checkOptional(long id) {
		Optional<DiceGame> optional = diceRepo.findById(id);
		if (!optional.isPresent()) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, 
					CommonConstants.returnDiceGameIdDoesNotExistMSG(id));
		}
		return optional;
	}

	@Override	//delete game by id
	public void deleteById(long id) {
		diceRepo.deleteById(id);
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

	public DiceGame playGame() {
		DiceGame game = new DiceGame();		//id is not being returned but rest is;
		game.playGame();
		return game;
	}

}
