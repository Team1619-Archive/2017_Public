package org.usfirst.frc.team1619.robot.framework.IO.sensors;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;

public class ControllerButtonSensor implements Sensor<Boolean> {

	public enum ControllerButton {
		A,
		X,
		Y,
		B,
		START,
		BACK,
		LEFT_BUMPER,
		RIGHT_BUMPER,
		LEFT_TRIGGER,
		RIGHT_TRIGGER
	}

	private XboxController controller;
	private ControllerButton id;

	public ControllerButtonSensor(XboxController controller, ControllerButton id) {
		this.controller = controller;
		this.id = id;
	}

	@Override
	public Boolean get() {
		switch (this.id) {
			case A:
				return this.controller.getAButton();
			case X:
				return this.controller.getXButton();
			case Y:
				return this.controller.getYButton();
			case B:
				return this.controller.getBButton();
			case START:
				return this.controller.getStartButton();
			case BACK:
				return this.controller.getBackButton();
			case LEFT_BUMPER:
				return this.controller.getBumper(GenericHID.Hand.kLeft);
			case RIGHT_BUMPER:
				return this.controller.getBumper(GenericHID.Hand.kRight);
			case LEFT_TRIGGER:
				return this.controller.getTriggerAxis(GenericHID.Hand.kLeft) > 0.25;
			case RIGHT_TRIGGER:
				return this.controller.getTriggerAxis(GenericHID.Hand.kRight) > 0.25;
			default:
				return false;
		}
	}

}
