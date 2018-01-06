package org.usfirst.frc.team1619.robot.framework.IO.PID;

import org.usfirst.frc.team1619.robot.framework.IO.In;
import org.usfirst.frc.team1619.robot.framework.IO.Out;
import org.usfirst.frc.team1619.robot.framework.IO.sensors.EncoderVelocitySensor;

public class VelocityController {

	private PIDController controller;
	private Out out;
	private In in;
	private int motorID, sensorID;

	private double threshold;
	private long timeThere = -1;

	public VelocityController(PIDController controller, Out out, In in, int motorID, int sensorID, double threshold) {
		assert in.getNumericSensor(sensorID) instanceof EncoderVelocitySensor : "Sensor must be of type EncoderVelocitySensor";

		this.controller = controller;
		this.out = out;
		this.in = in;
		this.motorID = motorID;
		this.sensorID = sensorID;
		this.threshold = threshold;
	}

	public void set(double velocity) {
		this.controller.set(velocity);
	}

	public void update() {
		double currentVelocity = this.in.getNumeric(this.sensorID);
		double error = this.controller.getError(currentVelocity);
		double output = this.controller.get(currentVelocity);
		this.out.motors.set(this.motorID, output);

		if (this.timeThere == -1) {
			if (Math.abs(error) <= this.threshold) {
				this.timeThere = System.currentTimeMillis();
			}
		}
		else if (Math.abs(error) > this.threshold) {
			this.timeThere = -1;
		}
	}

	public double getTimeThere() {
		if (this.timeThere == -1) {
			return -1.0;
		}

		return this.timeThere / 1000.0;
	}

}
