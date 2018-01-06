package org.usfirst.frc.team1619.robot.framework.IO;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;

public class Out {

	public Motors motors = new Motors();
	public Relays relays = new Relays();
	public Solenoids solenoids;
	private XboxController xboxController;

	public Out(int compressorID) {
		this.solenoids = new Solenoids(compressorID);
	}

	public void update() {
		this.motors.update();
		this.relays.update();
		this.solenoids.update();
	}

	public void disable() {
		this.motors.disable();
		this.relays.disable();
		this.solenoids.disable();
	}

	public void linkXboxController(XboxController xboxController) {
		this.xboxController = xboxController;
	}

	public void setXboxControllerRumble(double value) {
		this.xboxController.setRumble(GenericHID.RumbleType.kLeftRumble, value);
		this.xboxController.setRumble(GenericHID.RumbleType.kRightRumble, value);
	}

}
