package predojo.main;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

/**
 * Class responsible to build the entire output information <br>
 * about the players statistics.
 * 
 * @author Carlos Vinicius Pancioni
 *
 */
public class OutputBuilder {

	private static final String FIRST_PLACE_BADGE = "*";
	private static final String NO_DEATHS_BADGE = "$";
	private static final String KILLSTREAK_BADGE = "+";
	private static final String LONGEST_KILLSTREAK_BADGE = "!";
	private static final int DEFAULT_COLUMN_SIZE = 10;
	private static final int BADGE_COLUMN_SIZE = 12;
	private static final int PREFERED_WEAPON_SIZE = 20;

	private static String printFormatter;
	private static Entry<String, Player> player;
	private static int longestStreakInGame = 0;
	private static String longestStreakPlayer;

	public static void showResults(final Map<String, Player> playerMap) {

		final Optional<String> longestName = playerMap.keySet().stream()
				.sorted((e1, e2) -> e1.length() > e2.length() ? -1 : 1).findFirst();
		final int lengthOfBiggestNickname = longestName.get().length();
		final int totalSizeOfTable = lengthOfBiggestNickname + ((DEFAULT_COLUMN_SIZE * 2) + BADGE_COLUMN_SIZE + PREFERED_WEAPON_SIZE);
		printFormatter = "%-" + BADGE_COLUMN_SIZE + "s%" + lengthOfBiggestNickname + "s%" + DEFAULT_COLUMN_SIZE + "s%"
				+ DEFAULT_COLUMN_SIZE + "s%" + PREFERED_WEAPON_SIZE + "s";

		createTableHeader(totalSizeOfTable);

		final Set<Entry<String, Player>> iter = playerMap.entrySet();
		for (Entry<String, Player> entry : iter) {
			final int maxKillstreak = entry.getValue().getMaxKillstreak();
			if(maxKillstreak > longestStreakInGame){	
				longestStreakInGame = maxKillstreak;
				longestStreakPlayer = entry.getKey();
			}
			player = entry;
			
			printTableResult();
			drawTraceLine(totalSizeOfTable);
		}
		drawTraceLine(totalSizeOfTable);
		drawTraceLine(totalSizeOfTable);
	}

	private static void printTableResult() {
		System.out.format(printFormatter, defineAwards(), player.getKey(), player.getValue().getKills(),
				player.getValue().getDeaths(), player.getValue().getPreferedWeapon() + "\n");
	}

	private static String defineAwards() {
		String badges = "";
		final boolean isWinner = player.getValue().isWinner();
		final boolean hasNoDeaths = player.getValue().getDeaths() == 0 ? Boolean.TRUE : Boolean.FALSE;
		final boolean isPlayerWithLongestStreak = player.getKey() == longestStreakPlayer ? Boolean.TRUE : Boolean.FALSE;

		if (isWinner) {
			badges += FIRST_PLACE_BADGE;
		}

		if (hasNoDeaths) {
			badges += NO_DEATHS_BADGE;
		}
		
		if(isPlayerWithLongestStreak){
			badges += LONGEST_KILLSTREAK_BADGE + "(" + longestStreakInGame + ")";
		}
		return badges;
	}

	private static void createTableHeader(final int totalSizeOfTable) {
		drawTraceLine(totalSizeOfTable);
		System.out.format(printFormatter, "BADGES", "NICKNAME", "KILLS", "DEATHS", "PREFERED WEAPON" + "\n");
		drawTraceLine(totalSizeOfTable);
	}

	private static void drawTraceLine(final int totalSizeOfTable) {
		for (int i = 0; i < totalSizeOfTable; i++) {
			System.out.print("-");
		}
		System.out.println();
	}
}
