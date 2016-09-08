package predojo.main;

import java.util.ArrayList;
import java.util.List;

public class Player implements Comparable<Player> {

	private String name;
	private int kills;
	private int deaths;
	private int maxKillstreak;
	private boolean isWinner;
	private String preferedWeapon;
	private List<String> weaponsUsed;

	private int currentStreak = 0;

	public int getKills() {
		return kills;
	}

	public void addKill() {
		if (++currentStreak > maxKillstreak) {
			maxKillstreak = currentStreak;
		}
		kills++;
	}

	public int getDeaths() {
		return deaths;
	}

	public void addDeath() {
		currentStreak = 0;
		deaths++;
	}

	public List<String> getWeaponsUsed() {
		if (weaponsUsed == null) {
			weaponsUsed = new ArrayList<String>();
		}
		return weaponsUsed;
	}

	public void setWeaponsUsed(List<String> weaponsUsed) {
		this.weaponsUsed = weaponsUsed;
	}

	public boolean isWinner() {
		return isWinner;
	}

	public void setWinner(boolean isWinner) {
		this.isWinner = isWinner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(Player o) {
		if (kills == o.getKills()) {
			return deaths - o.getDeaths();
		}
		return o.getKills() - kills;
	}

	public String getPreferedWeapon() {
		return preferedWeapon;
	}

	public void setPreferedWeapon(String preferedWeapon) {
		this.preferedWeapon = preferedWeapon;
	}

	public int getMaxKillstreak() {
		return maxKillstreak;
	}
}
