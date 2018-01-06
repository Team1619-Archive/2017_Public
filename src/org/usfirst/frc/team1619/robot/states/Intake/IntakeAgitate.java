package org.usfirst.frc.team1619.robot.states.Intake;

import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.state.State;
import org.usfirst.frc.team1619.robot.framework.util.Timer;

public class IntakeAgitate extends State {

	private Timer timer = new Timer();
	private boolean extended = true;

	@Override
	protected void initialize() {
		this.timer.start(2000);
		state.intakeExtended = true;
	}

	@Override
	protected void update() {
		if (this.timer.isDone()) {
			this.extended = !this.extended;
			if (this.extended) {
				this.timer.start(2000);
			}
			else {
				this.timer.start(500);
			}
		}

		out.solenoids.set(IO.INTAKE_SOLENOID, this.extended);
	}

	@Override
	protected void dispose() {
		state.intakeExtended = true;
		out.solenoids.set(IO.INTAKE_SOLENOID, true);
	}

	@Override
	protected boolean isDone() {
		return false;
	}

}
