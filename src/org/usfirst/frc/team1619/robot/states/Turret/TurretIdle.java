package org.usfirst.frc.team1619.robot.states.Turret;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.state.State;
import org.usfirst.frc.team1619.robot.framework.state.StateWrapper;

public class TurretIdle extends State {

	public static final StateWrapper<TurretIdle> WRAPPER = new StateWrapper<TurretIdle>(TurretIdle.class) {

		@Override
		public boolean isReady() {
			return state.intakeExtended;
		}

	};

	@Override
	protected void initialize() {
		IO.turretController.setProfile(IO.TURRET_PROFILE_DEFAULT);
		IO.turretPositionController.setAbsolute(Constants.TURRET_POSITION_IDLE);
	}

	@Override
	protected void update() {
		IO.turretPositionController.update();
	}

	@Override
	protected boolean isDone() {
		return !state.intakeExtended;
	}

}
