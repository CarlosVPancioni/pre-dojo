package predojo.main;

/**
 * Retrieves all the initial information reading the input log
 * 
 * @author Carlos Vinicius Pancioni
 *
 */
public class LogInfo {

	private String date;
	private String time;
	private String killer;
	private String killed;
	private String weapon;

	public void build(String[] log) {
		setDate(log[0]);
		setTime(log[1]);
		setKiller(log[3]);
		setKilled(log[5]);
		setWeapon(log[7]);
	}

	public String getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}

	public String getKiller() {
		return killer;
	}

	public String getKilled() {
		return killed;
	}

	public String getWeapon() {
		return weapon;
	}

	private void setDate(String date) {
		this.date = date;
	}

	private void setTime(String time) {
		this.time = time;
	}

	private void setKiller(String killer) {
		this.killer = killer;
	}

	private void setKilled(String killed) {
		this.killed = killed;
	}

	private void setWeapon(String weapon) {
		this.weapon = weapon;
	}
}
