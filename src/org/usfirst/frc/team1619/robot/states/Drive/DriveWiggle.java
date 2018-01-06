package org.usfirst.frc.team1619.robot.states.Drive;

import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.state.State;
import org.usfirst.frc.team1619.robot.framework.util.Timer;

public class DriveWiggle extends State {

	private Timer timer = new Timer();
	private boolean left;

	@Override
	protected void initialize() {
		this.left = true;
		this.timer.start(250);
	}

	@Override
	protected void update() {
		if (this.timer.isDone()) {
			this.left = !this.left;
			this.timer.start(250);
		}

		out.motors.set(IO.DRIVE_LEFT, this.left ? -0.3 : 0.0);
		out.motors.set(IO.DRIVE_LEFT_2, this.left ? -0.3 : 0.0);
		out.motors.set(IO.DRIVE_RIGHT, this.left ? 0.0 : -0.3);
		out.motors.set(IO.DRIVE_RIGHT_2, this.left ? 0.0 : -0.3);
	}

	@Override
	protected boolean isDone() {
		return false;
	}

}
