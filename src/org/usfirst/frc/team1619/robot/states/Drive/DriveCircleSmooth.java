package org.usfirst.frc.team1619.robot.states.Drive;

import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.util.CircleTrajectory;
import org.usfirst.frc.team1619.robot.framework.util.Timer;

public class DriveCircleSmooth extends DriveCircle {

	private double switchAngle;

	private Timer timer = new Timer();
	private boolean hasWaitedForZero = false;

	public DriveCircleSmooth(CircleTrajectory trajectory) {
		super(trajectory);

		this.switchAngle = trajectory.getInteriorAngle() - 10.0;
	}

	@Override
	protected void initialize() {
		super.initialize();

		this.timer.start(100);
	}

	@Override
	protected void update() {
		super.update();

		if (!this.hasWaitedForZero && this.timer.isDone()) {
			this.hasWaitedForZero = true;
			this.timer.reset();
		}

		if (this.hasWaitedForZero && !this.timer.isStarted() && Math.abs(in.getNumeric(IO.NAVX_HEADING)) >= this.switchAngle) {
			IO.headingController.setProfile(IO.HEADING_PROFILE_DAMPENED);
			this.timer.start(500);
		}
	}

	@Override
	protected boolean isDone() {
		return this.hasWaitedForZero && this.timer.isDone();
	}

}
