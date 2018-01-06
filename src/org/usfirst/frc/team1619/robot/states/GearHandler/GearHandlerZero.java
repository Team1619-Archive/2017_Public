package org.usfirst.frc.team1619.robot.states.GearHandler;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.IO.sensors.EncoderPositionSensor;
import org.usfirst.frc.team1619.robot.framework.state.State;
import org.usfirst.frc.team1619.robot.framework.state.StateWrapper;
import org.usfirst.frc.team1619.robot.framework.util.Timer;

public class GearHandlerZero extends State {

	public static final StateWrapper<GearHandlerZero> WRAPPER = new StateWrapper<GearHandlerZero>(GearHandlerZero.class) {

		@Override
		public boolean isReady() {
			return in.getBoolean(IO.DRIVER_BUTTONS[Constants.DRIVER_BUTTON_GEAR_HANDLER_ZERO]) || in.getBoolean(Constants.OPERATOR_BUTTON_GEAR_HANDLER_ZERO);
		}

	};

	private Timer timer = new Timer();

	@Override
	protected void initialize() {
		this.timer.start(Constants.GEAR_HANDLER_PIVOT_ZERO_TIME);
	}

	@Override
	protected void update() {
		out.motors.set(IO.GEAR_HANDLER_PIVOT, Constants.GEAR_HANDLER_PIVOT_ZERO_OUTPUT);
		out.motors.set(IO.GEAR_HANDLER_INTAKE, Constants.GEAR_HANDLER_INTAKE_OUTPUT);
	}

	@Override
	protected boolean isDone() {
		return this.timer.isDone() && Math.abs(in.getNumeric(IO.GEAR_HANDLER_PIVOT_VELOCITY)) < 0.01;
	}

	@Override
	protected void dispose() {
		((EncoderPositionSensor) in.getNumericSensor(IO.GEAR_HANDLER_PIVOT_POSITION)).zero();
	}

}
