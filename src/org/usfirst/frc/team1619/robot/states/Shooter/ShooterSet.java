package org.usfirst.frc.team1619.robot.states.Shooter;

import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.controllers.Shot;
import org.usfirst.frc.team1619.robot.framework.state.State;

public class ShooterSet extends State {

	private Shot shot;

	public void setShot(Shot shot) {
		this.shot = shot;
	}

	public double getError() {
		return IO.shooterVelocityController.getError();
	}

	@Override
	protected void initialize() {
		IO.shooterVelocityController.setProfile(IO.SHOOTER_PROFILE_SHOOT);
		IO.shooterVelocityController.initialize();
	}

	@Override
	protected void update() {
		IO.shooterVelocityController.set((this.shot == null ? 0.0 : this.shot.rpm) * state.shootingPercentage);

		if (this.shot != null) {
			IO.shooterVelocityController.update();
		}
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
