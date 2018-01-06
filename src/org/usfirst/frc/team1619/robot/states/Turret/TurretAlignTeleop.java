package org.usfirst.frc.team1619.robot.states.Turret;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.state.StateWrapper;

public class TurretAlignTeleop extends TurretAlign {

	public static final StateWrapper<TurretAlignTeleop> WRAPPER = new StateWrapper<TurretAlignTeleop>(TurretAlignTeleop.class) {

		@Override
		public boolean isReady() {
			return in.getBoolean(IO.DRIVER_BUTTONS[Constants.DRIVER_BUTTON_TURRET_ALIGN]);
		}

	};

	public TurretAlignTeleop() {
		super(true, 1.0);
	}

	@Override
	protected boolean isDone() {
		return !in.getBoolean(IO.DRIVER_BUTTONS[Constants.DRIVER_BUTTON_TURRET_ALIGN]);
	}

}
