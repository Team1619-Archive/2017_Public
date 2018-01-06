package org.usfirst.frc.team1619.robot.states.Multi.Shoot;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.SubsystemID;
import org.usfirst.frc.team1619.robot.framework.state.ParallelState;
import org.usfirst.frc.team1619.robot.framework.util.Timer;
import org.usfirst.frc.team1619.robot.states.Elevator.ElevatorSet;
import org.usfirst.frc.team1619.robot.states.GearHandler.GearHandlerIdle;

public class Spool extends ParallelState {

	private boolean canProgress = true;
	private double rpm;
	private Timer timer = new Timer();

	public Spool(double rpm) {
		this.rpm = rpm;
	}

	public void setCanProgress(boolean canProgress) {
		this.canProgress = canProgress;
	}

	private void setRPM(double rpm) {
		this.rpm = rpm;
		this.timer.reset();
	}

	@Override
	protected void initialize() {
		super.initialize();

		IO.shooterVelocityController.setProfile(IO.SHOOTER_PROFILE_SPOOL);
		IO.shooterVelocityController.initialize();
		
		IO.elevatorController.setProfile(IO.ELEVATOR_PROFILE_SPOOL);
	}

	@Override
	protected void update() {
		super.update();

		IO.shooterVelocityController.set(this.rpm);
		IO.shooterVelocityController.update();

		if (!this.timer.isStarted() && Math.abs(IO.shooterVelocityController.getError()) < Constants.SHOOTING_SPOOL_ERROR_THRESHOLD) {
			this.timer.start(250);
		}
	}

	@Override
	protected boolean isDone() {
		return this.canProgress && this.timer.isDone();
	}

	@Override
	protected void dispose() {
		super.dispose();

		IO.shooterVelocityController.dispose();
	}

	@Override
	protected void addStates() {
		this.addBackgroundState(new ElevatorSet(Constants.SHOOTING_ELEVATOR_FEED_SPEED));
		this.addBackgroundState(new GearHandlerIdle());
	}

	@Override
	protected SubsystemID[] getSubsystemIDs() {
		return new SubsystemID[] { SubsystemID.SHOOTER, SubsystemID.GEAR_HANDLER };
	}

}
