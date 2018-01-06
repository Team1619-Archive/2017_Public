package org.usfirst.frc.team1619.robot.states.GearHandler;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.state.State;
import org.usfirst.frc.team1619.robot.framework.state.StateWrapper;

public class GearHandlerIdle extends State {

	public static final StateWrapper<GearHandlerIdle> WRAPPER = new StateWrapper<GearHandlerIdle>(GearHandlerIdle.class) {

		@Override
		public boolean isReady() {
			return true;
		}

	};

	@Override
	protected void initialize() {
		IO.gearHandlerController.setProfile(IO.GEAR_HANDLER_PIVOT_PROFILE_DEFAULT);
		IO.gearHandlerPositionController.setAbsolute(Constants.GEAR_HANDLER_PIVOT_IDLE);
	}

	@Override
	protected void update() {
		if (state.gearHandlerExtended && Math.abs(IO.gearHandlerController.getError(in.getNumeric(IO.GEAR_HANDLER_PIVOT_POSITION))) < 2.5) {
			state.gearHandlerExtended = false;
		}

		IO.gearHandlerPositionController.update();
	}

	@Override
	protected boolean isDone() {
		return false;
	}

}
