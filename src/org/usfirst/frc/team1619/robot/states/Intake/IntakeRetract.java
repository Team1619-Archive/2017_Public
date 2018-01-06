package org.usfirst.frc.team1619.robot.states.Intake;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.state.State;
import org.usfirst.frc.team1619.robot.framework.state.StateWrapper;

public class IntakeRetract extends State {

	public static final StateWrapper<IntakeRetract> WRAPPER = new StateWrapper<IntakeRetract>(IntakeRetract.class) {

		@Override
		public boolean isReady() {
			return state.intakeExtended && (in.getBooleanAndDelta(Constants.OPERATOR_BUTTON_INTAKE_TOGGLE, true) || in.getBooleanAndDelta((Constants.OPERATOR_BUTTON_GEAR_HANDLER_TOGGLE), true) || in.getBooleanAndDelta((Constants.OPERATOR_BUTTON_GEAR_HANDLER_TOGGLE_PLACEMENT), true));
		}

	};

	@Override
	protected void update() {
		out.solenoids.set(IO.INTAKE_SOLENOID, false);
	}

	@Override
	protected boolean isDone() {
		return true;
	}

	@Override
	protected void dispose() {
		state.intakeExtended = false;
	}

}
