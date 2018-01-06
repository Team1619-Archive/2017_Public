package org.usfirst.frc.team1619.robot.states.Multi;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.SubsystemID;
import org.usfirst.frc.team1619.robot.framework.state.MultiSubsystemState;
import org.usfirst.frc.team1619.robot.framework.util.Timer;

public class MultiFeed extends MultiSubsystemState {

	private boolean elevatorAtSpeed = true;

	private Timer timer = new Timer();

	@Override
	protected void initialize() {
		IO.elevatorController.setProfile(IO.ELEVATOR_PROFILE_DEFAULT);
		IO.elevatorVelocityController.set(Constants.SHOOTING_ELEVATOR_FEED_SPEED);
	}

	@Override
	protected void update() {
		if (!this.elevatorAtSpeed && Math.abs(IO.elevatorController.getError(in.getNumeric(IO.ELEVATOR_VELOCITY))) < 100.0) {
			this.timer.start(10000);
			this.elevatorAtSpeed = true;
		}

		if (this.elevatorAtSpeed) {
			out.motors.set(IO.HOPPER, 0.9);
		}

		if (!this.timer.isDone()) {
			IO.elevatorVelocityController.update();
		}
	}

	@Override
	protected boolean isDone() {
		return false;
	}

	@Override
	protected SubsystemID[] getSubsystemIDs() {
		return new SubsystemID[] { SubsystemID.HOPPER, SubsystemID.ELEVATOR };
	}

}
