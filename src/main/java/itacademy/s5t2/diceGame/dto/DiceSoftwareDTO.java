package itacademy.s5t2.diceGame.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import itacademy.s5t2.diceGame.domain.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiceSoftwareDTO {
	
	//////review whats needed in dto and in entity, to reduce repeated code
	
	private List<Player> playerList = new ArrayList<>();
	private double totalSuccessRate;
	private HashMap<String, Integer> totalResultsWinLossMap = createMap();
	private static String WINS = "Win";
	private static String LOSSES = "Lose";
	
	private HashMap<String, Integer> createMap() {
		HashMap<String, Integer> map = new HashMap<>();
		map.put(WINS, 0);
		map.put(LOSSES, 0);
		return map;
	}

	public boolean checkForUniqueName(Player p) {
		boolean uniqueName = true;
		if (searchPlayerListByName(p.getPlayerName()) != -1) {
			uniqueName = false;
		}
		return uniqueName;
	}

	public void addPlayer(Player p) {
		if (!p.getPlayerName().equals("ANONYMOUS")) {
			if (checkForUniqueName(p)) {
				createPlayer(p);
			} else {
				System.out.println("Player already exists in Database.");
			}
		} else {
			createPlayer(p);
		}
	}
	
	public void createPlayer(Player p) {
		playerList.add(p);
		for (Map.Entry<String, Integer> resultEntry : p.getPlayerResultsWinLossMap().entrySet()) {
			totalResultsWinLossMap.put(resultEntry.getKey(), resultEntry.getValue());
		}
		totalSuccessRate = calculateAverageSuccessRate();
	}
	
	public void removePlayer(Player p) {
		if (playerList != null) {
			int objectIndex = searchPlayerListByName(p.getPlayerName()); 
			for (int i = 0; i < playerList.get(objectIndex).getPlayerGames().size(); i++) {
				String result = getPlayerGameResult(objectIndex, i);
				totalResultsWinLossMap.put(result, (int)totalResultsWinLossMap.get(result) - 1);
			}
			playerList.remove(objectIndex);
			totalSuccessRate = calculateAverageSuccessRate();
		} else {
			System.out.println("Player doesn´t exist in database.");
		}
	}
	
	private String getPlayerGameResult(int objectIndex, int gameIndex) {
		return playerList.get(objectIndex).getPlayerGames().get(gameIndex).getGameResult();
	}
	
	public double calculateAverageSuccessRate() {
		return Math.round(totalResultsWinLossMap.get(WINS) / playerList.size() * 100);
	}
	
	
	public double getOnePlayerSuccessRate(String name) {
		double rate;
		if (searchPlayerListByName(name) != -1) {
			rate = playerList.get(0).getSuccessRate();
		} else {
			rate = 0.0;
		}
		return rate;
	}
	
	public int searchPlayerListByName(String name) {
        int counter = 0;
        int index = -1;

        while (counter < playerList.size()) {
            if (playerList.get(counter).getPlayerName() == name) {
                index = counter;
                counter = playerList.size();
            }
            counter++;
        }
        return index;
    }
	
	public int searchPlayerListById(int id) {
        int counter = 0;
        int index = -1;

        while (counter < playerList.size()) {
            if (playerList.get(counter).getIdPlayer() == id) {
                index = counter;
                counter = playerList.size();
            }
            counter++;
        }
        return index;
    }

}
