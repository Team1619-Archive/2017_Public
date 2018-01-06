package org.usfirst.frc.team1619.robot.states.Multi.GearAutoCenter;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.SubsystemID;
import org.usfirst.frc.team1619.robot.controllers.Shot;
import org.usfirst.frc.team1619.robot.framework.state.MultiSubsystemSequencerState;
import org.usfirst.frc.team1619.robot.framework.state.ParallelState;
import org.usfirst.frc.team1619.robot.framework.state.SequencerState;
import org.usfirst.frc.team1619.robot.framework.util.CircleTrajectory;
import org.usfirst.frc.team1619.robot.states.Drive.DriveCircleSmooth;
import org.usfirst.frc.team1619.robot.states.Drive.DriveGearCenter;
import org.usfirst.frc.team1619.robot.states.Drive.DriveRotate;
import org.usfirst.frc.team1619.robot.states.GearHandler.GearHandlerIdle;
import org.usfirst.frc.team1619.robot.states.Multi.MultiGearPlacementAuto;
import org.usfirst.frc.team1619.robot.states.Multi.MultiInitialize;
import org.usfirst.frc.team1619.robot.states.Shooter2.MultiShootSequenceAuto;
import org.usfirst.frc.team1619.robot.states.TimedState;
import org.usfirst.frc.team1619.robot.states.Turret.TurretSet;

public class MultiGearAutoCenter extends MultiSubsystemSequencerState {

	private boolean red;

	public MultiGearAutoCenter(boolean red) {
		this.red = red;
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
						this.add(new GearHandlerIdle());
					}

					@Override
					protected SubsystemID[] getSubsystemIDs() {
						return new SubsystemID[0];
					}

				});
				this.addState(new DriveGearCenter());
			}

			@Override
			protected SubsystemID[] getSubsystemIDs() {
				return new SubsystemID[0];
			}
		});
		this.add(new MultiGearPlacementAuto(false));
		this.add(new ParallelState() {
			@Override
			protected void addStates() {
				if (red) {
					this.addState(new DriveCircleSmooth(new CircleTrajectory(42.5 / 12.0, 130.0, true, 31.0 / 12.0, 8.0, 10.0, 10.0, false)));
				}
				else {
					this.addState(new SequencerState() {

						@Override
						protected void addSequence() {
							this.add(new DriveCircleSmooth(new CircleTrajectory(42.5 / 12.0, 130.0, false, 31.0 / 12.0, 8.0, 10.0, 10.0, false)));
							this.add(new DriveRotate(-100.0));
						}

					});
				}
				this.addState(new TurretSet(red ? 27.0 : 12.5));
				this.addBackgroundState(new SequencerState() {

					@Override
					protected void addSequence() {
						this.add(new TimedState(750));
						this.add(new GearHandlerIdle());
					}

				});
			}

			@Override
			protected SubsystemID[] getSubsystemIDs() {
				return new SubsystemID[0];
			}
		});
		this.add(new ParallelState() {
			@Override
			protected void addStates() {
				this.addState(new MultiShootSequenceAuto(0.6, Constants.SHOOTING_ELEVATOR_FEED_SPEED, new Shot(0.0, 9920, false), 10000));
				this.addBackgroundState(new GearHandlerIdle());
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
