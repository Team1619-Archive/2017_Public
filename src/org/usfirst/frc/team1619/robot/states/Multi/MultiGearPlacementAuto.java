package org.usfirst.frc.team1619.robot.states.Multi;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.SubsystemID;
import org.usfirst.frc.team1619.robot.framework.state.MultiSubsystemSequencerState;
import org.usfirst.frc.team1619.robot.framework.state.ParallelState;
import org.usfirst.frc.team1619.robot.framework.state.State;
import org.usfirst.frc.team1619.robot.framework.util.Timer;
import org.usfirst.frc.team1619.robot.states.TimedState;
import org.usfirst.frc.team1619.robot.states.Drive.DriveSet;
import org.usfirst.frc.team1619.robot.states.Drive.DriveWiggle;
import org.usfirst.frc.team1619.robot.states.GearHandler.GearHandlePlaceAuto;
import org.usfirst.frc.team1619.robot.states.GearHandler.GearHandlerSet;

public class MultiGearPlacementAuto extends MultiSubsystemSequencerState {

	private boolean backOff;

	public MultiGearPlacementAuto(boolean backOff) {
		this.backOff = backOff;
	}

	@Override
	protected void addSequence() {
		this.add(new GearHandlerSet(Constants.GEAR_HANDLER_PIVOT_PLACEMENT));

		ParallelState wiggleForward = new ParallelState() {

			@Override
			protected void addStates() {
				this.addBackgroundState(new DriveWiggle());
				this.addBackgroundState(new GearHandlerSet(Constants.GEAR_HANDLER_PIVOT_PLACEMENT));
				this.addState(new TimedState(1250));
			}

			@Override
			protected SubsystemID[] getSubsystemIDs() {
				return new SubsystemID[] {
						SubsystemID.DRIVE, SubsystemID.GEAR_HANDLER
				};
			}

		};

		this.add(wiggleForward);
		this.add(new ParallelState() {

			@Override
			protected void addStates() {
				this.addState(new GearHandlePlaceAuto());
				this.addBackgroundState(new DriveSet(0.15));
			}

			@Override
			protected SubsystemID[] getSubsystemIDs() {
				return new SubsystemID[] {
						SubsystemID.DRIVE, SubsystemID.GEAR_HANDLER
				};
			}

		});

		if (this.backOff) {
			State driveBack = new State() {

				private Timer timer = new Timer();

				@Override
				protected void initialize() {
					this.timer.start(375);
				}

				@Override
				protected void update() {
					out.motors.set(IO.DRIVE_LEFT, 0.5);
					out.motors.set(IO.DRIVE_LEFT_2, 0.5);
					out.motors.set(IO.DRIVE_RIGHT, 0.5);
					out.motors.set(IO.DRIVE_RIGHT_2, 0.5);
				}

				@Override
				protected boolean isDone() {
					return this.timer.isDone();
				}

			};

			this.add(driveBack);
		}
	}

	@Override
	protected SubsystemID[] getSubsystemIDs() {
		return new SubsystemID[] {
				SubsystemID.DRIVE, SubsystemID.GEAR_HANDLER
		};
	}

}
