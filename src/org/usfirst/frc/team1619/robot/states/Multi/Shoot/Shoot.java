package org.usfirst.frc.team1619.robot.states.Multi.Shoot;

import org.usfirst.frc.team1619.robot.SubsystemID;
import org.usfirst.frc.team1619.robot.framework.state.ParallelState;
import org.usfirst.frc.team1619.robot.states.Intake.IntakeAgitate;
import org.usfirst.frc.team1619.robot.states.Multi.MultiFeed;

public class Shoot extends ParallelState {

	private double rpm;
	private boolean red;

	public Shoot(double rpm, boolean red) {
		this.rpm = rpm;
		this.red = red;
	}

	@Override
	protected boolean isDone() {
		return false;
	}

	@Override
	protected void addStates() {
		this.addState(new ShooterSet(this.rpm));
		this.addState(new MultiFeed());
		if (this.red) {
			this.addBackgroundState(new IntakeAgitate());
		}
	}

	@Override
	protected SubsystemID[] getSubsystemIDs() {
		return new SubsystemID[] { SubsystemID.INTAKE, SubsystemID.HOPPER, SubsystemID.ELEVATOR, SubsystemID.SHOOTER };
	}

}
