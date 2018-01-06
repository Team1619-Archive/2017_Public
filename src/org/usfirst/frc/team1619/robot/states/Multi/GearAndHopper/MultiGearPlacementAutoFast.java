package org.usfirst.frc.team1619.robot.states.Multi.GearAndHopper;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.SubsystemID;
import org.usfirst.frc.team1619.robot.framework.state.MultiSubsystemSequencerState;
import org.usfirst.frc.team1619.robot.framework.state.ParallelState;
import org.usfirst.frc.team1619.robot.states.TimedState;
import org.usfirst.frc.team1619.robot.states.Drive.DriveSet;
import org.usfirst.frc.team1619.robot.states.GearHandler.GearHandlePlaceAuto;
import org.usfirst.frc.team1619.robot.states.GearHandler.GearHandlerSet;

public class MultiGearPlacementAutoFast extends MultiSubsystemSequencerState {

	@Override
	protected void addSequence() {
		ParallelState wiggleForward = new ParallelState() {

			@Override
			protected void addStates() {
				this.addBackgroundState(new DriveSet(-0.2));
				this.addBackgroundState(new GearHandlerSet(Constants.GEAR_HANDLER_PIVOT_PLACEMENT - 2.0));
				this.addState(new TimedState(500));
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
				this.addState(new GearHandlePlaceAuto(500));
				this.addBackgroundState(new DriveSet(0.25));
			}

			@Override
			protected SubsystemID[] getSubsystemIDs() {
				return new SubsystemID[] {
						SubsystemID.DRIVE, SubsystemID.GEAR_HANDLER
				};
			}

		});
	}

	@Override
	protected SubsystemID[] getSubsystemIDs() {
		return new SubsystemID[] {
				SubsystemID.DRIVE, SubsystemID.GEAR_HANDLER
		};
	}

}
