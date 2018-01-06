package org.usfirst.frc.team1619.robot.states.GearHandler;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.state.State;
import org.usfirst.frc.team1619.robot.framework.state.StateWrapper;

public class GearHandlerManual extends State {

	public static final StateWrapper<GearHandlerManual> WRAPPER = new StateWrapper<GearHandlerManual>(GearHandlerManual.class) {

		@Override
		public boolean isReady() {
			return true;
		}

	};

	@Override
	protected void update() {
		if (in.getBoolean(Constants.OPERATOR_BUTTON_GEAR_HANDLER_TOGGLE)) {
			out.motors.set(IO.GEAR_HANDLER_PIVOT, 0.25);
			out.motors.set(IO.GEAR_HANDLER_INTAKE, 0.25);
		}
		else if (in.getBoolean(IO.OPERATOR_BUTTON_A)) {
			out.motors.set(IO.GEAR_HANDLER_PIVOT, -0.25);
			out.motors.set(IO.GEAR_HANDLER_INTAKE, -0.25);
		}
	}

	@Override
	protected boolean isDone() {
		return false;
	}

}
