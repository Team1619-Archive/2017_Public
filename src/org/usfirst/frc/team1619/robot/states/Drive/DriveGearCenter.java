package org.usfirst.frc.team1619.robot.states.Drive;

import org.usfirst.frc.team1619.robot.framework.util.TrapezoidTrajectory;

public class DriveGearCenter extends DriveStraight {

	public DriveGearCenter() {
		super(new TrapezoidTrajectory(68.0 / 12.0, 4.0, 4.0, true));
	}

}
