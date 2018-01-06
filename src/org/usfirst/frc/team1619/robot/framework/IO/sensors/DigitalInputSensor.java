package org.usfirst.frc.team1619.robot.framework.IO.sensors;

import edu.wpi.first.wpilibj.DigitalInput;

public class DigitalInputSensor implements Sensor<Boolean> {

	private DigitalInput sensor;
	private boolean inverted;

	public DigitalInputSensor(int id, boolean inverted) {
		this.sensor = new DigitalInput(id);
		this.inverted = inverted;
	}

	@Override
	public Boolean get() {
		return this.inverted ? !this.sensor.get() : this.sensor.get();
	}

}
