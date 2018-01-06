package org.usfirst.frc.team1619.robot.states.Multi.ShootThenGearAuto;

import org.usfirst.frc.team1619.robot.SubsystemID;
import org.usfirst.frc.team1619.robot.framework.state.MultiSubsystemSequencerState;
import org.usfirst.frc.team1619.robot.framework.util.TrapezoidTrajectory;
import org.usfirst.frc.team1619.robot.states.Drive.DriveRotate;
import org.usfirst.frc.team1619.robot.states.Drive.DriveStraight;

public class MultiDriveField extends MultiSubsystemSequencerState {

	private boolean red;

	public MultiDriveField(boolean red) {
		super();

		this.red = red;
	}

	@Override
	protected void addSequence() {
		if (red) {
			this.add(new DriveRotate(60.0));
			this.add(new DriveStraight(new TrapezoidTrajectory((200.0 - 24.0) / 12.0, 8.5, 10.0, true)));
		}
		else {
			this.add(new DriveRotate(180.0 - 60.0));
			this.add(new DriveStraight(new TrapezoidTrajectory((200.0 - 24.0) / 12.0, 8.5, 10.0, false)));
		}
	}

	@Override
	protected SubsystemID[] getSubsystemIDs() {
		return new SubsystemID[0];
	}

}
