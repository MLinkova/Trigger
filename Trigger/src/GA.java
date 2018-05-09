import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

import org.jgap.*;
import org.jgap.impl.*;
import org.omg.CORBA.DoubleSeqHolder;

import robocode.control.*;

public class GA extends FitnessFunction {

	private int SuperTrackerScore;
	private int SuperRamFireScore;

	public GA(int trackerScore, int ramScore) {
		SuperTrackerScore = trackerScore;
		SuperRamFireScore = ramScore;
	}

	private void writeToFile(IChromosome chromosome) {
		System.out.println("Zapisujem");
		double[] parameter = new double[4];
		int j = 0;
		for (Gene gene : chromosome.getGenes()) {
			parameter[j] = (double) gene.getAllele();
			System.out.println(parameter[j]);
			j++;
		}

		File parameters = new File("SuperTrackerParamters.txt");
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(parameters);
			for (int i = 0; i < parameter.length; i++) {
				pw.print(parameter[i] + " ");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}

	}

	public void setScoreToSuperTracker(int score) {
		System.out.println("Volam set tracker " + score);
		SuperTrackerScore = score;
		System.out.println("SuperTracker score..." + SuperTrackerScore);

	}

	public void setScoreToSuperRamFire(int score) {
		System.out.println("Volam set Ram " + score);
		SuperRamFireScore = score;
		System.out.println("SuperRam score.... " + SuperRamFireScore);

	}

	private void run() throws InvalidConfigurationException {
		Configuration con = new DefaultConfiguration();
		con.addGeneticOperator(new MutationOperator(con, 20));
		con.setPreservFittestIndividual(true);
		con.setFitnessFunction(this);

		Gene[] genes = new Gene[4];
		genes[0] = new DoubleGene(con, 0.0, Double.MAX_VALUE);
		genes[0].setAllele(150.0);
		genes[1] = new DoubleGene(con, 0.0, Double.MAX_VALUE);
		genes[1].setAllele(0.1);
		genes[2] = new DoubleGene(con, 0, Double.MAX_VALUE);
		genes[2].setAllele(12.0);
		genes[3] = new DoubleGene(con, 0, Double.MAX_VALUE);
		genes[3].setAllele(12.0);

		IChromosome chrommosone = new Chromosome(con, genes);
		con.setSampleChromosome(chrommosone);

		con.setPopulationSize(4);

		Genotype population = Genotype.randomInitialGenotype(con);
		IChromosome fittestSolution = null;

		for (int gen = 0; gen < 2; gen++) {
			population.evolve(); // evolve population
			fittestSolution = population.getFittestChromosome(); // find fittest
																	// of
																	// population
			System.out.printf("\nafter %d generations the best solution is %s \n", gen + 1, fittestSolution);
		}

		writeToFile(fittestSolution); // pass best solution to build

	}

	@Override
	protected double evaluate(IChromosome chromosome) {
		writeToFile(chromosome);

		RobocodeEngine engine = new RobocodeEngine(new java.io.File("C:/robocode"));
		engine.addBattleListener(new BattleFitnessListener());
		engine.setVisible(true);

		BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600);
		RobotSpecification[] selectedRobots = engine.getLocalRepository("robots.SuperRamFire*,robots.SuperTracker*");
		RobotSetup[] robotSetups = new RobotSetup[2];
		Random r = new Random(Constants.seed);
		for (int i = 0; i < selectedRobots.length; i++) {
			double x = r.nextDouble();
			double y = r.nextDouble();
			robotSetups[i] = new RobotSetup(x, y, 0.0);
		}

		BattleSpecification battleSpec = new BattleSpecification(battlefield, Constants.NUMBER_ROUNDS,
				Constants.INACTIVITY_TIME, Constants.GUN_COOL_RATE, Constants.SENTRY_SIZE, false, selectedRobots,
				robotSetups);

		engine.runBattle(battleSpec, true);
		engine.close();

		double fitnesValue = SuperTrackerScore;

		return fitnesValue;
	}

	public static void main(String[] args) throws InvalidConfigurationException {
		GA g = new GA(0, 0);
		g.run();
	}

}
