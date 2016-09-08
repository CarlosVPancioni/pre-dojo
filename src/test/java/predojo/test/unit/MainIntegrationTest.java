package predojo.test.unit;

import java.io.FileNotFoundException;

import org.junit.Test;

import predojo.main.Main;

public class MainIntegrationTest {
	@Test
	public void gameIntegrationTest() throws FileNotFoundException {
		final String[] args = {};
		Main.main(args);

	}
}
