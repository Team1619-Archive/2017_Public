package org.usfirst.frc.team1619.robot.states.Multi.BumpAndDump;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.states.Turret.TurretSet;

public class TurretSetPosition extends TurretSet {

	public TurretSetPosition(boolean red) {
		super(red ? Constants.TURRET_POSITION_BUMP_AND_DUMP_SHOOT_RED : Constants.TURRET_POSITION_BUMP_AND_DUMP_SHOOT_BLUE);
	}

	@Override
	protected boolean isDone() {
		return Math.abs(in.getNumeric(IO.NAVX_HEADING)) > 85.0;
	}

}
