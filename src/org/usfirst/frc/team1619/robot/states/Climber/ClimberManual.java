package org.usfirst.frc.team1619.robot.states.Climber;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.controllers.LED.LEDs;
import org.usfirst.frc.team1619.robot.framework.state.State;
import org.usfirst.frc.team1619.robot.framework.state.StateWrapper;

public class ClimberManual extends State {

	public static final StateWrapper<ClimberManual> WRAPPER = new StateWrapper<ClimberManual>(ClimberManual.class) {

		@Override
		public boolean isReady() {
			return true;
		}

	};

	private LEDs leds = LEDs.getInstance();

	@Override
	protected void update() {
		if (in.getBoolean(Constants.OPERATOR_BUTTON_CLIMB)) {
			out.motors.set(IO.CLIMBER, 1.0);
			this.leds.setState(LEDs.LEDStateID.CLIMB);
		}
		else if (in.getBooleanAndDelta(Constants.OPERATOR_BUTTON_CLIMB, false)) {
			this.leds.setState(LEDs.LEDStateID.IDLE);
		}
	}

	@Override
	protected boolean isDone() {
		return false;
	}

	@Override
	protected void dispose() {
		this.leds.setState(LEDs.LEDStateID.IDLE);
	}

}
