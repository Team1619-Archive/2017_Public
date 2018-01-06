package org.usfirst.frc.team1619.robot.states.GearHandler;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.controllers.LED.LEDs;
import org.usfirst.frc.team1619.robot.framework.state.SequencerState;
import org.usfirst.frc.team1619.robot.framework.state.State;
import org.usfirst.frc.team1619.robot.framework.state.StateWrapper;
import org.usfirst.frc.team1619.robot.framework.util.Timer;
import org.usfirst.frc.team1619.robot.framework.util.stabilizers.BooleanStabilizer;
import org.usfirst.frc.team1619.robot.framework.util.stabilizers.Stabilizer;

public class GearHandlerIntake extends SequencerState {

	public static final StateWrapper<GearHandlerIntake> WRAPPER = new StateWrapper<GearHandlerIntake>(GearHandlerIntake.class) {

		@Override
		public boolean isReady() {
			return !state.intakeExtended && in.getBooleanAndDelta(Constants.OPERATOR_BUTTON_GEAR_HANDLER_TOGGLE, true);
		}

	};

	private Stabilizer<Boolean> gearPresenceStabilizer = new BooleanStabilizer(10);
	private LEDs leds = LEDs.getInstance();

	@Override
	protected void initialize() {
		super.initialize();

		state.gearHandlerExtended = true;
	}

	@Override
	protected void update() {
		super.update();

		this.leds.setState(LEDs.LEDStateID.GEAR_HANDLER_INTAKE);
		this.gearPresenceStabilizer.push(in.getBoolean(IO.GEAR_HANDLER_PRESENCE));
	}

	@Override
	protected void dispose() {
		super.dispose();

		this.leds.setState(LEDs.LEDStateID.IDLE);
	}

	@Override
	protected void addSequence() {
		this.add(new GearHandlerSet(Constants.GEAR_HANDLER_PIVOT_INTAKE) {

			private Timer timer = new Timer();

			@Override
			protected void initialize() {
				super.initialize();

				this.timer.start(500);
			}

			@Override
			protected boolean isDone() {
				return super.isDone() || this.timer.isDone();
			}

		});
		this.add(new State() {

			private Timer timer = new Timer();

			@Override
			protected void initialize() {
				this.timer.start(Constants.GEAR_HANDLER_PIVOT_RUN_DOWN_TIME);
			}

			@Override
			protected void update() {
				if (!this.timer.isDone()) {
					out.motors.set(IO.GEAR_HANDLER_PIVOT, Constants.GEAR_HANDLER_PIVOT_RUN_DOWN_OUTPUT);
				}
				else {
					if (in.getNumeric(IO.GEAR_HANDLER_PIVOT_POSITION) < 120.0) {
						out.motors.set(IO.GEAR_HANDLER_PIVOT, Constants.GEAR_HANDLER_PIVOT_RUN_DOWN_OUTPUT);
						out.setXboxControllerRumble(1.0);
					}
					else {
						out.setXboxControllerRumble(0.0);
					}
				}

				out.motors.set(IO.GEAR_HANDLER_INTAKE, Constants.GEAR_HANDLER_INTAKE_OUTPUT);
			}

			@Override
			protected boolean isDone() {
				return gearPresenceStabilizer.get() || in.getBooleanAndDelta(Constants.OPERATOR_BUTTON_GEAR_HANDLER_TOGGLE, true) || in.getBooleanAndDelta(Constants.OPERATOR_BUTTON_INTAKE_TOGGLE, true);
			}

			@Override
			protected void dispose() {
				out.setXboxControllerRumble(0.0);
			}
		});
	}

}
