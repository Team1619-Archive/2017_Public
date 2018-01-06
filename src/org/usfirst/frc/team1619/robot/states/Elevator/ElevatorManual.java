package org.usfirst.frc.team1619.robot.states.Elevator;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.state.State;
import org.usfirst.frc.team1619.robot.framework.state.StateWrapper;

public class ElevatorManual extends State {

	public static StateWrapper<ElevatorManual> WRAPPER = new StateWrapper<ElevatorManual>(ElevatorManual.class) {

		@Override
		public boolean isReady() {
			return true;
		}

	};

	@Override
	protected void update() {
		if (in.getBoolean(IO.DRIVER_BUTTONS[Constants.DRIVER_BUTTON_ELEVATOR_FORWARD])) {
			out.motors.set(IO.ELEVATOR, 1.0);
		}
		else if (in.getBoolean(IO.DRIVER_BUTTONS[Constants.DRIVER_BUTTON_ELEVATOR_REVERSE])) {
			out.motors.set(IO.ELEVATOR, -1.0);
		}
	}

	@Override
	protected boolean isDone() {
		return false;
	}

}
