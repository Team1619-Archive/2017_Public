package org.usfirst.frc.team1619.robot.framework.IO.sensors;

import edu.wpi.first.wpilibj.XboxController;

public class ControllerAxisSensor implements Sensor<Double> {

	private XboxController controller;
	private int axis;

	public ControllerAxisSensor(XboxController controller, int axis) {
		this.controller = controller;
		this.axis = axis;
	}

	@Override
	public Double get() {
		return this.controller.getRawAxis(this.axis);
	}

}
