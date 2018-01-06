package org.usfirst.frc.team1619.robot.states.Shooter2;

import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.controllers.Shot;
import org.usfirst.frc.team1619.robot.framework.state.State;

public class ShooterSet extends State {

	private Shot shot;

	public ShooterSet(Shot shot, int profile) {
		this.setProfile(profile);
		this.setShot(shot);
	}

	public void setShot(Shot shot) {
		this.shot = shot;
		IO.shooterVelocityController.set(this.shot.rpm);
	}

	public void setProfile(int profile) {
		IO.shooterVelocityController.setProfile(profile);
	}

	public double getError() {
		return this.shot.rpm - in.getNumeric(IO.SHOOTER_VELOCITY);
	}

	@Override
	protected void initialize() {
		IO.shooterVelocityController.initialize();
	}

	@Override
	protected void update() {
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
