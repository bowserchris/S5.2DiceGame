package itacademy.s5t2.diceGame.businessLayer.dto;

import java.util.Objects;

public class DiceGameDTO {

	private long gameId;
	private int dieResult1;
	private int dieResult2;
	private String gameResult;

	public DiceGameDTO(long gameId, int dieResult1, int dieResult2, String gameResult) {
		this.gameId = gameId;
		this.dieResult1 = dieResult1;
		this.dieResult2 = dieResult2;
		this.gameResult = gameResult;
	}

	public DiceGameDTO() {
	}

	public long getGameId() {
		return this.gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public int getDieResult1() {
		return this.dieResult1;
	}

	public void setDieResult1(int dieResult1) {
		this.dieResult1 = dieResult1;
	}

	public int getDieResult2() {
		return this.dieResult2;
	}

	public void setDieResult2(int dieResult2) {
		this.dieResult2 = dieResult2;
	}

	public String getGameResult() {
		return this.gameResult;
	}

	public void setGameResult(String gameResult) {
		this.gameResult = gameResult;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(this.dieResult1), Integer.valueOf(this.dieResult2),
				Long.valueOf(this.gameId), this.gameResult);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		DiceGameDTO other = (DiceGameDTO) obj;
		return this.dieResult1 == other.dieResult1 && this.dieResult2 == other.dieResult2 && this.gameId == other.gameId
				&& Objects.equals(this.gameResult, other.gameResult);
	}

	@Override
	public String toString() {
		return "DiceGameDTO [gameId=" + this.gameId + ", dieResult1=" + this.dieResult1 + ", dieResult2="
				+ this.dieResult2 + ", gameResult=" + this.gameResult + "]";
	}

	/*for mysql only tables
	 * @Schema(description = "The id of the player")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    //@JoinColumn(name="playerId")
    private Userdto userdto;
	 */

}
