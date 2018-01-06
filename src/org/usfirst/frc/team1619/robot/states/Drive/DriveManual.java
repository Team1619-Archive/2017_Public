package org.usfirst.frc.team1619.robot.states.Drive;

import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.state.State;
import org.usfirst.frc.team1619.robot.framework.state.StateWrapper;

public class DriveManual extends State {

	public static final StateWrapper<DriveManual> WRAPPER = new StateWrapper<DriveManual>(DriveManual.class) {

		@Override
		public boolean isReady() {
			return true;
		}

	};

	@Override
	protected void update() {
		if (in.getBooleanAndDelta(IO.DRIVER_BUTTONS[1], true)) {
			out.solenoids.set(IO.DRIVE_SHIFT_SOLENOID, true);
		}
		else if (in.getBooleanAndDelta(IO.DRIVER_BUTTONS[1], false)) {
			out.solenoids.set(IO.DRIVE_SHIFT_SOLENOID, false);
		}

		if (in.getBooleanAndDelta(IO.DRIVER_BUTTONS[2], true)) {
			state.driveInverted = !state.driveInverted;
		}

		double twist = in.getNumeric(IO.DRIVER_Z);
		if (state.driveInverted) {
			IO.drive.update(in.getNumeric(IO.DRIVER_Y), (twist < 0 ? -1 : 1) * Math.pow(Math.abs(twist), 1.5));
		}
		else {
			IO.drive.update(-1.0 * in.getNumeric(IO.DRIVER_Y), (twist < 0 ? -1 : 1) * Math.pow(Math.abs(twist), 1.5));
		}
	}

	@Override
	protected boolean isDone() {
		return false;
	}

}
