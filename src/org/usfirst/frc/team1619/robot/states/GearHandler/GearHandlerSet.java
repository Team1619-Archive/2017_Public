package org.usfirst.frc.team1619.robot.states.GearHandler;

import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.state.State;

public class GearHandlerSet extends State {

	private double position;

	public GearHandlerSet(double position) {
		this.position = position;
	}

	public void setAbsolute(double position) {
		this.position = position;
		IO.gearHandlerController.setProfile(IO.GEAR_HANDLER_PIVOT_PROFILE_DEFAULT);
		IO.gearHandlerPositionController.setAbsolute(this.position);
	}

	public void setRelative(double offset) {
		this.setAbsolute(this.position + offset);
	}

	@Override
	protected void initialize() {
		IO.gearHandlerController.setProfile(IO.GEAR_HANDLER_PIVOT_PROFILE_DEFAULT);
		IO.gearHandlerPositionController.setAbsolute(this.position);
	}

	@Override
	protected void update() {
		IO.gearHandlerPositionController.update();
	}

	@Override
	protected boolean isDone() {
		return Math.abs(IO.gearHandlerController.getError(in.getNumeric(IO.GEAR_HANDLER_PIVOT_POSITION))) < 5.0;
	}

}
