package predojo.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BinaryOperator;

/**
 * Engine responsible to create and calculate all data about the players in the
 * game
 * 
 * @author Carlos Vinicius Pancioni
 *
 */
public class PlayerEngine {

	private static final String WORLD = "<WORLD>";
	private static final String HAS_NO_KILLS = "NOOB :P";

	private static Map<String, Player> playerMap;
	private static Player player;

	public static Map<String, Player> buildPlayerResult(final List<LogInfo> logInfoList) {
		playerMap = new LinkedHashMap<>();

		for (LogInfo logInfo : logInfoList) {
			constructPlayerResult(logInfo);
		}
		generateFinalScore();
		return playerMap;
	}

	private static void choosePreferedWeapon(final Player player) {
		player.setPreferedWeapon(player.getWeaponsUsed().stream()
				.reduce(BinaryOperator.maxBy((o1, o2) -> Collections.frequency(player.getWeaponsUsed(), o1)
						- Collections.frequency(player.getWeaponsUsed(), o2)))
				.orElse(HAS_NO_KILLS));
	}

	private static void reorderResultMap(final List<Player> values) {
		playerMap.clear();
		for (Player playerResult : values) {
			playerMap.put(playerResult.getName(), playerResult);
		}
	}

	private static void generateFinalScore() {
		final Set<Entry<String, Player>> iter = playerMap.entrySet();
		final List<Player> values = new ArrayList<>();
		for (Entry<String, Player> entry : iter) {
			values.add(entry.getValue());
		}

		for (Player playerResult : values) {
			choosePreferedWeapon(playerResult);
		}
		Collections.sort(values);
		values.get(0).setWinner(true);
		reorderResultMap(values);
	}

	private static void constructPlayerResult(final LogInfo logInfo) {
		if (logInfo != null) {
			if (!logInfo.getKiller().equals(WORLD)) {
				processKiller(logInfo);
			}
			processKilled(logInfo);
		}
	}

	private static void processKiller(final LogInfo log) {
		final String killer = log.getKiller();
		final String weaponUsed = log.getWeapon();

		if (playerMap.containsKey(killer)) {
			playerMap.get(killer).addKill();
			playerMap.get(killer).getWeaponsUsed().add(weaponUsed);
		} else {
			player = new Player();
			player.setName(killer);
			player.addKill();
			player.getWeaponsUsed().add(weaponUsed);
			playerMap.put(killer, player);
		}
	}

	private static void processKilled(final LogInfo log) {
		final String killed = log.getKilled();

		if (playerMap.containsKey(killed)) {
			playerMap.get(killed).addDeath();
		} else {
			player = new Player();
			player.setName(killed);
			player.addDeath();
			playerMap.put(killed, player);
		}
	}
}
