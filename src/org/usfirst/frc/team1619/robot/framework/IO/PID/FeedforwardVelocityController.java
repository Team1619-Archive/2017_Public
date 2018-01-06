package org.usfirst.frc.team1619.robot.framework.IO.PID;

import org.usfirst.frc.team1619.robot.framework.IO.In;
import org.usfirst.frc.team1619.robot.framework.IO.Out;
import org.usfirst.frc.team1619.robot.framework.IO.sensors.EncoderVelocitySensor;
import org.usfirst.frc.team1619.robot.framework.util.Point;

public class FeedforwardVelocityController {

	private PIDController controller, headingController;
	private Out out;
	private In in;
	private int motorID, sensorID;
	private double kv, ka;
	private boolean inverted = false;
	private Point point;

	public FeedforwardVelocityController(PIDController controller, Out out, In in, int motorID, int sensorID, double kv, double ka) {
		this(controller, out, in, motorID, sensorID, kv, ka, null);
	}

	public FeedforwardVelocityController(PIDController controller, Out out, In in, int motorID, int sensorID, double kv, double ka, PIDController headingController) {
		assert in.getNumericSensor(sensorID) instanceof EncoderVelocitySensor : "Sensor must be of type EncoderVelocitySensor";

		this.controller = controller;
		this.out = out;
		this.in = in;
		this.motorID = motorID;
		this.sensorID = sensorID;
		this.kv = kv;
		this.ka = ka;
		this.headingController = headingController;

		this.controller.set(0.0);
		this.headingController.set(0.0);
	}

	public void set(Point point) {
		this.point = point;
	}

	public void update(boolean inverted) {
		double output = this.kv * this.point.velocity + this.ka * this.point.acceleration - this.controller.get(this.getError(inverted));
		this.out.motors.set(this.motorID, inverted ? -output : output);
	}

	public void updateWithHeading(double headingError, boolean inverted) {
		double output = this.kv * this.point.velocity + this.ka * this.point.acceleration - this.controller.get(this.getError(inverted)) + this.headingController.get(headingError);
		this.out.motors.set(this.motorID, inverted ? -output : output);
	}

	public double getError(boolean inverted) {
		return this.point.velocity - (inverted ? -this.in.getNumeric(this.sensorID) : this.in.getNumeric(this.sensorID));
	}

}
