package org.usfirst.frc.team1619.robot.states.Turret;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.state.State;

public class TurretMove extends State {

	private double movement;
	private boolean relative;
	private double timeThere;

	public TurretMove(double position) {
		this(position, false, Constants.TURRET_TIME_THERE);
	}

	public TurretMove(double position, double timeThere) {
		this(position, false, timeThere);
	}

	public TurretMove(double movement, boolean relative) {
		this(movement, relative, Constants.TURRET_TIME_THERE);
	}

	public TurretMove(double movement, boolean relative, double timeThere) {
		this.movement = movement;
		this.relative = relative;
		this.timeThere = timeThere;
	}

	@Override
	protected void initialize() {
		IO.turretController.setProfile(IO.TURRET_PROFILE_DEFAULT);
		if (this.relative) {
			IO.turretPositionController.setRelative(this.movement);
		}
		else {
			IO.turretPositionController.setAbsolute(this.movement);
		}
	}

	@Override
	protected void update() {
		IO.turretPositionController.update();
	}

	@Override
	protected boolean isDone() {
		return this.timeThere >= 0.0 && IO.turretPositionController.getTimeThere() > this.timeThere;
	}

}
