package org.usfirst.frc.team1619.robot.states.Multi.GearAuto;

import org.usfirst.frc.team1619.robot.SubsystemID;
import org.usfirst.frc.team1619.robot.framework.state.MultiSubsystemSequencerState;
import org.usfirst.frc.team1619.robot.framework.state.ParallelState;
import org.usfirst.frc.team1619.robot.states.Drive.DriveGear;
import org.usfirst.frc.team1619.robot.states.GearHandler.GearHandlerIdle;
import org.usfirst.frc.team1619.robot.states.Multi.MultiGearPlacementAuto;
import org.usfirst.frc.team1619.robot.states.Multi.MultiInitialize;

public class MultiGearAuto extends MultiSubsystemSequencerState {

	private boolean red;

	public MultiGearAuto(boolean red) {
		this.red = red;
	}

	@Override
	protected void addSequence() {
		ParallelState initializeAndDrive = new ParallelState() {

			@Override
			protected void addStates() {
				MultiSubsystemSequencerState initializeThenGearHandlerIdle = new MultiSubsystemSequencerState() {

					@Override
					protected void addSequence() {
						this.add(new MultiInitialize(false));
						this.add(new GearHandlerIdle());
					}

					@Override
					protected SubsystemID[] getSubsystemIDs() {
						return new SubsystemID[0];
					}

				};

				this.addBackgroundState(initializeThenGearHandlerIdle);
				this.addState(new DriveGear(red));
			}

			@Override
			protected SubsystemID[] getSubsystemIDs() {
				return new SubsystemID[0];
			}

		};

		this.add(initializeAndDrive);
		this.add(new MultiGearPlacementAuto(true));
		this.add(new GearHandlerIdle());
	}

	@Override
	protected SubsystemID[] getSubsystemIDs() {
		return new SubsystemID[] { SubsystemID.DRIVE, SubsystemID.INTAKE, SubsystemID.TURRET, SubsystemID.GEAR_HANDLER };
	}
}
