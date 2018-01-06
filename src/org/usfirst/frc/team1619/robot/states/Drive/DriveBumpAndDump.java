package org.usfirst.frc.team1619.robot.states.Drive;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.util.CircleTrajectory;
import org.usfirst.frc.team1619.robot.framework.util.Timer;

public class DriveBumpAndDump extends DriveCircle {

	private Timer timer = new Timer();
	private boolean hasWaitedForZero = false;

	public DriveBumpAndDump(boolean red) {
		super(new CircleTrajectory(red ? Constants.AUTO_BUMP_AND_DUMP_CIRCLE_RADIUS_RED : Constants.AUTO_BUMP_AND_DUMP_CIRCLE_RADIUS_BLUE, Constants.AUTO_BUMP_AND_DUMP_CIRCLE_INTERNAL_ANGLE, !red, 31.0 / 12.0, 8.0, 10.0, 10.0, !red));
	}

	public boolean getAlmostThere() {
		return this.hasWaitedForZero && this.timer.isStarted();
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

		if (this.hasWaitedForZero && !this.timer.isStarted() && Math.abs(in.getNumeric(IO.NAVX_HEADING)) >= 85.0) {
			IO.headingController.setProfile(IO.HEADING_PROFILE_DAMPENED);
			this.timer.start(1000);
		}
	}

	@Override
	protected boolean isDone() {
		return this.hasWaitedForZero && this.timer.isDone();
	}

}
