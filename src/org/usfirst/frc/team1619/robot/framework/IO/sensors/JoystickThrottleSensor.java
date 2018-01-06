package org.usfirst.frc.team1619.robot.framework.IO.sensors;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickThrottleSensor implements Sensor<Double> {

	private Joystick joystick;

	public JoystickThrottleSensor(Joystick joystick) {
		this.joystick = joystick;
	}

	@Override
	public Double get() {
		return this.joystick.getThrottle();
	}

}
