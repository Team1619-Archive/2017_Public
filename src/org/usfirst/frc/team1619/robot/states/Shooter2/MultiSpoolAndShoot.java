package org.usfirst.frc.team1619.robot.states.Shooter2;

import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.SubsystemID;
import org.usfirst.frc.team1619.robot.controllers.Shot;
import org.usfirst.frc.team1619.robot.framework.state.MultiSubsystemState;
import org.usfirst.frc.team1619.robot.framework.util.Timer;
import org.usfirst.frc.team1619.robot.states.Elevator.ElevatorSet;

public class MultiSpoolAndShoot extends MultiSubsystemState {

	private static final Shot DEFAULT_SHOT = new Shot(0.0, 8750.0, false);

	private boolean spooling = true;
	private Timer timer = new Timer();

	private ShooterSet shooterSet = new ShooterSet(DEFAULT_SHOT, IO.SHOOTER_PROFILE_SPOOL);
	private ElevatorSet elevatorSet;

	public MultiSpoolAndShoot() {
		this(1.0);
	}

	public MultiSpoolAndShoot(double elevatorOutput) {
		this.elevatorSet = new ElevatorSet(elevatorOutput);
	}

	public void setShot(Shot shot) {
		this.setShot(shot, true);
	}

	public void setShot(Shot shot, boolean spool) {
		if (spool) {
			this.spooling = true;
			this.shooterSet.setProfile(IO.SHOOTER_PROFILE_SPOOL);
			this.timer.reset();
		}
		this.shooterSet.setShot(shot == null ? DEFAULT_SHOT : shot);
	}

	public boolean getSpooling() {
		return this.spooling;
	}

	@Override
	protected void initialize() {
		this.shooterSet.initialize();
		this.elevatorSet.setProfile(IO.ELEVATOR_PROFILE_SPOOL);
		this.elevatorSet.initializeState(null);
	}

	@Override
	protected void update() {
		if (this.timer.isDone()) {
			this.spooling = false;
			this.shooterSet.setProfile(IO.SHOOTER_PROFILE_SHOOT);
			this.timer.reset();
		}
		if (spooling && !this.timer.isStarted() && Math.abs(this.shooterSet.getError()) < 250.0) {
			this.timer.start(250);
		}

		if (!spooling) {
			this.elevatorSet.setProfile(IO.ELEVATOR_PROFILE_DEFAULT);
		}
		else {
			this.elevatorSet.setProfile(IO.ELEVATOR_PROFILE_SPOOL);
		}
		this.elevatorSet.updateState(null);
		this.shooterSet.update();
	}

	@Override
	protected boolean isDone() {
		return false;
	}

	@Override
	protected void dispose() {
		this.shooterSet.dispose();
	}

	@Override
	protected SubsystemID[] getSubsystemIDs() {
		return new SubsystemID[] {
				SubsystemID.ELEVATOR, SubsystemID.SHOOTER
		};
	}

}
