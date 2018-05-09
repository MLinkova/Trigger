
import java.util.HashSet;

import java.util.Random;
import java.util.Set;

import robocode.control.*;

public class RouteFinder {

	public static void main(String[] args) {
		// Create the RobocodeEngine
		RobocodeEngine engine = new RobocodeEngine(new java.io.File("C:/robocode"));
		// RobocodeEngine engine = new RobocodeEngine(new
		// java.io.File("/Users/dominika/robocode"));
		// Show the Robocode battle view
		engine.setVisible(true);
		// Create the battlefield
		int NumPixelRows = Constants.BATTLEFIELD_WIDTH;
		int NumPixelCols = Constants.BATTLEFIELD_HEIGH;
		BattlefieldSpecification battlefield = new BattlefieldSpecification(NumPixelRows, NumPixelCols);
		// Setup battle parameters
		int numberOfRounds = Constants.NUMBER_ROUNDS;
		long inactivityTime = Constants.INACTIVITY_TIME;
		double gunCoolingRate = Constants.GUN_COOL_RATE;
		int sentryBorderSize = Constants.SENTRY_SIZE;
		boolean hideEnemyNames = false;
		/*
		 * Create obstacles and place them at random so that no pair of
		 * obstacles are at the same position
		 */
		RobotSpecification[] modelRobots = engine.getLocalRepository("robots.SuperTracker*,robots.SuperRamFire*");
		RobotSpecification[] existingRobots = new RobotSpecification[Constants.SIZE_ROBOT_FIELD];
		RobotSetup[] robotSetups = new RobotSetup[Constants.SIZE_ROBOT_FIELD];

		// generate random position of obstacles
		Random r = new Random(Constants.seed);
		double InitialObstacleRow = (double) (r.nextInt(Constants.NUMBER_ROWS + 1)) * Constants.SIZE_OF_TILES
				+ Constants.HALF_TILE;
		double InitialObstacleCol = (double) (r.nextInt(Constants.NUMBER_COLUMNS + 1)) * Constants.SIZE_OF_TILES
				+ Constants.HALF_TILE;

		existingRobots[0] = modelRobots[0];
		robotSetups[0] = new RobotSetup(InitialObstacleRow, InitialObstacleCol, 0.0);

		/*
		 * Create the agent and place it in a random position without obstacle
		 */
		existingRobots[1] = modelRobots[1];

		double InitialAgentRow = (double) (r.nextInt(Constants.NUMBER_ROWS + 1)) * Constants.SIZE_OF_TILES
				+ Constants.HALF_TILE;
		double InitialAgentCol = (double) (r.nextInt(Constants.NUMBER_COLUMNS + 1)) * Constants.SIZE_OF_TILES
				+ Constants.HALF_TILE;
		robotSetups[1] = new RobotSetup(InitialAgentRow, InitialAgentCol, 0.0);

		/* Create and run the battle */
		BattleSpecification battleSpec = new BattleSpecification(battlefield, numberOfRounds, inactivityTime,
				gunCoolingRate, sentryBorderSize, hideEnemyNames, existingRobots, robotSetups);
		// Run our specified battle and let it run till it is over
		engine.runBattle(battleSpec, true); // waits till the battle finishes
		// Cleanup our RobocodeEngine
		engine.close();
		// Make sure that the Java VM is shut down properly
		System.exit(0);
	}
}