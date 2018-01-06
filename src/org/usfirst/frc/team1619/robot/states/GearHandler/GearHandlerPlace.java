package org.usfirst.frc.team1619.robot.states.GearHandler;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.state.State;

public class GearHandlerPlace extends State {

	@Override
	protected void update() {
		out.motors.set(IO.GEAR_HANDLER_INTAKE, Constants.GEAR_HANDLER_PLACEMENT_INTAKE_OUTPUT);

		double pivotPosition = in.getNumeric(IO.GEAR_HANDLER_PIVOT_POSITION);
		if (pivotPosition < 80.0) {
			out.motors.set(IO.GEAR_HANDLER_PIVOT, Constants.GEAR_HANDLER_PLACEMENT_PIVOT_OUTPUT);
		}
		else if (pivotPosition < Constants.GEAR_HANDLER_PIVOT_INTAKE - 10.0) {
			out.motors.set(IO.GEAR_HANDLER_PIVOT, 0.5);
		}
	}

	@Override
	protected boolean isDone() {
		return in.getBooleanAndDelta(Constants.OPERATOR_BUTTON_GEAR_HANDLER_TOGGLE, true);
	}

}
