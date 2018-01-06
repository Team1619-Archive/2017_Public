package org.usfirst.frc.team1619.robot.states.Drive;

import org.usfirst.frc.team1619.robot.framework.state.SequencerState;
import org.usfirst.frc.team1619.robot.framework.util.TrapezoidTrajectory;

public class DriveGear extends SequencerState {

	private boolean red;

	public DriveGear(boolean red) {
		this.red = red;
	}

	@Override
	protected void addSequence() {
		if (this.red) {
			this.add(new DriveStraight(new TrapezoidTrajectory(111.0 / 12.0, 7.5, 7.5, true)));
			this.add(new DriveRotate(59.0));
		}
		else {
			this.add(new DriveStraight(new TrapezoidTrajectory(93.0 / 12.0, 7.5, 7.5, true)));
			this.add(new DriveRotate(-59.0));
		}
	}

}
