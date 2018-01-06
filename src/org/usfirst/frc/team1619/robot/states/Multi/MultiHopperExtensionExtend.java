package org.usfirst.frc.team1619.robot.states.Multi;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.state.MultiSubsystemStateWrapper;

public class MultiHopperExtensionExtend extends MultiHopperExtensionSet {

	public static final MultiSubsystemStateWrapper<MultiHopperExtensionExtend> WRAPPER = new MultiSubsystemStateWrapper<MultiHopperExtensionExtend>(MultiHopperExtensionExtend.class) {

		@Override
		public boolean isReady() {
			return !state.intakeExtended && in.getBoolean(IO.DRIVER_BUTTONS[Constants.DRIVER_BUTTON_HOPPER_EXTENSION_EXTEND]);
		}

	};

	public MultiHopperExtensionExtend() {
		super(true);
	}

	@Override
	protected boolean isDone() {
		return !in.getBoolean(IO.DRIVER_BUTTONS[Constants.DRIVER_BUTTON_HOPPER_EXTENSION_EXTEND]);
	}

}
