package org.usfirst.frc.team1619.robot.states.Multi.BumpAndDump;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.SubsystemID;
import org.usfirst.frc.team1619.robot.framework.state.ParallelState;
import org.usfirst.frc.team1619.robot.states.TimedState;
import org.usfirst.frc.team1619.robot.states.Multi.Shoot.ShootSequence;
import org.usfirst.frc.team1619.robot.states.Turret.TurretAlign;

public class AlignAndShoot extends ParallelState {

	private boolean red;

	public AlignAndShoot(boolean red) {
		this.red = red;
	}

	@Override
	protected void addStates() {
		this.addBackgroundState(new ParallelState() {

			@Override
			protected void addStates() {
				this.addBackgroundState(new TurretAlign(true, Constants.SHOOTING_ANGLE_ERROR_THRESHOLD_AUTO));
				this.addState(new TimedState(2000));
			}

			@Override
			protected SubsystemID[] getSubsystemIDs() {
				return new SubsystemID[0];
			}

		});
		this.addBackgroundState(new ShootHopperExtensionGearHandler(this.red));
		this.addState(new ShootSequence(this.red));
	}

	@Override
	protected SubsystemID[] getSubsystemIDs() {
		return new SubsystemID[] {
				SubsystemID.INTAKE, SubsystemID.HOPPER, SubsystemID.ELEVATOR, SubsystemID.TURRET, SubsystemID.SHOOTER, SubsystemID.GEAR_HANDLER, SubsystemID.HOPPER_EXTENSION
		};
	}

}
