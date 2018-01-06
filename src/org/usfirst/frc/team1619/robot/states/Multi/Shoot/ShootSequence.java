package org.usfirst.frc.team1619.robot.states.Multi.Shoot;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.SubsystemID;
import org.usfirst.frc.team1619.robot.framework.state.MultiSubsystemSequencerState;

public class ShootSequence extends MultiSubsystemSequencerState {

	private Spool spool;
	private boolean aligned = false;
	private boolean red;

	public ShootSequence(boolean red) {
		this.red = red;
		this.spool = new Spool(this.red ? Constants.AUTO_BUMP_AND_DUMP_SHOT_RPM_RED : Constants.AUTO_BUMP_AND_DUMP_SHOT_RPM_BLUE);
	}

	public void setAligned(boolean aligned) {
		this.aligned = aligned;
		this.spool.setCanProgress(this.aligned);
	}

	@Override
	protected void initialize() {
		super.initialize();

		out.solenoids.disableCompressor();
	}

	@Override
	protected void dispose() {
		super.dispose();

		out.solenoids.enableCompressor();
	}

	@Override
	protected void addSequence() {
		this.add(this.spool);
		this.add(new Shoot(this.red ? Constants.AUTO_BUMP_AND_DUMP_SHOT_RPM_RED * state.shootingPercentage : Constants.AUTO_BUMP_AND_DUMP_SHOT_RPM_BLUE * state.shootingPercentage, this.red));
	}

	@Override
	protected SubsystemID[] getSubsystemIDs() {
		return new SubsystemID[] {
				SubsystemID.INTAKE, SubsystemID.HOPPER, SubsystemID.ELEVATOR, SubsystemID.SHOOTER
		};
	}

}
