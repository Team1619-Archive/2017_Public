package org.usfirst.frc.team1619.robot.framework.IO.sensors;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickButtonSensorFactory {

	public static JoystickButtonSensor[] getButtonSensors(Joystick joystick, int[] id) {
		JoystickButtonSensor[] buttonSensors = new JoystickButtonSensor[id.length + 1];
		for (int i = 1; i < buttonSensors.length; i++) {
			buttonSensors[i] = new JoystickButtonSensor(joystick, id[i]);
		}

		return buttonSensors;
	}

	public static JoystickButtonSensor[] getButtonSensors(Joystick joystick, int max) {
		JoystickButtonSensor[] buttonSensors = new JoystickButtonSensor[max + 1];
		for (int i = 1; i <= max; i++) {
			buttonSensors[i] = new JoystickButtonSensor(joystick, i);
		}

		return buttonSensors;
	}
}
