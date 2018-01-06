package org.usfirst.frc.team1619.robot.controllers.LED;

import org.usfirst.frc.team1619.robot.IO;

public abstract class LEDState {

	public abstract void initialize();

	public abstract void update();

	public abstract boolean isDone();

	public abstract void dispose();

	public void set(boolean output) {
		IO.out.relays.set(IO.LED_STRIP, output);
	}

}
