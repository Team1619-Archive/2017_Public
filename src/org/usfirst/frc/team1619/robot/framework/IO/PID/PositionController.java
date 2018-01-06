package org.usfirst.frc.team1619.robot.framework.IO.PID;

import org.usfirst.frc.team1619.robot.framework.IO.In;
import org.usfirst.frc.team1619.robot.framework.IO.Out;
import org.usfirst.frc.team1619.robot.framework.IO.sensors.EncoderPositionSensor;

public class PositionController {

	protected PIDController controller;
	protected Out out;
	protected In in;
	protected int motorID, sensorID;

	protected double threshold;
	protected double timeThereThreshold;
	protected long timeThere = -1;

	public PositionController(PIDController controller, Out out, In in, int motorID, int sensorID, double threshold) {
		this(controller, out, in, motorID, sensorID, threshold, threshold);
	}

	public PositionController(PIDController controller, Out out, In in, int motorID, int sensorID, double threshold, double timeThereThreshold) {
		assert in.getNumericSensor(sensorID) instanceof EncoderPositionSensor : "Sensor must be of type EncoderPositionSensor";

		this.controller = controller;
		this.out = out;
		this.in = in;
		this.motorID = motorID;
		this.sensorID = sensorID;
		this.threshold = threshold;
		this.timeThereThreshold = timeThereThreshold;
	}

	public void setAbsolute(double position) {
		this.timeThere = -1;
		this.controller.set(position);
	}

	public void setRelative(double offset) {
		this.timeThere = -1;
		this.controller.set(this.in.getNumeric(this.sensorID) + offset);
	}

	public void update() {
		double currentPosition = this.in.getNumeric(this.sensorID);
		double error = this.controller.getError(currentPosition);
		boolean inThreshold = Math.abs(error) <= threshold;

		if (inThreshold) {
			this.out.motors.set(this.motorID, 0.0);
			this.controller.resetProfile();

			if (Math.abs(error) <= timeThereThreshold && this.timeThere == -1) {
				this.timeThere = System.currentTimeMillis();
			}
			return;
		}
		else if (this.timeThere != -1) {
			this.timeThere = -1;
		}

		double output = this.controller.get(currentPosition);
		this.out.motors.set(this.motorID, output);
	}
	
	public double getError() {
		double currentPosition = this.in.getNumeric(this.sensorID);
		return this.controller.getError(currentPosition);
	}
	
	public double getTimeThere() {
		if (this.timeThere == -1) {
			return -1.0;
		}

		return (System.currentTimeMillis() - this.timeThere) / 1000.0;
	}

}
