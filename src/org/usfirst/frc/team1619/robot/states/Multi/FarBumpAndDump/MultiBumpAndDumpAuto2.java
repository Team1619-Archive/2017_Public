package org.usfirst.frc.team1619.robot.states.Multi.FarBumpAndDump;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.SubsystemID;
import org.usfirst.frc.team1619.robot.framework.state.MultiSubsystemSequencerState;
import org.usfirst.frc.team1619.robot.framework.state.ParallelState;
import org.usfirst.frc.team1619.robot.states.TimedState;
import org.usfirst.frc.team1619.robot.states.Drive.DriveBumpAndDump2;
import org.usfirst.frc.team1619.robot.states.GearHandler.GearHandlerIdle;
import org.usfirst.frc.team1619.robot.states.Multi.MultiHopperExtensionSet;
import org.usfirst.frc.team1619.robot.states.Multi.MultiInitialize;
import org.usfirst.frc.team1619.robot.states.Multi.BumpAndDump.AlignAndShoot;
import org.usfirst.frc.team1619.robot.states.Multi.BumpAndDump.TurretSetPosition;
import org.usfirst.frc.team1619.robot.states.Multi.Shoot.ShooterSet;

public class MultiBumpAndDumpAuto2 extends MultiSubsystemSequencerState {

	private boolean red;

	public MultiBumpAndDumpAuto2(boolean red) {
		this.red = red;
	}

	@Override
	protected void addSequence() {
		this.add(new ParallelState() {

			@Override
			protected void addStates() {
				this.addState(new MultiSubsystemSequencerState() {

					@Override
					protected void addSequence() {
						this.add(new MultiInitialize(red));
						this.add(new ParallelState() {

							@Override
							protected void addStates() {
								this.addState(new TimedState(2125));
								this.addBackgroundState(new ShooterSet((red ? Constants.AUTO_BUMP_AND_DUMP_SHOT_RPM_RED : Constants.AUTO_BUMP_AND_DUMP_SHOT_RPM_BLUE) - 500));
								this.addBackgroundState(new TurretSetPosition(red));
								this.addBackgroundState(new GearHandlerIdle());
								this.addBackgroundState(new MultiHopperExtensionSet(!red));
							}

							@Override
							protected SubsystemID[] getSubsystemIDs() {
								return new SubsystemID[0];
							}

						});
						this.add(new AlignAndShoot(red));
					}

					@Override
					protected SubsystemID[] getSubsystemIDs() {
						return new SubsystemID[0];
					}
				});
				this.addState(new DriveBumpAndDump2(red));
			}

			@Override
			protected SubsystemID[] getSubsystemIDs() {
				return new SubsystemID[0];
			}
		});
	}

	@Override
	protected SubsystemID[] getSubsystemIDs() {
		return new SubsystemID[] {
				SubsystemID.DRIVE, SubsystemID.INTAKE, SubsystemID.HOPPER, SubsystemID.ELEVATOR, SubsystemID.TURRET, SubsystemID.SHOOTER, SubsystemID.GEAR_HANDLER
		};
	}

}
