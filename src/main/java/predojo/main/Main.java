package predojo.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	private static final String NEW = "New";
	private static final String MATCH = "Match";

	public static void main(String[] args) {
		final Scanner scan = new Scanner(System.in);
		final List<LogInfo> logInfoList = new ArrayList<LogInfo>();

		while (scan.hasNextLine()) {
			final String nextLine = scan.nextLine();
			if (!nextLine.isEmpty()) {
				final LogInfo logInfo = new LogInfo();
				final String[] splittedLog = nextLine.split(" ");
				final String firstLogWord = splittedLog[3];

				if (!firstLogWord.startsWith(NEW) && !firstLogWord.startsWith(MATCH)) {
					logInfo.build(splittedLog);
					logInfoList.add(logInfo);
				}

				if (firstLogWord.startsWith(MATCH)) {
					OutputBuilder.showResults(PlayerEngine.buildPlayerResult(logInfoList));
				}
			}
		}
		scan.close();
	}
}
