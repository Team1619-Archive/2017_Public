package org.usfirst.frc.team1619.robot.states.Drive;

import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.state.State;
import org.usfirst.frc.team1619.robot.framework.util.CircleTrajectory;
import org.usfirst.frc.team1619.robot.framework.util.Timer;

public class DriveCircle extends State {

	private CircleTrajectory trajectory;
	private Timer timer = new Timer();

	public DriveCircle(CircleTrajectory trajectory) {
		this.trajectory = trajectory;
	}

	protected void initialize() {
		this.trajectory.calculate(250);
		IO.headingController.setProfile(IO.HEADING_PROFILE_DEFAULT);
		IO.driveLeftController.setProfile(IO.DRIVE_LEFT_PROFILE_DEFAULT);
		IO.driveRightController.setProfile(IO.DRIVE_RIGHT_PROFILE_DEFAULT);
		IO.driveCircleController.initialize(this.trajectory);

		this.timer.start(100);
	}

	@Override
	protected void update() {
		IO.driveCircleController.update();
	}

	@Override
	protected boolean isDone() {
		return this.timer.isDone() && IO.driveCircleController.isDone();
	}

	@Override
	protected void dispose() {
		IO.driveCircleController.dispose();
	}

}
