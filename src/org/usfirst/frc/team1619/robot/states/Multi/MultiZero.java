package org.usfirst.frc.team1619.robot.states.Multi;

import org.usfirst.frc.team1619.robot.SubsystemID;
import org.usfirst.frc.team1619.robot.framework.state.ParallelState;
import org.usfirst.frc.team1619.robot.states.GearHandler.GearHandlerZero;
import org.usfirst.frc.team1619.robot.states.Turret.TurretZero;

public class MultiZero extends ParallelState {

	@Override
	protected void addStates() {
		this.addState(new TurretZero());
		this.addState(new GearHandlerZero());
	}

	@Override
	protected SubsystemID[] getSubsystemIDs() {
		return new SubsystemID[] { SubsystemID.TURRET, SubsystemID.GEAR_HANDLER };
	}

}
