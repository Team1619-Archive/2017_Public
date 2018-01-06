package org.usfirst.frc.team1619.robot.states.Multi;

import org.usfirst.frc.team1619.robot.SubsystemID;
import org.usfirst.frc.team1619.robot.framework.state.MultiSubsystemSequencerState;
import org.usfirst.frc.team1619.robot.framework.state.MultiSubsystemStateWrapper;
import org.usfirst.frc.team1619.robot.states.Multi.FarBumpAndDump.MultiBumpAndDumpAuto2;
import org.usfirst.frc.team1619.robot.states.Multi.GearAndHopper.MultiGearAndHopper;
import org.usfirst.frc.team1619.robot.states.Multi.GearAutoCenter.MultiGearAutoCenter;
import org.usfirst.frc.team1619.robot.states.Multi.ShootThenGearAuto.MultiShootThenGearAuto;

public class MultiAutoSequence extends MultiSubsystemSequencerState {

	public static final MultiSubsystemStateWrapper<MultiAutoSequence> WRAPPER = new MultiSubsystemStateWrapper<MultiAutoSequence>(MultiAutoSequence.class) {

		@Override
		public boolean isReady() {
			return true;
		}

	};

	@Override
	protected boolean isDone() {
		return false;
	}

	@Override
	protected void addSequence() {
		switch (state.autoSequenceID) {
			case 1: // Boiler side shoot then gear
				this.add(new MultiShootThenGearAuto(state.autoRed));
				break;
			case 2: // Center gear then shoot
				this.add(new MultiGearAutoCenter(state.autoRed));
				break;
			case 3: // Bump and dump auto
				this.add(new MultiBumpAndDumpAuto2(state.autoRed));
				break;
			case 4: // Boiler side gear then hopper and shoot
				this.add(new MultiGearAndHopper(state.autoRed));
				break;
		}
	}

	@Override
	protected SubsystemID[] getSubsystemIDs() {
		return new SubsystemID[] {
				SubsystemID.DRIVE, SubsystemID.INTAKE, SubsystemID.HOPPER, SubsystemID.ELEVATOR, SubsystemID.TURRET, SubsystemID.SHOOTER, SubsystemID.GEAR_HANDLER, SubsystemID.HOPPER_EXTENSION
		};
	}

}
