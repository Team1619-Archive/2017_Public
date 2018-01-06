package org.usfirst.frc.team1619.robot.states.Intake;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.state.State;
import org.usfirst.frc.team1619.robot.framework.state.StateWrapper;

public class IntakeExtend extends State {

	public static final StateWrapper<IntakeExtend> WRAPPER = new StateWrapper<IntakeExtend>(IntakeExtend.class) {

		@Override
		public boolean isReady() {
			return !(state.intakeExtended || state.gearHandlerExtended) && in.getBooleanAndDelta(Constants.OPERATOR_BUTTON_INTAKE_TOGGLE, true);
		}

	};

	@Override
	protected void update() {
		out.solenoids.set(IO.INTAKE_SOLENOID, true);
	}

	@Override
	protected boolean isDone() {
		return true;
	}

	@Override
	public void dispose() {
		state.intakeExtended = true;
	}

}
