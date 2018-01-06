package org.usfirst.frc.team1619.robot.controllers.LED;

import org.usfirst.frc.team1619.robot.IO;

public class LEDs {

	public enum LEDStateID {
		IDLE,
		GEAR_HANDLER_INTAKE,
		GEAR_HANDLER_PLACEMENT,
		CLIMB
	}

	private static final LEDs instance;

	static {
		instance = new LEDs();
	}

	public static LEDs getInstance() {
		return instance;
	}

	private LEDState activeState = null;
	private LEDStateID activeStateID = LEDStateID.IDLE;

	public void setState(LEDStateID stateID) {
		if (stateID == LEDStateID.IDLE || stateID.compareTo(this.activeStateID) > 0) {
			if (this.activeState != null) {
				this.activeState.dispose();
			}

			switch (stateID) {
				case IDLE:
					this.activeState = null;
					break;
				case GEAR_HANDLER_INTAKE:
					this.activeState = new LEDGearIntake();
					break;
				case GEAR_HANDLER_PLACEMENT:
					this.activeState = new LEDGearPlacement();
					break;
				case CLIMB:
					this.activeState = new LEDClimb();
					break;
			}
			if (this.activeState != null) {
				this.activeState.initialize();
			}
			this.activeStateID = stateID;
		}
	}

	public void update() {
		if (this.activeStateID != LEDStateID.IDLE && this.activeState.isDone()) {
			this.activeState.dispose();
			this.activeState = null;
			this.activeStateID = LEDStateID.IDLE;
		}

		if (this.activeStateID == LEDStateID.IDLE) {
			IO.out.relays.set(IO.LED_STRIP, true);
		}
		else {
			this.activeState.update();
		}
	}

}
