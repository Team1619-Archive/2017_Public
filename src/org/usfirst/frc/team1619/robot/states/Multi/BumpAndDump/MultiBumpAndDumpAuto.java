package org.usfirst.frc.team1619.robot.states.Multi.BumpAndDump;

import org.usfirst.frc.team1619.robot.SubsystemID;
import org.usfirst.frc.team1619.robot.framework.state.ParallelState;
import org.usfirst.frc.team1619.robot.states.Drive.DriveBumpAndDump;

public class MultiBumpAndDumpAuto extends ParallelState {

	private boolean red;
	private InitializeWaitAndShoot initializeWaitAndShoot;
	private DriveBumpAndDump driveBumpAndDump;

	public MultiBumpAndDumpAuto(boolean red) {
		this.red = red;
		this.initializeWaitAndShoot = new InitializeWaitAndShoot(this.red);
		this.driveBumpAndDump = new DriveBumpAndDump(this.red);
	}

	@Override
	protected void update() {
		this.initializeWaitAndShoot.setDone(this.driveBumpAndDump.getAlmostThere());

		super.update();
	}

	@Override
	protected void addStates() {
		this.addState(this.driveBumpAndDump);
		this.addState(this.initializeWaitAndShoot);
	}

	@Override
	protected SubsystemID[] getSubsystemIDs() {
		return new SubsystemID[] { SubsystemID.DRIVE, SubsystemID.INTAKE, SubsystemID.HOPPER, SubsystemID.ELEVATOR, SubsystemID.TURRET, SubsystemID.SHOOTER, SubsystemID.GEAR_HANDLER, SubsystemID.HOPPER_EXTENSION };
	}

}
