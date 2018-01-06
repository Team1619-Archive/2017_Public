package org.usfirst.frc.team1619.robot.states.Multi.BumpAndDump;

import org.usfirst.frc.team1619.robot.SubsystemID;
import org.usfirst.frc.team1619.robot.framework.state.MultiSubsystemSequencerState;
import org.usfirst.frc.team1619.robot.states.Multi.MultiInitialize;

public class InitializeWaitAndShoot extends MultiSubsystemSequencerState {

	private boolean red;
	private Wait wait;

	public InitializeWaitAndShoot(boolean red) {
		this.red = red;
		this.wait = new Wait(this.red);
	}

	public void setDone(boolean done) {
		this.wait.setDone(done);
	}

	@Override
	protected void addSequence() {
		this.add(new MultiInitialize(this.red));
		this.add(this.wait);
		this.add(new AlignAndShoot(this.red));
	}

	@Override
	protected SubsystemID[] getSubsystemIDs() {
		return new SubsystemID[] { SubsystemID.INTAKE, SubsystemID.HOPPER, SubsystemID.ELEVATOR, SubsystemID.TURRET, SubsystemID.SHOOTER, SubsystemID.GEAR_HANDLER, SubsystemID.HOPPER_EXTENSION };
	}

}
