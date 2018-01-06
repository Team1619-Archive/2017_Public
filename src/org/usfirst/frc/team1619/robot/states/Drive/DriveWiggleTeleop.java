package org.usfirst.frc.team1619.robot.states.Drive;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.state.StateWrapper;

public class DriveWiggleTeleop extends DriveWiggle {

	public static final StateWrapper<DriveWiggleTeleop> WRAPPER = new StateWrapper<DriveWiggleTeleop>(DriveWiggleTeleop.class) {

		@Override
		public boolean isReady() {
			return in.getBoolean(IO.DRIVER_BUTTONS[Constants.DRIVER_BUTTON_DRIVE_WIGGLE]);
		}

	};

	@Override
	protected boolean isDone() {
		return !in.getBoolean(IO.DRIVER_BUTTONS[Constants.DRIVER_BUTTON_DRIVE_WIGGLE]);
	}

}
