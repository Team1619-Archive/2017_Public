package org.usfirst.frc.team1619.robot.states.Multi.GearAndHopper;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.state.State;

public class GearHandlerPresent extends State {

	@Override
	protected void initialize() {
		IO.gearHandlerController.setProfile(IO.GEAR_HANDLER_PIVOT_PROFILE_DEFAULT);
		IO.gearHandlerPositionController.setAbsolute(Constants.GEAR_HANDLER_PIVOT_PLACEMENT);
	}

	@Override
	protected void update() {
		IO.gearHandlerPositionController.update();
	}

	@Override
	protected boolean isDone() {
		return false;
	}

}
