package org.usfirst.frc.team1619.robot.states.Shooter2;

import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.SubsystemID;
import org.usfirst.frc.team1619.robot.framework.state.MultiSubsystemState;

public class MultiFeed extends MultiSubsystemState {

	private double hopperOutput, intakeOutput;
	private boolean feeding = false;

	public MultiFeed() {
		this(0.9, 1.0);
	}

	public MultiFeed(double hopperOutput, double intakeOutput) {
		this.hopperOutput = hopperOutput;
		this.intakeOutput = intakeOutput;
	}

	public void setFeeding(boolean feeding) {
		this.feeding = feeding;
	}

	@Override
	protected void initialize() {
		super.initialize();
	}
	
	@Override
	protected void update() {
		if (this.feeding) {
			out.motors.set(IO.HOPPER, this.hopperOutput);
			out.motors.set(IO.INTAKE, this.intakeOutput);
		}
	}

	@Override
	protected boolean isDone() {
		return false;
	}

	@Override
	protected SubsystemID[] getSubsystemIDs() {
		return new SubsystemID[] { SubsystemID.INTAKE, SubsystemID.HOPPER };
	}

}
