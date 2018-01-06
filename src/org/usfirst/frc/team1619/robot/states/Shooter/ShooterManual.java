package org.usfirst.frc.team1619.robot.states.Shooter;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.state.State;
import org.usfirst.frc.team1619.robot.framework.state.StateWrapper;

public class ShooterManual extends State {

	public static final StateWrapper<ShooterManual> WRAPPER = new StateWrapper<ShooterManual>(ShooterManual.class) {

		@Override
		public boolean isReady() {
			return true;
		}

	};

	@Override
	protected void update() {
		if (IO.in.getBooleanAndDelta(IO.DRIVER_BUTTONS[Constants.DRIVER_BUTTON_INCREASE_SHOOTING_PERCENTAGE], true)) {
			state.shootingPercentage += 0.0025;
		}
		else if (IO.in.getBooleanAndDelta(IO.DRIVER_BUTTONS[Constants.DRIVER_BUTTON_DECREASE_SHOOTING_PERCENTAGE], true)) {
			state.shootingPercentage -= 0.0025;
		}

		if (in.getBoolean(IO.DRIVER_BUTTONS[Constants.DRIVER_BUTTON_SHOOTER_FORWARD])) {
			out.motors.set(IO.SHOOTER, 0.5);
			out.motors.set(IO.SHOOTER_2, 0.5);
		}
		else if (in.getBoolean(IO.DRIVER_BUTTONS[Constants.DRIVER_BUTTON_SHOOTER_REVERSE])) {
			out.motors.set(IO.SHOOTER, -0.5);
			out.motors.set(IO.SHOOTER_2, -0.5);
		}
	}

	@Override
	protected boolean isDone() {
		return false;
	}

}
