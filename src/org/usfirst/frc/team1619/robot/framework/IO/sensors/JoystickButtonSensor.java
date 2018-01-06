package org.usfirst.frc.team1619.robot.framework.IO.sensors;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickButtonSensor implements Sensor<Boolean> {

	private Joystick joystick;
	private int id;

	public JoystickButtonSensor(Joystick joystick, int id) {
		this.joystick = joystick;
		this.id = id;
	}

	@Override
	public Boolean get() {
		return this.joystick.getRawButton(this.id);
	}

}
