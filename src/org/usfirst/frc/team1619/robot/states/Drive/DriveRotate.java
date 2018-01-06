package org.usfirst.frc.team1619.robot.states.Drive;

import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.state.State;
import org.usfirst.frc.team1619.robot.framework.util.TrapezoidTrajectory;

public class DriveRotate extends State {

	private TrapezoidTrajectory trajectory;
	private boolean left;

	public DriveRotate(double angle) {
		this.trajectory = new TrapezoidTrajectory(Math.abs(angle), 3.5, 0.4, false);
		this.trajectory.calculate(250);
		this.left = angle < 0;
	}

	@Override
	protected void initialize() {
		IO.driveLeftController.setProfile(IO.DRIVE_LEFT_PROFILE_DEFAULT);
		IO.driveRightController.setProfile(IO.DRIVE_RIGHT_PROFILE_DEFAULT);
		IO.driveRotationController.initialize(this.left, this.trajectory);
	}

	@Override
	protected void update() {
		IO.driveRotationController.update();
	}

	@Override
	protected boolean isDone() {
		return IO.driveRotationController.isDone();
	}

	@Override
	protected void dispose() {
		IO.driveRotationController.dispose();
	}

}
