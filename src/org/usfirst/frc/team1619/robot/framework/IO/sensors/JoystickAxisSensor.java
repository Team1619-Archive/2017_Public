package org.usfirst.frc.team1619.robot.framework.IO.sensors;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickAxisSensor implements Sensor<Double> {

	private Joystick joystick;
	private Joystick.AxisType axisType;
	private boolean inverted;

	public JoystickAxisSensor(Joystick joystick, Joystick.AxisType axisType, boolean inverted) {
		this.joystick = joystick;
		this.axisType = axisType;
		this.inverted = inverted;
	}

	@Override
	public Double get() {
		return (this.inverted ? -1.0 : 1.0) * this.joystick.getAxis(this.axisType);
	}

}
