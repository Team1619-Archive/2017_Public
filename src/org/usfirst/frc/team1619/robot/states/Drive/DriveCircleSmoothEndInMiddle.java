package org.usfirst.frc.team1619.robot.states.Drive;

import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.util.CircleTrajectory;
import org.usfirst.frc.team1619.robot.framework.util.Timer;

public class DriveCircleSmoothEndInMiddle extends DriveCircleSmooth {

	private double interruptAngle;
	private Timer timer = new Timer();

	public DriveCircleSmoothEndInMiddle(CircleTrajectory trajectory, double interruptAngle) {
		super(trajectory);

		this.interruptAngle = interruptAngle;
	}

	@Override
	protected void initialize() {
		super.initialize();

		this.timer.start(250);
	}

	@Override
	protected boolean isDone() {
		return this.timer.isDone() && Math.abs(in.getNumeric(IO.NAVX_HEADING)) > this.interruptAngle;
	}

}
