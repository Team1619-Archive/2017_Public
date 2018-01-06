package org.usfirst.frc.team1619.robot.states.Multi;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.SubsystemID;
import org.usfirst.frc.team1619.robot.framework.state.MultiSubsystemState;
import org.usfirst.frc.team1619.robot.framework.state.MultiSubsystemStateWrapper;

public class MultiOperatorManual extends MultiSubsystemState {

	public static final MultiSubsystemStateWrapper<MultiOperatorManual> WRAPPER = new MultiSubsystemStateWrapper<MultiOperatorManual>(MultiOperatorManual.class) {

		@Override
		public boolean isReady() {
			return in.getBoolean(Constants.OPERATOR_BUTTON_MANUAL);
		}

	};

	@Override
	protected void update() {
		out.solenoids.set(IO.INTAKE_SOLENOID, false);

		double yAxis = in.getNumeric(IO.OPERATOR_LEFT_AXIS_Y);
		if (Math.abs(yAxis) > 0.2) {
			out.motors.set(IO.GEAR_HANDLER_PIVOT, -yAxis / 2.0);
		}

		double xAxis = in.getNumeric(IO.OPERATOR_RIGHT_AXIS_X);
		if (Math.abs(xAxis) > 0.2) {
			out.motors.set(IO.TURRET, xAxis / 4.0);
			out.motors.enforceLimit(IO.TURRET, in.getBoolean(IO.TURRET_LEFT_LIMIT_SWITCH), in.getBoolean(IO.TURRET_RIGHT_LIMIT_SWITCH));
		}

		if (in.getBoolean(Constants.OPERATOR_BUTTON_MANUAL_GEAR_INTAKE_OUT)) {
			out.motors.set(IO.GEAR_HANDLER_INTAKE, -1.0);
		}
		else if (in.getBoolean(Constants.OPERATOR_BUTTON_MANUAL_GEAR_INTAKE_IN)) {
			out.motors.set(IO.GEAR_HANDLER_INTAKE, 1.0);
		}
	}

	@Override
	protected boolean isDone() {
		return !in.getBoolean(Constants.OPERATOR_BUTTON_MANUAL);
	}

	@Override
	protected SubsystemID[] getSubsystemIDs() {
		return new SubsystemID[] { SubsystemID.TURRET, SubsystemID.GEAR_HANDLER };
	}

}
