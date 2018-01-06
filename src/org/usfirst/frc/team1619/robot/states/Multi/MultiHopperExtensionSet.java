package org.usfirst.frc.team1619.robot.states.Multi;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.SubsystemID;
import org.usfirst.frc.team1619.robot.framework.state.MultiSubsystemState;

public class MultiHopperExtensionSet extends MultiSubsystemState {

	private boolean extended;

	public MultiHopperExtensionSet(boolean extended) {
		this.extended = extended;
	}

	@Override
	protected void initialize() {
		IO.gearHandlerController.setProfile(IO.GEAR_HANDLER_PIVOT_PROFILE_DEFAULT);
		IO.gearHandlerPositionController.setAbsolute(this.extended ? Constants.GEAR_HANDLER_PIVOT_HOPPER_EXTENSION : Constants.GEAR_HANDLER_PIVOT_IDLE);
	}

	@Override
	protected void update() {
		IO.gearHandlerPositionController.update();

		out.solenoids.set(IO.HOPPER_EXTEND_SOLENOID, this.extended);
	}

	@Override
	protected boolean isDone() {
		return false;
	}

	@Override
	protected SubsystemID[] getSubsystemIDs() {
		return new SubsystemID[] { SubsystemID.HOPPER_EXTENSION, SubsystemID.GEAR_HANDLER };
	}

}
