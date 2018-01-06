package org.usfirst.frc.team1619.robot.states.Drive;

import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.state.State;

public class DriveSet extends State {

	private double output;

	public DriveSet(double output) {
		this.output = output;
	}

	@Override
	protected void update() {
		out.motors.set(IO.DRIVE_LEFT, output);
		out.motors.set(IO.DRIVE_LEFT_2, output);
		out.motors.set(IO.DRIVE_RIGHT, output);
		out.motors.set(IO.DRIVE_RIGHT_2, output);
	}

	@Override
	protected boolean isDone() {
		return false;
	}

}
