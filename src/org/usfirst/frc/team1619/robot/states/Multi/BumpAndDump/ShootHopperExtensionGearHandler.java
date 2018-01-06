package org.usfirst.frc.team1619.robot.states.Multi.BumpAndDump;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.SubsystemID;
import org.usfirst.frc.team1619.robot.framework.state.MultiSubsystemState;
import org.usfirst.frc.team1619.robot.framework.util.Timer;

public class ShootHopperExtensionGearHandler extends MultiSubsystemState {

	private boolean red;
	private boolean gearIn = true;
	private boolean extensionIn = true;
	private boolean initialWait = true;
	private Timer initialTimer = new Timer();
	private Timer gearTimer = new Timer();
	private Timer extensionTimer = new Timer();

	public ShootHopperExtensionGearHandler(boolean red) {
		this.red = red;
	}

	@Override
	protected void initialize() {
		IO.gearHandlerController.setProfile(IO.GEAR_HANDLER_PIVOT_PROFILE_DEFAULT);
		IO.gearHandlerPositionController.setAbsolute(this.red ? Constants.GEAR_HANDLER_PIVOT_IDLE : Constants.GEAR_HANDLER_PIVOT_HOPPER_EXTENSION);
		if (!this.red) {
			this.initialTimer.start(2750);
		}
	}

	@Override
	protected void update() {
		if (this.red) {
			out.solenoids.set(IO.HOPPER_EXTEND_SOLENOID, false);
		}
		else {
			if (this.initialWait) {
				IO.gearHandlerPositionController.setAbsolute(Constants.GEAR_HANDLER_PIVOT_HOPPER_EXTENSION);
				out.solenoids.set(IO.HOPPER_EXTEND_SOLENOID, true);
				if (this.initialTimer.isDone()) {
					this.initialWait = false;
					this.gearTimer.start(Constants.GEAR_HANDLER_AGITATE_TIME_UP);
					this.extensionTimer.start(100);
				}
			}
			else {
				if (this.gearTimer.isDone()) {
					this.gearIn = !this.gearIn;
					IO.gearHandlerPositionController.setAbsolute((this.gearIn && !this.initialWait) ? Constants.GEAR_HANDLER_PIVOT_IDLE : Constants.GEAR_HANDLER_PIVOT_HOPPER_EXTENSION);
					this.gearTimer.start(this.gearIn ? Constants.GEAR_HANDLER_AGITATE_TIME_UP : Constants.GEAR_HANDLER_AGITATE_TIME_DOWN);
				}
				if (this.extensionTimer.isDone()) {
					this.extensionIn = !this.extensionIn;
					out.solenoids.set(IO.HOPPER_EXTEND_SOLENOID, !this.extensionIn);
					this.extensionTimer.start(this.extensionIn ? 100 : 1000);
				}
			}
		}

		IO.gearHandlerPositionController.update();
	}

	@Override
	protected boolean isDone() {
		return false;
	}

	@Override
	protected void dispose() {
		super.dispose();

		out.solenoids.set(IO.HOPPER_EXTEND_SOLENOID, false);
	}

	@Override
	protected SubsystemID[] getSubsystemIDs() {
		return new SubsystemID[] { SubsystemID.GEAR_HANDLER, SubsystemID.HOPPER_EXTENSION };
	}

}
