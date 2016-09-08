package predojo.test.unit;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import predojo.main.LogInfo;
import predojo.main.Player;
import predojo.main.PlayerEngine;

public class EngineUnitTest {

	private List<LogInfo> parameter = new ArrayList<>();
	private List<Player> players = new ArrayList<>();

	@Before
	public void setup() {
		final LogInfo obj1 = new LogInfo();
		obj1.build("23/04/2013 15:36:04 - Roman killed Nick using M16".split(" "));

		final LogInfo obj2 = new LogInfo();
		obj2.build("23/04/2013 15:36:04 - Goku killed Roman using Glock".split(" "));

		final LogInfo obj3 = new LogInfo();
		obj3.build("23/04/2013 15:36:04 - Albert killed John using Ak-47".split(" "));

		final LogInfo obj4 = new LogInfo();
		obj4.build("23/04/2013 15:36:04 - Goku killed Dora using Glock".split(" "));

		final LogInfo obj5 = new LogInfo();
		obj5.build("23/04/2013 15:36:04 - Goku killed Albert using M16".split(" "));

		parameter.add(obj1);
		parameter.add(obj2);
		parameter.add(obj3);
		parameter.add(obj4);
		parameter.add(obj5);
	}

	@Test
	public void playerEngineTest() {
		final Map<String, Player> playerMap = PlayerEngine.buildPlayerResult(parameter);

		final Set<Entry<String, Player>> iterator = playerMap.entrySet();
		for (Entry<String, Player> obj : iterator) {
			final String key = obj.getKey();
			final Player player = obj.getValue();
			assertNotNull("The player can't be null", player);
			assertThat("The key has to be the same as the player's name", player.getName(), equalTo(key));
			assertThat("The list size of used weapons must be the same as the number of the kills", player.getKills(),
					equalTo(player.getWeaponsUsed().size()));
			
			players.add(player);
		}

		testGameInformation();
	}

	private void testGameInformation() {
		assertThat("This game must have six players", players, hasSize(6));
		assertThat("This game must have the same number of deaths and kills",
				players.stream().mapToInt(Player::getKills).sum(), 
				equalTo(players.stream().mapToInt(Player::getDeaths).sum()));
		assertThat("The quantity of players that has no kills in game has to be three",
				players.stream().filter(o -> o.getKills() == 0).count(), equalTo(3L));
		testWinnerInformation(players.get(0));
		testOtherPlayersInformation();
		
	}

	private void testOtherPlayersInformation() {
		assertFalse("The player cannot be the winner", players.get(1).isWinner());
		assertThat("The players's name is Roman", players.get(1).getName(), equalTo("Roman"));
		assertThat("The winner got three kills in the game", players.get(1).getKills(), equalTo(1));
		assertThat("The winner has no death ", players.get(1).getDeaths(), equalTo(1));
		assertThat("The winner has a Glock as prefered weapon", players.get(1).getPreferedWeapon(), equalTo("M16"));
		
		assertFalse("The player cannot be the winner", players.get(2).isWinner());
		assertThat("The players's name is Roman", players.get(2).getName(), equalTo("Albert"));
		assertThat("The winner has no death ", players.get(2).getDeaths(), equalTo(1));
		
		assertFalse("The player cannot be the winner", players.get(3).isWinner());
		assertThat("The players's name is Roman", players.get(3).getName(), equalTo("Nick"));
	}

	private void testWinnerInformation(final Player winner) {
		assertTrue("The first player of the list is always the winner", winner.isWinner());
		assertThat("The winner's name is Goku", winner.getName(), equalTo("Goku"));
		assertThat("The winner got three kills in the game", winner.getKills(), equalTo(3));
		assertThat("The winner has no death ", winner.getDeaths(), equalTo(0));
		assertThat("The winner has a Glock as prefered weapon", winner.getPreferedWeapon(), equalTo("Glock"));
		assertThat("The winner max killstreak is two of his three kills", winner.getMaxKillstreak(), equalTo(3));
	}

}
