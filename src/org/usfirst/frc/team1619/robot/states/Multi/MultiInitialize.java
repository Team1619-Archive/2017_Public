package org.usfirst.frc.team1619.robot.states.Multi;

import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.SubsystemID;
import org.usfirst.frc.team1619.robot.framework.state.MultiSubsystemSequencerState;
import org.usfirst.frc.team1619.robot.framework.state.MultiSubsystemStateWrapper;
import org.usfirst.frc.team1619.robot.framework.state.SequencerState;
import org.usfirst.frc.team1619.robot.states.Intake.IntakeExtend;
import org.usfirst.frc.team1619.robot.states.Intake.IntakeRetract;
import org.usfirst.frc.team1619.robot.states.TimedState;

public class MultiInitialize extends MultiSubsystemSequencerState {

	public static final MultiSubsystemStateWrapper<MultiInitialize> WRAPPER = new MultiSubsystemStateWrapper<MultiInitialize>(MultiInitialize.class) {

		@Override
		public boolean isReady() {
			return !state.initialized;
		}

	};

	private boolean intakeExtend;

	public MultiInitialize() {
		this(true);
	}

	public MultiInitialize(boolean intakeExtend) {
		this.intakeExtend = intakeExtend;
	}

	@Override
	protected void update() {
		super.update();

		out.solenoids.set(IO.HOPPER_EXTEND_SOLENOID, false);
	}

	@Override
	protected void dispose() {
		state.initialized = true;
	}

	@Override
	protected void addSequence() {
		SequencerState waitThenExtendIntake = new SequencerState() {

			@Override
			protected void addSequence() {
				this.add(new TimedState(250));
				if (intakeExtend) {
					this.add(new IntakeExtend());
				}
				else {
					this.add(new IntakeRetract());
				}
			}

		};

		this.add(waitThenExtendIntake);
		this.add(new MultiZero());
	}

	@Override
	protected SubsystemID[] getSubsystemIDs() {
		return new SubsystemID[] { SubsystemID.INTAKE, SubsystemID.TURRET, SubsystemID.GEAR_HANDLER, SubsystemID.HOPPER_EXTENSION };
	}

}
