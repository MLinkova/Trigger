import robocode.BattleResults;
import robocode.control.events.BattleAdaptor;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.BattleErrorEvent;
import robocode.control.events.BattleFinishedEvent;
import robocode.control.events.BattleMessageEvent;
import robocode.control.events.BattlePausedEvent;
import robocode.control.events.BattleResumedEvent;
import robocode.control.events.BattleStartedEvent;
import robocode.control.events.IBattleListener;
import robocode.control.events.RoundEndedEvent;
import robocode.control.events.RoundStartedEvent;
import robocode.control.events.TurnEndedEvent;
import robocode.control.events.TurnStartedEvent;

public class BattleFitnessListener extends BattleAdaptor {

	@Override
	public void onBattleCompleted(BattleCompletedEvent event) {
		int trackerScore = 0, ramScore = 0;

		for (BattleResults result : event.getSortedResults()) {
			if ("robots.SuperRamFire*".equals(result.getTeamLeaderName())) {
				System.out.println("SuperRam score " + result.getScore());
				// robot.setScoreToSuperRamFire(result.getScore());
				ramScore = result.getScore();
			} else {
				// robot.setScoreToSuperTracker(result.getScore());
				trackerScore = result.getScore();
				System.out.println("SuperTracker score " + result.getScore());
			}
		}
		
		GA robot = new GA(trackerScore, ramScore);

	}

	@Override
	public void onBattleError(BattleErrorEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBattleFinished(BattleFinishedEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBattleMessage(BattleMessageEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBattlePaused(BattlePausedEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBattleResumed(BattleResumedEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBattleStarted(BattleStartedEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRoundEnded(RoundEndedEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRoundStarted(RoundStartedEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTurnEnded(TurnEndedEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTurnStarted(TurnStartedEvent arg0) {
		// TODO Auto-generated method stub

	}

}
