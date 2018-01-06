package org.usfirst.frc.team1619.robot.states.GearHandler;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.controllers.LED.LEDs;
import org.usfirst.frc.team1619.robot.framework.state.SequencerState;
import org.usfirst.frc.team1619.robot.framework.state.StateWrapper;
import org.usfirst.frc.team1619.robot.framework.util.Timer;

public class GearHandlerPlacement extends SequencerState {

	public static final StateWrapper<GearHandlerPlacement> WRAPPER = new StateWrapper<GearHandlerPlacement>(GearHandlerPlacement.class) {

		@Override
		public boolean isReady() {
			return !state.intakeExtended && in.getBooleanAndDelta(Constants.OPERATOR_BUTTON_GEAR_HANDLER_TOGGLE_PLACEMENT, true);
		}

	};

	private LEDs leds = LEDs.getInstance();

	@Override
	protected void initialize() {
		super.initialize();

		state.gearHandlerExtended = true;
	}

	@Override
	protected void update() {
		super.update();

		this.leds.setState(LEDs.LEDStateID.GEAR_HANDLER_PLACEMENT);
	}

	@Override
	protected boolean isDone() {
		return super.isDone() || in.getBooleanAndDelta(Constants.OPERATOR_BUTTON_GEAR_HANDLER_TOGGLE, false) || in.getBooleanAndDelta(Constants.OPERATOR_BUTTON_INTAKE_TOGGLE, true);
	}

	@Override
	protected void dispose() {
		super.dispose();

		this.leds.setState(LEDs.LEDStateID.IDLE);
	}

	@Override
	protected void addSequence() {
		this.add(new GearHandlerSet(Constants.GEAR_HANDLER_PIVOT_PLACEMENT) {

			private Timer timer = new Timer();

			@Override
			protected void initialize() {
				super.initialize();

				this.timer.start(100);
			}

			@Override
			protected void update() {
				double axis = -in.getNumeric(IO.OPERATOR_LEFT_AXIS_Y);
				if (Math.abs(axis) > 0.2) {
					this.setRelative(axis);
				}

				super.update();
			}

			@Override
			protected boolean isDone() {
				return this.timer.isDone() && in.getBooleanAndDelta(Constants.OPERATOR_BUTTON_GEAR_HANDLER_TOGGLE_PLACEMENT, true);
			}

		});
		this.add(new GearHandlerPlace());
	}

}
