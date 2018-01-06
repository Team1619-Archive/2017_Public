package org.usfirst.frc.team1619.robot.states.Turret;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.IO.sensors.EncoderPositionSensor;
import org.usfirst.frc.team1619.robot.framework.state.State;
import org.usfirst.frc.team1619.robot.framework.state.StateWrapper;

public class TurretZero extends State {

	public static final StateWrapper<TurretZero> WRAPPER = new StateWrapper<TurretZero>(TurretZero.class) {

		@Override
		public boolean isReady() {
			return in.getBoolean(IO.DRIVER_BUTTONS[Constants.DRIVER_BUTTON_TURRET_ZERO]);
		}

	};

	@Override
	protected void update() {
		out.motors.set(IO.TURRET, Constants.TURRET_ZERO_OUTPUT);
	}

	@Override
	protected boolean isDone() {
		return in.getBoolean(IO.TURRET_LEFT_LIMIT_SWITCH);
	}

	@Override
	protected void dispose() {
		((EncoderPositionSensor) in.getNumericSensor(IO.TURRET_POSITION)).zero();
	}

}
