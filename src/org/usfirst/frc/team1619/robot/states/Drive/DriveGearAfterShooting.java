package org.usfirst.frc.team1619.robot.states.Drive;

import org.usfirst.frc.team1619.robot.framework.state.SequencerState;
import org.usfirst.frc.team1619.robot.framework.util.TrapezoidTrajectory;

public class DriveGearAfterShooting extends SequencerState {

	private boolean red;

	public DriveGearAfterShooting(boolean red) {
		this.red = red;
	}

	@Override
	protected void addSequence() {
		if (red) {
			this.add(new DriveStraight(new TrapezoidTrajectory((107.5 - 24.0) / 12.0, 8.5, 10.0, false)));
			this.add(new DriveRotate(180.0 - 60.0));
		}
		else {
			this.add(new DriveStraight(new TrapezoidTrajectory((109.5 - 24.0) / 12.0, 8.5, 10.0, true)));
			this.add(new DriveRotate(60.0));
		}
	}

}
