package org.usfirst.frc.team1619.robot.states.Drive;

import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.state.State;
import org.usfirst.frc.team1619.robot.framework.util.TrapezoidTrajectory;

public class DriveStraight extends State {

	private TrapezoidTrajectory trajectory;
	
	public DriveStraight(TrapezoidTrajectory trajectory) {
		this.trajectory = trajectory;
	}

	@Override
	protected void initialize() {
		this.trajectory.calculate(250);
		IO.driveLeftController.setProfile(IO.DRIVE_LEFT_PROFILE_DEFAULT);
		IO.driveRightController.setProfile(IO.DRIVE_RIGHT_PROFILE_DEFAULT);
		IO.driveStraightController.initialize(this.trajectory);
	}

	@Override
	protected void update() {
		IO.driveStraightController.update();
	}

	@Override
	protected boolean isDone() {
		return IO.driveStraightController.isDone();
	}

	@Override
	protected void dispose() {
		IO.driveStraightController.dispose();
	}

}
