package org.usfirst.frc.team1619.robot.states.Multi.GearAndHopper;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.SubsystemID;
import org.usfirst.frc.team1619.robot.framework.IO.sensors.NavxHeadingSensor;
import org.usfirst.frc.team1619.robot.framework.state.MultiSubsystemSequencerState;
import org.usfirst.frc.team1619.robot.framework.state.ParallelState;
import org.usfirst.frc.team1619.robot.framework.state.SequencerState;
import org.usfirst.frc.team1619.robot.framework.util.CircleTrajectory;
import org.usfirst.frc.team1619.robot.states.TimedState;
import org.usfirst.frc.team1619.robot.states.Drive.DriveCircleSmoothEndInMiddle;
import org.usfirst.frc.team1619.robot.states.Drive.DriveRotate;
import org.usfirst.frc.team1619.robot.states.Drive.DriveSet;
import org.usfirst.frc.team1619.robot.states.GearHandler.GearHandlerIdle;
import org.usfirst.frc.team1619.robot.states.Intake.IntakeExtend;
import org.usfirst.frc.team1619.robot.states.Multi.MultiHopperExtensionSet;
import org.usfirst.frc.team1619.robot.states.Multi.MultiInitialize;
import org.usfirst.frc.team1619.robot.states.Multi.BumpAndDump.AlignAndShoot;
import org.usfirst.frc.team1619.robot.states.Multi.BumpAndDump.TurretSetPosition;
import org.usfirst.frc.team1619.robot.states.Multi.Shoot.ShooterSet;

public class MultiGearAndHopper extends MultiSubsystemSequencerState {

	private boolean red;

	public MultiGearAndHopper(boolean red) {
		this.red = red;
	}

	@Override
	protected void initialize() {
		super.initialize();

		((NavxHeadingSensor) IO.in.getNumericSensor(IO.NAVX_HEADING)).zero();
	}

	@Override
	protected void addSequence() {
		this.add(new ParallelState() {

			@Override
			protected void addStates() {
				this.addBackgroundState(new MultiSubsystemSequencerState() {

					@Override
					protected void addSequence() {
						this.add(new MultiInitialize(false));
						this.add(new GearHandlerPresent());
					}

					@Override
					protected SubsystemID[] getSubsystemIDs() {
						return new SubsystemID[0];
					}

				});
				this.addState(new DriveCircleSmoothEndInMiddle(new CircleTrajectory(115.0 / 12.0, 62.5, red, 31.0 / 12.0, 8.0, 10.0, 10.0, true), 57.5));
			}

			@Override
			protected SubsystemID[] getSubsystemIDs() {
				return new SubsystemID[0];
			}
		});
		// this.add(new ParallelState() {
		//
		// @Override
		// protected void addStates() {
		// this.addState(new DriveRotateToGear(red));
		// this.addBackgroundState(new GearHandlerPresent());
		// }
		//
		// @Override
		// protected SubsystemID[] getSubsystemIDs() {
		// return new SubsystemID[0];
		// }
		// });
		this.add(new MultiGearPlacementAutoFast());
		this.add(new ParallelState() {

			@Override
			protected void addStates() {
				this.addState(new DriveCircleSmoothEndInMiddle(new CircleTrajectory(110.0 / 12.0, 35.0, red, 31.0 / 12.0, 8.0, 10.0, 10.0, false), 30.0));
				this.addBackgroundState(new GearHandlerIdle());
				if (red) {
					this.addBackgroundState(new IntakeExtend());
				}
				this.addBackgroundState(new TurretSetPosition(red));
				this.addBackgroundState(new MultiHopperExtensionSet(!red));
			}

			@Override
			protected SubsystemID[] getSubsystemIDs() {
				return new SubsystemID[0];
			}

		});

		if (!this.red) {
			this.add(new ParallelState() {

				@Override
				protected void addStates() {
					this.addState(new DriveRotate(175.0));
					this.addBackgroundState(new MultiHopperExtensionSet(true));
				}

				@Override
				protected SubsystemID[] getSubsystemIDs() {
					return new SubsystemID[0];
				}

			});
		}
		this.add(new MultiSubsystemSequencerState() {

			@Override
			protected void addSequence() {
				this.add(new ParallelState() {

					@Override
					protected void addStates() {
						this.addState(new TimedState(red ? 800 : 900));
						this.addBackgroundState(new DriveSet(red ? 0.75 : -0.75));
						this.addBackgroundState(new GearHandlerIdle());
						this.addBackgroundState(new ShooterSet((red ? Constants.AUTO_BUMP_AND_DUMP_SHOT_RPM_RED : Constants.AUTO_BUMP_AND_DUMP_SHOT_RPM_BLUE) - 500));
					}

					@Override
					protected SubsystemID[] getSubsystemIDs() {
						return new SubsystemID[0];
					}
				});
				if (red) {
					this.add(new AlignAndShoot(true));
				}
				else {
					this.add(new ParallelState() {

						@Override
						protected void addStates() {
							this.addState(new AlignAndShoot(false));
							this.addBackgroundState(new SequencerState() {

								@Override
								protected void addSequence() {
									this.add(new TimedState(100));
									this.add(new DriveRotate(6.25));
								}

							});
						}

						@Override
						protected SubsystemID[] getSubsystemIDs() {
							return new SubsystemID[0];
						}

					});
				}
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

}
