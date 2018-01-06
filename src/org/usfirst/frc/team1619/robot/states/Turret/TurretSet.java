package org.usfirst.frc.team1619.robot.states.Turret;

import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.state.State;

public class TurretSet extends State {

	private double position;

	public TurretSet(double position) {
		this.position = position;
	}

	@Override
	protected void initialize() {
		IO.turretController.setProfile(IO.TURRET_PROFILE_DEFAULT);
		IO.turretPositionController.setAbsolute(this.position);
	}

	@Override
	protected void update() {
		IO.turretPositionController.update();
	}

	@Override
	protected boolean isDone() {
		return IO.turretPositionController.getTimeThere() > 0.2;
	}

}
