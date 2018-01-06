package org.usfirst.frc.team1619.robot.framework.IO.PID;

import org.usfirst.frc.team1619.robot.framework.IO.In;
import org.usfirst.frc.team1619.robot.framework.IO.Out;

public class BoundedPositionController extends PositionController {

	private double lowerBound, upperBound;
	private int lowerSensorID, upperSensorID;

	public BoundedPositionController(PIDController controller, Out out, In in, int motorID, int sensorID, double threshold, double timeThereThreshold, int lowerSensorID, int upperSensorID, double lowerBound, double upperBound) {
		super(controller, out, in, motorID, sensorID, threshold, timeThereThreshold);

		this.lowerSensorID = lowerSensorID;
		this.upperSensorID = upperSensorID;
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

	@Override
	public void setAbsolute(double position) {
		super.setAbsolute(Math.max(Math.min(position, this.upperBound), this.lowerBound));
	}

	@Override
	public void setRelative(double offset) {
		this.setAbsolute(this.in.getNumeric(this.sensorID) + offset);
	}

	@Override
	public void update() {
		double currentPosition = this.in.getNumeric(this.sensorID);
		double error = this.controller.getError(currentPosition);
		boolean inThreshold = Math.abs(error) <= threshold;

		if (inThreshold) {
			this.out.motors.set(this.motorID, 0.0);
			this.controller.resetProfile();

			if (this.timeThere == -1) {
				this.timeThere = System.currentTimeMillis();
			}
			return;
		}
		else if (this.timeThere != -1) {
			this.timeThere = -1;
		}

		double output = this.controller.get(currentPosition);
		this.out.motors.set(this.motorID, output);
		if (this.lowerSensorID > -1 && this.upperSensorID > -1) {
			this.out.motors.enforceLimit(this.motorID, this.in.getBoolean(this.lowerSensorID), this.in.getBoolean(this.upperSensorID));
		}
	}

}
