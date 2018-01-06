package org.usfirst.frc.team1619.robot.states.Multi.Shoot;

import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.state.State;

public class ShooterSet extends State {

	private double rpm;

	public ShooterSet(double rpm) {
		this.rpm = rpm;
	}

	@Override
	protected void initialize() {
		IO.shooterVelocityController.setProfile(IO.SHOOTER_PROFILE_SHOOT);
		IO.shooterVelocityController.initialize();
	}

	@Override
	protected void update() {
		IO.shooterVelocityController.set(this.rpm);
		IO.shooterVelocityController.update();
	}

	@Override
	protected boolean isDone() {
		return false;
	}

	@Override
	protected void dispose() {
		IO.shooterVelocityController.dispose();
	}

}
