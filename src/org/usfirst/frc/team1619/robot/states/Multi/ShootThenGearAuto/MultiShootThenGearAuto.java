package org.usfirst.frc.team1619.robot.states.Multi.ShootThenGearAuto;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.SubsystemID;
import org.usfirst.frc.team1619.robot.controllers.Shot;
import org.usfirst.frc.team1619.robot.framework.state.MultiSubsystemSequencerState;
import org.usfirst.frc.team1619.robot.framework.state.ParallelState;
import org.usfirst.frc.team1619.robot.framework.util.TrapezoidTrajectory;
import org.usfirst.frc.team1619.robot.states.Drive.DriveGearAfterShooting;
import org.usfirst.frc.team1619.robot.states.Drive.DriveStraight;
import org.usfirst.frc.team1619.robot.states.GearHandler.GearHandlerIdle;
import org.usfirst.frc.team1619.robot.states.Multi.MultiGearPlacementAuto;
import org.usfirst.frc.team1619.robot.states.Multi.MultiInitialize;
import org.usfirst.frc.team1619.robot.states.Shooter2.MultiShootSequenceAuto;
import org.usfirst.frc.team1619.robot.states.Turret.TurretSet;

public class MultiShootThenGearAuto extends MultiSubsystemSequencerState {

	private class ShootThenDrive extends ParallelState {

		private boolean red;

		public ShootThenDrive(boolean red) {
			this.red = red;
		}

		@Override
		protected void addStates() {
			this.addState(new MultiSubsystemSequencerState() {

				@Override
				protected void addSequence() {
					this.add(new MultiShootSequenceAuto(0.9, Constants.SHOOTING_ELEVATOR_FEED_SPEED, new Shot(0.0, red ? 10265 : 9668, false), 3500));
					this.add(new DriveGearAfterShooting(red));
				}

				@Override
				protected SubsystemID[] getSubsystemIDs() {
					return new SubsystemID[0];
				}

			});
			this.addBackgroundState(new GearHandlerIdle());
		}

		@Override
		protected SubsystemID[] getSubsystemIDs() {
			return new SubsystemID[0];
		}

	}

	private boolean red;

	public MultiShootThenGearAuto(boolean red) {
		this.red = red;
	}

	@Override
	protected void addSequence() {
		this.add(new ParallelState() {

			@Override
			protected void addStates() {
				this.addState(new DriveStraight(new TrapezoidTrajectory(24.0 / 12.0, 7.5, 5.0, !red)));
				this.addState(new MultiSubsystemSequencerState() {

					@Override
					protected void addSequence() {
						this.add(new MultiInitialize(false));
						this.add(new TurretSet(red ? 68.5 : 25.0));
					}

					@Override
					protected SubsystemID[] getSubsystemIDs() {
						return new SubsystemID[0];
					}

				});
			}

			@Override
			protected SubsystemID[] getSubsystemIDs() {
				return new SubsystemID[0];
			}
		});
		this.add(new ShootThenDrive(this.red));
		this.add(new MultiGearPlacementAuto(true));
		this.add(new ParallelState() {

			@Override
			protected void addStates() {
				this.addBackgroundState(new GearHandlerIdle());
				this.addState(new MultiDriveField(red));
			}

			@Override
			protected SubsystemID[] getSubsystemIDs() {
				return new SubsystemID[0];
			}

		});
		this.add(new GearHandlerIdle());
	}

	@Override
	protected SubsystemID[] getSubsystemIDs() {
		return new SubsystemID[] {
				SubsystemID.DRIVE, SubsystemID.INTAKE, SubsystemID.HOPPER, SubsystemID.ELEVATOR, SubsystemID.TURRET, SubsystemID.SHOOTER, SubsystemID.GEAR_HANDLER
		};
	}

}
