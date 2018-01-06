package org.usfirst.frc.team1619.robot.states.HopperExtension;

import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.state.State;
import org.usfirst.frc.team1619.robot.framework.state.StateWrapper;

public class HopperExtensionIdle extends State {

	public static final StateWrapper<HopperExtensionIdle> WRAPPER = new StateWrapper<HopperExtensionIdle>(HopperExtensionIdle.class) {

		@Override
		public boolean isReady() {
			return true;
		}

	};

	@Override
	protected void update() {
		out.solenoids.set(IO.HOPPER_EXTEND_SOLENOID, false);
	}

	@Override
	protected boolean isDone() {
		return false;
	}

}
