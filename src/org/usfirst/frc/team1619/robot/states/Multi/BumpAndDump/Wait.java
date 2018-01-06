package org.usfirst.frc.team1619.robot.states.Multi.BumpAndDump;

import org.usfirst.frc.team1619.robot.SubsystemID;
import org.usfirst.frc.team1619.robot.framework.state.ParallelState;
import org.usfirst.frc.team1619.robot.states.Multi.MultiHopperExtensionSet;

public class Wait extends ParallelState {

	private boolean red;
	private boolean done = false;

	public Wait(boolean red) {
		this.red = red;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	@Override
	protected boolean isDone() {
		return this.done;
	}

	@Override
	protected void addStates() {
		this.addBackgroundState(new TurretSetPosition(this.red));
		this.addBackgroundState(new MultiHopperExtensionSet(!this.red));
	}

	@Override
	protected SubsystemID[] getSubsystemIDs() {
		return new SubsystemID[] { SubsystemID.TURRET, SubsystemID.GEAR_HANDLER, SubsystemID.HOPPER_EXTENSION };
	}

}
