package org.usfirst.frc.team1619.robot.states.Intake;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.state.State;
import org.usfirst.frc.team1619.robot.framework.state.StateWrapper;

public class IntakeManual extends State {

	public static final StateWrapper<IntakeManual> WRAPPER = new StateWrapper<IntakeManual>(IntakeManual.class) {

		@Override
		public boolean isReady() {
			return true;
		}

	};

	@Override
	protected void update() {
		if (in.getBoolean(Constants.OPERATOR_BUTTON_INTAKE)) {
			out.motors.set(IO.INTAKE, 1.0);
			out.motors.set(IO.HOPPER, -0.5);
		}
		else if (in.getBoolean(Constants.OPERATOR_INTAKE_REVERSE)) {
			out.motors.set(IO.INTAKE, -1.0);
		}
	}

	@Override
	protected boolean isDone() {
		return false;
	}

}
