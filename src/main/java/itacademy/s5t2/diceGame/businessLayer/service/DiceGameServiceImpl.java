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

/**
 * Service layer for the Dice Game implementation
 * 
 * @author bowser-chris
 */
@Service
public class DiceGameServiceImpl implements DiceGameServiceInter {

	@Autowired
	private final DiceGameRepository diceRepo;
	@Autowired
	private final DiceGameDTOMapper dtoMapper;

	public DiceGameServiceImpl(DiceGameRepository repo, DiceGameDTOMapper map) {
		this.diceRepo = repo;
		this.dtoMapper = map;
	}

	@Override
	public List<DiceGameDTO> getAllDiceGames() {
		return this.diceRepo.findAll()
				.stream()
				.map(d -> this.mapToDiceGameDTO(d))
				.collect(Collectors.toList());
	}

	@Override
	public DiceGame saveDiceGame(DiceGame dg) {
		return this.diceRepo.save(dg);
	}

	@Override
	public DiceGame updateDiceGame(long id, DiceGameDTO dtoRequest) {
		DiceGameDTO gameInDB = this.getById(id);
		DiceGame gameUpdated = null;
		if (gameInDB != null) {
			gameUpdated = this.mapToDiceGame(dtoRequest);
		}
		return gameUpdated;
	}

	/**
	 * Converts from Dice Game to DTO
	 * 
	 * @param dg the dice game
	 * @return dto the dto of the dice game
	 */
	public DiceGameDTO mapToDiceGameDTO(DiceGame dg) {
		return this.dtoMapper.apply(dg);
	}

	/**
	 * Converts from DTO to Dice Game
	 * 
	 * @param dto the dto of the game
	 * @return dg the dice game
	 */
	public DiceGame mapToDiceGame(DiceGameDTO dto) {
		return this.dtoMapper.applyToEntity(dto);
	}

	@Override
	public DiceGameDTO getById(long id) {
		Optional<DiceGame> optional = this.checkOptional(id);
		DiceGameDTO game = this.mapToDiceGameDTO(optional.get());
		return game;
	}

	/**
	 * Checks DB if the dice game is present, if not throws exception
	 * 
	 * @param id id of the dice game
	 * @return optional if the dice game is present
	 * @throws ResponseStatusException Dice game doesnt exist
	 */
	public Optional<DiceGame> checkOptional(long id) {
		Optional<DiceGame> optional = this.diceRepo.findById(id);
		if (!optional.isPresent()) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,
					CommonConstants.returnDiceGameIdDoesNotExistMSG(id));
		}
		return optional;
	}

	@Override
	public void deleteById(long id) {
		this.diceRepo.deleteById(id);
	}

	@Override
	public int getDieValue1(long id) {
		return this.diceRepo.findById(id).get().getDieResult1();
	}

	@Override
	public int getDieValue2(long id) {
		return this.diceRepo.findById(id).get().getDieResult2();
	}

	@Override
	public String getResult(long id) {
		return this.diceRepo.findById(id).get().getGameResult();
	}

	public DiceGame playGame() {
		DiceGame game = new DiceGame();
		game.playGame();
		return game;
	}
}