package predojo.test.unit;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import predojo.main.LogInfo;

public class InputLogBuilderUnitTest {

	private List<String> input = new ArrayList<>();
	private List<LogInfo> logList = new ArrayList<>();

	@Before
	public void setup() {
		input.add("23/04/2013 15:36:04 - Roman killed Nick using M16");
		input.add("23/04/2013 15:36:04 - Goku killed Roman using Glock");
		input.add("23/04/2013 15:36:04 - Nick killed John using Ak-47");
	}

	@Test
	public void logListBuilderTest() {
		final LogInfo logInfo = new LogInfo();

		for (String string : input) {
			logInfo.build(string.split(" "));

			assertThat(" The fourth position of the array must be the name of the killer ", string.split(" ")[3],
					equalTo(logInfo.getKiller()));
			assertThat(" The fourth position of the array must be the name of the player who got killed ",
					string.split(" ")[5], equalTo(logInfo.getKilled()));
			assertThat(" The fourth position of the array must be the name of weapon used to kill ",
					string.split(" ")[7], equalTo(logInfo.getWeapon()));

			logList.add(logInfo);
		}
		
		assertThat("This list must have 3 elements", logList, hasSize(3));

	}
}
