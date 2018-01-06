package org.usfirst.frc.team1619.robot.states.Elevator;

import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.state.State;

public class ElevatorSet extends State {

	private double velocity;

	public ElevatorSet(double velocity) {
		this.velocity = velocity;
	}

	public void setProfile(int profile) {
		IO.elevatorController.setProfile(profile);
	}

	@Override
	protected void initialize() {
		IO.elevatorVelocityController.set(this.velocity);
	}

	@Override
	protected void update() {
		IO.elevatorVelocityController.update();
	}

	@Override
	protected boolean isDone() {
		return false;
	}

}
