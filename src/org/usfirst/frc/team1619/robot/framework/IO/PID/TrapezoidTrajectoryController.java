package org.usfirst.frc.team1619.robot.framework.IO.PID;

import org.usfirst.frc.team1619.robot.framework.IO.In;
import org.usfirst.frc.team1619.robot.framework.IO.sensors.EncoderPositionSensor;
import org.usfirst.frc.team1619.robot.framework.IO.sensors.NavxHeadingSensor;
import org.usfirst.frc.team1619.robot.framework.util.Point;
import org.usfirst.frc.team1619.robot.framework.util.TrapezoidTrajectory;

public class TrapezoidTrajectoryController {

	private TrapezoidTrajectory trajectory;
	private FollowerController leftFollowerController, rightFollowerController;
	private FeedforwardVelocityController leftVelocityController,
			rightVelocityController;
	private EncoderPositionSensor leftEncoderPosition, rightEncoderPosition;
	private NavxHeadingSensor heading;
	private boolean done;

	public TrapezoidTrajectoryController(FollowerController leftFollowerController, FollowerController rightFollowerController, FeedforwardVelocityController leftVelocityController, FeedforwardVelocityController rightVelocityController, In in, int leftSensorID, int rightSensorID, int headingSensorID) {
		this.leftFollowerController = leftFollowerController;
		this.rightFollowerController = rightFollowerController;
		this.leftVelocityController = leftVelocityController;
		this.rightVelocityController = rightVelocityController;
		this.leftEncoderPosition = (EncoderPositionSensor) in.getNumericSensor(leftSensorID);
		this.rightEncoderPosition = (EncoderPositionSensor) in.getNumericSensor(rightSensorID);
		this.heading = (NavxHeadingSensor) in.getNumericSensor(headingSensorID);
	}

	public void initialize(TrapezoidTrajectory trajectory) {
		this.done = false;

		this.trajectory = trajectory;
		this.leftFollowerController.initialize();
		this.rightFollowerController.initialize();
		this.leftEncoderPosition.zero();
		this.rightEncoderPosition.zero();
		this.heading.zero();
	}

	public void update() {
		this.update(false);
	}

	public void update(boolean gyroCorrect) {
		double distance = Math.max((Math.abs(this.leftEncoderPosition.get()) + Math.abs(this.rightEncoderPosition.get())) / 2.0, 0.05);
		if (!this.done && Math.abs(distance - this.trajectory.getDistance()) < 0.75 / 12.0) {
			this.done = true;
		}

		Point point = this.trajectory.getPoint(distance);

		this.leftVelocityController.set(point);
		this.rightVelocityController.set(point);

		double headingError = -this.heading.get();
		boolean inverted = this.trajectory.isInverted();
		if (gyroCorrect) {
			this.leftVelocityController.updateWithHeading(headingError, inverted);
			this.rightVelocityController.updateWithHeading(headingError, inverted);
		}
		else {
			this.leftVelocityController.update(inverted);
			this.rightVelocityController.update(inverted);
		}

		this.leftFollowerController.update();
		this.rightFollowerController.update();
	}

	public boolean isDone() {
		return this.done;
	}

	public void dispose() {
		this.leftFollowerController.dispose();
		this.rightFollowerController.dispose();
	}

}
