package org.usfirst.frc.team1619.robot.states.Shooter;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.controllers.Shot;
import org.usfirst.frc.team1619.robot.framework.state.State;
import org.usfirst.frc.team1619.robot.framework.util.Timer;

public class ShooterSpool extends State {

	private static final Shot SPOOL_SHOT = new Shot(0, 8500, false);

	private Shot shot = null;
	private Timer timer = new Timer();

	public void setShot(Shot shot) {
		this.shot = shot;
	}

	public boolean isSpooled() {
		return this.timer.isDone();
	}

	public double getError() {
		return IO.shooterVelocityController.getError();
	}

	@Override
	protected void initialize() {
		IO.shooterVelocityController.setProfile(IO.SHOOTER_PROFILE_SPOOL);
		IO.shooterVelocityController.initialize();
	}

	@Override
	protected void update() {
		IO.shooterVelocityController.set(this.shot == null ? SPOOL_SHOT.rpm : this.shot.rpm);
		IO.shooterVelocityController.update();

		if (this.shot != null && Math.abs(IO.shooterVelocityController.getError()) < Constants.SHOOTING_SPOOL_ERROR_THRESHOLD) {
			if (!this.timer.isStarted()) {
				this.timer.start(250);
			}
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
