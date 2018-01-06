package org.usfirst.frc.team1619.robot.states.Multi.GearAndHopper;

import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.IO.sensors.NavxHeadingSensor;
import org.usfirst.frc.team1619.robot.framework.state.State;
import org.usfirst.frc.team1619.robot.framework.util.TrapezoidTrajectory;

public class DriveRotateToGear extends State {

	private TrapezoidTrajectory trajectory;
	private double targetAngle;

	public DriveRotateToGear(boolean red) {
		this.targetAngle = red ? -58.0 : 58.0;
	}

	@Override
	protected void initialize() {
		double error = ((NavxHeadingSensor) IO.in.getNumericSensor(IO.NAVX_HEADING)).get() - this.targetAngle;
		this.trajectory = new TrapezoidTrajectory(Math.abs(error), 3.5, 0.4, false);
		this.trajectory.calculate(250);
		IO.driveLeftController.setProfile(IO.DRIVE_LEFT_PROFILE_DEFAULT);
		IO.driveRightController.setProfile(IO.DRIVE_RIGHT_PROFILE_DEFAULT);
		IO.driveRotationController.initialize(error > 0, this.trajectory);
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
